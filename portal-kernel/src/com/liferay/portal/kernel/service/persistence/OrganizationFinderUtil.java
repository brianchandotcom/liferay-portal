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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class OrganizationFinderUtil {
	public static int countByKeywords(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String keywords, String type, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params) {
		return getFinder()
				   .countByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params);
	}

	public static int countByO_U(long organizationId, long userId) {
		return getFinder().countByO_U(organizationId, userId);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String name, String type, String street, String city, String zip,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String[] names, String type, String[] streets, String[] cities,
		String[] zips, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator);
	}

	public static int countO_ByKeywords(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String keywords, String type, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params) {
		return getFinder()
				   .countO_ByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params);
	}

	public static int countO_ByO_U(long organizationId, long userId) {
		return getFinder().countO_ByO_U(organizationId, userId);
	}

	public static int countO_ByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String name, String type, String street, String city, String zip,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countO_ByC_PO_N_T_S_C_Z_R_C(companyId,
			parentOrganizationId, parentOrganizationIdComparator, name, type,
			street, city, zip, regionId, countryId, params, andOperator);
	}

	public static int countO_ByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String[] names, String type, String[] streets, String[] cities,
		String[] zips, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countO_ByC_PO_N_T_S_C_Z_R_C(companyId,
			parentOrganizationId, parentOrganizationIdComparator, names, type,
			streets, cities, zips, regionId, countryId, params, andOperator);
	}

	public static int countO_U_ByC_P(long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder()
				   .countO_U_ByC_P(companyId, parentOrganizationId,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<Long> findByC_P(long companyId,
		long parentOrganizationId, long previousOrganizationId, int size) {
		return getFinder()
				   .findByC_P(companyId, parentOrganizationId,
			previousOrganizationId, size);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findO_ByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByNoAssets() {
		return getFinder().findO_ByNoAssets();
	}

	public static java.util.List<Long> findO_ByC_P(long companyId,
		long parentOrganizationId, long previousOrganizationId, int size) {
		return getFinder()
				   .findO_ByC_P(companyId, parentOrganizationId,
			previousOrganizationId, size);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findO_ByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getFinder()
				   .findO_ByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<Object> findO_U_ByC_P(long companyId,
		long parentOrganizationId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder()
				   .findO_U_ByC_P(companyId, parentOrganizationId,
			queryDefinition);
	}

	public static OrganizationFinder getFinder() {
		if (_finder == null) {
			_finder = (OrganizationFinder)PortalBeanLocatorUtil.locate(OrganizationFinder.class.getName());

			ReferenceRegistry.registerReference(OrganizationFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(OrganizationFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(OrganizationFinderUtil.class,
			"_finder");
	}

	private static OrganizationFinder _finder;
}