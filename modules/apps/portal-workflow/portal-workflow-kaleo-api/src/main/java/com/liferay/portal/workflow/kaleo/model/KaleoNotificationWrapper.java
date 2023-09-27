/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link KaleoNotification}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotification
 * @generated
 */
public class KaleoNotificationWrapper
	extends BaseModelWrapper<KaleoNotification>
	implements KaleoNotification, ModelWrapper<KaleoNotification> {

	public KaleoNotificationWrapper(KaleoNotification kaleoNotification) {
		super(kaleoNotification);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoNotificationId", getKaleoNotificationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoClassName", getKaleoClassName());
		attributes.put("kaleoClassPK", getKaleoClassPK());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
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
	public KaleoNotification cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this kaleo notification.
	 *
	 * @return the company ID of this kaleo notification
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo notification.
	 *
	 * @return the create date of this kaleo notification
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo notification.
	 *
	 * @return the ct collection ID of this kaleo notification
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this kaleo notification.
	 *
	 * @return the description of this kaleo notification
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the execution type of this kaleo notification.
	 *
	 * @return the execution type of this kaleo notification
	 */
	@Override
	public String getExecutionType() {
		return model.getExecutionType();
	}

	/**
	 * Returns the group ID of this kaleo notification.
	 *
	 * @return the group ID of this kaleo notification
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo class name of this kaleo notification.
	 *
	 * @return the kaleo class name of this kaleo notification
	 */
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	 * Returns the kaleo class pk of this kaleo notification.
	 *
	 * @return the kaleo class pk of this kaleo notification
	 */
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo notification.
	 *
	 * @return the kaleo definition ID of this kaleo notification
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo notification.
	 *
	 * @return the kaleo definition version ID of this kaleo notification
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo node name of this kaleo notification.
	 *
	 * @return the kaleo node name of this kaleo notification
	 */
	@Override
	public String getKaleoNodeName() {
		return model.getKaleoNodeName();
	}

	/**
	 * Returns the kaleo notification ID of this kaleo notification.
	 *
	 * @return the kaleo notification ID of this kaleo notification
	 */
	@Override
	public long getKaleoNotificationId() {
		return model.getKaleoNotificationId();
	}

	/**
	 * Returns the modified date of this kaleo notification.
	 *
	 * @return the modified date of this kaleo notification
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo notification.
	 *
	 * @return the mvcc version of this kaleo notification
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo notification.
	 *
	 * @return the name of this kaleo notification
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the notification types of this kaleo notification.
	 *
	 * @return the notification types of this kaleo notification
	 */
	@Override
	public String getNotificationTypes() {
		return model.getNotificationTypes();
	}

	/**
	 * Returns the primary key of this kaleo notification.
	 *
	 * @return the primary key of this kaleo notification
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the template of this kaleo notification.
	 *
	 * @return the template of this kaleo notification
	 */
	@Override
	public String getTemplate() {
		return model.getTemplate();
	}

	/**
	 * Returns the template language of this kaleo notification.
	 *
	 * @return the template language of this kaleo notification
	 */
	@Override
	public String getTemplateLanguage() {
		return model.getTemplateLanguage();
	}

	/**
	 * Returns the user ID of this kaleo notification.
	 *
	 * @return the user ID of this kaleo notification
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo notification.
	 *
	 * @return the user name of this kaleo notification
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo notification.
	 *
	 * @return the user uuid of this kaleo notification
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this kaleo notification.
	 *
	 * @param companyId the company ID of this kaleo notification
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo notification.
	 *
	 * @param createDate the create date of this kaleo notification
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo notification.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo notification
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this kaleo notification.
	 *
	 * @param description the description of this kaleo notification
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the execution type of this kaleo notification.
	 *
	 * @param executionType the execution type of this kaleo notification
	 */
	@Override
	public void setExecutionType(String executionType) {
		model.setExecutionType(executionType);
	}

	/**
	 * Sets the group ID of this kaleo notification.
	 *
	 * @param groupId the group ID of this kaleo notification
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo class name of this kaleo notification.
	 *
	 * @param kaleoClassName the kaleo class name of this kaleo notification
	 */
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	 * Sets the kaleo class pk of this kaleo notification.
	 *
	 * @param kaleoClassPK the kaleo class pk of this kaleo notification
	 */
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo notification.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo notification
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo notification.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo notification
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo node name of this kaleo notification.
	 *
	 * @param kaleoNodeName the kaleo node name of this kaleo notification
	 */
	@Override
	public void setKaleoNodeName(String kaleoNodeName) {
		model.setKaleoNodeName(kaleoNodeName);
	}

	/**
	 * Sets the kaleo notification ID of this kaleo notification.
	 *
	 * @param kaleoNotificationId the kaleo notification ID of this kaleo notification
	 */
	@Override
	public void setKaleoNotificationId(long kaleoNotificationId) {
		model.setKaleoNotificationId(kaleoNotificationId);
	}

	/**
	 * Sets the modified date of this kaleo notification.
	 *
	 * @param modifiedDate the modified date of this kaleo notification
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo notification.
	 *
	 * @param mvccVersion the mvcc version of this kaleo notification
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo notification.
	 *
	 * @param name the name of this kaleo notification
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the notification types of this kaleo notification.
	 *
	 * @param notificationTypes the notification types of this kaleo notification
	 */
	@Override
	public void setNotificationTypes(String notificationTypes) {
		model.setNotificationTypes(notificationTypes);
	}

	/**
	 * Sets the primary key of this kaleo notification.
	 *
	 * @param primaryKey the primary key of this kaleo notification
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the template of this kaleo notification.
	 *
	 * @param template the template of this kaleo notification
	 */
	@Override
	public void setTemplate(String template) {
		model.setTemplate(template);
	}

	/**
	 * Sets the template language of this kaleo notification.
	 *
	 * @param templateLanguage the template language of this kaleo notification
	 */
	@Override
	public void setTemplateLanguage(String templateLanguage) {
		model.setTemplateLanguage(templateLanguage);
	}

	/**
	 * Sets the user ID of this kaleo notification.
	 *
	 * @param userId the user ID of this kaleo notification
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo notification.
	 *
	 * @param userName the user name of this kaleo notification
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo notification.
	 *
	 * @param userUuid the user uuid of this kaleo notification
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<KaleoNotification, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoNotification, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoNotificationWrapper wrap(
		KaleoNotification kaleoNotification) {

		return new KaleoNotificationWrapper(kaleoNotification);
	}

}