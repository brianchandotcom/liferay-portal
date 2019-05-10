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
public interface AssetTagFinder {

	public int countByG_N(long groupId, String name);

	public int countByG_C_N(long groupId, long classNameId, String name);

	public java.util.List<com.liferay.asset.kernel.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.asset.kernel.model.AssetTag> obc);

	public java.util.List<com.liferay.asset.kernel.model.AssetTag>
		findByG_N_S_E(
			long groupId, String name, int startPeriod, int endPeriod,
			int periodLength);

}