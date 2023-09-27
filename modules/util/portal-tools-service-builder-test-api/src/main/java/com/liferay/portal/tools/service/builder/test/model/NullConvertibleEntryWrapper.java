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
 * This class is a wrapper for {@link NullConvertibleEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntry
 * @generated
 */
public class NullConvertibleEntryWrapper
	extends BaseModelWrapper<NullConvertibleEntry>
	implements ModelWrapper<NullConvertibleEntry>, NullConvertibleEntry {

	public NullConvertibleEntryWrapper(
		NullConvertibleEntry nullConvertibleEntry) {

		super(nullConvertibleEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("nullConvertibleEntryId", getNullConvertibleEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long nullConvertibleEntryId = (Long)attributes.get(
			"nullConvertibleEntryId");

		if (nullConvertibleEntryId != null) {
			setNullConvertibleEntryId(nullConvertibleEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public NullConvertibleEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the name of this null convertible entry.
	 *
	 * @return the name of this null convertible entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the null convertible entry ID of this null convertible entry.
	 *
	 * @return the null convertible entry ID of this null convertible entry
	 */
	@Override
	public long getNullConvertibleEntryId() {
		return model.getNullConvertibleEntryId();
	}

	/**
	 * Returns the primary key of this null convertible entry.
	 *
	 * @return the primary key of this null convertible entry
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
	 * Sets the name of this null convertible entry.
	 *
	 * @param name the name of this null convertible entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the null convertible entry ID of this null convertible entry.
	 *
	 * @param nullConvertibleEntryId the null convertible entry ID of this null convertible entry
	 */
	@Override
	public void setNullConvertibleEntryId(long nullConvertibleEntryId) {
		model.setNullConvertibleEntryId(nullConvertibleEntryId);
	}

	/**
	 * Sets the primary key of this null convertible entry.
	 *
	 * @param primaryKey the primary key of this null convertible entry
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
	protected NullConvertibleEntryWrapper wrap(
		NullConvertibleEntry nullConvertibleEntry) {

		return new NullConvertibleEntryWrapper(nullConvertibleEntry);
	}

}