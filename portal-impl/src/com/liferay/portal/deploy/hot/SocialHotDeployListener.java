/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Zsolt Berentey
 */
public class SocialHotDeployListener extends BaseHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering social for ", t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering social for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-social.xml"))
		};

		if (xmls[0] == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering social for " + servletContextName);
		}

		List<Object> objects = SocialConfigurationUtil.read(
			hotDeployEvent.getContextClassLoader(), xmls);

		_objects.put(servletContextName, objects);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Social for " + servletContextName + " is available for use");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		List<Object> objects = (List<Object>)_objects.get(servletContextName);

		if (objects == null) {
			return;
		}

		for (Object object : objects) {
			if (object instanceof SocialActivityDefinition) {
				SocialActivityDefinition activityDefinition =
					(SocialActivityDefinition)object;

				SocialConfigurationUtil.removeActivityDefinition(
					activityDefinition);

				continue;
			}

			Tuple tuple = (Tuple)object;

			SocialActivityDefinition activityDefinition =
				(SocialActivityDefinition)tuple.getObject(0);

			Object tupleObject1 = tuple.getObject(1);

			if (tupleObject1 instanceof SocialAchievement) {
				List<SocialAchievement> achievements =
					activityDefinition.getAchievements();

				achievements.remove(tupleObject1);
			}
			else if (tupleObject1 instanceof SocialActivityCounterDefinition) {
				Collection<SocialActivityCounterDefinition>
					activityCounterDefinitions =
						activityDefinition.getActivityCounterDefinitions();

				activityCounterDefinitions.remove(tupleObject1);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Social for " + servletContextName + " was unregistered");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialHotDeployListener.class);

	private static final Map<String, Object> _objects =
		new HashMap<String, Object>();

}