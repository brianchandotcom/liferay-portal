/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FinderWhereClauseEntrySoap implements Serializable {

	public static FinderWhereClauseEntrySoap toSoapModel(
		FinderWhereClauseEntry model) {

		FinderWhereClauseEntrySoap soapModel = new FinderWhereClauseEntrySoap();

		soapModel.setFinderWhereClauseEntryId(
			model.getFinderWhereClauseEntryId());
		soapModel.setName(model.getName());
		soapModel.setNickname(model.getNickname());

		return soapModel;
	}

	public static FinderWhereClauseEntrySoap[] toSoapModels(
		FinderWhereClauseEntry[] models) {

		FinderWhereClauseEntrySoap[] soapModels =
			new FinderWhereClauseEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FinderWhereClauseEntrySoap[][] toSoapModels(
		FinderWhereClauseEntry[][] models) {

		FinderWhereClauseEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FinderWhereClauseEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FinderWhereClauseEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FinderWhereClauseEntrySoap[] toSoapModels(
		List<FinderWhereClauseEntry> models) {

		List<FinderWhereClauseEntrySoap> soapModels =
			new ArrayList<FinderWhereClauseEntrySoap>(models.size());

		for (FinderWhereClauseEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new FinderWhereClauseEntrySoap[soapModels.size()]);
	}

	public FinderWhereClauseEntrySoap() {
	}

	public long getPrimaryKey() {
		return _finderWhereClauseEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFinderWhereClauseEntryId(pk);
	}

	public long getFinderWhereClauseEntryId() {
		return _finderWhereClauseEntryId;
	}

	public void setFinderWhereClauseEntryId(long finderWhereClauseEntryId) {
		_finderWhereClauseEntryId = finderWhereClauseEntryId;
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

	private long _finderWhereClauseEntryId;
	private String _name;
	private String _nickname;

}