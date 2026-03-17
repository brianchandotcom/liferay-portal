/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WhereClauseEntrySoap implements Serializable {

	public static WhereClauseEntrySoap toSoapModel(WhereClauseEntry model) {
		WhereClauseEntrySoap soapModel = new WhereClauseEntrySoap();

		soapModel.setWhereClauseEntryId(model.getWhereClauseEntryId());
		soapModel.setName(model.getName());
		soapModel.setNickname(model.getNickname());

		return soapModel;
	}

	public static WhereClauseEntrySoap[] toSoapModels(
		WhereClauseEntry[] models) {

		WhereClauseEntrySoap[] soapModels =
			new WhereClauseEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WhereClauseEntrySoap[][] toSoapModels(
		WhereClauseEntry[][] models) {

		WhereClauseEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new WhereClauseEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new WhereClauseEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WhereClauseEntrySoap[] toSoapModels(
		List<WhereClauseEntry> models) {

		List<WhereClauseEntrySoap> soapModels =
			new ArrayList<WhereClauseEntrySoap>(models.size());

		for (WhereClauseEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WhereClauseEntrySoap[soapModels.size()]);
	}

	public WhereClauseEntrySoap() {
	}

	public long getPrimaryKey() {
		return _whereClauseEntryId;
	}

	public void setPrimaryKey(long pk) {
		setWhereClauseEntryId(pk);
	}

	public long getWhereClauseEntryId() {
		return _whereClauseEntryId;
	}

	public void setWhereClauseEntryId(long whereClauseEntryId) {
		_whereClauseEntryId = whereClauseEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getNickname() {
		return _nickname;
	}

	public void setNickname(String nickname) {
		_nickname = nickname;
	}

	private long _whereClauseEntryId;
	private String _name;
	private String _nickname;

}
// SB-Hash:963516790