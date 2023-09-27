/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LazyBlobEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntry
 * @generated
 */
public class LazyBlobEntryWrapper
	extends BaseModelWrapper<LazyBlobEntry>
	implements LazyBlobEntry, ModelWrapper<LazyBlobEntry> {

	public LazyBlobEntryWrapper(LazyBlobEntry lazyBlobEntry) {
		super(lazyBlobEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("lazyBlobEntryId", getLazyBlobEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("blob1", getBlob1());
		attributes.put("blob2", getBlob2());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long lazyBlobEntryId = (Long)attributes.get("lazyBlobEntryId");

		if (lazyBlobEntryId != null) {
			setLazyBlobEntryId(lazyBlobEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob blob1 = (Blob)attributes.get("blob1");

		if (blob1 != null) {
			setBlob1(blob1);
		}

		Blob blob2 = (Blob)attributes.get("blob2");

		if (blob2 != null) {
			setBlob2(blob2);
		}
	}

	@Override
	public LazyBlobEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the blob1 of this lazy blob entry.
	 *
	 * @return the blob1 of this lazy blob entry
	 */
	@Override
	public Blob getBlob1() {
		return model.getBlob1();
	}

	/**
	 * Returns the blob2 of this lazy blob entry.
	 *
	 * @return the blob2 of this lazy blob entry
	 */
	@Override
	public Blob getBlob2() {
		return model.getBlob2();
	}

	/**
	 * Returns the group ID of this lazy blob entry.
	 *
	 * @return the group ID of this lazy blob entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the lazy blob entry ID of this lazy blob entry.
	 *
	 * @return the lazy blob entry ID of this lazy blob entry
	 */
	@Override
	public long getLazyBlobEntryId() {
		return model.getLazyBlobEntryId();
	}

	/**
	 * Returns the primary key of this lazy blob entry.
	 *
	 * @return the primary key of this lazy blob entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this lazy blob entry.
	 *
	 * @return the uuid of this lazy blob entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the blob1 of this lazy blob entry.
	 *
	 * @param blob1 the blob1 of this lazy blob entry
	 */
	@Override
	public void setBlob1(Blob blob1) {
		model.setBlob1(blob1);
	}

	/**
	 * Sets the blob2 of this lazy blob entry.
	 *
	 * @param blob2 the blob2 of this lazy blob entry
	 */
	@Override
	public void setBlob2(Blob blob2) {
		model.setBlob2(blob2);
	}

	/**
	 * Sets the group ID of this lazy blob entry.
	 *
	 * @param groupId the group ID of this lazy blob entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the lazy blob entry ID of this lazy blob entry.
	 *
	 * @param lazyBlobEntryId the lazy blob entry ID of this lazy blob entry
	 */
	@Override
	public void setLazyBlobEntryId(long lazyBlobEntryId) {
		model.setLazyBlobEntryId(lazyBlobEntryId);
	}

	/**
	 * Sets the primary key of this lazy blob entry.
	 *
	 * @param primaryKey the primary key of this lazy blob entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this lazy blob entry.
	 *
	 * @param uuid the uuid of this lazy blob entry
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
	protected LazyBlobEntryWrapper wrap(LazyBlobEntry lazyBlobEntry) {
		return new LazyBlobEntryWrapper(lazyBlobEntry);
	}

}