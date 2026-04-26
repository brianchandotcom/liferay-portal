/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderAttachment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachment
 * @generated
 */
public class CommerceOrderAttachmentWrapper
	extends BaseModelWrapper<CommerceOrderAttachment>
	implements CommerceOrderAttachment, ModelWrapper<CommerceOrderAttachment> {

	public CommerceOrderAttachmentWrapper(
		CommerceOrderAttachment commerceOrderAttachment) {

		super(commerceOrderAttachment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceOrderAttachmentId", getCommerceOrderAttachmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("priority", getPriority());
		attributes.put("restricted", isRestricted());
		attributes.put("title", getTitle());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceOrderAttachmentId = (Long)attributes.get(
			"commerceOrderAttachmentId");

		if (commerceOrderAttachmentId != null) {
			setCommerceOrderAttachmentId(commerceOrderAttachmentId);
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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean restricted = (Boolean)attributes.get("restricted");

		if (restricted != null) {
			setRestricted(restricted);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public CommerceOrderAttachment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce order attachment ID of this commerce order attachment.
	 *
	 * @return the commerce order attachment ID of this commerce order attachment
	 */
	@Override
	public long getCommerceOrderAttachmentId() {
		return model.getCommerceOrderAttachmentId();
	}

	/**
	 * Returns the commerce order ID of this commerce order attachment.
	 *
	 * @return the commerce order ID of this commerce order attachment
	 */
	@Override
	public long getCommerceOrderId() {
		return model.getCommerceOrderId();
	}

	/**
	 * Returns the company ID of this commerce order attachment.
	 *
	 * @return the company ID of this commerce order attachment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce order attachment.
	 *
	 * @return the create date of this commerce order attachment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce order attachment.
	 *
	 * @return the external reference code of this commerce order attachment
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the file entry ID of this commerce order attachment.
	 *
	 * @return the file entry ID of this commerce order attachment
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this commerce order attachment.
	 *
	 * @return the group ID of this commerce order attachment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce order attachment.
	 *
	 * @return the modified date of this commerce order attachment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce order attachment.
	 *
	 * @return the mvcc version of this commerce order attachment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce order attachment.
	 *
	 * @return the primary key of this commerce order attachment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce order attachment.
	 *
	 * @return the priority of this commerce order attachment
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the restricted of this commerce order attachment.
	 *
	 * @return the restricted of this commerce order attachment
	 */
	@Override
	public boolean getRestricted() {
		return model.getRestricted();
	}

	/**
	 * Returns the title of this commerce order attachment.
	 *
	 * @return the title of this commerce order attachment
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the type of this commerce order attachment.
	 *
	 * @return the type of this commerce order attachment
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce order attachment.
	 *
	 * @return the user ID of this commerce order attachment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order attachment.
	 *
	 * @return the user name of this commerce order attachment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order attachment.
	 *
	 * @return the user uuid of this commerce order attachment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce order attachment.
	 *
	 * @return the uuid of this commerce order attachment
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce order attachment is restricted.
	 *
	 * @return <code>true</code> if this commerce order attachment is restricted; <code>false</code> otherwise
	 */
	@Override
	public boolean isRestricted() {
		return model.isRestricted();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce order attachment ID of this commerce order attachment.
	 *
	 * @param commerceOrderAttachmentId the commerce order attachment ID of this commerce order attachment
	 */
	@Override
	public void setCommerceOrderAttachmentId(long commerceOrderAttachmentId) {
		model.setCommerceOrderAttachmentId(commerceOrderAttachmentId);
	}

	/**
	 * Sets the commerce order ID of this commerce order attachment.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order attachment
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		model.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the company ID of this commerce order attachment.
	 *
	 * @param companyId the company ID of this commerce order attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce order attachment.
	 *
	 * @param createDate the create date of this commerce order attachment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce order attachment.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order attachment
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the file entry ID of this commerce order attachment.
	 *
	 * @param fileEntryId the file entry ID of this commerce order attachment
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this commerce order attachment.
	 *
	 * @param groupId the group ID of this commerce order attachment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce order attachment.
	 *
	 * @param modifiedDate the modified date of this commerce order attachment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce order attachment.
	 *
	 * @param mvccVersion the mvcc version of this commerce order attachment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce order attachment.
	 *
	 * @param primaryKey the primary key of this commerce order attachment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce order attachment.
	 *
	 * @param priority the priority of this commerce order attachment
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this commerce order attachment is restricted.
	 *
	 * @param restricted the restricted of this commerce order attachment
	 */
	@Override
	public void setRestricted(boolean restricted) {
		model.setRestricted(restricted);
	}

	/**
	 * Sets the title of this commerce order attachment.
	 *
	 * @param title the title of this commerce order attachment
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the type of this commerce order attachment.
	 *
	 * @param type the type of this commerce order attachment
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce order attachment.
	 *
	 * @param userId the user ID of this commerce order attachment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order attachment.
	 *
	 * @param userName the user name of this commerce order attachment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order attachment.
	 *
	 * @param userUuid the user uuid of this commerce order attachment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce order attachment.
	 *
	 * @param uuid the uuid of this commerce order attachment
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceOrderAttachmentWrapper wrap(
		CommerceOrderAttachment commerceOrderAttachment) {

		return new CommerceOrderAttachmentWrapper(commerceOrderAttachment);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1894073025