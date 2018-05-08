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

package com.liferay.portal.search.web.internal.category.facet.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

/**
 * @author Lino Alves
 */
public class AssetCategoriesFacetConfigurationImpl
	implements AssetCategoriesFacetConfiguration {

	public AssetCategoriesFacetConfigurationImpl(
		FacetConfiguration facetConfiguration) {

		_jsonObject = facetConfiguration.getData();
	}

	@Override
	public int getFrequencyThreshold() {
		return _jsonObject.getInt("frequencyThreshold");
	}

	@Override
	public int getMaxTerms() {
		return _jsonObject.getInt("maxTerms");
	}

	@Override
	public void setFrequencyThreshold(int frequencyThreshold) {
		_jsonObject.put("frequencyThreshold", frequencyThreshold);
	}

	@Override
	public void setMaxTerms(int maxTerms) {
		_jsonObject.put("maxTerms", maxTerms);
	}

	private final JSONObject _jsonObject;

}