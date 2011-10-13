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

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.util.SocialActivitiesUtil;

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

		SocialActivitiesUtil.readXml(document, event, _vars);

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
					SocialActivitiesUtil.getSocialActivityDefinition(
						pathElements[1],
						GetterUtil.getInteger(pathElements[2]));

				if (activityDefinition != null) {
					Object obj = _vars.get(path);

					if (obj instanceof SocialActivityCounterDefinition) {
						activityDefinition.getCounters().remove(
							(SocialActivityCounterDefinition)obj);
					} else if (obj instanceof SocialAchievement) {
						activityDefinition.getAchievements().remove(
							(SocialAchievement)obj);
					} else if (obj instanceof SocialActivityDefinition) {
						SocialActivitiesUtil.removeSocialActivityDefinition(
							activityDefinition);
					}
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

	private static String _LIFERAY_SOCIAL_XML = "/WEB-INF/liferay-social.xml";

	private static Log _log = LogFactoryUtil.getLog(
			SocialHotDeployListener.class);

	private static Map<String, Object> _vars =
		new HashMap<String, Object>();

}