/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface ExportImportConfigurationFinder {

	public int countByKeywords(
		long companyId, long groupId, String keywords, int type, int status);

	public int countByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator);

	public int filterCountByKeywords(
		long companyId, long groupId, String keywords, int type, int status);

	public int filterCountByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator);

	public int filterCountByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, int status, boolean andOperator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByKeywords(
				long companyId, long groupId, String keywords, int type,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByC_G_N_D_T(
				long companyId, long groupId, String name, String description,
				int type, int status, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByC_G_N_D_T(
				long companyId, long groupId, String[] names,
				String[] descriptions, int type, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByKeywords(
				long companyId, long groupId, String keywords, int type,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByC_G_N_D_T(
				long companyId, long groupId, String name, String description,
				int type, int status, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

	public java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByC_G_N_D_T(
				long companyId, long groupId, String[] names,
				String[] descriptions, int type, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator);

}