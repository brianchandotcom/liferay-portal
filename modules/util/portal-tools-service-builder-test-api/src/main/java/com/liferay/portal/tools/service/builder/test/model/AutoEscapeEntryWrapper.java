/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AutoEscapeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntry
 * @generated
 */
public class AutoEscapeEntryWrapper
	extends BaseModelWrapper<AutoEscapeEntry>
	implements AutoEscapeEntry, ModelWrapper<AutoEscapeEntry> {

	public AutoEscapeEntryWrapper(AutoEscapeEntry autoEscapeEntry) {
		super(autoEscapeEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("autoEscapeEntryId", getAutoEscapeEntryId());
		attributes.put("autoEscapeEnabledColumn", getAutoEscapeEnabledColumn());
		attributes.put(
			"autoEscapeDisabledColumn", getAutoEscapeDisabledColumn());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long autoEscapeEntryId = (Long)attributes.get("autoEscapeEntryId");

		if (autoEscapeEntryId != null) {
			setAutoEscapeEntryId(autoEscapeEntryId);
		}

		String autoEscapeEnabledColumn = (String)attributes.get(
			"autoEscapeEnabledColumn");

		if (autoEscapeEnabledColumn != null) {
			setAutoEscapeEnabledColumn(autoEscapeEnabledColumn);
		}

		String autoEscapeDisabledColumn = (String)attributes.get(
			"autoEscapeDisabledColumn");

		if (autoEscapeDisabledColumn != null) {
			setAutoEscapeDisabledColumn(autoEscapeDisabledColumn);
		}
	}

	@Override
	public AutoEscapeEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the auto escape disabled column of this auto escape entry.
	 *
	 * @return the auto escape disabled column of this auto escape entry
	 */
	@Override
	public String getAutoEscapeDisabledColumn() {
		return model.getAutoEscapeDisabledColumn();
	}

	/**
	 * Returns the auto escape enabled column of this auto escape entry.
	 *
	 * @return the auto escape enabled column of this auto escape entry
	 */
	@Override
	public String getAutoEscapeEnabledColumn() {
		return model.getAutoEscapeEnabledColumn();
	}

	/**
	 * Returns the auto escape entry ID of this auto escape entry.
	 *
	 * @return the auto escape entry ID of this auto escape entry
	 */
	@Override
	public long getAutoEscapeEntryId() {
		return model.getAutoEscapeEntryId();
	}

	/**
	 * Returns the primary key of this auto escape entry.
	 *
	 * @return the primary key of this auto escape entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Sets the auto escape disabled column of this auto escape entry.
	 *
	 * @param autoEscapeDisabledColumn the auto escape disabled column of this auto escape entry
	 */
	@Override
	public void setAutoEscapeDisabledColumn(String autoEscapeDisabledColumn) {
		model.setAutoEscapeDisabledColumn(autoEscapeDisabledColumn);
	}

	/**
	 * Sets the auto escape enabled column of this auto escape entry.
	 *
	 * @param autoEscapeEnabledColumn the auto escape enabled column of this auto escape entry
	 */
	@Override
	public void setAutoEscapeEnabledColumn(String autoEscapeEnabledColumn) {
		model.setAutoEscapeEnabledColumn(autoEscapeEnabledColumn);
	}

	/**
	 * Sets the auto escape entry ID of this auto escape entry.
	 *
	 * @param autoEscapeEntryId the auto escape entry ID of this auto escape entry
	 */
	@Override
	public void setAutoEscapeEntryId(long autoEscapeEntryId) {
		model.setAutoEscapeEntryId(autoEscapeEntryId);
	}

	/**
	 * Sets the primary key of this auto escape entry.
	 *
	 * @param primaryKey the primary key of this auto escape entry
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
	protected AutoEscapeEntryWrapper wrap(AutoEscapeEntry autoEscapeEntry) {
		return new AutoEscapeEntryWrapper(autoEscapeEntry);
	}

}