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
public interface AssetLinkFinder {

	public java.util.List<com.liferay.asset.kernel.model.AssetLink>
		findByAssetEntryGroupId(long groupId, int start, int end);

	public java.util.List<com.liferay.asset.kernel.model.AssetLink> findByG_C(
		long groupId, java.util.Date startDate, java.util.Date endDate,
		int start, int end);

	public java.util.List<com.liferay.asset.kernel.model.AssetLink> findByC_C(
		long classNameId, long classPK);

}