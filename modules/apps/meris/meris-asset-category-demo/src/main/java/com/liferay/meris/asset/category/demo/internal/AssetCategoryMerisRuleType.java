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

import com.liferay.meris.MerisRuleType;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component
public class AssetCategoryMerisRuleType
	implements MerisRuleType, Comparable<AssetCategoryMerisRuleType> {

	@Override
	public int compareTo(AssetCategoryMerisRuleType assetCategoryMerisRule) {
		String merisRuleId = getMerisRuleTypeId();

		return merisRuleId.compareTo(
			assetCategoryMerisRule.getMerisRuleTypeId());
	}

	@Override
	public String getDescription(Locale locale) {
		return ResourceActionsUtil.getModelResource(
			locale, getMerisRuleTypeId() + ".description");
	}

	@Override
	public Map<String, Object> getMerisRuleTypeDefaultSettings() {
		Map<String, Object> merisRuleTypeDefaultSetting = new HashMap<>();

		merisRuleTypeDefaultSetting.put("assetCategoryId", 0);

		return Collections.unmodifiableMap(merisRuleTypeDefaultSetting);
	}

	@Override
	public String getMerisRuleTypeId() {
		return AssetCategoryMerisRuleType.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		return ResourceActionsUtil.getModelResource(
			locale, getMerisRuleTypeId());
	}

	@Override
	public boolean matches(
		Map<String, Object> context, Map<String, Object> merisRuleSettings) {

		long[] assetCategoryIds = GetterUtil.getLongValues(
			context.get("assetCategoryIds"));

		if (assetCategoryIds.length == 0) {
			return false;
		}

		long assetCategoryId = MapUtil.getLong(
			merisRuleSettings, "assetCategoryId");

		if (ArrayUtil.contains(assetCategoryIds, assetCategoryId)) {
			return true;
		}

		return false;
	}

}