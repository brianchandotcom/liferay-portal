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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.meris.MerisRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class AssetCategoryMerisRule
	implements MerisRule, Comparable<AssetCategoryMerisRule> {

	public AssetCategoryMerisRule(AssetCategory assetCategory) {
		_assetCategory = assetCategory;
	}

	@Override
	public int compareTo(AssetCategoryMerisRule assetCategoryMerisRule) {
		String merisRuleId = getMerisRuleId();

		return merisRuleId.compareTo(assetCategoryMerisRule.getMerisRuleId());
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetCategory.getDescription(locale);
	}

	@Override
	public String getMerisRuleId() {
		return String.valueOf(_assetCategory.getCategoryId());
	}

	@Override
	public String getMerisRuleTypeId() {
		return AssetCategoryMerisRuleType.class.getName();
	}

	@Override
	public Map<String, Object> getMerisRuleTypeSettings() {
		Map<String, Object> merisRuleTypeDefaultSetting = new HashMap<>();

		merisRuleTypeDefaultSetting.put(
			"assetCategoryId", _assetCategory.getCategoryId());

		return Collections.unmodifiableMap(merisRuleTypeDefaultSetting);
	}

	@Override
	public String getName(Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	private final AssetCategory _assetCategory;

}