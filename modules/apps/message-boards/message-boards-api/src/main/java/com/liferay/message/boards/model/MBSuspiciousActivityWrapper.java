/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link MBSuspiciousActivity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivity
 * @generated
 */
public class MBSuspiciousActivityWrapper
	extends BaseModelWrapper<MBSuspiciousActivity>
	implements MBSuspiciousActivity, ModelWrapper<MBSuspiciousActivity> {

	public MBSuspiciousActivityWrapper(
		MBSuspiciousActivity mbSuspiciousActivity) {

		super(mbSuspiciousActivity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("suspiciousActivityId", getSuspiciousActivityId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("messageId", getMessageId());
		attributes.put("threadId", getThreadId());
		attributes.put("reason", getReason());
		attributes.put("validated", isValidated());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long suspiciousActivityId = (Long)attributes.get(
			"suspiciousActivityId");

		if (suspiciousActivityId != null) {
			setSuspiciousActivityId(suspiciousActivityId);
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

		Long messageId = (Long)attributes.get("messageId");

		if (messageId != null) {
			setMessageId(messageId);
		}

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
		}

		String reason = (String)attributes.get("reason");

		if (reason != null) {
			setReason(reason);
		}

		Boolean validated = (Boolean)attributes.get("validated");

		if (validated != null) {
			setValidated(validated);
		}
	}

	@Override
	public MBSuspiciousActivity cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this message boards suspicious activity.
	 *
	 * @return the company ID of this message boards suspicious activity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this message boards suspicious activity.
	 *
	 * @return the container model ID of this message boards suspicious activity
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this message boards suspicious activity.
	 *
	 * @return the container name of this message boards suspicious activity
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this message boards suspicious activity.
	 *
	 * @return the create date of this message boards suspicious activity
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards suspicious activity.
	 *
	 * @return the ct collection ID of this message boards suspicious activity
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this message boards suspicious activity.
	 *
	 * @return the group ID of this message boards suspicious activity
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the message ID of this message boards suspicious activity.
	 *
	 * @return the message ID of this message boards suspicious activity
	 */
	@Override
	public long getMessageId() {
		return model.getMessageId();
	}

	/**
	 * Returns the modified date of this message boards suspicious activity.
	 *
	 * @return the modified date of this message boards suspicious activity
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards suspicious activity.
	 *
	 * @return the mvcc version of this message boards suspicious activity
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this message boards suspicious activity.
	 *
	 * @return the parent container model ID of this message boards suspicious activity
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the primary key of this message boards suspicious activity.
	 *
	 * @return the primary key of this message boards suspicious activity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the reason of this message boards suspicious activity.
	 *
	 * @return the reason of this message boards suspicious activity
	 */
	@Override
	public String getReason() {
		return model.getReason();
	}

	/**
	 * Returns the suspicious activity ID of this message boards suspicious activity.
	 *
	 * @return the suspicious activity ID of this message boards suspicious activity
	 */
	@Override
	public long getSuspiciousActivityId() {
		return model.getSuspiciousActivityId();
	}

	/**
	 * Returns the thread ID of this message boards suspicious activity.
	 *
	 * @return the thread ID of this message boards suspicious activity
	 */
	@Override
	public long getThreadId() {
		return model.getThreadId();
	}

	/**
	 * Returns the user ID of this message boards suspicious activity.
	 *
	 * @return the user ID of this message boards suspicious activity
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards suspicious activity.
	 *
	 * @return the user name of this message boards suspicious activity
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards suspicious activity.
	 *
	 * @return the user uuid of this message boards suspicious activity
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards suspicious activity.
	 *
	 * @return the uuid of this message boards suspicious activity
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the validated of this message boards suspicious activity.
	 *
	 * @return the validated of this message boards suspicious activity
	 */
	@Override
	public boolean getValidated() {
		return model.getValidated();
	}

	/**
	 * Returns <code>true</code> if this message boards suspicious activity is validated.
	 *
	 * @return <code>true</code> if this message boards suspicious activity is validated; <code>false</code> otherwise
	 */
	@Override
	public boolean isValidated() {
		return model.isValidated();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this message boards suspicious activity.
	 *
	 * @param companyId the company ID of this message boards suspicious activity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this message boards suspicious activity.
	 *
	 * @param containerModelId the container model ID of this message boards suspicious activity
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this message boards suspicious activity.
	 *
	 * @param createDate the create date of this message boards suspicious activity
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards suspicious activity.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards suspicious activity
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this message boards suspicious activity.
	 *
	 * @param groupId the group ID of this message boards suspicious activity
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the message ID of this message boards suspicious activity.
	 *
	 * @param messageId the message ID of this message boards suspicious activity
	 */
	@Override
	public void setMessageId(long messageId) {
		model.setMessageId(messageId);
	}

	/**
	 * Sets the modified date of this message boards suspicious activity.
	 *
	 * @param modifiedDate the modified date of this message boards suspicious activity
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards suspicious activity.
	 *
	 * @param mvccVersion the mvcc version of this message boards suspicious activity
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this message boards suspicious activity.
	 *
	 * @param parentContainerModelId the parent container model ID of this message boards suspicious activity
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the primary key of this message boards suspicious activity.
	 *
	 * @param primaryKey the primary key of this message boards suspicious activity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the reason of this message boards suspicious activity.
	 *
	 * @param reason the reason of this message boards suspicious activity
	 */
	@Override
	public void setReason(String reason) {
		model.setReason(reason);
	}

	/**
	 * Sets the suspicious activity ID of this message boards suspicious activity.
	 *
	 * @param suspiciousActivityId the suspicious activity ID of this message boards suspicious activity
	 */
	@Override
	public void setSuspiciousActivityId(long suspiciousActivityId) {
		model.setSuspiciousActivityId(suspiciousActivityId);
	}

	/**
	 * Sets the thread ID of this message boards suspicious activity.
	 *
	 * @param threadId the thread ID of this message boards suspicious activity
	 */
	@Override
	public void setThreadId(long threadId) {
		model.setThreadId(threadId);
	}

	/**
	 * Sets the user ID of this message boards suspicious activity.
	 *
	 * @param userId the user ID of this message boards suspicious activity
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards suspicious activity.
	 *
	 * @param userName the user name of this message boards suspicious activity
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards suspicious activity.
	 *
	 * @param userUuid the user uuid of this message boards suspicious activity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards suspicious activity.
	 *
	 * @param uuid the uuid of this message boards suspicious activity
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets whether this message boards suspicious activity is validated.
	 *
	 * @param validated the validated of this message boards suspicious activity
	 */
	@Override
	public void setValidated(boolean validated) {
		model.setValidated(validated);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<MBSuspiciousActivity, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBSuspiciousActivity, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBSuspiciousActivityWrapper wrap(
		MBSuspiciousActivity mbSuspiciousActivity) {

		return new MBSuspiciousActivityWrapper(mbSuspiciousActivity);
	}

}