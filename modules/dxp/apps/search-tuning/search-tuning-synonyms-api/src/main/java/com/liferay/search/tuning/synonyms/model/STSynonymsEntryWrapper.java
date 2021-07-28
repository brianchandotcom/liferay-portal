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

package com.liferay.search.tuning.synonyms.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link STSynonymsEntry}.
 * </p>
 *
 * @author Bryan Engler
 * @see STSynonymsEntry
 * @generated
 */
public class STSynonymsEntryWrapper
	extends BaseModelWrapper<STSynonymsEntry>
	implements ModelWrapper<STSynonymsEntry>, STSynonymsEntry {

	public STSynonymsEntryWrapper(STSynonymsEntry stSynonymsEntry) {
		super(stSynonymsEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("STSynonymsEntryId", getSTSynonymsEntryId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long STSynonymsEntryId = (Long)attributes.get("STSynonymsEntryId");

		if (STSynonymsEntryId != null) {
			setSTSynonymsEntryId(STSynonymsEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	/**
	 * Returns the company ID of this st synonyms entry.
	 *
	 * @return the company ID of this st synonyms entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this st synonyms entry.
	 *
	 * @return the mvcc version of this st synonyms entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this st synonyms entry.
	 *
	 * @return the primary key of this st synonyms entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the st synonyms entry ID of this st synonyms entry.
	 *
	 * @return the st synonyms entry ID of this st synonyms entry
	 */
	@Override
	public long getSTSynonymsEntryId() {
		return model.getSTSynonymsEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this st synonyms entry.
	 *
	 * @param companyId the company ID of this st synonyms entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this st synonyms entry.
	 *
	 * @param mvccVersion the mvcc version of this st synonyms entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this st synonyms entry.
	 *
	 * @param primaryKey the primary key of this st synonyms entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the st synonyms entry ID of this st synonyms entry.
	 *
	 * @param STSynonymsEntryId the st synonyms entry ID of this st synonyms entry
	 */
	@Override
	public void setSTSynonymsEntryId(long STSynonymsEntryId) {
		model.setSTSynonymsEntryId(STSynonymsEntryId);
	}

	@Override
	protected STSynonymsEntryWrapper wrap(STSynonymsEntry stSynonymsEntry) {
		return new STSynonymsEntryWrapper(stSynonymsEntry);
	}

}