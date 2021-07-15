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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.search.tuning.rankings.service.http.RankingServiceSoap}.
 *
 * @author Bryan Engler
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RankingSoap implements Serializable {

	public static RankingSoap toSoapModel(Ranking model) {
		RankingSoap soapModel = new RankingSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRankingId(model.getRankingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setJson(model.getJson());

		return soapModel;
	}

	public static RankingSoap[] toSoapModels(Ranking[] models) {
		RankingSoap[] soapModels = new RankingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RankingSoap[][] toSoapModels(Ranking[][] models) {
		RankingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RankingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RankingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RankingSoap[] toSoapModels(List<Ranking> models) {
		List<RankingSoap> soapModels = new ArrayList<RankingSoap>(
			models.size());

		for (Ranking model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RankingSoap[soapModels.size()]);
	}

	public RankingSoap() {
	}

	public long getPrimaryKey() {
		return _rankingId;
	}

	public void setPrimaryKey(long pk) {
		setRankingId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRankingId() {
		return _rankingId;
	}

	public void setRankingId(long rankingId) {
		_rankingId = rankingId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
	}

	private long _mvccVersion;
	private long _rankingId;
	private long _companyId;
	private String _json;

}