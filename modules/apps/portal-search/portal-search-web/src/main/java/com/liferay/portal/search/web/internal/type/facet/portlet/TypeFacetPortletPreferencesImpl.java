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

package com.liferay.portal.search.web.internal.type.facet.portlet;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class TypeFacetPortletPreferencesImpl
	implements TypeFacetPortletPreferences {

	public TypeFacetPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public Optional<String[]> getAssetTypesArray() {
		Optional<String> assetTypes = _portletPreferencesHelper.getString(
			TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES);

		return assetTypes.map(StringUtil::split);
	}

	@Override
	public String getAssetTypesString() {
		return _portletPreferencesHelper.getString(
			TypeFacetPortletPreferences.PREFERENCE_KEY_ASSET_TYPES,
			StringPool.BLANK);
	}

	@Override
	public List<KeyValuePair> getAvailableAssetTypes(
		long companyId, Locale locale) {

		Optional<String[]> assetTypesOptional = getAssetTypesArray();

		String[] allAssetTypes = getAllAssetTypes(companyId);

		String[] assetTypes = assetTypesOptional.orElse(allAssetTypes);

		List<KeyValuePair> availableAssetTypes = new ArrayList<>();

		for (String className : allAssetTypes) {
			if (!ArrayUtil.contains(assetTypes, className)) {
				availableAssetTypes.add(getKeyValuePair(locale, className));
			}
		}

		return availableAssetTypes;
	}

	@Override
	public List<KeyValuePair> getCurrentAssetTypes(
		long companyId, Locale locale) {

		String[] assetTypes = getCurrentAssetTypesArray(companyId);

		List<KeyValuePair> currentAssetTypes = new ArrayList<>();

		for (String className : assetTypes) {
			currentAssetTypes.add(getKeyValuePair(locale, className));
		}

		return currentAssetTypes;
	}

	@Override
	public String[] getCurrentAssetTypesArray(long companyId) {
		Optional<String[]> assetTypesOptional = getAssetTypesArray();

		return assetTypesOptional.orElseGet(() -> getAllAssetTypes(companyId));
	}

	@Override
	public int getFrequencyThreshold() {
		return _portletPreferencesHelper.getInteger(
			TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD, 1);
	}

	@Override
	public String getParameterName() {
		return _portletPreferencesHelper.getString(
			TypeFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME, "type");
	}

	@Override
	public boolean isFrequenciesVisible() {
		return _portletPreferencesHelper.getBoolean(
			TypeFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

	protected String[] getAllAssetTypes(long companyId) {
		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				companyId);

		Stream<AssetRendererFactory<?>> assetRendererFactoriesStream =
			assetRendererFactories.stream();

		return assetRendererFactoriesStream.filter(
			AssetRendererFactory::isSearchable
		).map(
			AssetRendererFactory::getClassName
		).toArray(
			String[]::new
		);
	}

	protected KeyValuePair getKeyValuePair(Locale locale, String className) {
		return new KeyValuePair(
			className, ResourceActionsUtil.getModelResource(locale, className));
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}