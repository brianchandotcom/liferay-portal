/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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