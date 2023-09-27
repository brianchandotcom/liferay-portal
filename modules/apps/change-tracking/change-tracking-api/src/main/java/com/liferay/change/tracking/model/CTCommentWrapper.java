/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTComment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTComment
 * @generated
 */
public class CTCommentWrapper
	extends BaseModelWrapper<CTComment>
	implements CTComment, ModelWrapper<CTComment> {

	public CTCommentWrapper(CTComment ctComment) {
		super(ctComment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCommentId", getCtCommentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("ctEntryId", getCtEntryId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCommentId = (Long)attributes.get("ctCommentId");

		if (ctCommentId != null) {
			setCtCommentId(ctCommentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long ctEntryId = (Long)attributes.get("ctEntryId");

		if (ctEntryId != null) {
			setCtEntryId(ctEntryId);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public CTComment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct comment.
	 *
	 * @return the company ID of this ct comment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ct comment.
	 *
	 * @return the create date of this ct comment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ct comment.
	 *
	 * @return the ct collection ID of this ct comment
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct comment ID of this ct comment.
	 *
	 * @return the ct comment ID of this ct comment
	 */
	@Override
	public long getCtCommentId() {
		return model.getCtCommentId();
	}

	/**
	 * Returns the ct entry ID of this ct comment.
	 *
	 * @return the ct entry ID of this ct comment
	 */
	@Override
	public long getCtEntryId() {
		return model.getCtEntryId();
	}

	/**
	 * Returns the modified date of this ct comment.
	 *
	 * @return the modified date of this ct comment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ct comment.
	 *
	 * @return the mvcc version of this ct comment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct comment.
	 *
	 * @return the primary key of this ct comment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this ct comment.
	 *
	 * @return the user ID of this ct comment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this ct comment.
	 *
	 * @return the user uuid of this ct comment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this ct comment.
	 *
	 * @return the value of this ct comment
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public boolean isEdited() {
		return model.isEdited();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct comment.
	 *
	 * @param companyId the company ID of this ct comment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ct comment.
	 *
	 * @param createDate the create date of this ct comment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ct comment.
	 *
	 * @param ctCollectionId the ct collection ID of this ct comment
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct comment ID of this ct comment.
	 *
	 * @param ctCommentId the ct comment ID of this ct comment
	 */
	@Override
	public void setCtCommentId(long ctCommentId) {
		model.setCtCommentId(ctCommentId);
	}

	/**
	 * Sets the ct entry ID of this ct comment.
	 *
	 * @param ctEntryId the ct entry ID of this ct comment
	 */
	@Override
	public void setCtEntryId(long ctEntryId) {
		model.setCtEntryId(ctEntryId);
	}

	/**
	 * Sets the modified date of this ct comment.
	 *
	 * @param modifiedDate the modified date of this ct comment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ct comment.
	 *
	 * @param mvccVersion the mvcc version of this ct comment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct comment.
	 *
	 * @param primaryKey the primary key of this ct comment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this ct comment.
	 *
	 * @param userId the user ID of this ct comment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this ct comment.
	 *
	 * @param userUuid the user uuid of this ct comment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this ct comment.
	 *
	 * @param value the value of this ct comment
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTCommentWrapper wrap(CTComment ctComment) {
		return new CTCommentWrapper(ctComment);
	}

}