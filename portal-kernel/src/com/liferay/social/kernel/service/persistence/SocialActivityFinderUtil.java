/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityFinderUtil {

	public static int countByGroupId(long groupId) {
		return getFinder().countByGroupId(groupId);
	}

	public static int countByGroupUsers(long groupId) {
		return getFinder().countByGroupUsers(groupId);
	}

	public static int countByOrganizationId(long organizationId) {
		return getFinder().countByOrganizationId(organizationId);
	}

	public static int countByOrganizationUsers(long organizationId) {
		return getFinder().countByOrganizationUsers(organizationId);
	}

	public static int countByRelation(long userId) {
		return getFinder().countByRelation(userId);
	}

	public static int countByRelationType(long userId, int type) {
		return getFinder().countByRelationType(userId, type);
	}

	public static int countByUserGroups(long userId) {
		return getFinder().countByUserGroups(userId);
	}

	public static int countByUserGroupsAndOrganizations(long userId) {
		return getFinder().countByUserGroupsAndOrganizations(userId);
	}

	public static int countByUserOrganizations(long userId) {
		return getFinder().countByUserOrganizations(userId);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByGroupId(long groupId, int start, int end) {

		return getFinder().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByGroupUsers(long groupId, int start, int end) {

		return getFinder().findByGroupUsers(groupId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByOrganizationId(long organizationId, int start, int end) {

		return getFinder().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByOrganizationUsers(long organizationId, int start, int end) {

		return getFinder().findByOrganizationUsers(organizationId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByRelation(long userId, int start, int end) {

		return getFinder().findByRelation(userId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByRelationType(long userId, int type, int start, int end) {

		return getFinder().findByRelationType(userId, type, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByUserGroups(long userId, int start, int end) {

		return getFinder().findByUserGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByUserGroupsAndOrganizations(long userId, int start, int end) {

		return getFinder().findByUserGroupsAndOrganizations(userId, start, end);
	}

	public static java.util.List<com.liferay.social.kernel.model.SocialActivity>
		findByUserOrganizations(long userId, int start, int end) {

		return getFinder().findByUserOrganizations(userId, start, end);
	}

	public static SocialActivityFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityFinder)PortalBeanLocatorUtil.locate(
				SocialActivityFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(SocialActivityFinder finder) {
		_finder = finder;
	}

	private static SocialActivityFinder _finder;

}