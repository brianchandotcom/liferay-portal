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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CacheDisabledEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheDisabledEntry
 * @generated
 */
public class CacheDisabledEntryWrapper
	extends BaseModelWrapper<CacheDisabledEntry>
	implements CacheDisabledEntry, ModelWrapper<CacheDisabledEntry> {

	public CacheDisabledEntryWrapper(CacheDisabledEntry cacheDisabledEntry) {
		super(cacheDisabledEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cacheDisabledEntryId", getCacheDisabledEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cacheDisabledEntryId = (Long)attributes.get(
			"cacheDisabledEntryId");

		if (cacheDisabledEntryId != null) {
			setCacheDisabledEntryId(cacheDisabledEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the cache disabled entry ID of this cache disabled entry.
	 *
	 * @return the cache disabled entry ID of this cache disabled entry
	 */
	@Override
	public long getCacheDisabledEntryId() {
		return model.getCacheDisabledEntryId();
	}

	/**
	 * Returns the name of this cache disabled entry.
	 *
	 * @return the name of this cache disabled entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this cache disabled entry.
	 *
	 * @return the primary key of this cache disabled entry
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
	 * Sets the cache disabled entry ID of this cache disabled entry.
	 *
	 * @param cacheDisabledEntryId the cache disabled entry ID of this cache disabled entry
	 */
	@Override
	public void setCacheDisabledEntryId(long cacheDisabledEntryId) {
		model.setCacheDisabledEntryId(cacheDisabledEntryId);
	}

	/**
	 * Sets the name of this cache disabled entry.
	 *
	 * @param name the name of this cache disabled entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this cache disabled entry.
	 *
	 * @param primaryKey the primary key of this cache disabled entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected CacheDisabledEntryWrapper wrap(
		CacheDisabledEntry cacheDisabledEntry) {

		return new CacheDisabledEntryWrapper(cacheDisabledEntry);
	}

}