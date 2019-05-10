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
				<com.liferay.portal.kernel.model.UserGroup> obc);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByKeywords(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc);

	public com.liferay.portal.kernel.model.UserGroup findByC_N(
			long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchUserGroupException;

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc);

	public java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc);

}