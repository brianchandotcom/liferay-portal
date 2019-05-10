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

package com.liferay.asset.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface AssetCategoryFinder {

	public int countByG_C_N(long groupId, long classNameId, String name);

	public int countByG_N_P(
		long groupId, String name, String[] categoryProperties);

	public int filterCountByC_C(long classNameId, long classPK);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		filterFindByC_C(long classNameId, long classPK, int start, int end);

	public com.liferay.asset.kernel.model.AssetCategory findByG_N(
			long groupId, String name)
		throws com.liferay.asset.kernel.exception.NoSuchCategoryException;

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByC_C(long classNameId, long classPK);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(long groupId, String name, String[] categoryProperties);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(
			long groupId, String name, String[] categoryProperties, int start,
			int end);

}