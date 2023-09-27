/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivitySet;

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

		String[] classNames = activityInterpreter.getClassNames();

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

		return false;
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
	private final Set<String> _classNames = new HashSet<>();
	private final String _portletId;

}