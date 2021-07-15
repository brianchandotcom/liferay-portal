/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.rankings.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link STRankingsEntry}.
 * </p>
 *
 * @author Bryan Engler
 * @see STRankingsEntry
 * @generated
 */
public class STRankingsEntryWrapper
	extends BaseModelWrapper<STRankingsEntry>
	implements ModelWrapper<STRankingsEntry>, STRankingsEntry {

	public STRankingsEntryWrapper(STRankingsEntry stRankingsEntry) {
		super(stRankingsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("STRankingsEntryId", getSTRankingsEntryId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long STRankingsEntryId = (Long)attributes.get("STRankingsEntryId");

		if (STRankingsEntryId != null) {
			setSTRankingsEntryId(STRankingsEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	/**
	 * Returns the company ID of this st rankings entry.
	 *
	 * @return the company ID of this st rankings entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this st rankings entry.
	 *
	 * @return the mvcc version of this st rankings entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this st rankings entry.
	 *
	 * @return the primary key of this st rankings entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the st rankings entry ID of this st rankings entry.
	 *
	 * @return the st rankings entry ID of this st rankings entry
	 */
	@Override
	public long getSTRankingsEntryId() {
		return model.getSTRankingsEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this st rankings entry.
	 *
	 * @param companyId the company ID of this st rankings entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this st rankings entry.
	 *
	 * @param mvccVersion the mvcc version of this st rankings entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this st rankings entry.
	 *
	 * @param primaryKey the primary key of this st rankings entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the st rankings entry ID of this st rankings entry.
	 *
	 * @param STRankingsEntryId the st rankings entry ID of this st rankings entry
	 */
	@Override
	public void setSTRankingsEntryId(long STRankingsEntryId) {
		model.setSTRankingsEntryId(STRankingsEntryId);
	}

	@Override
	protected STRankingsEntryWrapper wrap(STRankingsEntry stRankingsEntry) {
		return new STRankingsEntryWrapper(stRankingsEntry);
	}

}