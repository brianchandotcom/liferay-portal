/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityFinderUtil {
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupId(groupId);
	}

	public static int countByGroupUsers(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupUsers(groupId);
	}

	public static int countByOrganizationId(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByOrganizationId(organizationId);
	}

	public static int countByOrganizationUsers(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByOrganizationUsers(organizationId);
	}

	public static int countByRelation(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByRelation(userId);
	}

	public static int countByRelationType(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByRelationType(userId, type);
	}

	public static int countByUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUserGroups(userId);
	}

	public static int countByUserGroupsAndOrganizations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUserGroupsAndOrganizations(userId);
	}

	public static int countByUserOrganizations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUserOrganizations(userId);
	}

	public static int countU_ByActivitySetId(long activitySetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countU_ByActivitySetId(activitySetId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByGroupUsers(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByOrganizationUsers(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelation(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByRelation(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelationType(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByRelationType(userId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroupsAndOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserGroupsAndOrganizations(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserOrganizations(userId, start, end);
	}

	public static SocialActivityFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityFinder)PortalBeanLocatorUtil.locate(SocialActivityFinder.class.getName());

			ReferenceRegistry.registerReference(SocialActivityFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SocialActivityFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SocialActivityFinderUtil.class,
			"_finder");
	}

	private static SocialActivityFinder _finder;
}