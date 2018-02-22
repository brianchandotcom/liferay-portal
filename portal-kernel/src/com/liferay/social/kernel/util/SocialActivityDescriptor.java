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

package com.liferay.social.kernel.util;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.service.SocialActivityInterpreterLocalServiceUtil;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityDescriptor {

	public SocialActivityDescriptor(SocialActivity activity) {
		_activity = activity;

		_activitySet = null;
	}

	public SocialActivityDescriptor(SocialActivitySet activitySet) {
		_activitySet = activitySet;

		_activity = null;
	}

	public long getCreateDate() {
		if (_activity != null) {
			return _activity.getCreateDate();
		}

		return _activitySet.getCreateDate();
	}

	public long getUserId() {
		if (_activity != null) {
			return _activity.getUserId();
		}

		return _activitySet.getUserId();
	}

	public SocialActivityFeedEntry interpret(
		String selector, ServiceContext serviceContext) {

		if (_activity != null) {
			return SocialActivityInterpreterLocalServiceUtil.interpret(
				selector, _activity, serviceContext);
		}

		return SocialActivityInterpreterLocalServiceUtil.interpret(
			selector, _activitySet, serviceContext);
	}

	private final SocialActivity _activity;
	private final SocialActivitySet _activitySet;

}