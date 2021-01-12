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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.GroupScopedEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class GroupScopedEntrySoap implements Serializable {

	public static GroupScopedEntrySoap toSoapModel(GroupScopedEntry model) {
		GroupScopedEntrySoap soapModel = new GroupScopedEntrySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setGroupScopedEntryId(model.getGroupScopedEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static GroupScopedEntrySoap[] toSoapModels(
		GroupScopedEntry[] models) {

		GroupScopedEntrySoap[] soapModels =
			new GroupScopedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static GroupScopedEntrySoap[][] toSoapModels(
		GroupScopedEntry[][] models) {

		GroupScopedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new GroupScopedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new GroupScopedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static GroupScopedEntrySoap[] toSoapModels(
		List<GroupScopedEntry> models) {

		List<GroupScopedEntrySoap> soapModels =
			new ArrayList<GroupScopedEntrySoap>(models.size());

		for (GroupScopedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new GroupScopedEntrySoap[soapModels.size()]);
	}

	public GroupScopedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _GroupScopedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setGroupScopedEntryId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getGroupScopedEntryId() {
		return _GroupScopedEntryId;
	}

	public void setGroupScopedEntryId(long GroupScopedEntryId) {
		_GroupScopedEntryId = GroupScopedEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private String _externalReferenceCode;
	private long _GroupScopedEntryId;
	private long _companyId;
	private long _groupId;

}