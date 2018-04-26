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

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface OrganizationFinder {
	public int countByKeywords(long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params);

	public int countByO_U(long organizationId, long userId);

	public int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String name, String type, String street, String city, String zip,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String[] names, String type, String[] streets, String[] cities,
		String[] zips, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countO_ByKeywords(long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params);

	public int countO_ByO_U(long organizationId, long userId);

	public int countO_ByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String name, String type, String street, String city, String zip,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countO_ByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId, String parentOrganizationIdComparator,
		String[] names, String type, String[] streets, String[] cities,
		String[] zips, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countO_U_ByC_P(long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findByNoAssets();

	public java.util.List<Long> findByC_P(long companyId,
		long parentOrganizationId, long previousOrganizationId, int size);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByKeywords(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String keywords, String type,
		Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByNoAssets();

	public java.util.List<Long> findO_ByC_P(long companyId,
		long parentOrganizationId, long previousOrganizationId, int size);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String name, String type,
		String street, String city, String zip, Long regionId, Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<com.liferay.portal.kernel.model.Organization> findO_ByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		String parentOrganizationIdComparator, String[] names, String type,
		String[] streets, String[] cities, String[] zips, Long regionId,
		Long countryId, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc);

	public java.util.List<Object> findO_U_ByC_P(long companyId,
		long parentOrganizationId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);
}