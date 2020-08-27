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

package com.liferay.commerce.discount.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 * @generated
 */
@ProviderType
public interface CommerceDiscountRelFinder {

	public int countCategoriesByCommerceDiscountId(
		long commerceDiscountId, String name);

	public int countCategoriesByCommerceDiscountId(
		long commerceDiscountId, String name, boolean inlineSQLHelper);

	public int countCPDefinitionsByCommerceDiscountId(
		long commerceDiscountId, String name, String languageId);

	public int countCPDefinitionsByCommerceDiscountId(
		long commerceDiscountId, String name, String languageId,
		boolean inlineSQLHelper);

	public int countPricingClassesByCommerceDiscountId(
		long commerceDiscountId, String title);

	public int countPricingClassesByCommerceDiscountId(
		long commerceDiscountId, String title, boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findCategoriesByCommerceDiscountId(
				long commerceDiscountId, String name, int start, int end);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findCategoriesByCommerceDiscountId(
				long commerceDiscountId, String name, int start, int end,
				boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findCPDefinitionsByCommerceDiscountId(
				long commerceDiscountId, String name, String languageId,
				int start, int end);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findCPDefinitionsByCommerceDiscountId(
				long commerceDiscountId, String name, String languageId,
				int start, int end, boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findPricingClassesByCommerceDiscountId(
				long commerceDiscountId, String title, int start, int end);

	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
			findPricingClassesByCommerceDiscountId(
				long commerceDiscountId, String title, int start, int end,
				boolean inlineSQLHelper);

}