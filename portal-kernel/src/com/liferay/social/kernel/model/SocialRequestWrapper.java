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

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class SocialRequestWrapper
	extends BaseModelWrapper<SocialRequest>
	implements ModelWrapper<SocialRequest>, SocialRequest {

	public SocialRequestWrapper(SocialRequest socialRequest) {
		super(socialRequest);
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

	/**
	 * Returns the fully qualified class name of this social request.
	 *
	 * @return the fully qualified class name of this social request
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this social request.
	 *
	 * @return the class name ID of this social request
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this social request.
	 *
	 * @return the class pk of this social request
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this social request.
	 *
	 * @return the company ID of this social request
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this social request.
	 *
	 * @return the create date of this social request
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the extra data of this social request.
	 *
	 * @return the extra data of this social request
	 */
	@Override
	public String getExtraData() {
		return model.getExtraData();
	}

	/**
	 * Returns the group ID of this social request.
	 *
	 * @return the group ID of this social request
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this social request.
	 *
	 * @return the modified date of this social request
	 */
	@Override
	public long getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this social request.
	 *
	 * @return the primary key of this social request
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the receiver user ID of this social request.
	 *
	 * @return the receiver user ID of this social request
	 */
	@Override
	public long getReceiverUserId() {
		return model.getReceiverUserId();
	}

	/**
	 * Returns the receiver user uuid of this social request.
	 *
	 * @return the receiver user uuid of this social request
	 */
	@Override
	public String getReceiverUserUuid() {
		return model.getReceiverUserUuid();
	}

	/**
	 * Returns the request ID of this social request.
	 *
	 * @return the request ID of this social request
	 */
	@Override
	public long getRequestId() {
		return model.getRequestId();
	}

	/**
	 * Returns the status of this social request.
	 *
	 * @return the status of this social request
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the type of this social request.
	 *
	 * @return the type of this social request
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this social request.
	 *
	 * @return the user ID of this social request
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this social request.
	 *
	 * @return the user uuid of this social request
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this social request.
	 *
	 * @return the uuid of this social request
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social request model instance should use the <code>SocialRequest</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this social request.
	 *
	 * @param classNameId the class name ID of this social request
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this social request.
	 *
	 * @param classPK the class pk of this social request
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this social request.
	 *
	 * @param companyId the company ID of this social request
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this social request.
	 *
	 * @param createDate the create date of this social request
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the extra data of this social request.
	 *
	 * @param extraData the extra data of this social request
	 */
	@Override
	public void setExtraData(String extraData) {
		model.setExtraData(extraData);
	}

	/**
	 * Sets the group ID of this social request.
	 *
	 * @param groupId the group ID of this social request
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this social request.
	 *
	 * @param modifiedDate the modified date of this social request
	 */
	@Override
	public void setModifiedDate(long modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this social request.
	 *
	 * @param primaryKey the primary key of this social request
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the receiver user ID of this social request.
	 *
	 * @param receiverUserId the receiver user ID of this social request
	 */
	@Override
	public void setReceiverUserId(long receiverUserId) {
		model.setReceiverUserId(receiverUserId);
	}

	/**
	 * Sets the receiver user uuid of this social request.
	 *
	 * @param receiverUserUuid the receiver user uuid of this social request
	 */
	@Override
	public void setReceiverUserUuid(String receiverUserUuid) {
		model.setReceiverUserUuid(receiverUserUuid);
	}

	/**
	 * Sets the request ID of this social request.
	 *
	 * @param requestId the request ID of this social request
	 */
	@Override
	public void setRequestId(long requestId) {
		model.setRequestId(requestId);
	}

	/**
	 * Sets the status of this social request.
	 *
	 * @param status the status of this social request
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the type of this social request.
	 *
	 * @param type the type of this social request
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this social request.
	 *
	 * @param userId the user ID of this social request
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this social request.
	 *
	 * @param userUuid the user uuid of this social request
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this social request.
	 *
	 * @param uuid the uuid of this social request
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected SocialRequestWrapper wrap(SocialRequest socialRequest) {
		return new SocialRequestWrapper(socialRequest);
	}

}