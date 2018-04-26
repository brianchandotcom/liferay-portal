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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class GroupFinderUtil {
	public static int countByLayouts(long companyId, long parentGroupId,
		boolean site) {
		return getFinder().countByLayouts(companyId, parentGroupId, site);
	}

	public static int countByLayouts(long companyId, long parentGroupId,
		boolean site, Boolean active) {
		return getFinder().countByLayouts(companyId, parentGroupId, site, active);
	}

	public static int countByG_U(long groupId, long userId, boolean inherit) {
		return getFinder().countByG_U(groupId, userId, inherit);
	}

	public static int countByC_C_PG_N_D(long companyId, long[] classNameIds,
		long parentGroupId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countByC_C_PG_N_D(companyId, classNameIds, parentGroupId,
			names, descriptions, params, andOperator);
	}

	public static java.util.List<Long> findByActiveGroupIds(long userId) {
		return getFinder().findByActiveGroupIds(userId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByCompanyId(
		long companyId, java.util.LinkedHashMap<String, Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc) {
		return getFinder().findByCompanyId(companyId, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, Boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc) {
		return getFinder()
				   .findByLayouts(companyId, parentGroupId, site, active,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end) {
		return getFinder()
				   .findByLayouts(companyId, parentGroupId, site, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc) {
		return getFinder()
				   .findByLayouts(companyId, parentGroupId, site, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByLiveGroups() {
		return getFinder().findByLiveGroups();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByNoLayouts(
		long classNameId, boolean privateLayout, int start, int end) {
		return getFinder()
				   .findByNoLayouts(classNameId, privateLayout, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByNullFriendlyURL() {
		return getFinder().findByNullFriendlyURL();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findBySystem(
		long companyId) {
		return getFinder().findBySystem(companyId);
	}

	public static java.util.List<Long> findByC_P(long companyId,
		long parentGroupId, long previousGroupId, int size) {
		return getFinder()
				   .findByC_P(companyId, parentGroupId, previousGroupId, size);
	}

	public static com.liferay.portal.kernel.model.Group findByC_GK(
		long companyId, String groupKey)
		throws com.liferay.portal.kernel.exception.NoSuchGroupException {
		return getFinder().findByC_GK(companyId, groupKey);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByL_TS_S_RSGC(
		long liveGroupId, String typeSettings, boolean site,
		int remoteStagingGroupCount) {
		return getFinder()
				   .findByL_TS_S_RSGC(liveGroupId, typeSettings, site,
			remoteStagingGroupCount);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> findByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId,
		String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc) {
		return getFinder()
				   .findByC_C_PG_N_D(companyId, classNameIds, parentGroupId,
			names, descriptions, params, andOperator, start, end, obc);
	}

	public static GroupFinder getFinder() {
		if (_finder == null) {
			_finder = (GroupFinder)PortalBeanLocatorUtil.locate(GroupFinder.class.getName());

			ReferenceRegistry.registerReference(GroupFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(GroupFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(GroupFinderUtil.class, "_finder");
	}

	private static GroupFinder _finder;
}