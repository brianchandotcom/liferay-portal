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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivitySet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityInterpreterImpl
	implements SocialActivityInterpreter {

	public SocialActivityInterpreterImpl(
		String portletId, SocialActivityInterpreter activityInterpreter) {

		_portletId = portletId;
		_activityInterpreter = activityInterpreter;

		String[] classNames = _activityInterpreter.getClassNames();

		for (String className : classNames) {
			_classNames.add(className);
		}
	}

	@Override
	public String[] getClassNames() {
		return _activityInterpreter.getClassNames();
	}

	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getSelector() {
		return _activityInterpreter.getSelector();
	}

	public boolean hasClassName(String className) {
		if (_classNames.contains(className)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return _activityInterpreter.hasPermission(
			permissionChecker, activity, actionId, serviceContext);
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ServiceContext serviceContext) {

		return _activityInterpreter.interpret(activity, serviceContext);
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivitySet activitySet, ServiceContext serviceContext) {

		return _activityInterpreter.interpret(activitySet, serviceContext);
	}

	@Override
	public void updateActivitySet(long activityId) throws PortalException {
		_activityInterpreter.updateActivitySet(activityId);
	}

	private final SocialActivityInterpreter _activityInterpreter;
	private final Set<String> _classNames = new HashSet<String>();
	private final String _portletId;

}