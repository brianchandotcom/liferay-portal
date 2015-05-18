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

package com.liferay.portlet.social.service.impl.bundle.socialactivityinterpreterlocalserviceimpl;

import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivitySet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=SocialActivityInterpreterLocalServiceImplTest",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestSocialActivityInterpreter
	implements SocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		String[] classNames = new String[1];

		classNames[0] = TestSocialActivityInterpreter.class.getName();

		return classNames;
	}

	@Override
	public String getSelector() {
		return "SocialActivityInterpretorLocalServiceImplTest";
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, SocialActivity activity,
		String actionId, ServiceContext serviceContext) {

		return false;
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ServiceContext serviceContext) {

		return null;
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivitySet activitySet, ServiceContext serviceContext) {

		return null;
	}

	@Override
	public void updateActivitySet(long activityId) {
		return;
	}

}