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

package com.liferay.commerce.pricing.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Riccardo Alberti
 * @generated
 */
@ProviderType
public interface CommercePriceModifierRelFinder {

	public int countCategoriesByCommercePriceModifierId(
		long commercePriceModifierId, String name);

	public int countCategoriesByCommercePriceModifierId(
		long commercePriceModifierId, String name, boolean inlineSQLHelper);

	public int countCPDefinitionsByCommercePriceModifierId(
		long commercePriceModifierId, String name, String languageId);

	public int countCPDefinitionsByCommercePriceModifierId(
		long commercePriceModifierId, String name, String languageId,
		boolean inlineSQLHelper);

	public int countPricingClassesByCommercePriceModifierId(
		long commercePriceModifierId, String title);

	public int countPricingClassesByCommercePriceModifierId(
		long commercePriceModifierId, String title, boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findCategoriesByCommercePriceModifierId(
				long commercePriceModifierId, String name, int start, int end);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findCategoriesByCommercePriceModifierId(
				long commercePriceModifierId, String name, int start, int end,
				boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findCPDefinitionsByCommercePriceModifierId(
				long commercePriceModifierId, String name, String languageId,
				int start, int end);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findCPDefinitionsByCommercePriceModifierId(
				long commercePriceModifierId, String name, String languageId,
				int start, int end, boolean inlineSQLHelper);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findPricingClassesByCommercePriceModifierId(
				long commercePriceModifierId, String title, int start, int end);

	public java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
			findPricingClassesByCommercePriceModifierId(
				long commercePriceModifierId, String title, int start, int end,
				boolean inlineSQLHelper);

}