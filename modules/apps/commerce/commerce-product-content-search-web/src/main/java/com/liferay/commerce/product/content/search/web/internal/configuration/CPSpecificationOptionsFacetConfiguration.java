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

package com.liferay.commerce.product.content.search.web.internal.configuration;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionsFacetConfiguration {

	public CPSpecificationOptionsFacetConfiguration(
		FacetConfiguration facetConfiguration) {

		_jsonObject = facetConfiguration.getData();
	}

	public int getFrequencyThreshold() {
		return _jsonObject.getInt("frequencyThreshold");
	}

	public int getMaxTerms() {
		return _jsonObject.getInt("maxTerms");
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_jsonObject.put("frequencyThreshold", frequencyThreshold);
	}

	public void setMaxTerms(int maxTerms) {
		_jsonObject.put("maxTerms", maxTerms);
	}

	private final JSONObject _jsonObject;

}