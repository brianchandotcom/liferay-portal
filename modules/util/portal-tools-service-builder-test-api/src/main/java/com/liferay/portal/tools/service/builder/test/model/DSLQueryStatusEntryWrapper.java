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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DSLQueryStatusEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryStatusEntry
 * @generated
 */
public class DSLQueryStatusEntryWrapper
	extends BaseModelWrapper<DSLQueryStatusEntry>
	implements DSLQueryStatusEntry, ModelWrapper<DSLQueryStatusEntry> {

	public DSLQueryStatusEntryWrapper(DSLQueryStatusEntry dslQueryStatusEntry) {
		super(dslQueryStatusEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dslQueryStatusEntryId", getDslQueryStatusEntryId());
		attributes.put("dslQueryEntryId", getDslQueryEntryId());
		attributes.put("status", getStatus());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dslQueryStatusEntryId = (Long)attributes.get(
			"dslQueryStatusEntryId");

		if (dslQueryStatusEntryId != null) {
			setDslQueryStatusEntryId(dslQueryStatusEntryId);
		}

		Long dslQueryEntryId = (Long)attributes.get("dslQueryEntryId");

		if (dslQueryEntryId != null) {
			setDslQueryEntryId(dslQueryEntryId);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	 * Returns the dsl query entry ID of this dsl query status entry.
	 *
	 * @return the dsl query entry ID of this dsl query status entry
	 */
	@Override
	public long getDslQueryEntryId() {
		return model.getDslQueryEntryId();
	}

	/**
	 * Returns the dsl query status entry ID of this dsl query status entry.
	 *
	 * @return the dsl query status entry ID of this dsl query status entry
	 */
	@Override
	public long getDslQueryStatusEntryId() {
		return model.getDslQueryStatusEntryId();
	}

	/**
	 * Returns the primary key of this dsl query status entry.
	 *
	 * @return the primary key of this dsl query status entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this dsl query status entry.
	 *
	 * @return the status of this dsl query status entry
	 */
	@Override
	public String getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status date of this dsl query status entry.
	 *
	 * @return the status date of this dsl query status entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the dsl query entry ID of this dsl query status entry.
	 *
	 * @param dslQueryEntryId the dsl query entry ID of this dsl query status entry
	 */
	@Override
	public void setDslQueryEntryId(long dslQueryEntryId) {
		model.setDslQueryEntryId(dslQueryEntryId);
	}

	/**
	 * Sets the dsl query status entry ID of this dsl query status entry.
	 *
	 * @param dslQueryStatusEntryId the dsl query status entry ID of this dsl query status entry
	 */
	@Override
	public void setDslQueryStatusEntryId(long dslQueryStatusEntryId) {
		model.setDslQueryStatusEntryId(dslQueryStatusEntryId);
	}

	/**
	 * Sets the primary key of this dsl query status entry.
	 *
	 * @param primaryKey the primary key of this dsl query status entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this dsl query status entry.
	 *
	 * @param status the status of this dsl query status entry
	 */
	@Override
	public void setStatus(String status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status date of this dsl query status entry.
	 *
	 * @param statusDate the status date of this dsl query status entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	@Override
	protected DSLQueryStatusEntryWrapper wrap(
		DSLQueryStatusEntry dslQueryStatusEntry) {

		return new DSLQueryStatusEntryWrapper(dslQueryStatusEntry);
	}

}