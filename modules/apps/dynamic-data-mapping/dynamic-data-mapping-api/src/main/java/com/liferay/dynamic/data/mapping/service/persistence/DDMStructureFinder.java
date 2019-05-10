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
public interface DDMStructureFinder {

	public int countByKeywords(
		long companyId, long[] groupIds, long classNameId, long classPK,
		String keywords);

	public int countByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status);

	public int countByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status);

	public int countByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator);

	public int countByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status);

	public int filterCountByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status);

	public int filterCountByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator);

	public int filterCountByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		filterFindByKeywords(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int type, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		filterFindByKeywords(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		filterFindByC_G_C_S(
			long companyId, long[] groupIds, long classNameId, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		filterFindByC_G_C_N_D_S_T_S(
			long companyId, long[] groupIds, long classNameId, String name,
			String description, String storageType, int type, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		filterFindByC_G_C_N_D_S_T_S(
			long companyId, long[] groupIds, long classNameId, String[] names,
			String[] descriptions, String storageType, int type, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		findByKeywords(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		findByKeywords(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		findByC_G_C_S(
			long companyId, long[] groupIds, long classNameId, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		findByC_G_C_N_D_S_T_S(
			long companyId, long[] groupIds, long classNameId, String name,
			String description, String storageType, int type, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
		findByC_G_C_N_D_S_T_S(
			long companyId, long[] groupIds, long classNameId, String[] names,
			String[] descriptions, String storageType, int type, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator);

}