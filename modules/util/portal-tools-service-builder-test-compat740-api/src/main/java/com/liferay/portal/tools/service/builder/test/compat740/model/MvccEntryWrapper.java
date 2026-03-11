/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MvccEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MvccEntry
 * @generated
 */
public class MvccEntryWrapper
	extends BaseModelWrapper<MvccEntry>
	implements ModelWrapper<MvccEntry>, MvccEntry {

	public MvccEntryWrapper(MvccEntry mvccEntry) {
		super(mvccEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("mvccEntryId", getMvccEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long mvccEntryId = (Long)attributes.get("mvccEntryId");

		if (mvccEntryId != null) {
			setMvccEntryId(mvccEntryId);
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
	public MvccEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this mvcc entry.
	 *
	 * @return the company ID of this mvcc entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc entry ID of this mvcc entry.
	 *
	 * @return the mvcc entry ID of this mvcc entry
	 */
	@Override
	public long getMvccEntryId() {
		return model.getMvccEntryId();
	}

	/**
	 * Returns the mvcc version of this mvcc entry.
	 *
	 * @return the mvcc version of this mvcc entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this mvcc entry.
	 *
	 * @return the name of this mvcc entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this mvcc entry.
	 *
	 * @return the primary key of this mvcc entry
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
	 * Sets the company ID of this mvcc entry.
	 *
	 * @param companyId the company ID of this mvcc entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc entry ID of this mvcc entry.
	 *
	 * @param mvccEntryId the mvcc entry ID of this mvcc entry
	 */
	@Override
	public void setMvccEntryId(long mvccEntryId) {
		model.setMvccEntryId(mvccEntryId);
	}

	/**
	 * Sets the mvcc version of this mvcc entry.
	 *
	 * @param mvccVersion the mvcc version of this mvcc entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this mvcc entry.
	 *
	 * @param name the name of this mvcc entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this mvcc entry.
	 *
	 * @param primaryKey the primary key of this mvcc entry
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
	protected MvccEntryWrapper wrap(MvccEntry mvccEntry) {
		return new MvccEntryWrapper(mvccEntry);
	}

}