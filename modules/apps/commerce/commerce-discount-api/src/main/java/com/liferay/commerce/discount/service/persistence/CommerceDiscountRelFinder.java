/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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