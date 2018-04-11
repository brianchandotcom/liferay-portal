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
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
		return Objects.toString(_assetCategory.getCategoryId());
	}

	@Override
	public String getName(Locale locale) {
		return _assetCategory.getTitle(locale);
	}

	@Override
	public boolean matches(Map<String, Object> context) {
		long[] categoryIds = (long[])context.get("categoryIds");

		if (ArrayUtil.contains(categoryIds, _assetCategory.getCategoryId())) {
			return true;
		}

		return false;
	}

	private final AssetCategory _assetCategory;

}