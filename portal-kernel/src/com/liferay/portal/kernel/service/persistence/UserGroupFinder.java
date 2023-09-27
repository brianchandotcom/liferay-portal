/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface UserGroupFinder {

	public int countByKeywords(
		long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params);

	public int countByC_N_D(
		long companyId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countByC_N_D(
		long companyId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int filterCountByKeywords(
		long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params);

	public int filterCountByC_N_D(
		long companyId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int filterCountByC_N_D(
		long companyId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByKeywords(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByKeywords(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

}