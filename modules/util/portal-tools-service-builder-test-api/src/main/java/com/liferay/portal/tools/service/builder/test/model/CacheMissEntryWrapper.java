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
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CacheMissEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntry
 * @generated
 */
public class CacheMissEntryWrapper
	extends BaseModelWrapper<CacheMissEntry>
	implements CacheMissEntry, ModelWrapper<CacheMissEntry> {

	public CacheMissEntryWrapper(CacheMissEntry cacheMissEntry) {
		super(cacheMissEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("cacheMissEntryId", getCacheMissEntryId());

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

		Long cacheMissEntryId = (Long)attributes.get("cacheMissEntryId");

		if (cacheMissEntryId != null) {
			setCacheMissEntryId(cacheMissEntryId);
		}
	}

	/**
	 * Returns the cache miss entry ID of this cache miss entry.
	 *
	 * @return the cache miss entry ID of this cache miss entry
	 */
	@Override
	public long getCacheMissEntryId() {
		return model.getCacheMissEntryId();
	}

	/**
	 * Returns the ct collection ID of this cache miss entry.
	 *
	 * @return the ct collection ID of this cache miss entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this cache miss entry.
	 *
	 * @return the mvcc version of this cache miss entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cache miss entry.
	 *
	 * @return the primary key of this cache miss entry
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
	 * Sets the cache miss entry ID of this cache miss entry.
	 *
	 * @param cacheMissEntryId the cache miss entry ID of this cache miss entry
	 */
	@Override
	public void setCacheMissEntryId(long cacheMissEntryId) {
		model.setCacheMissEntryId(cacheMissEntryId);
	}

	/**
	 * Sets the ct collection ID of this cache miss entry.
	 *
	 * @param ctCollectionId the ct collection ID of this cache miss entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this cache miss entry.
	 *
	 * @param mvccVersion the mvcc version of this cache miss entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cache miss entry.
	 *
	 * @param primaryKey the primary key of this cache miss entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public Map<String, Function<CacheMissEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CacheMissEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CacheMissEntryWrapper wrap(CacheMissEntry cacheMissEntry) {
		return new CacheMissEntryWrapper(cacheMissEntry);
	}

}