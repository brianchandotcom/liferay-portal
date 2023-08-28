/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.cart.model;

import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class Product {

	public Product(
		long id, long parentProductId, String name, String sku, int quantity,
		String thumbnail, String unitOfMeasureKey, PriceModel prices,
		ProductSettingsModel settings, String[] errorMessages,
		long cpInstanceId) {

		_id = id;
		_parentProductId = parentProductId;
		_name = name;
		_sku = sku;
		_quantity = quantity;
		_thumbnail = thumbnail;
		_unitOfMeasureKey = unitOfMeasureKey;
		_prices = prices;
		_settings = settings;
		_errorMessages = errorMessages;
		_cpInstanceId = cpInstanceId;
	}

	public List<Product> getChildItems() {
		return _childItems;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public List<KeyValuePair> getOptions() {
		return _options;
	}

	public long getParentProductId() {
		return _parentProductId;
	}

	public PriceModel getPrices() {
		return _prices;
	}

	public int getQuantity() {
		return _quantity;
	}

	public ProductSettingsModel getSettings() {
		return _settings;
	}

	public String getSku() {
		return _sku;
	}

	public String getThumbnail() {
		return _thumbnail;
	}

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	public void setChildItems(List<Product> childItems) {
		_childItems = childItems;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOptions(List<KeyValuePair> options) {
		_options = options;
	}

	public void setParentProductId(long parentProductId) {
		_parentProductId = parentProductId;
	}

	public void setPrices(PriceModel prices) {
		_prices = prices;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setSettings(ProductSettingsModel settings) {
		_settings = settings;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public void setThumbnail(String thumbnail) {
		_thumbnail = thumbnail;
	}

	public void setUnitOfMeasureKey(String unitOfMeasureKey) {
		_unitOfMeasureKey = unitOfMeasureKey;
	}

	private List<Product> _childItems;
	private long _cpInstanceId;
	private String[] _errorMessages;
	private long _id;
	private String _name;
	private List<KeyValuePair> _options;
	private long _parentProductId;
	private PriceModel _prices;
	private int _quantity;
	private ProductSettingsModel _settings;
	private String _sku;
	private String _thumbnail;
	private String _unitOfMeasureKey;

}