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

package com.liferay.batch.engine.demo1.internal.v1_0.model;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class Product {

	public Integer[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public String getDefaultSku() {
		return _defaultSku;
	}

	public Map<String, String> getDescription() {
		return _description;
	}

	public Map<String, ?> getExpando() {
		return _expando;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public Long getId() {
		return _id;
	}

	public Map<String, String> getName() {
		return _name;
	}

	public String getProductTypeName() {
		return _productTypeName;
	}

	public Map<String, String> getShortDescription() {
		return _shortDescription;
	}

	public Sku[] getSkus() {
		return _skus;
	}

	public Boolean isActive() {
		return _active;
	}

	public void setActive(Boolean active) {
		_active = active;
	}

	public void setAssetCategoryIds(Integer[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setDefaultSku(String defaultSku) {
		_defaultSku = defaultSku;
	}

	public void setDescription(Map<String, String> description) {
		_description = description;
	}

	public void setExpando(Map<String, ?> expando) {
		_expando = expando;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(Map<String, String> name) {
		_name = name;
	}

	public void setProductTypeName(String productTypeName) {
		_productTypeName = productTypeName;
	}

	public void setShortDescription(Map<String, String> shortDescription) {
		_shortDescription = shortDescription;
	}

	public void setSkus(Sku[] skus) {
		_skus = skus;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(24);

		sb.append("Product{_active=");
		sb.append(_active);
		sb.append(", _assetCategoryIds=");
		sb.append(Arrays.toString(_assetCategoryIds));
		sb.append(", _defaultSku='");
		sb.append(_defaultSku);
		sb.append('\'');
		sb.append(", _description=");
		sb.append(_description);
		sb.append(", _expando=");
		sb.append(_expando);
		sb.append(", _externalReferenceCode='");
		sb.append(_externalReferenceCode + '\'');
		sb.append(", _id=");
		sb.append(_id);
		sb.append(", _name=");
		sb.append(_name);
		sb.append(", _productTypeName='");
		sb.append(_productTypeName + '\'');
		sb.append(", _shortDescription=");
		sb.append(_shortDescription);
		sb.append(", _skus=");
		sb.append(Arrays.toString(_skus));
		sb.append('}');

		return sb.toString();
	}

	private Boolean _active;
	private Integer[] _assetCategoryIds;
	private String _defaultSku;
	private Map<String, String> _description;
	private Map<String, ?> _expando;
	private String _externalReferenceCode;
	private Long _id;
	private Map<String, String> _name;
	private String _productTypeName;
	private Map<String, String> _shortDescription;
	private Sku[] _skus;

}