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

package com.liferay.commerce.product.internal.catalog.rule;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.catalog.rule.CPRuleType;
import com.liferay.commerce.product.constants.CPRuleConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.ToLongFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.rule.type.key=" + CPRuleConstants.TYPE_ASSET_CATEGORY,
		"commerce.product.rule.type.order:Integer=10"
	},
	service = CPRuleType.class
)
public class AssetCategoryCPRuleTypeImpl implements CPRuleType {

	@Override
	public void contributeDocument(Document document, CPDefinition cpDefinition)
		throws PortalException {

		Set<AssetCategory> assetCategories = _getAssetCategories(cpDefinition);

		long[] assetCategoryIds = new long[assetCategories.size()];

		int i = 0;

		for (AssetCategory assetCategory : assetCategories) {
			assetCategoryIds[i] = assetCategory.getCategoryId();

			i++;
		}

		document.addKeyword(
			_FIELD_CP_RULE_ASSET_CATEGORY_IDS, assetCategoryIds);
	}

	@Override
	public String getKey() {
		return CPRuleConstants.TYPE_ASSET_CATEGORY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "categories");
	}

	@Override
	public boolean isSatisfied(CPDefinition cpDefinition, CPRule cpRule)
		throws PortalException {

		Set<Long> assetCategoryIds = _toSet(
			_getAssetCategories(cpDefinition), AssetCategory::getCategoryId);

		List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels =
			_cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRels(
				cpRule.getCPRuleId());

		Set<Long> cpRuleAssetCategoryIds = _toSet(
			cpRuleAssetCategoryRels,
			CPRuleAssetCategoryRel::getAssetCategoryId);

		if (assetCategoryIds.containsAll(cpRuleAssetCategoryIds)) {
			return true;
		}

		return false;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, CPRule cpRule)
		throws PortalException {

		List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels =
			_cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRels(
				cpRule.getCPRuleId());

		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel :
				cpRuleAssetCategoryRels) {

			booleanFilter.addTerm(
				_FIELD_CP_RULE_ASSET_CATEGORY_IDS,
				String.valueOf(cpRuleAssetCategoryRel.getAssetCategoryId()),
				BooleanClauseOccur.MUST);
		}
	}

	private Set<AssetCategory> _getAssetCategories(CPDefinition cpDefinition)
		throws PortalException {

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		Set<AssetCategory> assetCategories = new HashSet<>();

		for (AssetCategory assetCategory : assetEntry.getCategories()) {
			assetCategories.add(assetCategory);
			assetCategories.addAll(assetCategory.getAncestors());
		}

		return assetCategories;
	}

	private <T> Set<Long> _toSet(
		Iterable<T> list, ToLongFunction<T> toLongFunction) {

		Set<Long> set = new HashSet<>();

		for (T object : list) {
			set.add(toLongFunction.applyAsLong(object));
		}

		return set;
	}

	private static final String _FIELD_CP_RULE_ASSET_CATEGORY_IDS =
		"cpRuleAssetCategoryIds";

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CPRuleAssetCategoryRelLocalService
		_cpRuleAssetCategoryRelLocalService;

}