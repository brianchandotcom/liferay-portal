/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MappingEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntry
 * @generated
 */
public class MappingEntryWrapper
	extends BaseModelWrapper<MappingEntry>
	implements MappingEntry, ModelWrapper<MappingEntry> {

	public MappingEntryWrapper(MappingEntry mappingEntry) {
		super(mappingEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mappingEntryId", getMappingEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mappingEntryId = (Long)attributes.get("mappingEntryId");

		if (mappingEntryId != null) {
			setMappingEntryId(mappingEntryId);
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

	/**
	 * Returns the company ID of this mapping entry.
	 *
	 * @return the company ID of this mapping entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mapping entry ID of this mapping entry.
	 *
	 * @return the mapping entry ID of this mapping entry
	 */
	@Override
	public long getMappingEntryId() {
		return model.getMappingEntryId();
	}

	/**
	 * Returns the name of this mapping entry.
	 *
	 * @return the name of this mapping entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this mapping entry.
	 *
	 * @return the primary key of this mapping entry
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
	 * Sets the company ID of this mapping entry.
	 *
	 * @param companyId the company ID of this mapping entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mapping entry ID of this mapping entry.
	 *
	 * @param mappingEntryId the mapping entry ID of this mapping entry
	 */
	@Override
	public void setMappingEntryId(long mappingEntryId) {
		model.setMappingEntryId(mappingEntryId);
	}

	/**
	 * Sets the name of this mapping entry.
	 *
	 * @param name the name of this mapping entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this mapping entry.
	 *
	 * @param primaryKey the primary key of this mapping entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected MappingEntryWrapper wrap(MappingEntry mappingEntry) {
		return new MappingEntryWrapper(mappingEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1523052788