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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommerceWarehouseFinder {
	public int countByKeywords(long groupId, String keywords, boolean all,
		long commerceCountryId);

	public int countByG_N_D_S_C_Z_C(long groupId, String name,
		String description, String street, String city, String zip,
		boolean all, long commerceCountryId, boolean andOperator);

	public int countByG_N_D_S_C_Z_C(long groupId, String[] names,
		String[] descriptions, String[] streets, String[] cities,
		String[] zips, boolean all, long commerceCountryId, boolean andOperator);

	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> findByCPInstanceId(
		long cpInstanceId, int start, int end);

	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> findByKeywords(
		long groupId, String keywords, boolean all, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator);

	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> findByG_N_D_S_C_Z_C(
		long groupId, String name, String description, String street,
		String city, String zip, boolean all, long commerceCountryId,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator);

	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> findByG_N_D_S_C_Z_C(
		long groupId, String[] names, String[] descriptions, String[] streets,
		String[] cities, String[] zips, boolean all, long commerceCountryId,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator);
}