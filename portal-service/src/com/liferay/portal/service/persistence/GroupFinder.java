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

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface GroupFinder {
	public int countByLayouts(long companyId, long parentGroupId, boolean site);

	public int countByG_U(long groupId, long userId, boolean inherit);

	public int countByC_C_PG_N_D(long companyId, long[] classNameIds,
		long parentGroupId, java.lang.String[] names,
		java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	public java.util.List<com.liferay.portal.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end);

	public java.util.List<com.liferay.portal.model.Group> findByLayouts(
		long companyId, long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc);

	public java.util.List<com.liferay.portal.model.Group> findByLiveGroups();

	public java.util.List<com.liferay.portal.model.Group> findByNoLayouts(
		long classNameId, int start, int end);

	public java.util.List<com.liferay.portal.model.Group> findByNullFriendlyURL();

	public java.util.List<com.liferay.portal.model.Group> findBySystem(
		long companyId);

	public java.util.List<com.liferay.portal.model.Group> findByCompanyId(
		long companyId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc);

	public java.util.List<java.lang.Long> findByC_P(long companyId,
		long parentGroupId, long previousGroupId, int size);

	public com.liferay.portal.model.Group findByC_GK(long companyId,
		java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException;

	public java.util.List<com.liferay.portal.model.Group> findByC_C_PG_N_D(
		long companyId, long[] classNameIds, long parentGroupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc);
}