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

package com.liferay.social.activities.web.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = SocialActivityQueryHelper.class)
public class SocialActivityQueryHelper {

	public List<SocialActivity> getSocialActivities(
			Group group, int start, int end)
		throws Exception {

		if (group.isOrganization()) {
			return _socialActivityLocalService.getOrganizationActivities(
				group.getOrganizationId(), start, end);
		}
		else if (group.isRegularSite()) {
			return _socialActivityLocalService.getGroupActivities(
				group.getGroupId(), start, end);
		}
		else if (group.isUser()) {
			return _socialActivityLocalService.getUserActivities(
				group.getClassPK(), start, end);
		}

		return Collections.emptyList();
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {

		_socialActivityLocalService = socialActivityLocalService;
	}

	private GroupLocalService _groupLocalService;
	private SocialActivityLocalService _socialActivityLocalService;

}