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
 * This class is a wrapper for {@link KaleoNotificationRecipient}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationRecipient
 * @generated
 */
public class KaleoNotificationRecipientWrapper
	extends BaseModelWrapper<KaleoNotificationRecipient>
	implements KaleoNotificationRecipient,
			   ModelWrapper<KaleoNotificationRecipient> {

	public KaleoNotificationRecipientWrapper(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		super(kaleoNotificationRecipient);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"kaleoNotificationRecipientId", getKaleoNotificationRecipientId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNotificationId", getKaleoNotificationId());
		attributes.put("recipientClassName", getRecipientClassName());
		attributes.put("recipientClassPK", getRecipientClassPK());
		attributes.put("recipientRoleType", getRecipientRoleType());
		attributes.put("recipientScript", getRecipientScript());
		attributes.put("recipientScriptLanguage", getRecipientScriptLanguage());
		attributes.put("recipientScriptContexts", getRecipientScriptContexts());
		attributes.put("address", getAddress());
		attributes.put(
			"notificationReceptionType", getNotificationReceptionType());

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

		Long kaleoNotificationRecipientId = (Long)attributes.get(
			"kaleoNotificationRecipientId");

		if (kaleoNotificationRecipientId != null) {
			setKaleoNotificationRecipientId(kaleoNotificationRecipientId);
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

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
		}

		Long kaleoDefinitionVersionId = (Long)attributes.get(
			"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		Long kaleoNotificationId = (Long)attributes.get("kaleoNotificationId");

		if (kaleoNotificationId != null) {
			setKaleoNotificationId(kaleoNotificationId);
		}

		String recipientClassName = (String)attributes.get(
			"recipientClassName");

		if (recipientClassName != null) {
			setRecipientClassName(recipientClassName);
		}

		Long recipientClassPK = (Long)attributes.get("recipientClassPK");

		if (recipientClassPK != null) {
			setRecipientClassPK(recipientClassPK);
		}

		Integer recipientRoleType = (Integer)attributes.get(
			"recipientRoleType");

		if (recipientRoleType != null) {
			setRecipientRoleType(recipientRoleType);
		}

		String recipientScript = (String)attributes.get("recipientScript");

		if (recipientScript != null) {
			setRecipientScript(recipientScript);
		}

		String recipientScriptLanguage = (String)attributes.get(
			"recipientScriptLanguage");

		if (recipientScriptLanguage != null) {
			setRecipientScriptLanguage(recipientScriptLanguage);
		}

		String recipientScriptContexts = (String)attributes.get(
			"recipientScriptContexts");

		if (recipientScriptContexts != null) {
			setRecipientScriptContexts(recipientScriptContexts);
		}

		String address = (String)attributes.get("address");

		if (address != null) {
			setAddress(address);
		}

		String notificationReceptionType = (String)attributes.get(
			"notificationReceptionType");

		if (notificationReceptionType != null) {
			setNotificationReceptionType(notificationReceptionType);
		}
	}

	@Override
	public KaleoNotificationRecipient cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the address of this kaleo notification recipient.
	 *
	 * @return the address of this kaleo notification recipient
	 */
	@Override
	public String getAddress() {
		return model.getAddress();
	}

	/**
	 * Returns the company ID of this kaleo notification recipient.
	 *
	 * @return the company ID of this kaleo notification recipient
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo notification recipient.
	 *
	 * @return the create date of this kaleo notification recipient
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo notification recipient.
	 *
	 * @return the ct collection ID of this kaleo notification recipient
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this kaleo notification recipient.
	 *
	 * @return the group ID of this kaleo notification recipient
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo notification recipient.
	 *
	 * @return the kaleo definition ID of this kaleo notification recipient
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo notification recipient.
	 *
	 * @return the kaleo definition version ID of this kaleo notification recipient
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo notification ID of this kaleo notification recipient.
	 *
	 * @return the kaleo notification ID of this kaleo notification recipient
	 */
	@Override
	public long getKaleoNotificationId() {
		return model.getKaleoNotificationId();
	}

	/**
	 * Returns the kaleo notification recipient ID of this kaleo notification recipient.
	 *
	 * @return the kaleo notification recipient ID of this kaleo notification recipient
	 */
	@Override
	public long getKaleoNotificationRecipientId() {
		return model.getKaleoNotificationRecipientId();
	}

	/**
	 * Returns the modified date of this kaleo notification recipient.
	 *
	 * @return the modified date of this kaleo notification recipient
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo notification recipient.
	 *
	 * @return the mvcc version of this kaleo notification recipient
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification reception type of this kaleo notification recipient.
	 *
	 * @return the notification reception type of this kaleo notification recipient
	 */
	@Override
	public String getNotificationReceptionType() {
		return model.getNotificationReceptionType();
	}

	/**
	 * Returns the primary key of this kaleo notification recipient.
	 *
	 * @return the primary key of this kaleo notification recipient
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the recipient class name of this kaleo notification recipient.
	 *
	 * @return the recipient class name of this kaleo notification recipient
	 */
	@Override
	public String getRecipientClassName() {
		return model.getRecipientClassName();
	}

	/**
	 * Returns the recipient class pk of this kaleo notification recipient.
	 *
	 * @return the recipient class pk of this kaleo notification recipient
	 */
	@Override
	public long getRecipientClassPK() {
		return model.getRecipientClassPK();
	}

	/**
	 * Returns the recipient role type of this kaleo notification recipient.
	 *
	 * @return the recipient role type of this kaleo notification recipient
	 */
	@Override
	public int getRecipientRoleType() {
		return model.getRecipientRoleType();
	}

	/**
	 * Returns the recipient script of this kaleo notification recipient.
	 *
	 * @return the recipient script of this kaleo notification recipient
	 */
	@Override
	public String getRecipientScript() {
		return model.getRecipientScript();
	}

	/**
	 * Returns the recipient script contexts of this kaleo notification recipient.
	 *
	 * @return the recipient script contexts of this kaleo notification recipient
	 */
	@Override
	public String getRecipientScriptContexts() {
		return model.getRecipientScriptContexts();
	}

	/**
	 * Returns the recipient script language of this kaleo notification recipient.
	 *
	 * @return the recipient script language of this kaleo notification recipient
	 */
	@Override
	public String getRecipientScriptLanguage() {
		return model.getRecipientScriptLanguage();
	}

	/**
	 * Returns the user ID of this kaleo notification recipient.
	 *
	 * @return the user ID of this kaleo notification recipient
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo notification recipient.
	 *
	 * @return the user name of this kaleo notification recipient
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo notification recipient.
	 *
	 * @return the user uuid of this kaleo notification recipient
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
	 * Sets the address of this kaleo notification recipient.
	 *
	 * @param address the address of this kaleo notification recipient
	 */
	@Override
	public void setAddress(String address) {
		model.setAddress(address);
	}

	/**
	 * Sets the company ID of this kaleo notification recipient.
	 *
	 * @param companyId the company ID of this kaleo notification recipient
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo notification recipient.
	 *
	 * @param createDate the create date of this kaleo notification recipient
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo notification recipient.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo notification recipient
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this kaleo notification recipient.
	 *
	 * @param groupId the group ID of this kaleo notification recipient
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo notification recipient.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo notification recipient
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo notification recipient.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo notification recipient
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo notification ID of this kaleo notification recipient.
	 *
	 * @param kaleoNotificationId the kaleo notification ID of this kaleo notification recipient
	 */
	@Override
	public void setKaleoNotificationId(long kaleoNotificationId) {
		model.setKaleoNotificationId(kaleoNotificationId);
	}

	/**
	 * Sets the kaleo notification recipient ID of this kaleo notification recipient.
	 *
	 * @param kaleoNotificationRecipientId the kaleo notification recipient ID of this kaleo notification recipient
	 */
	@Override
	public void setKaleoNotificationRecipientId(
		long kaleoNotificationRecipientId) {

		model.setKaleoNotificationRecipientId(kaleoNotificationRecipientId);
	}

	/**
	 * Sets the modified date of this kaleo notification recipient.
	 *
	 * @param modifiedDate the modified date of this kaleo notification recipient
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo notification recipient.
	 *
	 * @param mvccVersion the mvcc version of this kaleo notification recipient
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification reception type of this kaleo notification recipient.
	 *
	 * @param notificationReceptionType the notification reception type of this kaleo notification recipient
	 */
	@Override
	public void setNotificationReceptionType(String notificationReceptionType) {
		model.setNotificationReceptionType(notificationReceptionType);
	}

	/**
	 * Sets the primary key of this kaleo notification recipient.
	 *
	 * @param primaryKey the primary key of this kaleo notification recipient
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the recipient class name of this kaleo notification recipient.
	 *
	 * @param recipientClassName the recipient class name of this kaleo notification recipient
	 */
	@Override
	public void setRecipientClassName(String recipientClassName) {
		model.setRecipientClassName(recipientClassName);
	}

	/**
	 * Sets the recipient class pk of this kaleo notification recipient.
	 *
	 * @param recipientClassPK the recipient class pk of this kaleo notification recipient
	 */
	@Override
	public void setRecipientClassPK(long recipientClassPK) {
		model.setRecipientClassPK(recipientClassPK);
	}

	/**
	 * Sets the recipient role type of this kaleo notification recipient.
	 *
	 * @param recipientRoleType the recipient role type of this kaleo notification recipient
	 */
	@Override
	public void setRecipientRoleType(int recipientRoleType) {
		model.setRecipientRoleType(recipientRoleType);
	}

	/**
	 * Sets the recipient script of this kaleo notification recipient.
	 *
	 * @param recipientScript the recipient script of this kaleo notification recipient
	 */
	@Override
	public void setRecipientScript(String recipientScript) {
		model.setRecipientScript(recipientScript);
	}

	/**
	 * Sets the recipient script contexts of this kaleo notification recipient.
	 *
	 * @param recipientScriptContexts the recipient script contexts of this kaleo notification recipient
	 */
	@Override
	public void setRecipientScriptContexts(String recipientScriptContexts) {
		model.setRecipientScriptContexts(recipientScriptContexts);
	}

	/**
	 * Sets the recipient script language of this kaleo notification recipient.
	 *
	 * @param recipientScriptLanguage the recipient script language of this kaleo notification recipient
	 */
	@Override
	public void setRecipientScriptLanguage(String recipientScriptLanguage) {
		model.setRecipientScriptLanguage(recipientScriptLanguage);
	}

	/**
	 * Sets the user ID of this kaleo notification recipient.
	 *
	 * @param userId the user ID of this kaleo notification recipient
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo notification recipient.
	 *
	 * @param userName the user name of this kaleo notification recipient
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo notification recipient.
	 *
	 * @param userUuid the user uuid of this kaleo notification recipient
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
	public Map<String, Function<KaleoNotificationRecipient, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoNotificationRecipient, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoNotificationRecipientWrapper wrap(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return new KaleoNotificationRecipientWrapper(
			kaleoNotificationRecipient);
	}

}