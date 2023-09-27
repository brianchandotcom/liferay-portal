/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.revert.schema.version.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RSVEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntry
 * @generated
 */
public class RSVEntryWrapper
	extends BaseModelWrapper<RSVEntry>
	implements ModelWrapper<RSVEntry>, RSVEntry {

	public RSVEntryWrapper(RSVEntry rsvEntry) {
		super(rsvEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("rsvEntryId", getRsvEntryId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long rsvEntryId = (Long)attributes.get("rsvEntryId");

		if (rsvEntryId != null) {
			setRsvEntryId(rsvEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public RSVEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this rsv entry.
	 *
	 * @return the company ID of this rsv entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this rsv entry.
	 *
	 * @return the mvcc version of this rsv entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this rsv entry.
	 *
	 * @return the primary key of this rsv entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rsv entry ID of this rsv entry.
	 *
	 * @return the rsv entry ID of this rsv entry
	 */
	@Override
	public long getRsvEntryId() {
		return model.getRsvEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this rsv entry.
	 *
	 * @param companyId the company ID of this rsv entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this rsv entry.
	 *
	 * @param mvccVersion the mvcc version of this rsv entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this rsv entry.
	 *
	 * @param primaryKey the primary key of this rsv entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rsv entry ID of this rsv entry.
	 *
	 * @param rsvEntryId the rsv entry ID of this rsv entry
	 */
	@Override
	public void setRsvEntryId(long rsvEntryId) {
		model.setRsvEntryId(rsvEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RSVEntryWrapper wrap(RSVEntry rsvEntry) {
		return new RSVEntryWrapper(rsvEntry);
	}

}