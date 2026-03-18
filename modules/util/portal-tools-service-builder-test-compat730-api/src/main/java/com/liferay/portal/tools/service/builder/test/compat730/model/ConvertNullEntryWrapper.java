/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ConvertNullEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntry
 * @generated
 */
public class ConvertNullEntryWrapper
	extends BaseModelWrapper<ConvertNullEntry>
	implements ConvertNullEntry, ModelWrapper<ConvertNullEntry> {

	public ConvertNullEntryWrapper(ConvertNullEntry convertNullEntry) {
		super(convertNullEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("convertNullEntryId", getConvertNullEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long convertNullEntryId = (Long)attributes.get("convertNullEntryId");

		if (convertNullEntryId != null) {
			setConvertNullEntryId(convertNullEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the convert null entry ID of this convert null entry.
	 *
	 * @return the convert null entry ID of this convert null entry
	 */
	@Override
	public long getConvertNullEntryId() {
		return model.getConvertNullEntryId();
	}

	/**
	 * Returns the name of this convert null entry.
	 *
	 * @return the name of this convert null entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this convert null entry.
	 *
	 * @return the primary key of this convert null entry
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
	 * Sets the convert null entry ID of this convert null entry.
	 *
	 * @param convertNullEntryId the convert null entry ID of this convert null entry
	 */
	@Override
	public void setConvertNullEntryId(long convertNullEntryId) {
		model.setConvertNullEntryId(convertNullEntryId);
	}

	/**
	 * Sets the name of this convert null entry.
	 *
	 * @param name the name of this convert null entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this convert null entry.
	 *
	 * @param primaryKey the primary key of this convert null entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected ConvertNullEntryWrapper wrap(ConvertNullEntry convertNullEntry) {
		return new ConvertNullEntryWrapper(convertNullEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1953596773