/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public int countByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
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

	public int filterCountByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
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
		filterFindByC_G_C_C_R_T_M_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String type, String mode,
			int status, int start, int end,
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
		findByC_G_C_C_R_T_M_S(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String type, String mode,
			int status, int start, int end,
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