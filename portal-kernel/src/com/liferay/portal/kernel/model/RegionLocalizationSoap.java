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

package com.liferay.portal.kernel.model;

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
public class RegionLocalizationSoap implements Serializable {

	public static RegionLocalizationSoap toSoapModel(RegionLocalization model) {
		RegionLocalizationSoap soapModel = new RegionLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRegionLocalizationId(model.getRegionLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setRegionId(model.getRegionId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static RegionLocalizationSoap[] toSoapModels(
		RegionLocalization[] models) {

		RegionLocalizationSoap[] soapModels =
			new RegionLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RegionLocalizationSoap[][] toSoapModels(
		RegionLocalization[][] models) {

		RegionLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RegionLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RegionLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RegionLocalizationSoap[] toSoapModels(
		List<RegionLocalization> models) {

		List<RegionLocalizationSoap> soapModels =
			new ArrayList<RegionLocalizationSoap>(models.size());

		for (RegionLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new RegionLocalizationSoap[soapModels.size()]);
	}

	public RegionLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _regionLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setRegionLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRegionLocalizationId() {
		return _regionLocalizationId;
	}

	public void setRegionLocalizationId(long regionLocalizationId) {
		_regionLocalizationId = regionLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getRegionId() {
		return _regionId;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private long _mvccVersion;
	private long _regionLocalizationId;
	private long _companyId;
	private long _regionId;
	private String _languageId;
	private String _title;

}