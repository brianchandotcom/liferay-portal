/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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