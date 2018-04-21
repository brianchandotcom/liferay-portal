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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderNote}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNote
 * @generated
 */
@ProviderType
public class CommerceOrderNoteWrapper implements CommerceOrderNote,
	ModelWrapper<CommerceOrderNote> {
	public CommerceOrderNoteWrapper(CommerceOrderNote commerceOrderNote) {
		_commerceOrderNote = commerceOrderNote;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrderNote.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderNote.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceOrderNoteId", getCommerceOrderNoteId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("content", getContent());
		attributes.put("restricted", isRestricted());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceOrderNoteId = (Long)attributes.get("commerceOrderNoteId");

		if (commerceOrderNoteId != null) {
			setCommerceOrderNoteId(commerceOrderNoteId);
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

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Boolean restricted = (Boolean)attributes.get("restricted");

		if (restricted != null) {
			setRestricted(restricted);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceOrderNoteWrapper((CommerceOrderNote)_commerceOrderNote.clone());
	}

	@Override
	public int compareTo(CommerceOrderNote commerceOrderNote) {
		return _commerceOrderNote.compareTo(commerceOrderNote);
	}

	/**
	* Returns the commerce order ID of this commerce order note.
	*
	* @return the commerce order ID of this commerce order note
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceOrderNote.getCommerceOrderId();
	}

	/**
	* Returns the commerce order note ID of this commerce order note.
	*
	* @return the commerce order note ID of this commerce order note
	*/
	@Override
	public long getCommerceOrderNoteId() {
		return _commerceOrderNote.getCommerceOrderNoteId();
	}

	/**
	* Returns the company ID of this commerce order note.
	*
	* @return the company ID of this commerce order note
	*/
	@Override
	public long getCompanyId() {
		return _commerceOrderNote.getCompanyId();
	}

	/**
	* Returns the content of this commerce order note.
	*
	* @return the content of this commerce order note
	*/
	@Override
	public java.lang.String getContent() {
		return _commerceOrderNote.getContent();
	}

	/**
	* Returns the create date of this commerce order note.
	*
	* @return the create date of this commerce order note
	*/
	@Override
	public Date getCreateDate() {
		return _commerceOrderNote.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceOrderNote.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce order note.
	*
	* @return the group ID of this commerce order note
	*/
	@Override
	public long getGroupId() {
		return _commerceOrderNote.getGroupId();
	}

	/**
	* Returns the modified date of this commerce order note.
	*
	* @return the modified date of this commerce order note
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceOrderNote.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce order note.
	*
	* @return the primary key of this commerce order note
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceOrderNote.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrderNote.getPrimaryKeyObj();
	}

	/**
	* Returns the restricted of this commerce order note.
	*
	* @return the restricted of this commerce order note
	*/
	@Override
	public boolean getRestricted() {
		return _commerceOrderNote.getRestricted();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser() {
		return _commerceOrderNote.getUser();
	}

	/**
	* Returns the user ID of this commerce order note.
	*
	* @return the user ID of this commerce order note
	*/
	@Override
	public long getUserId() {
		return _commerceOrderNote.getUserId();
	}

	/**
	* Returns the user name of this commerce order note.
	*
	* @return the user name of this commerce order note
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceOrderNote.getUserName();
	}

	/**
	* Returns the user uuid of this commerce order note.
	*
	* @return the user uuid of this commerce order note
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceOrderNote.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrderNote.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrderNote.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrderNote.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceOrderNote.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce order note is restricted.
	*
	* @return <code>true</code> if this commerce order note is restricted; <code>false</code> otherwise
	*/
	@Override
	public boolean isRestricted() {
		return _commerceOrderNote.isRestricted();
	}

	@Override
	public void persist() {
		_commerceOrderNote.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceOrderNote.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce order ID of this commerce order note.
	*
	* @param commerceOrderId the commerce order ID of this commerce order note
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderNote.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the commerce order note ID of this commerce order note.
	*
	* @param commerceOrderNoteId the commerce order note ID of this commerce order note
	*/
	@Override
	public void setCommerceOrderNoteId(long commerceOrderNoteId) {
		_commerceOrderNote.setCommerceOrderNoteId(commerceOrderNoteId);
	}

	/**
	* Sets the company ID of this commerce order note.
	*
	* @param companyId the company ID of this commerce order note
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceOrderNote.setCompanyId(companyId);
	}

	/**
	* Sets the content of this commerce order note.
	*
	* @param content the content of this commerce order note
	*/
	@Override
	public void setContent(java.lang.String content) {
		_commerceOrderNote.setContent(content);
	}

	/**
	* Sets the create date of this commerce order note.
	*
	* @param createDate the create date of this commerce order note
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceOrderNote.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceOrderNote.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceOrderNote.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceOrderNote.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce order note.
	*
	* @param groupId the group ID of this commerce order note
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceOrderNote.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce order note.
	*
	* @param modifiedDate the modified date of this commerce order note
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceOrderNote.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceOrderNote.setNew(n);
	}

	/**
	* Sets the primary key of this commerce order note.
	*
	* @param primaryKey the primary key of this commerce order note
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceOrderNote.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceOrderNote.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this commerce order note is restricted.
	*
	* @param restricted the restricted of this commerce order note
	*/
	@Override
	public void setRestricted(boolean restricted) {
		_commerceOrderNote.setRestricted(restricted);
	}

	/**
	* Sets the user ID of this commerce order note.
	*
	* @param userId the user ID of this commerce order note
	*/
	@Override
	public void setUserId(long userId) {
		_commerceOrderNote.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce order note.
	*
	* @param userName the user name of this commerce order note
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceOrderNote.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce order note.
	*
	* @param userUuid the user uuid of this commerce order note
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceOrderNote.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrderNote> toCacheModel() {
		return _commerceOrderNote.toCacheModel();
	}

	@Override
	public CommerceOrderNote toEscapedModel() {
		return new CommerceOrderNoteWrapper(_commerceOrderNote.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceOrderNote.toString();
	}

	@Override
	public CommerceOrderNote toUnescapedModel() {
		return new CommerceOrderNoteWrapper(_commerceOrderNote.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceOrderNote.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderNoteWrapper)) {
			return false;
		}

		CommerceOrderNoteWrapper commerceOrderNoteWrapper = (CommerceOrderNoteWrapper)obj;

		if (Objects.equals(_commerceOrderNote,
					commerceOrderNoteWrapper._commerceOrderNote)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceOrderNote getWrappedModel() {
		return _commerceOrderNote;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceOrderNote.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceOrderNote.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceOrderNote.resetOriginalValues();
	}

	private final CommerceOrderNote _commerceOrderNote;
}