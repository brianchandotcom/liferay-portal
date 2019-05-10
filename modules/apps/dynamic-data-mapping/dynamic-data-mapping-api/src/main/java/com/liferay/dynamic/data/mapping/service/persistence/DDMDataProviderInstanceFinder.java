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

package com.liferay.dynamic.data.mapping.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DDMDataProviderInstanceFinder {

	public int countByKeywords(
		long companyId, long[] groupIds, String keywords);

	public int countByC_G_N_D(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			filterByC_G(long companyId, long[] groupIds, int start, int end);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			filterByKeywords(
				long companyId, long[] groupIds, String keywords, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMDataProviderInstance> orderByComparator);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, String keywords);

	public int filterCountByC_G(long companyId, long[] groupIds);

	public int filterCountByC_G_N_D(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			filterFindByC_G_N_D(
				long companyId, long[] groupIds, String name,
				String description, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMDataProviderInstance> orderByComparator);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			findByKeywords(
				long companyId, long[] groupIds, String keywords, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMDataProviderInstance> orderByComparator);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			findByC_G_N_D(
				long companyId, long[] groupIds, String name,
				String description, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMDataProviderInstance> orderByComparator);

}