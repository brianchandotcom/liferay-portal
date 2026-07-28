/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TimestampTypeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntry
 * @generated
 */
public class TimestampTypeEntryWrapper
	extends BaseModelWrapper<TimestampTypeEntry>
	implements ModelWrapper<TimestampTypeEntry>, TimestampTypeEntry {

	public TimestampTypeEntryWrapper(TimestampTypeEntry timestampTypeEntry) {
		super(timestampTypeEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("timestampTypeEntryId", getTimestampTypeEntryId());
		attributes.put("dateValue", getDateValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long timestampTypeEntryId = (Long)attributes.get(
			"timestampTypeEntryId");

		if (timestampTypeEntryId != null) {
			setTimestampTypeEntryId(timestampTypeEntryId);
		}

		Date dateValue = (Date)attributes.get("dateValue");

		if (dateValue != null) {
			setDateValue(dateValue);
		}
	}

	@Override
	public TimestampTypeEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date value of this timestamp type entry.
	 *
	 * @return the date value of this timestamp type entry
	 */
	@Override
	public Date getDateValue() {
		return model.getDateValue();
	}

	/**
	 * Returns the primary key of this timestamp type entry.
	 *
	 * @return the primary key of this timestamp type entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the timestamp type entry ID of this timestamp type entry.
	 *
	 * @return the timestamp type entry ID of this timestamp type entry
	 */
	@Override
	public long getTimestampTypeEntryId() {
		return model.getTimestampTypeEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date value of this timestamp type entry.
	 *
	 * @param dateValue the date value of this timestamp type entry
	 */
	@Override
	public void setDateValue(Date dateValue) {
		model.setDateValue(dateValue);
	}

	/**
	 * Sets the primary key of this timestamp type entry.
	 *
	 * @param primaryKey the primary key of this timestamp type entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the timestamp type entry ID of this timestamp type entry.
	 *
	 * @param timestampTypeEntryId the timestamp type entry ID of this timestamp type entry
	 */
	@Override
	public void setTimestampTypeEntryId(long timestampTypeEntryId) {
		model.setTimestampTypeEntryId(timestampTypeEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected TimestampTypeEntryWrapper wrap(
		TimestampTypeEntry timestampTypeEntry) {

		return new TimestampTypeEntryWrapper(timestampTypeEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-599300520