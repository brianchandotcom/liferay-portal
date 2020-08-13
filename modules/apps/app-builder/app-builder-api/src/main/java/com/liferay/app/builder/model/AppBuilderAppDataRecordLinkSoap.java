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

package com.liferay.app.builder.model;

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
public class AppBuilderAppDataRecordLinkSoap implements Serializable {

	public static AppBuilderAppDataRecordLinkSoap toSoapModel(
		AppBuilderAppDataRecordLink model) {

		AppBuilderAppDataRecordLinkSoap soapModel =
			new AppBuilderAppDataRecordLinkSoap();

		soapModel.setAppBuilderAppDataRecordLinkId(
			model.getAppBuilderAppDataRecordLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAppBuilderAppId(model.getAppBuilderAppId());
		soapModel.setAppBuilderAppVersionId(model.getAppBuilderAppVersionId());
		soapModel.setDdlRecordId(model.getDdlRecordId());

		return soapModel;
	}

	public static AppBuilderAppDataRecordLinkSoap[] toSoapModels(
		AppBuilderAppDataRecordLink[] models) {

		AppBuilderAppDataRecordLinkSoap[] soapModels =
			new AppBuilderAppDataRecordLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppDataRecordLinkSoap[][] toSoapModels(
		AppBuilderAppDataRecordLink[][] models) {

		AppBuilderAppDataRecordLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AppBuilderAppDataRecordLinkSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new AppBuilderAppDataRecordLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppDataRecordLinkSoap[] toSoapModels(
		List<AppBuilderAppDataRecordLink> models) {

		List<AppBuilderAppDataRecordLinkSoap> soapModels =
			new ArrayList<AppBuilderAppDataRecordLinkSoap>(models.size());

		for (AppBuilderAppDataRecordLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AppBuilderAppDataRecordLinkSoap[soapModels.size()]);
	}

	public AppBuilderAppDataRecordLinkSoap() {
	}

	public long getPrimaryKey() {
		return _appBuilderAppDataRecordLinkId;
	}

	public void setPrimaryKey(long pk) {
		setAppBuilderAppDataRecordLinkId(pk);
	}

	public long getAppBuilderAppDataRecordLinkId() {
		return _appBuilderAppDataRecordLinkId;
	}

	public void setAppBuilderAppDataRecordLinkId(
		long appBuilderAppDataRecordLinkId) {

		_appBuilderAppDataRecordLinkId = appBuilderAppDataRecordLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	public void setAppBuilderAppId(long appBuilderAppId) {
		_appBuilderAppId = appBuilderAppId;
	}

	public long getAppBuilderAppVersionId() {
		return _appBuilderAppVersionId;
	}

	public void setAppBuilderAppVersionId(long appBuilderAppVersionId) {
		_appBuilderAppVersionId = appBuilderAppVersionId;
	}

	public long getDdlRecordId() {
		return _ddlRecordId;
	}

	public void setDdlRecordId(long ddlRecordId) {
		_ddlRecordId = ddlRecordId;
	}

	private long _appBuilderAppDataRecordLinkId;
	private long _groupId;
	private long _companyId;
	private long _appBuilderAppId;
	private long _appBuilderAppVersionId;
	private long _ddlRecordId;

}