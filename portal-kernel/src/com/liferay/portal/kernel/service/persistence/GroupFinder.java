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

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface GroupFinder {
	public int countByLayouts(long companyId, long parentGroupId, boolean site);

	public int countByLayouts(long companyId, long parentGroupId, boolean site,
		Boolean active);

	public int countByG_U(long groupId, long userId, boolean inherit);

	public int countByC_C_PG_N_D(long companyId, long[] classNameIds,
		long parentGroupId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public java.util.List<Long> findByActiveGroupIds(long userId);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByCompanyId(
		long companyId, java.util.LinkedHashMap<String, Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, Boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByLiveGroups();

	public java.util.List<com.liferay.portal.kernel.model.Group> findByNoLayouts(
		long classNameId, boolean privateLayout, int start, int end);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByNullFriendlyURL();

	public java.util.List<com.liferay.portal.kernel.model.Group> findBySystem(
		long companyId);

	public java.util.List<Long> findByC_P(long companyId, long parentGroupId,
		long previousGroupId, int size);

	public com.liferay.portal.kernel.model.Group findByC_GK(long companyId,
		String groupKey)
		throws com.liferay.portal.kernel.exception.NoSuchGroupException;

	public java.util.List<com.liferay.portal.kernel.model.Group> findByL_TS_S_RSGC(
		long liveGroupId, String typeSettings, boolean site,
		int remoteStagingGroupCount);

	public java.util.List<com.liferay.portal.kernel.model.Group> findByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId,
		String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Group> obc);
}