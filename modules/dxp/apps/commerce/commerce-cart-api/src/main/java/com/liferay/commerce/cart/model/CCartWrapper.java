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

package com.liferay.commerce.cart.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CCart}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CCart
 * @generated
 */
@ProviderType
public class CCartWrapper implements CCart, ModelWrapper<CCart> {
	public CCartWrapper(CCart cCart) {
		_cCart = cCart;
	}

	@Override
	public Class<?> getModelClass() {
		return CCart.class;
	}

	@Override
	public String getModelClassName() {
		return CCart.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CCartId", getCCartId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("cartUserId", getCartUserId());
		attributes.put("type", getType());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CCartId = (Long)attributes.get("CCartId");

		if (CCartId != null) {
			setCCartId(CCartId);
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

		Long cartUserId = (Long)attributes.get("cartUserId");

		if (cartUserId != null) {
			setCartUserId(cartUserId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	@Override
	public CCart toEscapedModel() {
		return new CCartWrapper(_cCart.toEscapedModel());
	}

	@Override
	public CCart toUnescapedModel() {
		return new CCartWrapper(_cCart.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cCart.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cCart.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cCart.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cCart.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CCart> toCacheModel() {
		return _cCart.toCacheModel();
	}

	@Override
	public int compareTo(CCart cCart) {
		return _cCart.compareTo(cCart);
	}

	/**
	* Returns the type of this c cart.
	*
	* @return the type of this c cart
	*/
	@Override
	public int getType() {
		return _cCart.getType();
	}

	@Override
	public int hashCode() {
		return _cCart.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cCart.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CCartWrapper((CCart)_cCart.clone());
	}

	/**
	* Returns the cart user uuid of this c cart.
	*
	* @return the cart user uuid of this c cart
	*/
	@Override
	public java.lang.String getCartUserUuid() {
		return _cCart.getCartUserUuid();
	}

	/**
	* Returns the title of this c cart.
	*
	* @return the title of this c cart
	*/
	@Override
	public java.lang.String getTitle() {
		return _cCart.getTitle();
	}

	/**
	* Returns the user name of this c cart.
	*
	* @return the user name of this c cart
	*/
	@Override
	public java.lang.String getUserName() {
		return _cCart.getUserName();
	}

	/**
	* Returns the user uuid of this c cart.
	*
	* @return the user uuid of this c cart
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cCart.getUserUuid();
	}

	/**
	* Returns the uuid of this c cart.
	*
	* @return the uuid of this c cart
	*/
	@Override
	public java.lang.String getUuid() {
		return _cCart.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cCart.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cCart.toXmlString();
	}

	/**
	* Returns the create date of this c cart.
	*
	* @return the create date of this c cart
	*/
	@Override
	public Date getCreateDate() {
		return _cCart.getCreateDate();
	}

	/**
	* Returns the modified date of this c cart.
	*
	* @return the modified date of this c cart
	*/
	@Override
	public Date getModifiedDate() {
		return _cCart.getModifiedDate();
	}

	/**
	* Returns the c cart ID of this c cart.
	*
	* @return the c cart ID of this c cart
	*/
	@Override
	public long getCCartId() {
		return _cCart.getCCartId();
	}

	/**
	* Returns the cart user ID of this c cart.
	*
	* @return the cart user ID of this c cart
	*/
	@Override
	public long getCartUserId() {
		return _cCart.getCartUserId();
	}

	/**
	* Returns the company ID of this c cart.
	*
	* @return the company ID of this c cart
	*/
	@Override
	public long getCompanyId() {
		return _cCart.getCompanyId();
	}

	/**
	* Returns the group ID of this c cart.
	*
	* @return the group ID of this c cart
	*/
	@Override
	public long getGroupId() {
		return _cCart.getGroupId();
	}

	/**
	* Returns the primary key of this c cart.
	*
	* @return the primary key of this c cart
	*/
	@Override
	public long getPrimaryKey() {
		return _cCart.getPrimaryKey();
	}

	/**
	* Returns the user ID of this c cart.
	*
	* @return the user ID of this c cart
	*/
	@Override
	public long getUserId() {
		return _cCart.getUserId();
	}

	@Override
	public void persist() {
		_cCart.persist();
	}

	/**
	* Sets the c cart ID of this c cart.
	*
	* @param CCartId the c cart ID of this c cart
	*/
	@Override
	public void setCCartId(long CCartId) {
		_cCart.setCCartId(CCartId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cCart.setCachedModel(cachedModel);
	}

	/**
	* Sets the cart user ID of this c cart.
	*
	* @param cartUserId the cart user ID of this c cart
	*/
	@Override
	public void setCartUserId(long cartUserId) {
		_cCart.setCartUserId(cartUserId);
	}

	/**
	* Sets the cart user uuid of this c cart.
	*
	* @param cartUserUuid the cart user uuid of this c cart
	*/
	@Override
	public void setCartUserUuid(java.lang.String cartUserUuid) {
		_cCart.setCartUserUuid(cartUserUuid);
	}

	/**
	* Sets the company ID of this c cart.
	*
	* @param companyId the company ID of this c cart
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cCart.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this c cart.
	*
	* @param createDate the create date of this c cart
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cCart.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cCart.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cCart.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cCart.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this c cart.
	*
	* @param groupId the group ID of this c cart
	*/
	@Override
	public void setGroupId(long groupId) {
		_cCart.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this c cart.
	*
	* @param modifiedDate the modified date of this c cart
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cCart.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cCart.setNew(n);
	}

	/**
	* Sets the primary key of this c cart.
	*
	* @param primaryKey the primary key of this c cart
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cCart.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cCart.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this c cart.
	*
	* @param title the title of this c cart
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cCart.setTitle(title);
	}

	/**
	* Sets the type of this c cart.
	*
	* @param type the type of this c cart
	*/
	@Override
	public void setType(int type) {
		_cCart.setType(type);
	}

	/**
	* Sets the user ID of this c cart.
	*
	* @param userId the user ID of this c cart
	*/
	@Override
	public void setUserId(long userId) {
		_cCart.setUserId(userId);
	}

	/**
	* Sets the user name of this c cart.
	*
	* @param userName the user name of this c cart
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cCart.setUserName(userName);
	}

	/**
	* Sets the user uuid of this c cart.
	*
	* @param userUuid the user uuid of this c cart
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cCart.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this c cart.
	*
	* @param uuid the uuid of this c cart
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cCart.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CCartWrapper)) {
			return false;
		}

		CCartWrapper cCartWrapper = (CCartWrapper)obj;

		if (Objects.equals(_cCart, cCartWrapper._cCart)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cCart.getStagedModelType();
	}

	@Override
	public CCart getWrappedModel() {
		return _cCart;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cCart.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cCart.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cCart.resetOriginalValues();
	}

	private final CCart _cCart;
}