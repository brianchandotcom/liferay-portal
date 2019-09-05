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

package com.liferay.social.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySetFinderUtil {

	public static int countByOrganizationId(long organizationId) {
		return getFinder().countByOrganizationId(organizationId);
	}

	public static int countByRelation(long userId) {
		return getFinder().countByRelation(userId);
	}

	public static int countByRelationType(long userId, int type) {
		return getFinder().countByRelationType(userId, type);
	}

	public static int countByUser(long userId) {
		return getFinder().countByUser(userId);
	}

	public static int countByUserGroups(long userId) {
		return getFinder().countByUserGroups(userId);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySet>
			findByOrganizationId(long organizationId, int start, int end) {

		return getFinder().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySet> findByRelation(
			long userId, int start, int end) {

		return getFinder().findByRelation(userId, start, end);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySet> findByRelationType(
			long userId, int type, int start, int end) {

		return getFinder().findByRelationType(userId, type, start, end);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySet> findByUser(
			long userId, int start, int end) {

		return getFinder().findByUser(userId, start, end);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySet> findByUserGroups(
			long userId, int start, int end) {

		return getFinder().findByUserGroups(userId, start, end);
	}

	public static SocialActivitySetFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivitySetFinder)PortalBeanLocatorUtil.locate(
				SocialActivitySetFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(SocialActivitySetFinder finder) {
		_finder = finder;
	}

	private static SocialActivitySetFinder _finder;

}