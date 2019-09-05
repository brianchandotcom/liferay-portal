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

package com.liferay.chat.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Entry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
public class EntryWrapper
	extends BaseModelWrapper<Entry> implements Entry, ModelWrapper<Entry> {

	public EntryWrapper(Entry entry) {
		super(entry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("entryId", getEntryId());
		attributes.put("createDate", getCreateDate());
		attributes.put("fromUserId", getFromUserId());
		attributes.put("toUserId", getToUserId());
		attributes.put("content", getContent());
		attributes.put("flag", getFlag());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long fromUserId = (Long)attributes.get("fromUserId");

		if (fromUserId != null) {
			setFromUserId(fromUserId);
		}

		Long toUserId = (Long)attributes.get("toUserId");

		if (toUserId != null) {
			setToUserId(toUserId);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Integer flag = (Integer)attributes.get("flag");

		if (flag != null) {
			setFlag(flag);
		}
	}

	/**
	 * Returns the content of this entry.
	 *
	 * @return the content of this entry
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this entry.
	 *
	 * @return the create date of this entry
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entry ID of this entry.
	 *
	 * @return the entry ID of this entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the flag of this entry.
	 *
	 * @return the flag of this entry
	 */
	@Override
	public int getFlag() {
		return model.getFlag();
	}

	/**
	 * Returns the from user ID of this entry.
	 *
	 * @return the from user ID of this entry
	 */
	@Override
	public long getFromUserId() {
		return model.getFromUserId();
	}

	/**
	 * Returns the from user uuid of this entry.
	 *
	 * @return the from user uuid of this entry
	 */
	@Override
	public String getFromUserUuid() {
		return model.getFromUserUuid();
	}

	/**
	 * Returns the primary key of this entry.
	 *
	 * @return the primary key of this entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the to user ID of this entry.
	 *
	 * @return the to user ID of this entry
	 */
	@Override
	public long getToUserId() {
		return model.getToUserId();
	}

	/**
	 * Returns the to user uuid of this entry.
	 *
	 * @return the to user uuid of this entry
	 */
	@Override
	public String getToUserUuid() {
		return model.getToUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the content of this entry.
	 *
	 * @param content the content of this entry
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this entry.
	 *
	 * @param createDate the create date of this entry
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entry ID of this entry.
	 *
	 * @param entryId the entry ID of this entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the flag of this entry.
	 *
	 * @param flag the flag of this entry
	 */
	@Override
	public void setFlag(int flag) {
		model.setFlag(flag);
	}

	/**
	 * Sets the from user ID of this entry.
	 *
	 * @param fromUserId the from user ID of this entry
	 */
	@Override
	public void setFromUserId(long fromUserId) {
		model.setFromUserId(fromUserId);
	}

	/**
	 * Sets the from user uuid of this entry.
	 *
	 * @param fromUserUuid the from user uuid of this entry
	 */
	@Override
	public void setFromUserUuid(String fromUserUuid) {
		model.setFromUserUuid(fromUserUuid);
	}

	/**
	 * Sets the primary key of this entry.
	 *
	 * @param primaryKey the primary key of this entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the to user ID of this entry.
	 *
	 * @param toUserId the to user ID of this entry
	 */
	@Override
	public void setToUserId(long toUserId) {
		model.setToUserId(toUserId);
	}

	/**
	 * Sets the to user uuid of this entry.
	 *
	 * @param toUserUuid the to user uuid of this entry
	 */
	@Override
	public void setToUserUuid(String toUserUuid) {
		model.setToUserUuid(toUserUuid);
	}

	@Override
	protected EntryWrapper wrap(Entry entry) {
		return new EntryWrapper(entry);
	}

}