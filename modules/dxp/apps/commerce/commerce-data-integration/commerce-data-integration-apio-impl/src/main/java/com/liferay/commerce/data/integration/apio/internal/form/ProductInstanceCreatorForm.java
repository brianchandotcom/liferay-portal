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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

import java.util.Date;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ProductInstanceCreatorForm {

	public static Form<ProductInstanceCreatorForm> buildForm(
		Form.Builder<ProductInstanceCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The product instance creator form"
		).description(
			__ -> "This form can be used to create a product instance"
		).constructor(
			ProductInstanceCreatorForm::new
		).addRequiredString(
			"sku", ProductInstanceCreatorForm::_setSku
		).addOptionalString(
			"gtin", ProductInstanceCreatorForm::_setGtin
		).addOptionalString(
			"manufacturerPartNumber",
			ProductInstanceCreatorForm::_setManufacturerPartNumber
		).addOptionalBoolean(
			"purchasable", ProductInstanceCreatorForm::_setPurchasable
		).addOptionalDouble(
			"width", ProductInstanceCreatorForm::_setWidth
		).addOptionalDouble(
			"height", ProductInstanceCreatorForm::_setHeight
		).addOptionalDouble(
			"depth", ProductInstanceCreatorForm::_setDepth
		).addOptionalDouble(
			"weight", ProductInstanceCreatorForm::_setWeight
		).addOptionalDouble(
			"cost", ProductInstanceCreatorForm::_setCost
		).addOptionalDouble(
			"price", ProductInstanceCreatorForm::_setPrice
		).addOptionalDouble(
			"promoPrice", ProductInstanceCreatorForm::_setPromoPrice
		).addOptionalBoolean(
			"published", ProductInstanceCreatorForm::_setPublished
		).addOptionalDate(
			"displayDate", ProductInstanceCreatorForm::_setDisplayDate
		).addOptionalDate(
			"expirationDate", ProductInstanceCreatorForm::_setExpirationDate
		).addOptionalBoolean(
			"neverExpire", ProductInstanceCreatorForm::_setNeverExpire
		).build();
	}

	public double getCost() {
		return _cost;
	}

	public double getDepth() {
		return _depth;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String getGtin() {
		return _gtin;
	}

	public double getHeight() {
		return _height;
	}

	public String getManufacturerPartNumber() {
		return _manufacturerPartNumber;
	}

	public boolean getNeverExpire() {
		return _neverExpire;
	}

	public double getPrice() {
		return _price;
	}

	public double getPromoPrice() {
		return _promoPrice;
	}

	public boolean getPublished() {
		return _published;
	}

	public boolean getPurchasable() {
		return _purchasable;
	}

	public String getSku() {
		return _sku;
	}

	public double getWeight() {
		return _weight;
	}

	public double getWidth() {
		return _width;
	}

	private void _setCost(double cost) {
		_cost = cost;
	}

	private void _setDepth(double depth) {
		_depth = depth;
	}

	private void _setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	private void _setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	private void _setGtin(String gtin) {
		_gtin = gtin;
	}

	private void _setHeight(double height) {
		_height = height;
	}

	private void _setManufacturerPartNumber(String manufacturerPartNumber) {
		_manufacturerPartNumber = manufacturerPartNumber;
	}

	private void _setNeverExpire(boolean neverExpire) {
		_neverExpire = neverExpire;
	}

	private void _setPrice(double price) {
		_price = price;
	}

	private void _setPromoPrice(double promoPrice) {
		_promoPrice = promoPrice;
	}

	private void _setPublished(boolean published) {
		_published = published;
	}

	private void _setPurchasable(boolean purchasable) {
		_purchasable = purchasable;
	}

	private void _setSku(String sku) {
		_sku = sku;
	}

	private void _setWeight(double weight) {
		_weight = weight;
	}

	private void _setWidth(double width) {
		_width = width;
	}

	private double _cost;
	private double _depth;
	private Date _displayDate;
	private Date _expirationDate;
	private String _gtin;
	private double _height;
	private String _manufacturerPartNumber;
	private boolean _neverExpire;
	private double _price;
	private double _promoPrice;
	private boolean _published;
	private boolean _purchasable;
	private String _sku;
	private double _weight;
	private double _width;

}