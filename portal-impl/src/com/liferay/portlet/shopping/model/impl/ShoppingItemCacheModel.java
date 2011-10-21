/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.shopping.model.ShoppingItem;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ShoppingItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItem
 * @generated
 */
public class ShoppingItemCacheModel implements CacheModel<ShoppingItem>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(69);

		sb.append("{itemId=");
		sb.append(itemId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", properties=");
		sb.append(properties);
		sb.append(", fields=");
		sb.append(fields);
		sb.append(", fieldsQuantities=");
		sb.append(fieldsQuantities);
		sb.append(", minQuantity=");
		sb.append(minQuantity);
		sb.append(", maxQuantity=");
		sb.append(maxQuantity);
		sb.append(", price=");
		sb.append(price);
		sb.append(", discount=");
		sb.append(discount);
		sb.append(", taxable=");
		sb.append(taxable);
		sb.append(", shipping=");
		sb.append(shipping);
		sb.append(", useShippingFormula=");
		sb.append(useShippingFormula);
		sb.append(", requiresShipping=");
		sb.append(requiresShipping);
		sb.append(", stockQuantity=");
		sb.append(stockQuantity);
		sb.append(", featured=");
		sb.append(featured);
		sb.append(", sale=");
		sb.append(sale);
		sb.append(", smallImage=");
		sb.append(smallImage);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", smallImageURL=");
		sb.append(smallImageURL);
		sb.append(", mediumImage=");
		sb.append(mediumImage);
		sb.append(", mediumImageId=");
		sb.append(mediumImageId);
		sb.append(", mediumImageURL=");
		sb.append(mediumImageURL);
		sb.append(", largeImage=");
		sb.append(largeImage);
		sb.append(", largeImageId=");
		sb.append(largeImageId);
		sb.append(", largeImageURL=");
		sb.append(largeImageURL);
		sb.append("}");

		return sb.toString();
	}

	public ShoppingItem toEntityModel() {
		ShoppingItemImpl shoppingItemImpl = new ShoppingItemImpl();

		shoppingItemImpl.setItemId(itemId);
		shoppingItemImpl.setGroupId(groupId);
		shoppingItemImpl.setCompanyId(companyId);
		shoppingItemImpl.setUserId(userId);

		if (userName == null) {
			shoppingItemImpl.setUserName(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			shoppingItemImpl.setCreateDate(null);
		}
		else {
			shoppingItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			shoppingItemImpl.setModifiedDate(null);
		}
		else {
			shoppingItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		shoppingItemImpl.setCategoryId(categoryId);

		if (sku == null) {
			shoppingItemImpl.setSku(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setSku(sku);
		}

		if (name == null) {
			shoppingItemImpl.setName(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setName(name);
		}

		if (description == null) {
			shoppingItemImpl.setDescription(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setDescription(description);
		}

		if (properties == null) {
			shoppingItemImpl.setProperties(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setProperties(properties);
		}

		shoppingItemImpl.setFields(fields);

		if (fieldsQuantities == null) {
			shoppingItemImpl.setFieldsQuantities(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setFieldsQuantities(fieldsQuantities);
		}

		shoppingItemImpl.setMinQuantity(minQuantity);
		shoppingItemImpl.setMaxQuantity(maxQuantity);
		shoppingItemImpl.setPrice(price);
		shoppingItemImpl.setDiscount(discount);
		shoppingItemImpl.setTaxable(taxable);
		shoppingItemImpl.setShipping(shipping);
		shoppingItemImpl.setUseShippingFormula(useShippingFormula);
		shoppingItemImpl.setRequiresShipping(requiresShipping);
		shoppingItemImpl.setStockQuantity(stockQuantity);
		shoppingItemImpl.setFeatured(featured);
		shoppingItemImpl.setSale(sale);
		shoppingItemImpl.setSmallImage(smallImage);
		shoppingItemImpl.setSmallImageId(smallImageId);

		if (smallImageURL == null) {
			shoppingItemImpl.setSmallImageURL(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setSmallImageURL(smallImageURL);
		}

		shoppingItemImpl.setMediumImage(mediumImage);
		shoppingItemImpl.setMediumImageId(mediumImageId);

		if (mediumImageURL == null) {
			shoppingItemImpl.setMediumImageURL(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setMediumImageURL(mediumImageURL);
		}

		shoppingItemImpl.setLargeImage(largeImage);
		shoppingItemImpl.setLargeImageId(largeImageId);

		if (largeImageURL == null) {
			shoppingItemImpl.setLargeImageURL(StringPool.BLANK);
		}
		else {
			shoppingItemImpl.setLargeImageURL(largeImageURL);
		}

		shoppingItemImpl.resetOriginalValues();

		return shoppingItemImpl;
	}

	public long itemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long categoryId;
	public String sku;
	public String name;
	public String description;
	public String properties;
	public boolean fields;
	public String fieldsQuantities;
	public int minQuantity;
	public int maxQuantity;
	public double price;
	public double discount;
	public boolean taxable;
	public double shipping;
	public boolean useShippingFormula;
	public boolean requiresShipping;
	public int stockQuantity;
	public boolean featured;
	public boolean sale;
	public boolean smallImage;
	public long smallImageId;
	public String smallImageURL;
	public boolean mediumImage;
	public long mediumImageId;
	public String mediumImageURL;
	public boolean largeImage;
	public long largeImageId;
	public String largeImageURL;
}