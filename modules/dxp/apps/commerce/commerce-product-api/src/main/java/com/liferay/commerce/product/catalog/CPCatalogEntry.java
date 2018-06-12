/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.catalog;

/**
 * @author Marco Leo
 */
public class CPCatalogEntry {

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public String getDefaultImageFileUrl() {
		return _defaultImageFileUrl;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getProductTypeName() {
		return _productTypeName;
	}

	public String getShortDescription() {
		return _shortDescription;
	}

	public String getSku() {
		return _sku;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isIgnoreSKUCombinations() {
		return _ignoreSKUCombinations;
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setDefaultImageFileUrl(String defaultImageFileUrl) {
		_defaultImageFileUrl = defaultImageFileUrl;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setIgnoreSKUCombinations(boolean ignoreSKUCombinations) {
		_ignoreSKUCombinations = ignoreSKUCombinations;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setProductTypeName(String productTypeName) {
		_productTypeName = productTypeName;
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private long _cpDefinitionId;
	private String _defaultImageFileUrl;
	private String _description;
	private boolean _ignoreSKUCombinations;
	private String _name;
	private String _productTypeName;
	private String _shortDescription;
	private String _sku;
	private String _url;

}