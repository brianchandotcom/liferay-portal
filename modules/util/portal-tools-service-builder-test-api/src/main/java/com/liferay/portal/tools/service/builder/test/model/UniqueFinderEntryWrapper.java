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
 * This class is a wrapper for {@link UniqueFinderEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UniqueFinderEntry
 * @generated
 */
public class UniqueFinderEntryWrapper
	extends BaseModelWrapper<UniqueFinderEntry>
	implements ModelWrapper<UniqueFinderEntry>, UniqueFinderEntry {

	public UniqueFinderEntryWrapper(UniqueFinderEntry uniqueFinderEntry) {
		super(uniqueFinderEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uniqueFinderEntryId", getUniqueFinderEntryId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long uniqueFinderEntryId = (Long)attributes.get("uniqueFinderEntryId");

		if (uniqueFinderEntryId != null) {
			setUniqueFinderEntryId(uniqueFinderEntryId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public UniqueFinderEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the modified date of this unique finder entry.
	 *
	 * @return the modified date of this unique finder entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this unique finder entry.
	 *
	 * @return the name of this unique finder entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this unique finder entry.
	 *
	 * @return the primary key of this unique finder entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the unique finder entry ID of this unique finder entry.
	 *
	 * @return the unique finder entry ID of this unique finder entry
	 */
	@Override
	public long getUniqueFinderEntryId() {
		return model.getUniqueFinderEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the modified date of this unique finder entry.
	 *
	 * @param modifiedDate the modified date of this unique finder entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this unique finder entry.
	 *
	 * @param name the name of this unique finder entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this unique finder entry.
	 *
	 * @param primaryKey the primary key of this unique finder entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the unique finder entry ID of this unique finder entry.
	 *
	 * @param uniqueFinderEntryId the unique finder entry ID of this unique finder entry
	 */
	@Override
	public void setUniqueFinderEntryId(long uniqueFinderEntryId) {
		model.setUniqueFinderEntryId(uniqueFinderEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected UniqueFinderEntryWrapper wrap(
		UniqueFinderEntry uniqueFinderEntry) {

		return new UniqueFinderEntryWrapper(uniqueFinderEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1943212260