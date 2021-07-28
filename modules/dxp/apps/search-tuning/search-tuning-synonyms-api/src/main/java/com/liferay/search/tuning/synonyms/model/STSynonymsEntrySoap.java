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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.search.tuning.synonyms.service.http.STSynonymsEntryServiceSoap}.
 *
 * @author Bryan Engler
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class STSynonymsEntrySoap implements Serializable {

	public static STSynonymsEntrySoap toSoapModel(STSynonymsEntry model) {
		STSynonymsEntrySoap soapModel = new STSynonymsEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setSTSynonymsEntryId(model.getSTSynonymsEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static STSynonymsEntrySoap[] toSoapModels(STSynonymsEntry[] models) {
		STSynonymsEntrySoap[] soapModels =
			new STSynonymsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static STSynonymsEntrySoap[][] toSoapModels(
		STSynonymsEntry[][] models) {

		STSynonymsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new STSynonymsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new STSynonymsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static STSynonymsEntrySoap[] toSoapModels(
		List<STSynonymsEntry> models) {

		List<STSynonymsEntrySoap> soapModels =
			new ArrayList<STSynonymsEntrySoap>(models.size());

		for (STSynonymsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new STSynonymsEntrySoap[soapModels.size()]);
	}

	public STSynonymsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _STSynonymsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSTSynonymsEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getSTSynonymsEntryId() {
		return _STSynonymsEntryId;
	}

	public void setSTSynonymsEntryId(long STSynonymsEntryId) {
		_STSynonymsEntryId = STSynonymsEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private long _mvccVersion;
	private long _STSynonymsEntryId;
	private long _companyId;

}