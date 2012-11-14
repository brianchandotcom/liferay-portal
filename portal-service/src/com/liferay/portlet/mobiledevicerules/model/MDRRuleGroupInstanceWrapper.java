/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleGroupInstance}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRuleGroupInstance
 * @generated
 */
public class MDRRuleGroupInstanceWrapper implements MDRRuleGroupInstance,
	ModelWrapper<MDRRuleGroupInstance> {
	public MDRRuleGroupInstanceWrapper(
		MDRRuleGroupInstance mdrRuleGroupInstance) {
		_mdrRuleGroupInstance = mdrRuleGroupInstance;
	}

	public Class<?> getModelClass() {
		return MDRRuleGroupInstance.class;
	}

	public String getModelClassName() {
		return MDRRuleGroupInstance.class.getName();
	}

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

		return attributes;
	}

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
	}

	/**
	* Returns the primary key of this m d r rule group instance.
	*
	* @return the primary key of this m d r rule group instance
	*/
	public long getPrimaryKey() {
		return _mdrRuleGroupInstance.getPrimaryKey();
	}

	/**
	* Sets the primary key of this m d r rule group instance.
	*
	* @param primaryKey the primary key of this m d r rule group instance
	*/
	public void setPrimaryKey(long primaryKey) {
		_mdrRuleGroupInstance.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this m d r rule group instance.
	*
	* @return the uuid of this m d r rule group instance
	*/
	public java.lang.String getUuid() {
		return _mdrRuleGroupInstance.getUuid();
	}

	/**
	* Sets the uuid of this m d r rule group instance.
	*
	* @param uuid the uuid of this m d r rule group instance
	*/
	public void setUuid(java.lang.String uuid) {
		_mdrRuleGroupInstance.setUuid(uuid);
	}

	/**
	* Returns the rule group instance ID of this m d r rule group instance.
	*
	* @return the rule group instance ID of this m d r rule group instance
	*/
	public long getRuleGroupInstanceId() {
		return _mdrRuleGroupInstance.getRuleGroupInstanceId();
	}

	/**
	* Sets the rule group instance ID of this m d r rule group instance.
	*
	* @param ruleGroupInstanceId the rule group instance ID of this m d r rule group instance
	*/
	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		_mdrRuleGroupInstance.setRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Returns the group ID of this m d r rule group instance.
	*
	* @return the group ID of this m d r rule group instance
	*/
	public long getGroupId() {
		return _mdrRuleGroupInstance.getGroupId();
	}

	/**
	* Sets the group ID of this m d r rule group instance.
	*
	* @param groupId the group ID of this m d r rule group instance
	*/
	public void setGroupId(long groupId) {
		_mdrRuleGroupInstance.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this m d r rule group instance.
	*
	* @return the company ID of this m d r rule group instance
	*/
	public long getCompanyId() {
		return _mdrRuleGroupInstance.getCompanyId();
	}

	/**
	* Sets the company ID of this m d r rule group instance.
	*
	* @param companyId the company ID of this m d r rule group instance
	*/
	public void setCompanyId(long companyId) {
		_mdrRuleGroupInstance.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this m d r rule group instance.
	*
	* @return the user ID of this m d r rule group instance
	*/
	public long getUserId() {
		return _mdrRuleGroupInstance.getUserId();
	}

	/**
	* Sets the user ID of this m d r rule group instance.
	*
	* @param userId the user ID of this m d r rule group instance
	*/
	public void setUserId(long userId) {
		_mdrRuleGroupInstance.setUserId(userId);
	}

	/**
	* Returns the user uuid of this m d r rule group instance.
	*
	* @return the user uuid of this m d r rule group instance
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstance.getUserUuid();
	}

	/**
	* Sets the user uuid of this m d r rule group instance.
	*
	* @param userUuid the user uuid of this m d r rule group instance
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mdrRuleGroupInstance.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this m d r rule group instance.
	*
	* @return the user name of this m d r rule group instance
	*/
	public java.lang.String getUserName() {
		return _mdrRuleGroupInstance.getUserName();
	}

	/**
	* Sets the user name of this m d r rule group instance.
	*
	* @param userName the user name of this m d r rule group instance
	*/
	public void setUserName(java.lang.String userName) {
		_mdrRuleGroupInstance.setUserName(userName);
	}

	/**
	* Returns the create date of this m d r rule group instance.
	*
	* @return the create date of this m d r rule group instance
	*/
	public java.util.Date getCreateDate() {
		return _mdrRuleGroupInstance.getCreateDate();
	}

	/**
	* Sets the create date of this m d r rule group instance.
	*
	* @param createDate the create date of this m d r rule group instance
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mdrRuleGroupInstance.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this m d r rule group instance.
	*
	* @return the modified date of this m d r rule group instance
	*/
	public java.util.Date getModifiedDate() {
		return _mdrRuleGroupInstance.getModifiedDate();
	}

	/**
	* Sets the modified date of this m d r rule group instance.
	*
	* @param modifiedDate the modified date of this m d r rule group instance
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mdrRuleGroupInstance.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this m d r rule group instance.
	*
	* @return the fully qualified class name of this m d r rule group instance
	*/
	public java.lang.String getClassName() {
		return _mdrRuleGroupInstance.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_mdrRuleGroupInstance.setClassName(className);
	}

	/**
	* Returns the class name ID of this m d r rule group instance.
	*
	* @return the class name ID of this m d r rule group instance
	*/
	public long getClassNameId() {
		return _mdrRuleGroupInstance.getClassNameId();
	}

	/**
	* Sets the class name ID of this m d r rule group instance.
	*
	* @param classNameId the class name ID of this m d r rule group instance
	*/
	public void setClassNameId(long classNameId) {
		_mdrRuleGroupInstance.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this m d r rule group instance.
	*
	* @return the class p k of this m d r rule group instance
	*/
	public long getClassPK() {
		return _mdrRuleGroupInstance.getClassPK();
	}

	/**
	* Sets the class p k of this m d r rule group instance.
	*
	* @param classPK the class p k of this m d r rule group instance
	*/
	public void setClassPK(long classPK) {
		_mdrRuleGroupInstance.setClassPK(classPK);
	}

	/**
	* Returns the rule group ID of this m d r rule group instance.
	*
	* @return the rule group ID of this m d r rule group instance
	*/
	public long getRuleGroupId() {
		return _mdrRuleGroupInstance.getRuleGroupId();
	}

	/**
	* Sets the rule group ID of this m d r rule group instance.
	*
	* @param ruleGroupId the rule group ID of this m d r rule group instance
	*/
	public void setRuleGroupId(long ruleGroupId) {
		_mdrRuleGroupInstance.setRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the priority of this m d r rule group instance.
	*
	* @return the priority of this m d r rule group instance
	*/
	public int getPriority() {
		return _mdrRuleGroupInstance.getPriority();
	}

	/**
	* Sets the priority of this m d r rule group instance.
	*
	* @param priority the priority of this m d r rule group instance
	*/
	public void setPriority(int priority) {
		_mdrRuleGroupInstance.setPriority(priority);
	}

	public boolean isNew() {
		return _mdrRuleGroupInstance.isNew();
	}

	public void setNew(boolean n) {
		_mdrRuleGroupInstance.setNew(n);
	}

	public boolean isCachedModel() {
		return _mdrRuleGroupInstance.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mdrRuleGroupInstance.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mdrRuleGroupInstance.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrRuleGroupInstance.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrRuleGroupInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrRuleGroupInstance.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mdrRuleGroupInstance.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MDRRuleGroupInstanceWrapper((MDRRuleGroupInstance)_mdrRuleGroupInstance.clone());
	}

	public int compareTo(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		return _mdrRuleGroupInstance.compareTo(mdrRuleGroupInstance);
	}

	@Override
	public int hashCode() {
		return _mdrRuleGroupInstance.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> toCacheModel() {
		return _mdrRuleGroupInstance.toCacheModel();
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance toEscapedModel() {
		return new MDRRuleGroupInstanceWrapper(_mdrRuleGroupInstance.toEscapedModel());
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance toUnescapedModel() {
		return new MDRRuleGroupInstanceWrapper(_mdrRuleGroupInstance.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrRuleGroupInstance.toString();
	}

	public java.lang.String toXmlString() {
		return _mdrRuleGroupInstance.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstance.persist();
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getActions()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstance.getActions();
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup getRuleGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstance.getRuleGroup();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MDRRuleGroupInstance getWrappedMDRRuleGroupInstance() {
		return _mdrRuleGroupInstance;
	}

	public MDRRuleGroupInstance getWrappedModel() {
		return _mdrRuleGroupInstance;
	}

	public void resetOriginalValues() {
		_mdrRuleGroupInstance.resetOriginalValues();
	}

	private MDRRuleGroupInstance _mdrRuleGroupInstance;
}