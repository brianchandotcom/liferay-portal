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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialRelationConstants;
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
		Group group, Layout layout, Scope scope, int start, int end) {

		if (scope == Scope.ALL) {
			if (!group.isUser()) {
				return _socialActivityLocalService.getGroupActivities(
					group.getGroupId(), start, end);
			}

			return _socialActivityLocalService.getUserActivities(
				group.getClassPK(), start, end);
		}
		else if (group.isOrganization()) {
			return _socialActivityLocalService.getOrganizationActivities(
				group.getOrganizationId(), start, end);
		}
		else if (!group.isUser()) {
			return _socialActivityLocalService.getGroupActivities(
				group.getGroupId(), start, end);
		}
		else if (layout.isPublicLayout() || (scope == Scope.ME)) {
			return _socialActivityLocalService.getUserActivities(
				group.getClassPK(), start, end);
		}
		else if (scope == Scope.CONNECTIONS) {
			return _socialActivityLocalService.getRelationActivities(
				group.getClassPK(), SocialRelationConstants.TYPE_BI_CONNECTION,
				start, end);
		}
		else if (scope == Scope.FOLLOWING) {
			return _socialActivityLocalService.getRelationActivities(
				group.getClassPK(), SocialRelationConstants.TYPE_UNI_FOLLOWER,
				start, end);
		}
		else if (scope == Scope.MY_SITES) {
			return _socialActivityLocalService.getUserGroupsActivities(
				group.getClassPK(), start, end);
		}
		else {
			return Collections.emptyList();
		}
	}

	public enum Scope {

		ALL("all"), CONNECTIONS("connections"), FOLLOWING("following"),
		ME("me"), MY_SITES("my-sites");

		Scope(String value) {
			_value = value;
		}

		public static Scope fromValue(String value) {
			for (Scope scope : values()) {
				if (value.equals(scope.getValue())) {
					return scope;
				}
			}

			throw new IllegalArgumentException(value);
		}

		public String getValue() {
			return _value;
		}

		private final String _value;

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