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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.search.tuning.rankings.service.http.STRankingsEntryServiceSoap}.
 *
 * @author Bryan Engler
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class STRankingsEntrySoap implements Serializable {

	public static STRankingsEntrySoap toSoapModel(STRankingsEntry model) {
		STRankingsEntrySoap soapModel = new STRankingsEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setSTRankingsEntryId(model.getSTRankingsEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static STRankingsEntrySoap[] toSoapModels(STRankingsEntry[] models) {
		STRankingsEntrySoap[] soapModels =
			new STRankingsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static STRankingsEntrySoap[][] toSoapModels(
		STRankingsEntry[][] models) {

		STRankingsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new STRankingsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new STRankingsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static STRankingsEntrySoap[] toSoapModels(
		List<STRankingsEntry> models) {

		List<STRankingsEntrySoap> soapModels =
			new ArrayList<STRankingsEntrySoap>(models.size());

		for (STRankingsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new STRankingsEntrySoap[soapModels.size()]);
	}

	public STRankingsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _STRankingsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSTRankingsEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getSTRankingsEntryId() {
		return _STRankingsEntryId;
	}

	public void setSTRankingsEntryId(long STRankingsEntryId) {
		_STRankingsEntryId = STRankingsEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private long _mvccVersion;
	private long _STRankingsEntryId;
	private long _companyId;

}