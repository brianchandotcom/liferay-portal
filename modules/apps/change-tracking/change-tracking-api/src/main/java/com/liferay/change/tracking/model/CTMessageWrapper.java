/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTMessage
 * @generated
 */
public class CTMessageWrapper
	extends BaseModelWrapper<CTMessage>
	implements CTMessage, ModelWrapper<CTMessage> {

	public CTMessageWrapper(CTMessage ctMessage) {
		super(ctMessage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctMessageId", getCtMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("messageContent", getMessageContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctMessageId = (Long)attributes.get("ctMessageId");

		if (ctMessageId != null) {
			setCtMessageId(ctMessageId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String messageContent = (String)attributes.get("messageContent");

		if (messageContent != null) {
			setMessageContent(messageContent);
		}
	}

	@Override
	public CTMessage cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct message.
	 *
	 * @return the company ID of this ct message
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ct message.
	 *
	 * @return the ct collection ID of this ct message
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct message ID of this ct message.
	 *
	 * @return the ct message ID of this ct message
	 */
	@Override
	public long getCtMessageId() {
		return model.getCtMessageId();
	}

	/**
	 * Returns the message content of this ct message.
	 *
	 * @return the message content of this ct message
	 */
	@Override
	public String getMessageContent() {
		return model.getMessageContent();
	}

	/**
	 * Returns the mvcc version of this ct message.
	 *
	 * @return the mvcc version of this ct message
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct message.
	 *
	 * @return the primary key of this ct message
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct message.
	 *
	 * @param companyId the company ID of this ct message
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ct message.
	 *
	 * @param ctCollectionId the ct collection ID of this ct message
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct message ID of this ct message.
	 *
	 * @param ctMessageId the ct message ID of this ct message
	 */
	@Override
	public void setCtMessageId(long ctMessageId) {
		model.setCtMessageId(ctMessageId);
	}

	/**
	 * Sets the message content of this ct message.
	 *
	 * @param messageContent the message content of this ct message
	 */
	@Override
	public void setMessageContent(String messageContent) {
		model.setMessageContent(messageContent);
	}

	/**
	 * Sets the mvcc version of this ct message.
	 *
	 * @param mvccVersion the mvcc version of this ct message
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct message.
	 *
	 * @param primaryKey the primary key of this ct message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTMessageWrapper wrap(CTMessage ctMessage) {
		return new CTMessageWrapper(ctMessage);
	}

}