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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ExportImportConfigurationFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords, int type, int status) {
		return getFinder()
				   .countByKeywords(companyId, groupId, keywords, type, status);
	}

	public static int countByC_G_N_D_T(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int type,
		int status, boolean andOperator) {
		return getFinder()
				   .countByC_G_N_D_T(companyId, groupId, name, description,
			type, status, andOperator);
	}

	public static int filterCountByKeywords(long companyId, long groupId,
		java.lang.String keywords, int type, int status) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupId, keywords, type,
			status);
	}

	public static int filterCountByC_G_N_D_T(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int type,
		int status, boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_N_D_T(companyId, groupId, name,
			description, type, status, andOperator);
	}

	public static int filterCountByC_G_N_D_T(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions, int type,
		int status, boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_N_D_T(companyId, groupId, names,
			descriptions, type, status, andOperator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByKeywords(
		long companyId, long groupId, java.lang.String keywords, int type,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupId, keywords, type,
			status, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_N_D_T(companyId, groupId, name,
			description, type, status, andOperator, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_N_D_T(companyId, groupId, names,
			descriptions, type, status, andOperator, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int type,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, type, status,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .findByC_G_N_D_T(companyId, groupId, name, description,
			type, status, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.exportimport.model.ExportImportConfiguration> orderByComparator) {
		return getFinder()
				   .findByC_G_N_D_T(companyId, groupId, names, descriptions,
			type, status, andOperator, start, end, orderByComparator);
	}

	public static ExportImportConfigurationFinder getFinder() {
		if (_finder == null) {
			_finder = (ExportImportConfigurationFinder)PortalBeanLocatorUtil.locate(ExportImportConfigurationFinder.class.getName());

			ReferenceRegistry.registerReference(ExportImportConfigurationFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ExportImportConfigurationFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ExportImportConfigurationFinderUtil.class,
			"_finder");
	}

	private static ExportImportConfigurationFinder _finder;
}