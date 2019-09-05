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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExportImportConfigurationFinderUtil {

	public static int countByKeywords(
		long companyId, long groupId, String keywords, int type, int status) {

		return getFinder().countByKeywords(
			companyId, groupId, keywords, type, status);
	}

	public static int countByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator) {

		return getFinder().countByC_G_N_D_T(
			companyId, groupId, name, description, type, status, andOperator);
	}

	public static int filterCountByKeywords(
		long companyId, long groupId, String keywords, int type, int status) {

		return getFinder().filterCountByKeywords(
			companyId, groupId, keywords, type, status);
	}

	public static int filterCountByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator) {

		return getFinder().filterCountByC_G_N_D_T(
			companyId, groupId, name, description, type, status, andOperator);
	}

	public static int filterCountByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, int status, boolean andOperator) {

		return getFinder().filterCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByKeywords(
				long companyId, long groupId, String keywords, int type,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().filterFindByKeywords(
			companyId, groupId, keywords, type, status, start, end,
			orderByComparator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByC_G_N_D_T(
				long companyId, long groupId, String name, String description,
				int type, int status, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().filterFindByC_G_N_D_T(
			companyId, groupId, name, description, type, status, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			filterFindByC_G_N_D_T(
				long companyId, long groupId, String[] names,
				String[] descriptions, int type, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().filterFindByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByKeywords(
				long companyId, long groupId, String keywords, int type,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().findByKeywords(
			companyId, groupId, keywords, type, status, start, end,
			orderByComparator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByC_G_N_D_T(
				long companyId, long groupId, String name, String description,
				int type, int status, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().findByC_G_N_D_T(
			companyId, groupId, name, description, type, status, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.exportimport.kernel.model.ExportImportConfiguration>
			findByC_G_N_D_T(
				long companyId, long groupId, String[] names,
				String[] descriptions, int type, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.exportimport.kernel.model.
						ExportImportConfiguration> orderByComparator) {

		return getFinder().findByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	public static ExportImportConfigurationFinder getFinder() {
		if (_finder == null) {
			_finder =
				(ExportImportConfigurationFinder)PortalBeanLocatorUtil.locate(
					ExportImportConfigurationFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(ExportImportConfigurationFinder finder) {
		_finder = finder;
	}

	private static ExportImportConfigurationFinder _finder;

}