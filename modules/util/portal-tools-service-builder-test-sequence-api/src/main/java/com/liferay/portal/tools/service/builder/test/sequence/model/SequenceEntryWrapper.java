/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.sequence.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SequenceEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntry
 * @generated
 */
public class SequenceEntryWrapper
	extends BaseModelWrapper<SequenceEntry>
	implements ModelWrapper<SequenceEntry>, SequenceEntry {

	public SequenceEntryWrapper(SequenceEntry sequenceEntry) {
		super(sequenceEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("sequenceEntryId", getSequenceEntryId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long sequenceEntryId = (Long)attributes.get("sequenceEntryId");

		if (sequenceEntryId != null) {
			setSequenceEntryId(sequenceEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public SequenceEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this sequence entry.
	 *
	 * @return the company ID of this sequence entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this sequence entry.
	 *
	 * @return the mvcc version of this sequence entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this sequence entry.
	 *
	 * @return the primary key of this sequence entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sequence entry ID of this sequence entry.
	 *
	 * @return the sequence entry ID of this sequence entry
	 */
	@Override
	public long getSequenceEntryId() {
		return model.getSequenceEntryId();
	}

	/**
	 * Returns the uuid of this sequence entry.
	 *
	 * @return the uuid of this sequence entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this sequence entry.
	 *
	 * @param companyId the company ID of this sequence entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this sequence entry.
	 *
	 * @param mvccVersion the mvcc version of this sequence entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this sequence entry.
	 *
	 * @param primaryKey the primary key of this sequence entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sequence entry ID of this sequence entry.
	 *
	 * @param sequenceEntryId the sequence entry ID of this sequence entry
	 */
	@Override
	public void setSequenceEntryId(long sequenceEntryId) {
		model.setSequenceEntryId(sequenceEntryId);
	}

	/**
	 * Sets the uuid of this sequence entry.
	 *
	 * @param uuid the uuid of this sequence entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected SequenceEntryWrapper wrap(SequenceEntry sequenceEntry) {
		return new SequenceEntryWrapper(sequenceEntry);
	}

}