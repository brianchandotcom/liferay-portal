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