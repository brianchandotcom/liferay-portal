/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
 * This class is a wrapper for {@link UndefinedDefaultOrderEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntry
 * @generated
 */
public class UndefinedDefaultOrderEntryWrapper
	extends BaseModelWrapper<UndefinedDefaultOrderEntry>
	implements ModelWrapper<UndefinedDefaultOrderEntry>,
			   UndefinedDefaultOrderEntry {

	public UndefinedDefaultOrderEntryWrapper(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		super(undefinedDefaultOrderEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"undefinedDefaultOrderEntryId", getUndefinedDefaultOrderEntryId());
		attributes.put("name", getName());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long undefinedDefaultOrderEntryId = (Long)attributes.get(
			"undefinedDefaultOrderEntryId");

		if (undefinedDefaultOrderEntryId != null) {
			setUndefinedDefaultOrderEntryId(undefinedDefaultOrderEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public UndefinedDefaultOrderEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the modified date of this undefined default order entry.
	 *
	 * @return the modified date of this undefined default order entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this undefined default order entry.
	 *
	 * @return the name of this undefined default order entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this undefined default order entry.
	 *
	 * @return the primary key of this undefined default order entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the undefined default order entry ID of this undefined default order entry.
	 *
	 * @return the undefined default order entry ID of this undefined default order entry
	 */
	@Override
	public long getUndefinedDefaultOrderEntryId() {
		return model.getUndefinedDefaultOrderEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the modified date of this undefined default order entry.
	 *
	 * @param modifiedDate the modified date of this undefined default order entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this undefined default order entry.
	 *
	 * @param name the name of this undefined default order entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this undefined default order entry.
	 *
	 * @param primaryKey the primary key of this undefined default order entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the undefined default order entry ID of this undefined default order entry.
	 *
	 * @param undefinedDefaultOrderEntryId the undefined default order entry ID of this undefined default order entry
	 */
	@Override
	public void setUndefinedDefaultOrderEntryId(
		long undefinedDefaultOrderEntryId) {

		model.setUndefinedDefaultOrderEntryId(undefinedDefaultOrderEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected UndefinedDefaultOrderEntryWrapper wrap(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return new UndefinedDefaultOrderEntryWrapper(
			undefinedDefaultOrderEntry);
	}

}