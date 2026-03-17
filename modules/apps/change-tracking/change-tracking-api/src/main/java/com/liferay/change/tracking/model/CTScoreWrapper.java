/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTScore}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTScore
 * @generated
 */
public class CTScoreWrapper
	extends BaseModelWrapper<CTScore>
	implements CTScore, ModelWrapper<CTScore> {

	public CTScoreWrapper(CTScore ctScore) {
		super(ctScore);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctScoreId", getCtScoreId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("score", getScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctScoreId = (Long)attributes.get("ctScoreId");

		if (ctScoreId != null) {
			setCtScoreId(ctScoreId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Integer score = (Integer)attributes.get("score");

		if (score != null) {
			setScore(score);
		}
	}

	@Override
	public CTScore cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct score.
	 *
	 * @return the company ID of this ct score
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ct score.
	 *
	 * @return the ct collection ID of this ct score
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct score ID of this ct score.
	 *
	 * @return the ct score ID of this ct score
	 */
	@Override
	public long getCtScoreId() {
		return model.getCtScoreId();
	}

	/**
	 * Returns the mvcc version of this ct score.
	 *
	 * @return the mvcc version of this ct score
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct score.
	 *
	 * @return the primary key of this ct score
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the score of this ct score.
	 *
	 * @return the score of this ct score
	 */
	@Override
	public int getScore() {
		return model.getScore();
	}

	@Override
	public String getSizeClassification() {
		return model.getSizeClassification();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct score.
	 *
	 * @param companyId the company ID of this ct score
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ct score.
	 *
	 * @param ctCollectionId the ct collection ID of this ct score
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct score ID of this ct score.
	 *
	 * @param ctScoreId the ct score ID of this ct score
	 */
	@Override
	public void setCtScoreId(long ctScoreId) {
		model.setCtScoreId(ctScoreId);
	}

	/**
	 * Sets the mvcc version of this ct score.
	 *
	 * @param mvccVersion the mvcc version of this ct score
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct score.
	 *
	 * @param primaryKey the primary key of this ct score
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the score of this ct score.
	 *
	 * @param score the score of this ct score
	 */
	@Override
	public void setScore(int score) {
		model.setScore(score);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTScoreWrapper wrap(CTScore ctScore) {
		return new CTScoreWrapper(ctScore);
	}

}
// SB-Hash:-1143717998