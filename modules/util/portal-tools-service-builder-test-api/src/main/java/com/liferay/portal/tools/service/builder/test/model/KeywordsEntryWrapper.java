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
 * This class is a wrapper for {@link KeywordsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntry
 * @generated
 */
public class KeywordsEntryWrapper
	extends BaseModelWrapper<KeywordsEntry>
	implements KeywordsEntry, ModelWrapper<KeywordsEntry> {

	public KeywordsEntryWrapper(KeywordsEntry keywordsEntry) {
		super(keywordsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("keywordsEntryId", getKeywordsEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long keywordsEntryId = (Long)attributes.get("keywordsEntryId");

		if (keywordsEntryId != null) {
			setKeywordsEntryId(keywordsEntryId);
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
	public KeywordsEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this keywords entry.
	 *
	 * @return the company ID of this keywords entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the keywords entry ID of this keywords entry.
	 *
	 * @return the keywords entry ID of this keywords entry
	 */
	@Override
	public long getKeywordsEntryId() {
		return model.getKeywordsEntryId();
	}

	/**
	 * Returns the name of this keywords entry.
	 *
	 * @return the name of this keywords entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this keywords entry.
	 *
	 * @return the primary key of this keywords entry
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
	 * Sets the company ID of this keywords entry.
	 *
	 * @param companyId the company ID of this keywords entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the keywords entry ID of this keywords entry.
	 *
	 * @param keywordsEntryId the keywords entry ID of this keywords entry
	 */
	@Override
	public void setKeywordsEntryId(long keywordsEntryId) {
		model.setKeywordsEntryId(keywordsEntryId);
	}

	/**
	 * Sets the name of this keywords entry.
	 *
	 * @param name the name of this keywords entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this keywords entry.
	 *
	 * @param primaryKey the primary key of this keywords entry
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
	protected KeywordsEntryWrapper wrap(KeywordsEntry keywordsEntry) {
		return new KeywordsEntryWrapper(keywordsEntry);
	}

}