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

package com.liferay.portal.workflow.kaleo.model;

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
 * This class is a wrapper for {@link KaleoNotification}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotification
 * @generated
 */
@ProviderType
public class KaleoNotificationWrapper implements KaleoNotification,
	ModelWrapper<KaleoNotification> {
	public KaleoNotificationWrapper(KaleoNotification kaleoNotification) {
		_kaleoNotification = kaleoNotification;
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoNotification.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoNotification.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("kaleoNotificationId", getKaleoNotificationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoClassName", getKaleoClassName());
		attributes.put("kaleoClassPK", getKaleoClassPK());
		attributes.put("kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNodeName", getKaleoNodeName());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("executionType", getExecutionType());
		attributes.put("template", getTemplate());
		attributes.put("templateLanguage", getTemplateLanguage());
		attributes.put("notificationTypes", getNotificationTypes());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long kaleoNotificationId = (Long)attributes.get("kaleoNotificationId");

		if (kaleoNotificationId != null) {
			setKaleoNotificationId(kaleoNotificationId);
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

		String kaleoClassName = (String)attributes.get("kaleoClassName");

		if (kaleoClassName != null) {
			setKaleoClassName(kaleoClassName);
		}

		Long kaleoClassPK = (Long)attributes.get("kaleoClassPK");

		if (kaleoClassPK != null) {
			setKaleoClassPK(kaleoClassPK);
		}

		Long kaleoDefinitionVersionId = (Long)attributes.get(
				"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		String kaleoNodeName = (String)attributes.get("kaleoNodeName");

		if (kaleoNodeName != null) {
			setKaleoNodeName(kaleoNodeName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String executionType = (String)attributes.get("executionType");

		if (executionType != null) {
			setExecutionType(executionType);
		}

		String template = (String)attributes.get("template");

		if (template != null) {
			setTemplate(template);
		}

		String templateLanguage = (String)attributes.get("templateLanguage");

		if (templateLanguage != null) {
			setTemplateLanguage(templateLanguage);
		}

		String notificationTypes = (String)attributes.get("notificationTypes");

		if (notificationTypes != null) {
			setNotificationTypes(notificationTypes);
		}
	}

	@Override
	public Object clone() {
		return new KaleoNotificationWrapper((KaleoNotification)_kaleoNotification.clone());
	}

	@Override
	public int compareTo(KaleoNotification kaleoNotification) {
		return _kaleoNotification.compareTo(kaleoNotification);
	}

	/**
	* Returns the company ID of this kaleo notification.
	*
	* @return the company ID of this kaleo notification
	*/
	@Override
	public long getCompanyId() {
		return _kaleoNotification.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo notification.
	*
	* @return the create date of this kaleo notification
	*/
	@Override
	public Date getCreateDate() {
		return _kaleoNotification.getCreateDate();
	}

	/**
	* Returns the description of this kaleo notification.
	*
	* @return the description of this kaleo notification
	*/
	@Override
	public String getDescription() {
		return _kaleoNotification.getDescription();
	}

	/**
	* Returns the execution type of this kaleo notification.
	*
	* @return the execution type of this kaleo notification
	*/
	@Override
	public String getExecutionType() {
		return _kaleoNotification.getExecutionType();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _kaleoNotification.getExpandoBridge();
	}

	/**
	* Returns the group ID of this kaleo notification.
	*
	* @return the group ID of this kaleo notification
	*/
	@Override
	public long getGroupId() {
		return _kaleoNotification.getGroupId();
	}

	/**
	* Returns the kaleo class name of this kaleo notification.
	*
	* @return the kaleo class name of this kaleo notification
	*/
	@Override
	public String getKaleoClassName() {
		return _kaleoNotification.getKaleoClassName();
	}

	/**
	* Returns the kaleo class pk of this kaleo notification.
	*
	* @return the kaleo class pk of this kaleo notification
	*/
	@Override
	public long getKaleoClassPK() {
		return _kaleoNotification.getKaleoClassPK();
	}

	/**
	* Returns the kaleo definition version ID of this kaleo notification.
	*
	* @return the kaleo definition version ID of this kaleo notification
	*/
	@Override
	public long getKaleoDefinitionVersionId() {
		return _kaleoNotification.getKaleoDefinitionVersionId();
	}

	/**
	* Returns the kaleo node name of this kaleo notification.
	*
	* @return the kaleo node name of this kaleo notification
	*/
	@Override
	public String getKaleoNodeName() {
		return _kaleoNotification.getKaleoNodeName();
	}

	/**
	* Returns the kaleo notification ID of this kaleo notification.
	*
	* @return the kaleo notification ID of this kaleo notification
	*/
	@Override
	public long getKaleoNotificationId() {
		return _kaleoNotification.getKaleoNotificationId();
	}

	/**
	* Returns the modified date of this kaleo notification.
	*
	* @return the modified date of this kaleo notification
	*/
	@Override
	public Date getModifiedDate() {
		return _kaleoNotification.getModifiedDate();
	}

	/**
	* Returns the name of this kaleo notification.
	*
	* @return the name of this kaleo notification
	*/
	@Override
	public String getName() {
		return _kaleoNotification.getName();
	}

	/**
	* Returns the notification types of this kaleo notification.
	*
	* @return the notification types of this kaleo notification
	*/
	@Override
	public String getNotificationTypes() {
		return _kaleoNotification.getNotificationTypes();
	}

	/**
	* Returns the primary key of this kaleo notification.
	*
	* @return the primary key of this kaleo notification
	*/
	@Override
	public long getPrimaryKey() {
		return _kaleoNotification.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoNotification.getPrimaryKeyObj();
	}

	/**
	* Returns the template of this kaleo notification.
	*
	* @return the template of this kaleo notification
	*/
	@Override
	public String getTemplate() {
		return _kaleoNotification.getTemplate();
	}

	/**
	* Returns the template language of this kaleo notification.
	*
	* @return the template language of this kaleo notification
	*/
	@Override
	public String getTemplateLanguage() {
		return _kaleoNotification.getTemplateLanguage();
	}

	/**
	* Returns the user ID of this kaleo notification.
	*
	* @return the user ID of this kaleo notification
	*/
	@Override
	public long getUserId() {
		return _kaleoNotification.getUserId();
	}

	/**
	* Returns the user name of this kaleo notification.
	*
	* @return the user name of this kaleo notification
	*/
	@Override
	public String getUserName() {
		return _kaleoNotification.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo notification.
	*
	* @return the user uuid of this kaleo notification
	*/
	@Override
	public String getUserUuid() {
		return _kaleoNotification.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _kaleoNotification.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _kaleoNotification.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _kaleoNotification.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _kaleoNotification.isNew();
	}

	@Override
	public void persist() {
		_kaleoNotification.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_kaleoNotification.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this kaleo notification.
	*
	* @param companyId the company ID of this kaleo notification
	*/
	@Override
	public void setCompanyId(long companyId) {
		_kaleoNotification.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo notification.
	*
	* @param createDate the create date of this kaleo notification
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_kaleoNotification.setCreateDate(createDate);
	}

	/**
	* Sets the description of this kaleo notification.
	*
	* @param description the description of this kaleo notification
	*/
	@Override
	public void setDescription(String description) {
		_kaleoNotification.setDescription(description);
	}

	/**
	* Sets the execution type of this kaleo notification.
	*
	* @param executionType the execution type of this kaleo notification
	*/
	@Override
	public void setExecutionType(String executionType) {
		_kaleoNotification.setExecutionType(executionType);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_kaleoNotification.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_kaleoNotification.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_kaleoNotification.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this kaleo notification.
	*
	* @param groupId the group ID of this kaleo notification
	*/
	@Override
	public void setGroupId(long groupId) {
		_kaleoNotification.setGroupId(groupId);
	}

	/**
	* Sets the kaleo class name of this kaleo notification.
	*
	* @param kaleoClassName the kaleo class name of this kaleo notification
	*/
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		_kaleoNotification.setKaleoClassName(kaleoClassName);
	}

	/**
	* Sets the kaleo class pk of this kaleo notification.
	*
	* @param kaleoClassPK the kaleo class pk of this kaleo notification
	*/
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		_kaleoNotification.setKaleoClassPK(kaleoClassPK);
	}

	/**
	* Sets the kaleo definition version ID of this kaleo notification.
	*
	* @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo notification
	*/
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_kaleoNotification.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	* Sets the kaleo node name of this kaleo notification.
	*
	* @param kaleoNodeName the kaleo node name of this kaleo notification
	*/
	@Override
	public void setKaleoNodeName(String kaleoNodeName) {
		_kaleoNotification.setKaleoNodeName(kaleoNodeName);
	}

	/**
	* Sets the kaleo notification ID of this kaleo notification.
	*
	* @param kaleoNotificationId the kaleo notification ID of this kaleo notification
	*/
	@Override
	public void setKaleoNotificationId(long kaleoNotificationId) {
		_kaleoNotification.setKaleoNotificationId(kaleoNotificationId);
	}

	/**
	* Sets the modified date of this kaleo notification.
	*
	* @param modifiedDate the modified date of this kaleo notification
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_kaleoNotification.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this kaleo notification.
	*
	* @param name the name of this kaleo notification
	*/
	@Override
	public void setName(String name) {
		_kaleoNotification.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_kaleoNotification.setNew(n);
	}

	/**
	* Sets the notification types of this kaleo notification.
	*
	* @param notificationTypes the notification types of this kaleo notification
	*/
	@Override
	public void setNotificationTypes(String notificationTypes) {
		_kaleoNotification.setNotificationTypes(notificationTypes);
	}

	/**
	* Sets the primary key of this kaleo notification.
	*
	* @param primaryKey the primary key of this kaleo notification
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_kaleoNotification.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_kaleoNotification.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the template of this kaleo notification.
	*
	* @param template the template of this kaleo notification
	*/
	@Override
	public void setTemplate(String template) {
		_kaleoNotification.setTemplate(template);
	}

	/**
	* Sets the template language of this kaleo notification.
	*
	* @param templateLanguage the template language of this kaleo notification
	*/
	@Override
	public void setTemplateLanguage(String templateLanguage) {
		_kaleoNotification.setTemplateLanguage(templateLanguage);
	}

	/**
	* Sets the user ID of this kaleo notification.
	*
	* @param userId the user ID of this kaleo notification
	*/
	@Override
	public void setUserId(long userId) {
		_kaleoNotification.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo notification.
	*
	* @param userName the user name of this kaleo notification
	*/
	@Override
	public void setUserName(String userName) {
		_kaleoNotification.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo notification.
	*
	* @param userUuid the user uuid of this kaleo notification
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_kaleoNotification.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<KaleoNotification> toCacheModel() {
		return _kaleoNotification.toCacheModel();
	}

	@Override
	public KaleoNotification toEscapedModel() {
		return new KaleoNotificationWrapper(_kaleoNotification.toEscapedModel());
	}

	@Override
	public String toString() {
		return _kaleoNotification.toString();
	}

	@Override
	public KaleoNotification toUnescapedModel() {
		return new KaleoNotificationWrapper(_kaleoNotification.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _kaleoNotification.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoNotificationWrapper)) {
			return false;
		}

		KaleoNotificationWrapper kaleoNotificationWrapper = (KaleoNotificationWrapper)obj;

		if (Objects.equals(_kaleoNotification,
					kaleoNotificationWrapper._kaleoNotification)) {
			return true;
		}

		return false;
	}

	@Override
	public KaleoNotification getWrappedModel() {
		return _kaleoNotification;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _kaleoNotification.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _kaleoNotification.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_kaleoNotification.resetOriginalValues();
	}

	private final KaleoNotification _kaleoNotification;
}