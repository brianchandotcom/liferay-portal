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

package com.liferay.mobile.device.rules.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleGroupInstance}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstance
 * @generated
 */
@ProviderType
public class MDRRuleGroupInstanceWrapper implements MDRRuleGroupInstance,
	ModelWrapper<MDRRuleGroupInstance> {
	public MDRRuleGroupInstanceWrapper(
		MDRRuleGroupInstance mdrRuleGroupInstance) {
		_mdrRuleGroupInstance = mdrRuleGroupInstance;
	}

	@Override
	public Class<?> getModelClass() {
		return MDRRuleGroupInstance.class;
	}

	@Override
	public String getModelClassName() {
		return MDRRuleGroupInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("ruleGroupInstanceId", getRuleGroupInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("ruleGroupId", getRuleGroupId());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long ruleGroupInstanceId = (Long)attributes.get("ruleGroupInstanceId");

		if (ruleGroupInstanceId != null) {
			setRuleGroupInstanceId(ruleGroupInstanceId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long ruleGroupId = (Long)attributes.get("ruleGroupId");

		if (ruleGroupId != null) {
			setRuleGroupId(ruleGroupId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new MDRRuleGroupInstanceWrapper((MDRRuleGroupInstance)_mdrRuleGroupInstance.clone());
	}

	@Override
	public int compareTo(
		com.liferay.mobile.device.rules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		return _mdrRuleGroupInstance.compareTo(mdrRuleGroupInstance);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getActions() {
		return _mdrRuleGroupInstance.getActions();
	}

	/**
	* Returns the fully qualified class name of this m d r rule group instance.
	*
	* @return the fully qualified class name of this m d r rule group instance
	*/
	@Override
	public java.lang.String getClassName() {
		return _mdrRuleGroupInstance.getClassName();
	}

	/**
	* Returns the class name ID of this m d r rule group instance.
	*
	* @return the class name ID of this m d r rule group instance
	*/
	@Override
	public long getClassNameId() {
		return _mdrRuleGroupInstance.getClassNameId();
	}

	/**
	* Returns the class p k of this m d r rule group instance.
	*
	* @return the class p k of this m d r rule group instance
	*/
	@Override
	public long getClassPK() {
		return _mdrRuleGroupInstance.getClassPK();
	}

	/**
	* Returns the company ID of this m d r rule group instance.
	*
	* @return the company ID of this m d r rule group instance
	*/
	@Override
	public long getCompanyId() {
		return _mdrRuleGroupInstance.getCompanyId();
	}

	/**
	* Returns the create date of this m d r rule group instance.
	*
	* @return the create date of this m d r rule group instance
	*/
	@Override
	public Date getCreateDate() {
		return _mdrRuleGroupInstance.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrRuleGroupInstance.getExpandoBridge();
	}

	/**
	* Returns the group ID of this m d r rule group instance.
	*
	* @return the group ID of this m d r rule group instance
	*/
	@Override
	public long getGroupId() {
		return _mdrRuleGroupInstance.getGroupId();
	}

	/**
	* Returns the last publish date of this m d r rule group instance.
	*
	* @return the last publish date of this m d r rule group instance
	*/
	@Override
	public Date getLastPublishDate() {
		return _mdrRuleGroupInstance.getLastPublishDate();
	}

	/**
	* Returns the modified date of this m d r rule group instance.
	*
	* @return the modified date of this m d r rule group instance
	*/
	@Override
	public Date getModifiedDate() {
		return _mdrRuleGroupInstance.getModifiedDate();
	}

	/**
	* Returns the primary key of this m d r rule group instance.
	*
	* @return the primary key of this m d r rule group instance
	*/
	@Override
	public long getPrimaryKey() {
		return _mdrRuleGroupInstance.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrRuleGroupInstance.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this m d r rule group instance.
	*
	* @return the priority of this m d r rule group instance
	*/
	@Override
	public int getPriority() {
		return _mdrRuleGroupInstance.getPriority();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getRuleGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstance.getRuleGroup();
	}

	/**
	* Returns the rule group ID of this m d r rule group instance.
	*
	* @return the rule group ID of this m d r rule group instance
	*/
	@Override
	public long getRuleGroupId() {
		return _mdrRuleGroupInstance.getRuleGroupId();
	}

	/**
	* Returns the rule group instance ID of this m d r rule group instance.
	*
	* @return the rule group instance ID of this m d r rule group instance
	*/
	@Override
	public long getRuleGroupInstanceId() {
		return _mdrRuleGroupInstance.getRuleGroupInstanceId();
	}

	/**
	* Returns the user ID of this m d r rule group instance.
	*
	* @return the user ID of this m d r rule group instance
	*/
	@Override
	public long getUserId() {
		return _mdrRuleGroupInstance.getUserId();
	}

	/**
	* Returns the user name of this m d r rule group instance.
	*
	* @return the user name of this m d r rule group instance
	*/
	@Override
	public java.lang.String getUserName() {
		return _mdrRuleGroupInstance.getUserName();
	}

	/**
	* Returns the user uuid of this m d r rule group instance.
	*
	* @return the user uuid of this m d r rule group instance
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mdrRuleGroupInstance.getUserUuid();
	}

	/**
	* Returns the uuid of this m d r rule group instance.
	*
	* @return the uuid of this m d r rule group instance
	*/
	@Override
	public java.lang.String getUuid() {
		return _mdrRuleGroupInstance.getUuid();
	}

	@Override
	public int hashCode() {
		return _mdrRuleGroupInstance.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mdrRuleGroupInstance.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mdrRuleGroupInstance.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mdrRuleGroupInstance.isNew();
	}

	@Override
	public void persist() {
		_mdrRuleGroupInstance.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mdrRuleGroupInstance.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_mdrRuleGroupInstance.setClassName(className);
	}

	/**
	* Sets the class name ID of this m d r rule group instance.
	*
	* @param classNameId the class name ID of this m d r rule group instance
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_mdrRuleGroupInstance.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this m d r rule group instance.
	*
	* @param classPK the class p k of this m d r rule group instance
	*/
	@Override
	public void setClassPK(long classPK) {
		_mdrRuleGroupInstance.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this m d r rule group instance.
	*
	* @param companyId the company ID of this m d r rule group instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mdrRuleGroupInstance.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this m d r rule group instance.
	*
	* @param createDate the create date of this m d r rule group instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mdrRuleGroupInstance.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mdrRuleGroupInstance.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mdrRuleGroupInstance.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mdrRuleGroupInstance.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this m d r rule group instance.
	*
	* @param groupId the group ID of this m d r rule group instance
	*/
	@Override
	public void setGroupId(long groupId) {
		_mdrRuleGroupInstance.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this m d r rule group instance.
	*
	* @param lastPublishDate the last publish date of this m d r rule group instance
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_mdrRuleGroupInstance.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this m d r rule group instance.
	*
	* @param modifiedDate the modified date of this m d r rule group instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mdrRuleGroupInstance.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_mdrRuleGroupInstance.setNew(n);
	}

	/**
	* Sets the primary key of this m d r rule group instance.
	*
	* @param primaryKey the primary key of this m d r rule group instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mdrRuleGroupInstance.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrRuleGroupInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this m d r rule group instance.
	*
	* @param priority the priority of this m d r rule group instance
	*/
	@Override
	public void setPriority(int priority) {
		_mdrRuleGroupInstance.setPriority(priority);
	}

	/**
	* Sets the rule group ID of this m d r rule group instance.
	*
	* @param ruleGroupId the rule group ID of this m d r rule group instance
	*/
	@Override
	public void setRuleGroupId(long ruleGroupId) {
		_mdrRuleGroupInstance.setRuleGroupId(ruleGroupId);
	}

	/**
	* Sets the rule group instance ID of this m d r rule group instance.
	*
	* @param ruleGroupInstanceId the rule group instance ID of this m d r rule group instance
	*/
	@Override
	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		_mdrRuleGroupInstance.setRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Sets the user ID of this m d r rule group instance.
	*
	* @param userId the user ID of this m d r rule group instance
	*/
	@Override
	public void setUserId(long userId) {
		_mdrRuleGroupInstance.setUserId(userId);
	}

	/**
	* Sets the user name of this m d r rule group instance.
	*
	* @param userName the user name of this m d r rule group instance
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mdrRuleGroupInstance.setUserName(userName);
	}

	/**
	* Sets the user uuid of this m d r rule group instance.
	*
	* @param userUuid the user uuid of this m d r rule group instance
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mdrRuleGroupInstance.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this m d r rule group instance.
	*
	* @param uuid the uuid of this m d r rule group instance
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mdrRuleGroupInstance.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> toCacheModel() {
		return _mdrRuleGroupInstance.toCacheModel();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance toEscapedModel() {
		return new MDRRuleGroupInstanceWrapper(_mdrRuleGroupInstance.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrRuleGroupInstance.toString();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance toUnescapedModel() {
		return new MDRRuleGroupInstanceWrapper(_mdrRuleGroupInstance.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mdrRuleGroupInstance.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRRuleGroupInstanceWrapper)) {
			return false;
		}

		MDRRuleGroupInstanceWrapper mdrRuleGroupInstanceWrapper = (MDRRuleGroupInstanceWrapper)obj;

		if (Validator.equals(_mdrRuleGroupInstance,
					mdrRuleGroupInstanceWrapper._mdrRuleGroupInstance)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _mdrRuleGroupInstance.getStagedModelType();
	}

	@Override
	public MDRRuleGroupInstance getWrappedModel() {
		return _mdrRuleGroupInstance;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mdrRuleGroupInstance.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mdrRuleGroupInstance.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mdrRuleGroupInstance.resetOriginalValues();
	}

	private final MDRRuleGroupInstance _mdrRuleGroupInstance;
}