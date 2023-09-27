/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CacheFieldEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntry
 * @generated
 */
public class CacheFieldEntryWrapper
	extends BaseModelWrapper<CacheFieldEntry>
	implements CacheFieldEntry, ModelWrapper<CacheFieldEntry> {

	public CacheFieldEntryWrapper(CacheFieldEntry cacheFieldEntry) {
		super(cacheFieldEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cacheFieldEntryId", getCacheFieldEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cacheFieldEntryId = (Long)attributes.get("cacheFieldEntryId");

		if (cacheFieldEntryId != null) {
			setCacheFieldEntryId(cacheFieldEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public CacheFieldEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the cache field entry ID of this cache field entry.
	 *
	 * @return the cache field entry ID of this cache field entry
	 */
	@Override
	public long getCacheFieldEntryId() {
		return model.getCacheFieldEntryId();
	}

	/**
	 * Returns the group ID of this cache field entry.
	 *
	 * @return the group ID of this cache field entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the name of this cache field entry.
	 *
	 * @return the name of this cache field entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getNickname() {
		return model.getNickname();
	}

	/**
	 * Returns the primary key of this cache field entry.
	 *
	 * @return the primary key of this cache field entry
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
	 * Sets the cache field entry ID of this cache field entry.
	 *
	 * @param cacheFieldEntryId the cache field entry ID of this cache field entry
	 */
	@Override
	public void setCacheFieldEntryId(long cacheFieldEntryId) {
		model.setCacheFieldEntryId(cacheFieldEntryId);
	}

	/**
	 * Sets the group ID of this cache field entry.
	 *
	 * @param groupId the group ID of this cache field entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the name of this cache field entry.
	 *
	 * @param name the name of this cache field entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	@Override
	public void setNickname(String nickname) {
		model.setNickname(nickname);
	}

	/**
	 * Sets the primary key of this cache field entry.
	 *
	 * @param primaryKey the primary key of this cache field entry
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
	protected CacheFieldEntryWrapper wrap(CacheFieldEntry cacheFieldEntry) {
		return new CacheFieldEntryWrapper(cacheFieldEntry);
	}

}