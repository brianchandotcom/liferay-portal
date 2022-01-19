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

package com.liferay.commerce.term.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CTermEntryLocalizationSoap implements Serializable {

	public static CTermEntryLocalizationSoap toSoapModel(
		CTermEntryLocalization model) {

		CTermEntryLocalizationSoap soapModel = new CTermEntryLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCTermEntryLocalizationId(
			model.getCTermEntryLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCommerceTermEntryId(model.getCommerceTermEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setDescription(model.getDescription());
		soapModel.setLabel(model.getLabel());

		return soapModel;
	}

	public static CTermEntryLocalizationSoap[] toSoapModels(
		CTermEntryLocalization[] models) {

		CTermEntryLocalizationSoap[] soapModels =
			new CTermEntryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTermEntryLocalizationSoap[][] toSoapModels(
		CTermEntryLocalization[][] models) {

		CTermEntryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CTermEntryLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTermEntryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTermEntryLocalizationSoap[] toSoapModels(
		List<CTermEntryLocalization> models) {

		List<CTermEntryLocalizationSoap> soapModels =
			new ArrayList<CTermEntryLocalizationSoap>(models.size());

		for (CTermEntryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CTermEntryLocalizationSoap[soapModels.size()]);
	}

	public CTermEntryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _cTermEntryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setCTermEntryLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCTermEntryLocalizationId() {
		return _cTermEntryLocalizationId;
	}

	public void setCTermEntryLocalizationId(long cTermEntryLocalizationId) {
		_cTermEntryLocalizationId = cTermEntryLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCommerceTermEntryId() {
		return _commerceTermEntryId;
	}

	public void setCommerceTermEntryId(long commerceTermEntryId) {
		_commerceTermEntryId = commerceTermEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	private long _mvccVersion;
	private long _cTermEntryLocalizationId;
	private long _companyId;
	private long _commerceTermEntryId;
	private String _languageId;
	private String _description;
	private String _label;

}