/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItem
 * @generated
 */
public class ShoppingItemWrapper implements ShoppingItem,
	ModelWrapper<ShoppingItem> {
	public ShoppingItemWrapper(ShoppingItem shoppingItem) {
		_shoppingItem = shoppingItem;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingItem.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("itemId", getItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("categoryId", getCategoryId());
		attributes.put("sku", getSku());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("properties", getProperties());
		attributes.put("fields", getFields());
		attributes.put("fieldsQuantities", getFieldsQuantities());
		attributes.put("minQuantity", getMinQuantity());
		attributes.put("maxQuantity", getMaxQuantity());
		attributes.put("price", getPrice());
		attributes.put("discount", getDiscount());
		attributes.put("taxable", getTaxable());
		attributes.put("shipping", getShipping());
		attributes.put("useShippingFormula", getUseShippingFormula());
		attributes.put("requiresShipping", getRequiresShipping());
		attributes.put("stockQuantity", getStockQuantity());
		attributes.put("featured", getFeatured());
		attributes.put("sale", getSale());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("mediumImage", getMediumImage());
		attributes.put("mediumImageId", getMediumImageId());
		attributes.put("mediumImageURL", getMediumImageURL());
		attributes.put("largeImage", getLargeImage());
		attributes.put("largeImageId", getLargeImageId());
		attributes.put("largeImageURL", getLargeImageURL());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long itemId = (Long)attributes.get("itemId");

		if (itemId != null) {
			setItemId(itemId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String properties = (String)attributes.get("properties");

		if (properties != null) {
			setProperties(properties);
		}

		Boolean fields = (Boolean)attributes.get("fields");

		if (fields != null) {
			setFields(fields);
		}

		String fieldsQuantities = (String)attributes.get("fieldsQuantities");

		if (fieldsQuantities != null) {
			setFieldsQuantities(fieldsQuantities);
		}

		Integer minQuantity = (Integer)attributes.get("minQuantity");

		if (minQuantity != null) {
			setMinQuantity(minQuantity);
		}

		Integer maxQuantity = (Integer)attributes.get("maxQuantity");

		if (maxQuantity != null) {
			setMaxQuantity(maxQuantity);
		}

		Double price = (Double)attributes.get("price");

		if (price != null) {
			setPrice(price);
		}

		Double discount = (Double)attributes.get("discount");

		if (discount != null) {
			setDiscount(discount);
		}

		Boolean taxable = (Boolean)attributes.get("taxable");

		if (taxable != null) {
			setTaxable(taxable);
		}

		Double shipping = (Double)attributes.get("shipping");

		if (shipping != null) {
			setShipping(shipping);
		}

		Boolean useShippingFormula = (Boolean)attributes.get(
				"useShippingFormula");

		if (useShippingFormula != null) {
			setUseShippingFormula(useShippingFormula);
		}

		Boolean requiresShipping = (Boolean)attributes.get("requiresShipping");

		if (requiresShipping != null) {
			setRequiresShipping(requiresShipping);
		}

		Integer stockQuantity = (Integer)attributes.get("stockQuantity");

		if (stockQuantity != null) {
			setStockQuantity(stockQuantity);
		}

		Boolean featured = (Boolean)attributes.get("featured");

		if (featured != null) {
			setFeatured(featured);
		}

		Boolean sale = (Boolean)attributes.get("sale");

		if (sale != null) {
			setSale(sale);
		}

		Boolean smallImage = (Boolean)attributes.get("smallImage");

		if (smallImage != null) {
			setSmallImage(smallImage);
		}

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		String smallImageURL = (String)attributes.get("smallImageURL");

		if (smallImageURL != null) {
			setSmallImageURL(smallImageURL);
		}

		Boolean mediumImage = (Boolean)attributes.get("mediumImage");

		if (mediumImage != null) {
			setMediumImage(mediumImage);
		}

		Long mediumImageId = (Long)attributes.get("mediumImageId");

		if (mediumImageId != null) {
			setMediumImageId(mediumImageId);
		}

		String mediumImageURL = (String)attributes.get("mediumImageURL");

		if (mediumImageURL != null) {
			setMediumImageURL(mediumImageURL);
		}

		Boolean largeImage = (Boolean)attributes.get("largeImage");

		if (largeImage != null) {
			setLargeImage(largeImage);
		}

		Long largeImageId = (Long)attributes.get("largeImageId");

		if (largeImageId != null) {
			setLargeImageId(largeImageId);
		}

		String largeImageURL = (String)attributes.get("largeImageURL");

		if (largeImageURL != null) {
			setLargeImageURL(largeImageURL);
		}
	}

	/**
	* Returns the primary key of this shopping item.
	*
	* @return the primary key of this shopping item
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this shopping item.
	*
	* @param primaryKey the primary key of this shopping item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingItem.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the item ID of this shopping item.
	*
	* @return the item ID of this shopping item
	*/
	@Override
	public long getItemId() {
		return _shoppingItem.getItemId();
	}

	/**
	* Sets the item ID of this shopping item.
	*
	* @param itemId the item ID of this shopping item
	*/
	@Override
	public void setItemId(long itemId) {
		_shoppingItem.setItemId(itemId);
	}

	/**
	* Returns the group ID of this shopping item.
	*
	* @return the group ID of this shopping item
	*/
	@Override
	public long getGroupId() {
		return _shoppingItem.getGroupId();
	}

	/**
	* Sets the group ID of this shopping item.
	*
	* @param groupId the group ID of this shopping item
	*/
	@Override
	public void setGroupId(long groupId) {
		_shoppingItem.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this shopping item.
	*
	* @return the company ID of this shopping item
	*/
	@Override
	public long getCompanyId() {
		return _shoppingItem.getCompanyId();
	}

	/**
	* Sets the company ID of this shopping item.
	*
	* @param companyId the company ID of this shopping item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingItem.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this shopping item.
	*
	* @return the user ID of this shopping item
	*/
	@Override
	public long getUserId() {
		return _shoppingItem.getUserId();
	}

	/**
	* Sets the user ID of this shopping item.
	*
	* @param userId the user ID of this shopping item
	*/
	@Override
	public void setUserId(long userId) {
		_shoppingItem.setUserId(userId);
	}

	/**
	* Returns the user uuid of this shopping item.
	*
	* @return the user uuid of this shopping item
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItem.getUserUuid();
	}

	/**
	* Sets the user uuid of this shopping item.
	*
	* @param userUuid the user uuid of this shopping item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_shoppingItem.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this shopping item.
	*
	* @return the user name of this shopping item
	*/
	@Override
	public java.lang.String getUserName() {
		return _shoppingItem.getUserName();
	}

	/**
	* Sets the user name of this shopping item.
	*
	* @param userName the user name of this shopping item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_shoppingItem.setUserName(userName);
	}

	/**
	* Returns the create date of this shopping item.
	*
	* @return the create date of this shopping item
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _shoppingItem.getCreateDate();
	}

	/**
	* Sets the create date of this shopping item.
	*
	* @param createDate the create date of this shopping item
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_shoppingItem.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this shopping item.
	*
	* @return the modified date of this shopping item
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _shoppingItem.getModifiedDate();
	}

	/**
	* Sets the modified date of this shopping item.
	*
	* @param modifiedDate the modified date of this shopping item
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingItem.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the category ID of this shopping item.
	*
	* @return the category ID of this shopping item
	*/
	@Override
	public long getCategoryId() {
		return _shoppingItem.getCategoryId();
	}

	/**
	* Sets the category ID of this shopping item.
	*
	* @param categoryId the category ID of this shopping item
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_shoppingItem.setCategoryId(categoryId);
	}

	/**
	* Returns the sku of this shopping item.
	*
	* @return the sku of this shopping item
	*/
	@Override
	public java.lang.String getSku() {
		return _shoppingItem.getSku();
	}

	/**
	* Sets the sku of this shopping item.
	*
	* @param sku the sku of this shopping item
	*/
	@Override
	public void setSku(java.lang.String sku) {
		_shoppingItem.setSku(sku);
	}

	/**
	* Returns the name of this shopping item.
	*
	* @return the name of this shopping item
	*/
	@Override
	public java.lang.String getName() {
		return _shoppingItem.getName();
	}

	/**
	* Sets the name of this shopping item.
	*
	* @param name the name of this shopping item
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingItem.setName(name);
	}

	/**
	* Returns the description of this shopping item.
	*
	* @return the description of this shopping item
	*/
	@Override
	public java.lang.String getDescription() {
		return _shoppingItem.getDescription();
	}

	/**
	* Sets the description of this shopping item.
	*
	* @param description the description of this shopping item
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingItem.setDescription(description);
	}

	/**
	* Returns the properties of this shopping item.
	*
	* @return the properties of this shopping item
	*/
	@Override
	public java.lang.String getProperties() {
		return _shoppingItem.getProperties();
	}

	/**
	* Sets the properties of this shopping item.
	*
	* @param properties the properties of this shopping item
	*/
	@Override
	public void setProperties(java.lang.String properties) {
		_shoppingItem.setProperties(properties);
	}

	/**
	* Returns the fields of this shopping item.
	*
	* @return the fields of this shopping item
	*/
	@Override
	public boolean getFields() {
		return _shoppingItem.getFields();
	}

	/**
	* Returns <code>true</code> if this shopping item is fields.
	*
	* @return <code>true</code> if this shopping item is fields; <code>false</code> otherwise
	*/
	@Override
	public boolean isFields() {
		return _shoppingItem.isFields();
	}

	/**
	* Sets whether this shopping item is fields.
	*
	* @param fields the fields of this shopping item
	*/
	@Override
	public void setFields(boolean fields) {
		_shoppingItem.setFields(fields);
	}

	/**
	* Returns the fields quantities of this shopping item.
	*
	* @return the fields quantities of this shopping item
	*/
	@Override
	public java.lang.String getFieldsQuantities() {
		return _shoppingItem.getFieldsQuantities();
	}

	/**
	* Sets the fields quantities of this shopping item.
	*
	* @param fieldsQuantities the fields quantities of this shopping item
	*/
	@Override
	public void setFieldsQuantities(java.lang.String fieldsQuantities) {
		_shoppingItem.setFieldsQuantities(fieldsQuantities);
	}

	/**
	* Returns the min quantity of this shopping item.
	*
	* @return the min quantity of this shopping item
	*/
	@Override
	public int getMinQuantity() {
		return _shoppingItem.getMinQuantity();
	}

	/**
	* Sets the min quantity of this shopping item.
	*
	* @param minQuantity the min quantity of this shopping item
	*/
	@Override
	public void setMinQuantity(int minQuantity) {
		_shoppingItem.setMinQuantity(minQuantity);
	}

	/**
	* Returns the max quantity of this shopping item.
	*
	* @return the max quantity of this shopping item
	*/
	@Override
	public int getMaxQuantity() {
		return _shoppingItem.getMaxQuantity();
	}

	/**
	* Sets the max quantity of this shopping item.
	*
	* @param maxQuantity the max quantity of this shopping item
	*/
	@Override
	public void setMaxQuantity(int maxQuantity) {
		_shoppingItem.setMaxQuantity(maxQuantity);
	}

	/**
	* Returns the price of this shopping item.
	*
	* @return the price of this shopping item
	*/
	@Override
	public double getPrice() {
		return _shoppingItem.getPrice();
	}

	/**
	* Sets the price of this shopping item.
	*
	* @param price the price of this shopping item
	*/
	@Override
	public void setPrice(double price) {
		_shoppingItem.setPrice(price);
	}

	/**
	* Returns the discount of this shopping item.
	*
	* @return the discount of this shopping item
	*/
	@Override
	public double getDiscount() {
		return _shoppingItem.getDiscount();
	}

	/**
	* Sets the discount of this shopping item.
	*
	* @param discount the discount of this shopping item
	*/
	@Override
	public void setDiscount(double discount) {
		_shoppingItem.setDiscount(discount);
	}

	/**
	* Returns the taxable of this shopping item.
	*
	* @return the taxable of this shopping item
	*/
	@Override
	public boolean getTaxable() {
		return _shoppingItem.getTaxable();
	}

	/**
	* Returns <code>true</code> if this shopping item is taxable.
	*
	* @return <code>true</code> if this shopping item is taxable; <code>false</code> otherwise
	*/
	@Override
	public boolean isTaxable() {
		return _shoppingItem.isTaxable();
	}

	/**
	* Sets whether this shopping item is taxable.
	*
	* @param taxable the taxable of this shopping item
	*/
	@Override
	public void setTaxable(boolean taxable) {
		_shoppingItem.setTaxable(taxable);
	}

	/**
	* Returns the shipping of this shopping item.
	*
	* @return the shipping of this shopping item
	*/
	@Override
	public double getShipping() {
		return _shoppingItem.getShipping();
	}

	/**
	* Sets the shipping of this shopping item.
	*
	* @param shipping the shipping of this shopping item
	*/
	@Override
	public void setShipping(double shipping) {
		_shoppingItem.setShipping(shipping);
	}

	/**
	* Returns the use shipping formula of this shopping item.
	*
	* @return the use shipping formula of this shopping item
	*/
	@Override
	public boolean getUseShippingFormula() {
		return _shoppingItem.getUseShippingFormula();
	}

	/**
	* Returns <code>true</code> if this shopping item is use shipping formula.
	*
	* @return <code>true</code> if this shopping item is use shipping formula; <code>false</code> otherwise
	*/
	@Override
	public boolean isUseShippingFormula() {
		return _shoppingItem.isUseShippingFormula();
	}

	/**
	* Sets whether this shopping item is use shipping formula.
	*
	* @param useShippingFormula the use shipping formula of this shopping item
	*/
	@Override
	public void setUseShippingFormula(boolean useShippingFormula) {
		_shoppingItem.setUseShippingFormula(useShippingFormula);
	}

	/**
	* Returns the requires shipping of this shopping item.
	*
	* @return the requires shipping of this shopping item
	*/
	@Override
	public boolean getRequiresShipping() {
		return _shoppingItem.getRequiresShipping();
	}

	/**
	* Returns <code>true</code> if this shopping item is requires shipping.
	*
	* @return <code>true</code> if this shopping item is requires shipping; <code>false</code> otherwise
	*/
	@Override
	public boolean isRequiresShipping() {
		return _shoppingItem.isRequiresShipping();
	}

	/**
	* Sets whether this shopping item is requires shipping.
	*
	* @param requiresShipping the requires shipping of this shopping item
	*/
	@Override
	public void setRequiresShipping(boolean requiresShipping) {
		_shoppingItem.setRequiresShipping(requiresShipping);
	}

	/**
	* Returns the stock quantity of this shopping item.
	*
	* @return the stock quantity of this shopping item
	*/
	@Override
	public int getStockQuantity() {
		return _shoppingItem.getStockQuantity();
	}

	/**
	* Sets the stock quantity of this shopping item.
	*
	* @param stockQuantity the stock quantity of this shopping item
	*/
	@Override
	public void setStockQuantity(int stockQuantity) {
		_shoppingItem.setStockQuantity(stockQuantity);
	}

	/**
	* Returns the featured of this shopping item.
	*
	* @return the featured of this shopping item
	*/
	@Override
	public boolean getFeatured() {
		return _shoppingItem.getFeatured();
	}

	/**
	* Returns <code>true</code> if this shopping item is featured.
	*
	* @return <code>true</code> if this shopping item is featured; <code>false</code> otherwise
	*/
	@Override
	public boolean isFeatured() {
		return _shoppingItem.isFeatured();
	}

	/**
	* Sets whether this shopping item is featured.
	*
	* @param featured the featured of this shopping item
	*/
	@Override
	public void setFeatured(boolean featured) {
		_shoppingItem.setFeatured(featured);
	}

	/**
	* Returns the sale of this shopping item.
	*
	* @return the sale of this shopping item
	*/
	@Override
	public boolean getSale() {
		return _shoppingItem.getSale();
	}

	/**
	* Returns <code>true</code> if this shopping item is sale.
	*
	* @return <code>true</code> if this shopping item is sale; <code>false</code> otherwise
	*/
	@Override
	public boolean isSale() {
		return _shoppingItem.isSale();
	}

	/**
	* Sets whether this shopping item is sale.
	*
	* @param sale the sale of this shopping item
	*/
	@Override
	public void setSale(boolean sale) {
		_shoppingItem.setSale(sale);
	}

	/**
	* Returns the small image of this shopping item.
	*
	* @return the small image of this shopping item
	*/
	@Override
	public boolean getSmallImage() {
		return _shoppingItem.getSmallImage();
	}

	/**
	* Returns <code>true</code> if this shopping item is small image.
	*
	* @return <code>true</code> if this shopping item is small image; <code>false</code> otherwise
	*/
	@Override
	public boolean isSmallImage() {
		return _shoppingItem.isSmallImage();
	}

	/**
	* Sets whether this shopping item is small image.
	*
	* @param smallImage the small image of this shopping item
	*/
	@Override
	public void setSmallImage(boolean smallImage) {
		_shoppingItem.setSmallImage(smallImage);
	}

	/**
	* Returns the small image ID of this shopping item.
	*
	* @return the small image ID of this shopping item
	*/
	@Override
	public long getSmallImageId() {
		return _shoppingItem.getSmallImageId();
	}

	/**
	* Sets the small image ID of this shopping item.
	*
	* @param smallImageId the small image ID of this shopping item
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_shoppingItem.setSmallImageId(smallImageId);
	}

	/**
	* Returns the small image u r l of this shopping item.
	*
	* @return the small image u r l of this shopping item
	*/
	@Override
	public java.lang.String getSmallImageURL() {
		return _shoppingItem.getSmallImageURL();
	}

	/**
	* Sets the small image u r l of this shopping item.
	*
	* @param smallImageURL the small image u r l of this shopping item
	*/
	@Override
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_shoppingItem.setSmallImageURL(smallImageURL);
	}

	/**
	* Returns the medium image of this shopping item.
	*
	* @return the medium image of this shopping item
	*/
	@Override
	public boolean getMediumImage() {
		return _shoppingItem.getMediumImage();
	}

	/**
	* Returns <code>true</code> if this shopping item is medium image.
	*
	* @return <code>true</code> if this shopping item is medium image; <code>false</code> otherwise
	*/
	@Override
	public boolean isMediumImage() {
		return _shoppingItem.isMediumImage();
	}

	/**
	* Sets whether this shopping item is medium image.
	*
	* @param mediumImage the medium image of this shopping item
	*/
	@Override
	public void setMediumImage(boolean mediumImage) {
		_shoppingItem.setMediumImage(mediumImage);
	}

	/**
	* Returns the medium image ID of this shopping item.
	*
	* @return the medium image ID of this shopping item
	*/
	@Override
	public long getMediumImageId() {
		return _shoppingItem.getMediumImageId();
	}

	/**
	* Sets the medium image ID of this shopping item.
	*
	* @param mediumImageId the medium image ID of this shopping item
	*/
	@Override
	public void setMediumImageId(long mediumImageId) {
		_shoppingItem.setMediumImageId(mediumImageId);
	}

	/**
	* Returns the medium image u r l of this shopping item.
	*
	* @return the medium image u r l of this shopping item
	*/
	@Override
	public java.lang.String getMediumImageURL() {
		return _shoppingItem.getMediumImageURL();
	}

	/**
	* Sets the medium image u r l of this shopping item.
	*
	* @param mediumImageURL the medium image u r l of this shopping item
	*/
	@Override
	public void setMediumImageURL(java.lang.String mediumImageURL) {
		_shoppingItem.setMediumImageURL(mediumImageURL);
	}

	/**
	* Returns the large image of this shopping item.
	*
	* @return the large image of this shopping item
	*/
	@Override
	public boolean getLargeImage() {
		return _shoppingItem.getLargeImage();
	}

	/**
	* Returns <code>true</code> if this shopping item is large image.
	*
	* @return <code>true</code> if this shopping item is large image; <code>false</code> otherwise
	*/
	@Override
	public boolean isLargeImage() {
		return _shoppingItem.isLargeImage();
	}

	/**
	* Sets whether this shopping item is large image.
	*
	* @param largeImage the large image of this shopping item
	*/
	@Override
	public void setLargeImage(boolean largeImage) {
		_shoppingItem.setLargeImage(largeImage);
	}

	/**
	* Returns the large image ID of this shopping item.
	*
	* @return the large image ID of this shopping item
	*/
	@Override
	public long getLargeImageId() {
		return _shoppingItem.getLargeImageId();
	}

	/**
	* Sets the large image ID of this shopping item.
	*
	* @param largeImageId the large image ID of this shopping item
	*/
	@Override
	public void setLargeImageId(long largeImageId) {
		_shoppingItem.setLargeImageId(largeImageId);
	}

	/**
	* Returns the large image u r l of this shopping item.
	*
	* @return the large image u r l of this shopping item
	*/
	@Override
	public java.lang.String getLargeImageURL() {
		return _shoppingItem.getLargeImageURL();
	}

	/**
	* Sets the large image u r l of this shopping item.
	*
	* @param largeImageURL the large image u r l of this shopping item
	*/
	@Override
	public void setLargeImageURL(java.lang.String largeImageURL) {
		_shoppingItem.setLargeImageURL(largeImageURL);
	}

	@Override
	public boolean isNew() {
		return _shoppingItem.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_shoppingItem.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingItem.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingItem.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingItem.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingItem.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingItem.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingItem.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingItem.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingItemWrapper((ShoppingItem)_shoppingItem.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem) {
		return _shoppingItem.compareTo(shoppingItem);
	}

	@Override
	public int hashCode() {
		return _shoppingItem.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingItem> toCacheModel() {
		return _shoppingItem.toCacheModel();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingItem toEscapedModel() {
		return new ShoppingItemWrapper(_shoppingItem.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingItem toUnescapedModel() {
		return new ShoppingItemWrapper(_shoppingItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingItem.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingItem.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingItem.persist();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory() {
		return _shoppingItem.getCategory();
	}

	@Override
	public java.lang.String[] getFieldsQuantitiesArray() {
		return _shoppingItem.getFieldsQuantitiesArray();
	}

	@Override
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> getItemPrices()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _shoppingItem.getItemPrices();
	}

	@Override
	public void setFieldsQuantitiesArray(
		java.lang.String[] fieldsQuantitiesArray) {
		_shoppingItem.setFieldsQuantitiesArray(fieldsQuantitiesArray);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingItemWrapper)) {
			return false;
		}

		ShoppingItemWrapper shoppingItemWrapper = (ShoppingItemWrapper)obj;

		if (Validator.equals(_shoppingItem, shoppingItemWrapper._shoppingItem)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingItem getWrappedShoppingItem() {
		return _shoppingItem;
	}

	@Override
	public ShoppingItem getWrappedModel() {
		return _shoppingItem;
	}

	@Override
	public void resetOriginalValues() {
		_shoppingItem.resetOriginalValues();
	}

	private ShoppingItem _shoppingItem;
}