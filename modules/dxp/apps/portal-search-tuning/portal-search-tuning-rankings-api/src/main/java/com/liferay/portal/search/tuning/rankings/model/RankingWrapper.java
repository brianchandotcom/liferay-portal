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

package com.liferay.portal.search.tuning.rankings.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Ranking}.
 * </p>
 *
 * @author Bryan Engler
 * @see Ranking
 * @generated
 */
public class RankingWrapper
	extends BaseModelWrapper<Ranking>
	implements ModelWrapper<Ranking>, Ranking {

	public RankingWrapper(Ranking ranking) {
		super(ranking);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("rankingId", getRankingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long rankingId = (Long)attributes.get("rankingId");

		if (rankingId != null) {
			setRankingId(rankingId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	@Override
	public java.util.List<String> getAliases() {
		return model.getAliases();
	}

	/**
	 * Returns the company ID of this ranking.
	 *
	 * @return the company ID of this ranking
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public java.util.List<String> getHiddenDocumentIds() {
		return model.getHiddenDocumentIds();
	}

	@Override
	public boolean getInactive() {
		return model.getInactive();
	}

	@Override
	public String getIndexName() {
		return model.getIndexName();
	}

	/**
	 * Returns the json of this ranking.
	 *
	 * @return the json of this ranking
	 */
	@Override
	public String getJson() {
		return model.getJson();
	}

	/**
	 * Returns the mvcc version of this ranking.
	 *
	 * @return the mvcc version of this ranking
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public Map<Integer, String> getPinnedDocumentIds() {
		return model.getPinnedDocumentIds();
	}

	/**
	 * Returns the primary key of this ranking.
	 *
	 * @return the primary key of this ranking
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getQueryString() {
		return model.getQueryString();
	}

	@Override
	public String getRankingDocumentId() {
		return model.getRankingDocumentId();
	}

	/**
	 * Returns the ranking ID of this ranking.
	 *
	 * @return the ranking ID of this ranking
	 */
	@Override
	public long getRankingId() {
		return model.getRankingId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ranking.
	 *
	 * @param companyId the company ID of this ranking
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the json of this ranking.
	 *
	 * @param json the json of this ranking
	 */
	@Override
	public void setJson(String json) {
		model.setJson(json);
	}

	/**
	 * Sets the mvcc version of this ranking.
	 *
	 * @param mvccVersion the mvcc version of this ranking
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ranking.
	 *
	 * @param primaryKey the primary key of this ranking
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the ranking ID of this ranking.
	 *
	 * @param rankingId the ranking ID of this ranking
	 */
	@Override
	public void setRankingId(long rankingId) {
		model.setRankingId(rankingId);
	}

	@Override
	protected RankingWrapper wrap(Ranking ranking) {
		return new RankingWrapper(ranking);
	}

}