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
 * This class is a wrapper for {@link IndexEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntry
 * @generated
 */
public class IndexEntryWrapper
	extends BaseModelWrapper<IndexEntry>
	implements IndexEntry, ModelWrapper<IndexEntry> {

	public IndexEntryWrapper(IndexEntry indexEntry) {
		super(indexEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("indexEntryId", getIndexEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long indexEntryId = (Long)attributes.get("indexEntryId");

		if (indexEntryId != null) {
			setIndexEntryId(indexEntryId);
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

	@Override
	public IndexEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this index entry.
	 *
	 * @return the company ID of this index entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the index entry ID of this index entry.
	 *
	 * @return the index entry ID of this index entry
	 */
	@Override
	public long getIndexEntryId() {
		return model.getIndexEntryId();
	}

	/**
	 * Returns the name of this index entry.
	 *
	 * @return the name of this index entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this index entry.
	 *
	 * @return the primary key of this index entry
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
	 * Sets the company ID of this index entry.
	 *
	 * @param companyId the company ID of this index entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the index entry ID of this index entry.
	 *
	 * @param indexEntryId the index entry ID of this index entry
	 */
	@Override
	public void setIndexEntryId(long indexEntryId) {
		model.setIndexEntryId(indexEntryId);
	}

	/**
	 * Sets the name of this index entry.
	 *
	 * @param name the name of this index entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this index entry.
	 *
	 * @param primaryKey the primary key of this index entry
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
	protected IndexEntryWrapper wrap(IndexEntry indexEntry) {
		return new IndexEntryWrapper(indexEntry);
	}

}