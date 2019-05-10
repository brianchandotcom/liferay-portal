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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface TeamFinder {

	public int countByG_N_D(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params);

	public int filterCountByG_N_D(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params);

	public java.util.List<com.liferay.portal.kernel.model.Team>
		filterFindByG_N_D(
			long groupId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Team> obc);

	public java.util.List<com.liferay.portal.kernel.model.Team> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Team> obc);

	public java.util.List<com.liferay.portal.kernel.model.Team> findByG_N_D(
		long groupId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Team> obc);

}