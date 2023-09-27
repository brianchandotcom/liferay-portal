/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamFinderUtil {

	public static int countByG_N_D(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().countByG_N_D(groupId, name, description, params);
	}

	public static int filterCountByG_N_D(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().filterCountByG_N_D(
			groupId, name, description, params);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
		filterFindByG_N_D(
			long groupId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Team> orderByComparator) {

		return getFinder().filterFindByG_N_D(
			groupId, name, description, params, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
		findByG_U(
			long groupId, long userId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Team> orderByComparator) {

		return getFinder().findByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Team>
		findByG_N_D(
			long groupId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Team> orderByComparator) {

		return getFinder().findByG_N_D(
			groupId, name, description, params, start, end, orderByComparator);
	}

	public static TeamFinder getFinder() {
		if (_finder == null) {
			_finder = (TeamFinder)PortalBeanLocatorUtil.locate(
				TeamFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(TeamFinder finder) {
		_finder = finder;
	}

	private static TeamFinder _finder;

}