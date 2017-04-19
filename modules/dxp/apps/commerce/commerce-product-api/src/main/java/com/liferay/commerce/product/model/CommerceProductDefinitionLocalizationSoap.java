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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Marco Leo
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalizationSoap implements Serializable {
	public static CommerceProductDefinitionLocalizationSoap toSoapModel(
		CommerceProductDefinitionLocalization model) {
		CommerceProductDefinitionLocalizationSoap soapModel = new CommerceProductDefinitionLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCommerceProductDefinitionLocalizationId(model.getCommerceProductDefinitionLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCommerceProductDefinitionPK(model.getCommerceProductDefinitionPK());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTitle(model.getTitle());
		soapModel.setUrlTitle(model.getUrlTitle());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static CommerceProductDefinitionLocalizationSoap[] toSoapModels(
		CommerceProductDefinitionLocalization[] models) {
		CommerceProductDefinitionLocalizationSoap[] soapModels = new CommerceProductDefinitionLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionLocalizationSoap[][] toSoapModels(
		CommerceProductDefinitionLocalization[][] models) {
		CommerceProductDefinitionLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceProductDefinitionLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceProductDefinitionLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceProductDefinitionLocalizationSoap[] toSoapModels(
		List<CommerceProductDefinitionLocalization> models) {
		List<CommerceProductDefinitionLocalizationSoap> soapModels = new ArrayList<CommerceProductDefinitionLocalizationSoap>(models.size());

		for (CommerceProductDefinitionLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceProductDefinitionLocalizationSoap[soapModels.size()]);
	}

	public CommerceProductDefinitionLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _commerceProductDefinitionLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceProductDefinitionLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCommerceProductDefinitionLocalizationId() {
		return _commerceProductDefinitionLocalizationId;
	}

	public void setCommerceProductDefinitionLocalizationId(
		long commerceProductDefinitionLocalizationId) {
		_commerceProductDefinitionLocalizationId = commerceProductDefinitionLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCommerceProductDefinitionPK() {
		return _commerceProductDefinitionPK;
	}

	public void setCommerceProductDefinitionPK(long commerceProductDefinitionPK) {
		_commerceProductDefinitionPK = commerceProductDefinitionPK;
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

	public String getUrlTitle() {
		return _urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		_urlTitle = urlTitle;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private long _mvccVersion;
	private long _commerceProductDefinitionLocalizationId;
	private long _companyId;
	private long _commerceProductDefinitionPK;
	private String _languageId;
	private String _title;
	private String _urlTitle;
	private String _description;
}