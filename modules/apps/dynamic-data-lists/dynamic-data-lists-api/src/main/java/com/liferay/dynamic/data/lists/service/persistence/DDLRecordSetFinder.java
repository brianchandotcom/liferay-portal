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

package com.liferay.dynamic.data.lists.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DDLRecordSetFinder {

	public int countByKeywords(
		long companyId, long groupId, String keywords, int scope);

	public int countByG_D(
		long groupId, long ddmStructureId, boolean andOperator);

	public int countByC_G_N_D_S(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator);

	public int filterCountByKeywords(
		long companyId, long groupId, String keywords, int scope);

	public int filterCountByC_G_N_D_S(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		filterFindByKeywords(
			long companyId, long groupId, String keywords, int scope, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		filterFindByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		filterFindByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		findByKeywords(
			long companyId, long groupId, String keywords, int scope, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		findByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet>
		findByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator);

}