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

package com.liferay.portal.service.persistence;

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

	public static int countByG_U(long groupId, long userId, boolean inherit) {
		return getFinder().countByG_U(groupId, userId, inherit);
	}

	public static int countByC_C_PG_N_D(long companyId, long[] classNameIds,
		long parentGroupId, java.lang.String[] names,
		java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getFinder()
				   .countByC_C_PG_N_D(companyId, classNameIds, parentGroupId,
			names, descriptions, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end) {
		return getFinder()
				   .findByLayouts(companyId, parentGroupId, site, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc) {
		return getFinder()
				   .findByLayouts(companyId, parentGroupId, site, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByLiveGroups() {
		return getFinder().findByLiveGroups();
	}

	public static java.util.List<com.liferay.portal.model.Group> findByNoLayouts(
		long classNameId, int start, int end) {
		return getFinder().findByNoLayouts(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByNullFriendlyURL() {
		return getFinder().findByNullFriendlyURL();
	}

	public static java.util.List<com.liferay.portal.model.Group> findBySystem(
		long companyId) {
		return getFinder().findBySystem(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByCompanyId(
		long companyId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc) {
		return getFinder().findByCompanyId(companyId, params, start, end, obc);
	}

	public static java.util.List<java.lang.Long> findByC_P(long companyId,
		long parentGroupId, long previousGroupId, int size) {
		return getFinder()
				   .findByC_P(companyId, parentGroupId, previousGroupId, size);
	}

	public static com.liferay.portal.model.Group findByC_GK(long companyId,
		java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getFinder().findByC_GK(companyId, groupKey);
	}

	public static java.util.List<com.liferay.portal.model.Group> findByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc) {
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