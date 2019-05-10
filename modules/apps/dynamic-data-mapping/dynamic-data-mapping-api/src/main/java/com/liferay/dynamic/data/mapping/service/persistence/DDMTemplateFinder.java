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
public interface DDMTemplateFinder {

	public int countByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status);

	public int countByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status);

	public int countByG_SC_S(
		long groupId, long structureClassNameId, int status);

	public int countByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status);

	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator);

	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator);

	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator);

	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator);

	public int filterCountByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status);

	public int filterCountByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status);

	public int filterCountByG_SC_S(
		long groupId, long structureClassNameId, int status);

	public int filterCountByG_SC_S(
		long[] groupIds, long structureClassNameId, int status);

	public int filterCountByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status);

	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator);

	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator);

	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator);

	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByKeywords(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String keywords, String type, String mode,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByKeywords(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String keywords,
			String type, String mode, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByG_SC_S(
			long groupId, long structureClassNameId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByG_SC_S(
			long[] groupIds, long structureClassNameId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByC_G_C_C_R_T_M_S(
			long companyId, long[] groupIds, long classNameId, long classPK,
			long resourceClassNameId, String type, String mode, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String name, String description,
			String type, String mode, String language, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String[] names, String[] descriptions,
			String[] types, String[] modes, String[] languages, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String name,
			String description, String type, String mode, String language,
			int status, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		filterFindByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String[] names,
			String[] descriptions, String[] types, String[] modes,
			String[] languages, int status, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByKeywords(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String keywords, String type, String mode,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByKeywords(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String keywords,
			String type, String mode, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByG_SC_S(
			long groupId, long structureClassNameId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByG_SC_S(
			long[] groupIds, long structureClassNameId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByC_G_C_C_R_T_M_S(
			long companyId, long[] groupIds, long classNameId, long classPK,
			long resourceClassNameId, String type, String mode, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String name, String description,
			String type, String mode, String language, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String[] names, String[] descriptions,
			String[] types, String[] modes, String[] languages, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String name,
			String description, String type, String mode, String language,
			int status, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
		findByC_G_C_C_R_N_D_T_M_L_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String[] names,
			String[] descriptions, String[] types, String[] modes,
			String[] languages, int status, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator);

}