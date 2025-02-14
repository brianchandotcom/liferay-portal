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
 * This class is a wrapper for {@link DefinedDefaultOrderEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntry
 * @generated
 */
public class DefinedDefaultOrderEntryWrapper
	extends BaseModelWrapper<DefinedDefaultOrderEntry>
	implements DefinedDefaultOrderEntry,
			   ModelWrapper<DefinedDefaultOrderEntry> {

	public DefinedDefaultOrderEntryWrapper(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		super(definedDefaultOrderEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"definedDefaultOrderEntryId", getDefinedDefaultOrderEntryId());
		attributes.put("name", getName());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long definedDefaultOrderEntryId = (Long)attributes.get(
			"definedDefaultOrderEntryId");

		if (definedDefaultOrderEntryId != null) {
			setDefinedDefaultOrderEntryId(definedDefaultOrderEntryId);
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
	public DefinedDefaultOrderEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the defined default order entry ID of this defined default order entry.
	 *
	 * @return the defined default order entry ID of this defined default order entry
	 */
	@Override
	public long getDefinedDefaultOrderEntryId() {
		return model.getDefinedDefaultOrderEntryId();
	}

	/**
	 * Returns the modified date of this defined default order entry.
	 *
	 * @return the modified date of this defined default order entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this defined default order entry.
	 *
	 * @return the name of this defined default order entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this defined default order entry.
	 *
	 * @return the primary key of this defined default order entry
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
	 * Sets the defined default order entry ID of this defined default order entry.
	 *
	 * @param definedDefaultOrderEntryId the defined default order entry ID of this defined default order entry
	 */
	@Override
	public void setDefinedDefaultOrderEntryId(long definedDefaultOrderEntryId) {
		model.setDefinedDefaultOrderEntryId(definedDefaultOrderEntryId);
	}

	/**
	 * Sets the modified date of this defined default order entry.
	 *
	 * @param modifiedDate the modified date of this defined default order entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this defined default order entry.
	 *
	 * @param name the name of this defined default order entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this defined default order entry.
	 *
	 * @param primaryKey the primary key of this defined default order entry
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
	protected DefinedDefaultOrderEntryWrapper wrap(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return new DefinedDefaultOrderEntryWrapper(definedDefaultOrderEntry);
	}

}