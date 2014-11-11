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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class OrganizationFinderUtil {
	public static int countByKeywords(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {
		return getFinder()
				   .countByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params);
	}

	public static int countByO_U(long organizationId, long userId) {
		return getFinder().countByO_U(organizationId, userId);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String[] names, java.lang.String type,
		java.lang.String[] streets, java.lang.String[] cities,
		java.lang.String[] zips, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByKeywords(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Organization> obc) {
		return getFinder()
				   .findByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<java.lang.Long> findByC_P(long companyId,
		long parentOrganizationId, long previousOrganizationId, int size) {
		return getFinder()
				   .findByC_P(companyId, parentOrganizationId,
			previousOrganizationId, size);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Organization> obc) {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String[] names, java.lang.String type,
		java.lang.String[] streets, java.lang.String[] cities,
		java.lang.String[] zips, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Organization> obc) {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator, start, end, obc);
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