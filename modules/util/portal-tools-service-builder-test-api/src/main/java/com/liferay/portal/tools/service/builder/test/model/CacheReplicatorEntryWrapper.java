/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CacheReplicatorEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntry
 * @generated
 */
public class CacheReplicatorEntryWrapper
	extends BaseModelWrapper<CacheReplicatorEntry>
	implements CacheReplicatorEntry, ModelWrapper<CacheReplicatorEntry> {

	public CacheReplicatorEntryWrapper(
		CacheReplicatorEntry cacheReplicatorEntry) {

		super(cacheReplicatorEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cacheReplicatorEntryId", getCacheReplicatorEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cacheReplicatorEntryId = (Long)attributes.get(
			"cacheReplicatorEntryId");

		if (cacheReplicatorEntryId != null) {
			setCacheReplicatorEntryId(cacheReplicatorEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public CacheReplicatorEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the cache replicator entry ID of this cache replicator entry.
	 *
	 * @return the cache replicator entry ID of this cache replicator entry
	 */
	@Override
	public long getCacheReplicatorEntryId() {
		return model.getCacheReplicatorEntryId();
	}

	/**
	 * Returns the company ID of this cache replicator entry.
	 *
	 * @return the company ID of this cache replicator entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the name of this cache replicator entry.
	 *
	 * @return the name of this cache replicator entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this cache replicator entry.
	 *
	 * @return the primary key of this cache replicator entry
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
	 * Sets the cache replicator entry ID of this cache replicator entry.
	 *
	 * @param cacheReplicatorEntryId the cache replicator entry ID of this cache replicator entry
	 */
	@Override
	public void setCacheReplicatorEntryId(long cacheReplicatorEntryId) {
		model.setCacheReplicatorEntryId(cacheReplicatorEntryId);
	}

	/**
	 * Sets the company ID of this cache replicator entry.
	 *
	 * @param companyId the company ID of this cache replicator entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the name of this cache replicator entry.
	 *
	 * @param name the name of this cache replicator entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this cache replicator entry.
	 *
	 * @param primaryKey the primary key of this cache replicator entry
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
	protected CacheReplicatorEntryWrapper wrap(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return new CacheReplicatorEntryWrapper(cacheReplicatorEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:410999642