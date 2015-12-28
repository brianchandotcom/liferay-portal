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

package com.liferay.portlet.social.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialRequest}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequest
 * @generated
 */
@ProviderType
public class SocialRequestWrapper implements SocialRequest,
	ModelWrapper<SocialRequest> {
	public SocialRequestWrapper(SocialRequest socialRequest) {
		_socialRequest = socialRequest;
	}

	@Override
	public Class<?> getModelClass() {
		return SocialRequest.class;
	}

	@Override
	public String getModelClassName() {
		return SocialRequest.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("requestId", getRequestId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("type", getType());
		attributes.put("extraData", getExtraData());
		attributes.put("receiverUserId", getReceiverUserId());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long requestId = (Long)attributes.get("requestId");

		if (requestId != null) {
			setRequestId(requestId);
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

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long modifiedDate = (Long)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extraData = (String)attributes.get("extraData");

		if (extraData != null) {
			setExtraData(extraData);
		}

		Long receiverUserId = (Long)attributes.get("receiverUserId");

		if (receiverUserId != null) {
			setReceiverUserId(receiverUserId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SocialRequestWrapper((SocialRequest)_socialRequest.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.social.model.SocialRequest socialRequest) {
		return _socialRequest.compareTo(socialRequest);
	}

	/**
	* Returns the fully qualified class name of this social request.
	*
	* @return the fully qualified class name of this social request
	*/
	@Override
	public java.lang.String getClassName() {
		return _socialRequest.getClassName();
	}

	/**
	* Returns the class name ID of this social request.
	*
	* @return the class name ID of this social request
	*/
	@Override
	public long getClassNameId() {
		return _socialRequest.getClassNameId();
	}

	/**
	* Returns the class p k of this social request.
	*
	* @return the class p k of this social request
	*/
	@Override
	public long getClassPK() {
		return _socialRequest.getClassPK();
	}

	/**
	* Returns the company ID of this social request.
	*
	* @return the company ID of this social request
	*/
	@Override
	public long getCompanyId() {
		return _socialRequest.getCompanyId();
	}

	/**
	* Returns the create date of this social request.
	*
	* @return the create date of this social request
	*/
	@Override
	public long getCreateDate() {
		return _socialRequest.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialRequest.getExpandoBridge();
	}

	/**
	* Returns the extra data of this social request.
	*
	* @return the extra data of this social request
	*/
	@Override
	public java.lang.String getExtraData() {
		return _socialRequest.getExtraData();
	}

	/**
	* Returns the group ID of this social request.
	*
	* @return the group ID of this social request
	*/
	@Override
	public long getGroupId() {
		return _socialRequest.getGroupId();
	}

	/**
	* Returns the modified date of this social request.
	*
	* @return the modified date of this social request
	*/
	@Override
	public long getModifiedDate() {
		return _socialRequest.getModifiedDate();
	}

	/**
	* Returns the primary key of this social request.
	*
	* @return the primary key of this social request
	*/
	@Override
	public long getPrimaryKey() {
		return _socialRequest.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _socialRequest.getPrimaryKeyObj();
	}

	/**
	* Returns the receiver user ID of this social request.
	*
	* @return the receiver user ID of this social request
	*/
	@Override
	public long getReceiverUserId() {
		return _socialRequest.getReceiverUserId();
	}

	/**
	* Returns the receiver user uuid of this social request.
	*
	* @return the receiver user uuid of this social request
	*/
	@Override
	public java.lang.String getReceiverUserUuid() {
		return _socialRequest.getReceiverUserUuid();
	}

	/**
	* Returns the request ID of this social request.
	*
	* @return the request ID of this social request
	*/
	@Override
	public long getRequestId() {
		return _socialRequest.getRequestId();
	}

	/**
	* Returns the status of this social request.
	*
	* @return the status of this social request
	*/
	@Override
	public int getStatus() {
		return _socialRequest.getStatus();
	}

	/**
	* Returns the type of this social request.
	*
	* @return the type of this social request
	*/
	@Override
	public int getType() {
		return _socialRequest.getType();
	}

	/**
	* Returns the user ID of this social request.
	*
	* @return the user ID of this social request
	*/
	@Override
	public long getUserId() {
		return _socialRequest.getUserId();
	}

	/**
	* Returns the user uuid of this social request.
	*
	* @return the user uuid of this social request
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _socialRequest.getUserUuid();
	}

	/**
	* Returns the uuid of this social request.
	*
	* @return the uuid of this social request
	*/
	@Override
	public java.lang.String getUuid() {
		return _socialRequest.getUuid();
	}

	@Override
	public int hashCode() {
		return _socialRequest.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _socialRequest.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _socialRequest.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _socialRequest.isNew();
	}

	@Override
	public void persist() {
		_socialRequest.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_socialRequest.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_socialRequest.setClassName(className);
	}

	/**
	* Sets the class name ID of this social request.
	*
	* @param classNameId the class name ID of this social request
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_socialRequest.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this social request.
	*
	* @param classPK the class p k of this social request
	*/
	@Override
	public void setClassPK(long classPK) {
		_socialRequest.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this social request.
	*
	* @param companyId the company ID of this social request
	*/
	@Override
	public void setCompanyId(long companyId) {
		_socialRequest.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this social request.
	*
	* @param createDate the create date of this social request
	*/
	@Override
	public void setCreateDate(long createDate) {
		_socialRequest.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_socialRequest.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_socialRequest.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialRequest.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the extra data of this social request.
	*
	* @param extraData the extra data of this social request
	*/
	@Override
	public void setExtraData(java.lang.String extraData) {
		_socialRequest.setExtraData(extraData);
	}

	/**
	* Sets the group ID of this social request.
	*
	* @param groupId the group ID of this social request
	*/
	@Override
	public void setGroupId(long groupId) {
		_socialRequest.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this social request.
	*
	* @param modifiedDate the modified date of this social request
	*/
	@Override
	public void setModifiedDate(long modifiedDate) {
		_socialRequest.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_socialRequest.setNew(n);
	}

	/**
	* Sets the primary key of this social request.
	*
	* @param primaryKey the primary key of this social request
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_socialRequest.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialRequest.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the receiver user ID of this social request.
	*
	* @param receiverUserId the receiver user ID of this social request
	*/
	@Override
	public void setReceiverUserId(long receiverUserId) {
		_socialRequest.setReceiverUserId(receiverUserId);
	}

	/**
	* Sets the receiver user uuid of this social request.
	*
	* @param receiverUserUuid the receiver user uuid of this social request
	*/
	@Override
	public void setReceiverUserUuid(java.lang.String receiverUserUuid) {
		_socialRequest.setReceiverUserUuid(receiverUserUuid);
	}

	/**
	* Sets the request ID of this social request.
	*
	* @param requestId the request ID of this social request
	*/
	@Override
	public void setRequestId(long requestId) {
		_socialRequest.setRequestId(requestId);
	}

	/**
	* Sets the status of this social request.
	*
	* @param status the status of this social request
	*/
	@Override
	public void setStatus(int status) {
		_socialRequest.setStatus(status);
	}

	/**
	* Sets the type of this social request.
	*
	* @param type the type of this social request
	*/
	@Override
	public void setType(int type) {
		_socialRequest.setType(type);
	}

	/**
	* Sets the user ID of this social request.
	*
	* @param userId the user ID of this social request
	*/
	@Override
	public void setUserId(long userId) {
		_socialRequest.setUserId(userId);
	}

	/**
	* Sets the user uuid of this social request.
	*
	* @param userUuid the user uuid of this social request
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_socialRequest.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this social request.
	*
	* @param uuid the uuid of this social request
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_socialRequest.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialRequest> toCacheModel() {
		return _socialRequest.toCacheModel();
	}

	@Override
	public com.liferay.portlet.social.model.SocialRequest toEscapedModel() {
		return new SocialRequestWrapper(_socialRequest.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialRequest.toString();
	}

	@Override
	public com.liferay.portlet.social.model.SocialRequest toUnescapedModel() {
		return new SocialRequestWrapper(_socialRequest.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _socialRequest.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialRequestWrapper)) {
			return false;
		}

		SocialRequestWrapper socialRequestWrapper = (SocialRequestWrapper)obj;

		if (Validator.equals(_socialRequest, socialRequestWrapper._socialRequest)) {
			return true;
		}

		return false;
	}

	@Override
	public SocialRequest getWrappedModel() {
		return _socialRequest;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _socialRequest.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _socialRequest.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_socialRequest.resetOriginalValues();
	}

	private final SocialRequest _socialRequest;
}