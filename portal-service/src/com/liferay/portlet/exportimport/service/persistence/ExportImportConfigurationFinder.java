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

package com.liferay.portlet.exportimport.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface ExportImportConfigurationFinder {
	public int countByKeywords(long companyId, long groupId,
		java.lang.String keywords, int type, int status);

	public int countByC_G_N_D_T(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int type,
		int status, boolean andOperator);

	public int filterCountByKeywords(long companyId, long groupId,
		java.lang.String keywords, int type, int status);

	public int filterCountByC_G_N_D_T(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int type,
		int status, boolean andOperator);

	public int filterCountByC_G_N_D_T(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions, int type,
		int status, boolean andOperator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByKeywords(
		long companyId, long groupId, java.lang.String keywords, int type,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int type,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);

	public java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator);
}