/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
 * This class is a wrapper for {@link CommerceOrderNote}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNote
 * @generated
 */
public class CommerceOrderNoteWrapper
	extends BaseModelWrapper<CommerceOrderNote>
	implements CommerceOrderNote, ModelWrapper<CommerceOrderNote> {

	public CommerceOrderNoteWrapper(CommerceOrderNote commerceOrderNote) {
		super(commerceOrderNote);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
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
	public CommerceOrderNote cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce order ID of this commerce order note.
	 *
	 * @return the commerce order ID of this commerce order note
	 */
	@Override
	public long getCommerceOrderId() {
		return model.getCommerceOrderId();
	}

	/**
	 * Returns the commerce order note ID of this commerce order note.
	 *
	 * @return the commerce order note ID of this commerce order note
	 */
	@Override
	public long getCommerceOrderNoteId() {
		return model.getCommerceOrderNoteId();
	}

	/**
	 * Returns the company ID of this commerce order note.
	 *
	 * @return the company ID of this commerce order note
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this commerce order note.
	 *
	 * @return the content of this commerce order note
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this commerce order note.
	 *
	 * @return the create date of this commerce order note
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce order note.
	 *
	 * @return the external reference code of this commerce order note
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce order note.
	 *
	 * @return the group ID of this commerce order note
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce order note.
	 *
	 * @return the modified date of this commerce order note
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce order note.
	 *
	 * @return the mvcc version of this commerce order note
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce order note.
	 *
	 * @return the primary key of this commerce order note
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the restricted of this commerce order note.
	 *
	 * @return the restricted of this commerce order note
	 */
	@Override
	public boolean getRestricted() {
		return model.getRestricted();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser() {
		return model.getUser();
	}

	/**
	 * Returns the user ID of this commerce order note.
	 *
	 * @return the user ID of this commerce order note
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order note.
	 *
	 * @return the user name of this commerce order note
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order note.
	 *
	 * @return the user uuid of this commerce order note
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce order note.
	 *
	 * @return the uuid of this commerce order note
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce order note is restricted.
	 *
	 * @return <code>true</code> if this commerce order note is restricted; <code>false</code> otherwise
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
	 * Sets the commerce order ID of this commerce order note.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order note
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		model.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the commerce order note ID of this commerce order note.
	 *
	 * @param commerceOrderNoteId the commerce order note ID of this commerce order note
	 */
	@Override
	public void setCommerceOrderNoteId(long commerceOrderNoteId) {
		model.setCommerceOrderNoteId(commerceOrderNoteId);
	}

	/**
	 * Sets the company ID of this commerce order note.
	 *
	 * @param companyId the company ID of this commerce order note
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this commerce order note.
	 *
	 * @param content the content of this commerce order note
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this commerce order note.
	 *
	 * @param createDate the create date of this commerce order note
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce order note.
	 *
	 * @param externalReferenceCode the external reference code of this commerce order note
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce order note.
	 *
	 * @param groupId the group ID of this commerce order note
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce order note.
	 *
	 * @param modifiedDate the modified date of this commerce order note
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce order note.
	 *
	 * @param mvccVersion the mvcc version of this commerce order note
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce order note.
	 *
	 * @param primaryKey the primary key of this commerce order note
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this commerce order note is restricted.
	 *
	 * @param restricted the restricted of this commerce order note
	 */
	@Override
	public void setRestricted(boolean restricted) {
		model.setRestricted(restricted);
	}

	/**
	 * Sets the user ID of this commerce order note.
	 *
	 * @param userId the user ID of this commerce order note
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order note.
	 *
	 * @param userName the user name of this commerce order note
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order note.
	 *
	 * @param userUuid the user uuid of this commerce order note
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce order note.
	 *
	 * @param uuid the uuid of this commerce order note
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
	protected CommerceOrderNoteWrapper wrap(
		CommerceOrderNote commerceOrderNote) {

		return new CommerceOrderNoteWrapper(commerceOrderNote);
	}

}