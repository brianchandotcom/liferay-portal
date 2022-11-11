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
 * This class is a wrapper for {@link RedundantIndexEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedundantIndexEntry
 * @generated
 */
public class RedundantIndexEntryWrapper
	extends BaseModelWrapper<RedundantIndexEntry>
	implements ModelWrapper<RedundantIndexEntry>, RedundantIndexEntry {

	public RedundantIndexEntryWrapper(RedundantIndexEntry redundantIndexEntry) {
		super(redundantIndexEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("redundantIndexEntryId", getRedundantIndexEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long redundantIndexEntryId = (Long)attributes.get(
			"redundantIndexEntryId");

		if (redundantIndexEntryId != null) {
			setRedundantIndexEntryId(redundantIndexEntryId);
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
	public RedundantIndexEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this redundant index entry.
	 *
	 * @return the company ID of this redundant index entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the name of this redundant index entry.
	 *
	 * @return the name of this redundant index entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this redundant index entry.
	 *
	 * @return the primary key of this redundant index entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the redundant index entry ID of this redundant index entry.
	 *
	 * @return the redundant index entry ID of this redundant index entry
	 */
	@Override
	public long getRedundantIndexEntryId() {
		return model.getRedundantIndexEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this redundant index entry.
	 *
	 * @param companyId the company ID of this redundant index entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the name of this redundant index entry.
	 *
	 * @param name the name of this redundant index entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this redundant index entry.
	 *
	 * @param primaryKey the primary key of this redundant index entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the redundant index entry ID of this redundant index entry.
	 *
	 * @param redundantIndexEntryId the redundant index entry ID of this redundant index entry
	 */
	@Override
	public void setRedundantIndexEntryId(long redundantIndexEntryId) {
		model.setRedundantIndexEntryId(redundantIndexEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RedundantIndexEntryWrapper wrap(
		RedundantIndexEntry redundantIndexEntry) {

		return new RedundantIndexEntryWrapper(redundantIndexEntry);
	}

}