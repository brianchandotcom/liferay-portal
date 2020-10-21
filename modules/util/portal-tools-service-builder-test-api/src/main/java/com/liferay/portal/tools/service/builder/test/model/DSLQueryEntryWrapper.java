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
 * This class is a wrapper for {@link DSLQueryEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryEntry
 * @generated
 */
public class DSLQueryEntryWrapper
	extends BaseModelWrapper<DSLQueryEntry>
	implements DSLQueryEntry, ModelWrapper<DSLQueryEntry> {

	public DSLQueryEntryWrapper(DSLQueryEntry dslQueryEntry) {
		super(dslQueryEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dslQueryEntryId", getDslQueryEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dslQueryEntryId = (Long)attributes.get("dslQueryEntryId");

		if (dslQueryEntryId != null) {
			setDslQueryEntryId(dslQueryEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the dsl query entry ID of this dsl query entry.
	 *
	 * @return the dsl query entry ID of this dsl query entry
	 */
	@Override
	public long getDslQueryEntryId() {
		return model.getDslQueryEntryId();
	}

	/**
	 * Returns the name of this dsl query entry.
	 *
	 * @return the name of this dsl query entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this dsl query entry.
	 *
	 * @return the primary key of this dsl query entry
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
	 * Sets the dsl query entry ID of this dsl query entry.
	 *
	 * @param dslQueryEntryId the dsl query entry ID of this dsl query entry
	 */
	@Override
	public void setDslQueryEntryId(long dslQueryEntryId) {
		model.setDslQueryEntryId(dslQueryEntryId);
	}

	/**
	 * Sets the name of this dsl query entry.
	 *
	 * @param name the name of this dsl query entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this dsl query entry.
	 *
	 * @param primaryKey the primary key of this dsl query entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected DSLQueryEntryWrapper wrap(DSLQueryEntry dslQueryEntry) {
		return new DSLQueryEntryWrapper(dslQueryEntry);
	}

}