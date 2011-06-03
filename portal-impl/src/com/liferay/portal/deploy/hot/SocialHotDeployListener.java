/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityDefinition;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Zsolt Berentey
 */
public class SocialHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error registering social plugin for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering social plugin for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(servletContext.getResource(
			_LIFERAY_SOCIAL_XML));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering social plugins for " + servletContextName);
		}

		InputStream inputStream = servletContext.getResourceAsStream(
			_LIFERAY_SOCIAL_XML);

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot load " + _LIFERAY_SOCIAL_XML);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + _LIFERAY_SOCIAL_XML);
		}

		Document document = SAXReaderUtil.read(inputStream, true);

		_readXML(servletContextName, document, event.getContextClassLoader());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Social plugins for " + servletContextName +
					" are available for use");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		for (Iterator<String> it = _vars.keySet().iterator(); it.hasNext(); ) {
			String path = it.next();

			if (path.startsWith(servletContextName)) {
				String[] pathElements = StringUtil.split(path, "/");

				SocialActivityDefinition activityDefinition =
					ResourceActionsUtil.getSocialActivityDefinition(
						pathElements[1],
						GetterUtil.getInteger(pathElements[2]));

				Object obj = _vars.get(path);

				if (obj instanceof SocialActivityCounter) {
					activityDefinition.getCounters().remove(
						(SocialActivityCounter)obj);
				} else if (obj instanceof SocialAchievement) {
					activityDefinition.getAchievements().remove(
						(SocialAchievement)obj);
				}

				it.remove();
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Social plugins for " + servletContextName +
					" was unregistered");
		}
	}

	@SuppressWarnings("rawtypes")
	private void _readAchievement(
		String contextName,	Element achievementElement,
		SocialActivityDefinition activityDefinition, ClassLoader classLoader) {

		// Achievement

		Element achievementClassElement = achievementElement.element(
			"achievement-class");

		if (achievementClassElement == null ||
				achievementClassElement.getTextTrim().equals("")) {

			return;
		}

		SocialAchievement achievement = null;

		try {
			Class clazz = classLoader.loadClass(
				achievementClassElement.getTextTrim());

			achievement = (SocialAchievement)clazz.newInstance();

		} catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot instantiate achievement class " +
					achievementClassElement.getTextTrim(), e);
			}

			return;
		}

		achievement.setName(achievementElement.element("name").getTextTrim());
		achievement.setIcon(achievementElement.element("icon").getTextTrim());

		// Properties

		for (Element property : achievementElement.elements("property")) {
			_readAchievementProperty(property, achievement);
		}

		activityDefinition.getAchievements().add(achievement);

		String path = contextName + StringPool.SLASH +
			activityDefinition.getModelName() + StringPool.SLASH +
			activityDefinition.getActivityKey() + _ACHIEVEMENT_ +
			achievement.getName();

		_vars.put(path, achievement);
	}

	private void _readAchievementProperty(
		Element property, SocialAchievement achievement) {

		String name = property.element("name").getTextTrim();
		String value = property.element("value").getTextTrim();

		BeanPropertiesUtil.setProperty(achievement, name, value);
	}

	private void _readActivity(
		String contextName, Element activityElement, ClassLoader classLoader) {

		// Activity

		Element modelRefElement = activityElement.element("model-ref");

		if (modelRefElement == null) {
			return;
		}

		String modelName = modelRefElement.getTextTrim();

		Element activityIdElement =
			activityElement.element("activity-key");

		if (activityIdElement == null) {
			return;
		}

		int activityKey = GetterUtil.getInteger(
			activityIdElement.getTextTrim(), -1);

		if (activityKey < 0) {
			return;
		}

		SocialActivityDefinition socialActivityDefinition =
			ResourceActionsUtil.getSocialActivityDefinition(
				modelName, activityKey);

		if (socialActivityDefinition == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot find activity " + modelName + "/" +
					activityKey);
			}

			return;
		}

		// Counters

		for (Element counterElement :
				activityElement.elements("counter")) {

			_readCounter(contextName, counterElement, socialActivityDefinition);
		}

		// Achievements

		for (Element achievementElement :
			activityElement.elements("achievement")) {

			_readAchievement(
				contextName, achievementElement, socialActivityDefinition,
				classLoader);
		}
	}

	private void _readCounter(
		String contextName, Element counterElement,
		SocialActivityDefinition activityDefinition) {

		String name = counterElement.elementText("name");

		String ownerType = counterElement.elementText("owner");

		int increment = GetterUtil.getInteger(
				counterElement.elementText("increment"), 1);

		SocialActivityCounter counter = new SocialActivityCounter();

		counter.setName(name);
		counter.setOwnerType(ownerType);
		counter.setIncrement(increment);

		if (counter.getOwnerType() == 0) {
			_log.warn(
				"Unknown owner type '"+ ownerType +
				"' in social activity configuration for model '" +
				activityDefinition.getModelName() + "'");

			return;
		}

		activityDefinition.addCounter(counter);

		String path = contextName + StringPool.SLASH +
			activityDefinition.getModelName() + StringPool.SLASH +
			activityDefinition.getActivityKey() + _COUNTER_ + counter.getKey();

		_vars.put(path, counter);
	}

	private void _readXML(
		String contextName, Document document, ClassLoader classLoader) {

		Element rootElement = document.getRootElement();

		for (Element activityElement :	rootElement.elements("activity")) {
			_readActivity(contextName, activityElement, classLoader);
		}
	}

	private static String _ACHIEVEMENT_ = "/achievement/";

	private static String _COUNTER_ = "/counter/";

	private static String _LIFERAY_SOCIAL_XML = "/WEB-INF/liferay-social.xml";

	private static Log _log = LogFactoryUtil.getLog(
			SocialHotDeployListener.class);

	private static Map<String, Object> _vars =
		new HashMap<String, Object>();

}