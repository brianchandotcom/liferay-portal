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
public class CountryLocalizationSoap implements Serializable {

	public static CountryLocalizationSoap toSoapModel(
		CountryLocalization model) {

		CountryLocalizationSoap soapModel = new CountryLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCountryLocalizationId(model.getCountryLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCountryId(model.getCountryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static CountryLocalizationSoap[] toSoapModels(
		CountryLocalization[] models) {

		CountryLocalizationSoap[] soapModels =
			new CountryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CountryLocalizationSoap[][] toSoapModels(
		CountryLocalization[][] models) {

		CountryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CountryLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CountryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CountryLocalizationSoap[] toSoapModels(
		List<CountryLocalization> models) {

		List<CountryLocalizationSoap> soapModels =
			new ArrayList<CountryLocalizationSoap>(models.size());

		for (CountryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CountryLocalizationSoap[soapModels.size()]);
	}

	public CountryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _countryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setCountryLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCountryLocalizationId() {
		return _countryLocalizationId;
	}

	public void setCountryLocalizationId(long countryLocalizationId) {
		_countryLocalizationId = countryLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCountryId() {
		return _countryId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
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
	private long _countryLocalizationId;
	private long _companyId;
	private long _countryId;
	private String _languageId;
	private String _title;

}