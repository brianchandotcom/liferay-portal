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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface AssetTagFinder {
	public int countByG_N(long groupId, java.lang.String name);

	public int countByG_C_N(long groupId, long classNameId,
		java.lang.String name);

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc);

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_S_E(
		long groupId, java.lang.String name, int startPeriod, int endPeriod,
		int periodLength);
}