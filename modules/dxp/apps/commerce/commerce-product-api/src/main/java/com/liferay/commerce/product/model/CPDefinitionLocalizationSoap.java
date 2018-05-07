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
public class CPDefinitionLocalizationSoap implements Serializable {
	public static CPDefinitionLocalizationSoap toSoapModel(
		CPDefinitionLocalization model) {
		CPDefinitionLocalizationSoap soapModel = new CPDefinitionLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCpDefinitionLocalizationId(model.getCpDefinitionLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setName(model.getName());
		soapModel.setShortDescription(model.getShortDescription());
		soapModel.setDescription(model.getDescription());
		soapModel.setMetaTitle(model.getMetaTitle());
		soapModel.setMetaDescription(model.getMetaDescription());
		soapModel.setMetaKeywords(model.getMetaKeywords());

		return soapModel;
	}

	public static CPDefinitionLocalizationSoap[] toSoapModels(
		CPDefinitionLocalization[] models) {
		CPDefinitionLocalizationSoap[] soapModels = new CPDefinitionLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionLocalizationSoap[][] toSoapModels(
		CPDefinitionLocalization[][] models) {
		CPDefinitionLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionLocalizationSoap[] toSoapModels(
		List<CPDefinitionLocalization> models) {
		List<CPDefinitionLocalizationSoap> soapModels = new ArrayList<CPDefinitionLocalizationSoap>(models.size());

		for (CPDefinitionLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionLocalizationSoap[soapModels.size()]);
	}

	public CPDefinitionLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _cpDefinitionLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setCpDefinitionLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCpDefinitionLocalizationId() {
		return _cpDefinitionLocalizationId;
	}

	public void setCpDefinitionLocalizationId(long cpDefinitionLocalizationId) {
		_cpDefinitionLocalizationId = cpDefinitionLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getShortDescription() {
		return _shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		_shortDescription = shortDescription;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getMetaTitle() {
		return _metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		_metaTitle = metaTitle;
	}

	public String getMetaDescription() {
		return _metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		_metaDescription = metaDescription;
	}

	public String getMetaKeywords() {
		return _metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		_metaKeywords = metaKeywords;
	}

	private long _mvccVersion;
	private long _cpDefinitionLocalizationId;
	private long _companyId;
	private long _CPDefinitionId;
	private String _languageId;
	private String _name;
	private String _shortDescription;
	private String _description;
	private String _metaTitle;
	private String _metaDescription;
	private String _metaKeywords;
}