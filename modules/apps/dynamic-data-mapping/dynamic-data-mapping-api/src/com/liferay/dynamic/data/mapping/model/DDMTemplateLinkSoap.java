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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DDMTemplateLinkSoap implements Serializable {
	public static DDMTemplateLinkSoap toSoapModel(DDMTemplateLink model) {
		DDMTemplateLinkSoap soapModel = new DDMTemplateLinkSoap();

		soapModel.setTemplateLinkId(model.getTemplateLinkId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static DDMTemplateLinkSoap[] toSoapModels(DDMTemplateLink[] models) {
		DDMTemplateLinkSoap[] soapModels = new DDMTemplateLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateLinkSoap[][] toSoapModels(
		DDMTemplateLink[][] models) {
		DDMTemplateLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMTemplateLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMTemplateLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateLinkSoap[] toSoapModels(
		List<DDMTemplateLink> models) {
		List<DDMTemplateLinkSoap> soapModels = new ArrayList<DDMTemplateLinkSoap>(models.size());

		for (DDMTemplateLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMTemplateLinkSoap[soapModels.size()]);
	}

	public DDMTemplateLinkSoap() {
	}

	public long getPrimaryKey() {
		return _templateLinkId;
	}

	public void setPrimaryKey(long pk) {
		setTemplateLinkId(pk);
	}

	public long getTemplateLinkId() {
		return _templateLinkId;
	}

	public void setTemplateLinkId(long templateLinkId) {
		_templateLinkId = templateLinkId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(long templateId) {
		_templateId = templateId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private long _templateLinkId;
	private long _classNameId;
	private long _classPK;
	private long _templateId;
	private long _companyId;
}