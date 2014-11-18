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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class DDLRecordSetFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords, int scope) {
		return getFinder().countByKeywords(companyId, groupId, keywords, scope);
	}

	public static int countByC_G_N_D_S(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator) {
		return getFinder()
				   .countByC_G_N_D_S(companyId, groupId, name, description,
			scope, andOperator);
	}

	public static int filterCountByKeywords(long companyId, long groupId,
		java.lang.String keywords, int scope) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupId, keywords, scope);
	}

	public static int filterCountByC_G_N_D_S(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_N_D_S(companyId, groupId, name,
			description, scope, andOperator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> filterFindByKeywords(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupId, keywords, scope,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> filterFindByC_G_N_D_S(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_N_D_S(companyId, groupId, name,
			description, scope, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> filterFindByC_G_N_D_S(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_N_D_S(companyId, groupId, names,
			descriptions, scope, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, scope, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> findByC_G_N_D_S(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .findByC_G_N_D_S(companyId, groupId, name, description,
			scope, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> findByC_G_N_D_S(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> orderByComparator) {
		return getFinder()
				   .findByC_G_N_D_S(companyId, groupId, names, descriptions,
			scope, andOperator, start, end, orderByComparator);
	}

	public static DDLRecordSetFinder getFinder() {
		if (_finder == null) {
			_finder = (DDLRecordSetFinder)PortalBeanLocatorUtil.locate(DDLRecordSetFinder.class.getName());

			ReferenceRegistry.registerReference(DDLRecordSetFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDLRecordSetFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDLRecordSetFinderUtil.class,
			"_finder");
	}

	private static DDLRecordSetFinder _finder;
}