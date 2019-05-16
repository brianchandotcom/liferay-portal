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

import com.fasterxml.jackson.annotation.JsonFormat;

import com.liferay.portal.kernel.util.StringBundler;

import java.math.BigDecimal;

import java.util.Date;

/**
 * @author Ivica Cardic
 */
public class Sku {

	public BigDecimal getCost() {
		return _cost;
	}

	public Double getDepth() {
		return _depth;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	public Date getDisplayDate() {
		return _displayDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getGtin() {
		return _gtin;
	}

	public Double getHeight() {
		return _height;
	}

	public Long getId() {
		return _id;
	}

	public String getManufacturerPartNumber() {
		return _manufacturerPartNumber;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public BigDecimal getPromoPrice() {
		return _promoPrice;
	}

	public String getSku() {
		return _sku;
	}

	public Double getWeight() {
		return _weight;
	}

	public Double getWidth() {
		return _width;
	}

	public Boolean isNeverExpire() {
		return _neverExpire;
	}

	public Boolean isPublished() {
		return _published;
	}

	public Boolean isPurchasable() {
		return _purchasable;
	}

	public void setCost(BigDecimal cost) {
		_cost = cost;
	}

	public void setDepth(Double depth) {
		_depth = depth;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setGtin(String gtin) {
		_gtin = gtin;
	}

	public void setHeight(Double height) {
		_height = height;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		_manufacturerPartNumber = manufacturerPartNumber;
	}

	public void setNeverExpire(Boolean neverExpire) {
		_neverExpire = neverExpire;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		_promoPrice = promoPrice;
	}

	public void setPublished(Boolean published) {
		_published = published;
	}

	public void setPurchasable(Boolean purchasable) {
		_purchasable = purchasable;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public void setWeight(Double weight) {
		_weight = weight;
	}

	public void setWidth(Double width) {
		_width = width;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(38);

		sb.append("Sku{_cost=");
		sb.append(_cost);
		sb.append(", _depth=");
		sb.append(_depth);
		sb.append(", _displayDate=");
		sb.append(_displayDate);
		sb.append(", _expirationDate=");
		sb.append(_expirationDate);
		sb.append(", _externalReferenceCode='");
		sb.append(_externalReferenceCode + '\'');
		sb.append(", _gtin='");
		sb.append(_gtin);
		sb.append('\'');
		sb.append(", _height=");
		sb.append(_height);
		sb.append(", _id=");
		sb.append(_id);
		sb.append(", _manufacturerPartNumber='");
		sb.append(_manufacturerPartNumber);
		sb.append('\'');
		sb.append(", _neverExpire=");
		sb.append(_neverExpire);
		sb.append(", _price=");
		sb.append(_price);
		sb.append(", _promoPrice=");
		sb.append(_promoPrice);
		sb.append(", _published=");
		sb.append(_published);
		sb.append(", _purchasable=");
		sb.append(_purchasable);
		sb.append(", _sku='");
		sb.append(_sku);
		sb.append('\'');
		sb.append(", _weight=");
		sb.append(_weight);
		sb.append(", _width=");
		sb.append(_width);
		sb.append('}');

		return sb.toString();
	}

	private BigDecimal _cost;
	private Double _depth;
	private Date _displayDate;
	private Date _expirationDate;
	private String _externalReferenceCode;
	private String _gtin;
	private Double _height;
	private Long _id;
	private String _manufacturerPartNumber;
	private Boolean _neverExpire;
	private BigDecimal _price;
	private BigDecimal _promoPrice;
	private Boolean _published;
	private Boolean _purchasable;
	private String _sku;
	private Double _weight;
	private Double _width;

}