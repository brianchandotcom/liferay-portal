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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.DefaultScopedEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DefaultScopedEntrySoap implements Serializable {

	public static DefaultScopedEntrySoap toSoapModel(DefaultScopedEntry model) {
		DefaultScopedEntrySoap soapModel = new DefaultScopedEntrySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setDefaultScopedEntryId(model.getDefaultScopedEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static DefaultScopedEntrySoap[] toSoapModels(
		DefaultScopedEntry[] models) {

		DefaultScopedEntrySoap[] soapModels =
			new DefaultScopedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DefaultScopedEntrySoap[][] toSoapModels(
		DefaultScopedEntry[][] models) {

		DefaultScopedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DefaultScopedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DefaultScopedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DefaultScopedEntrySoap[] toSoapModels(
		List<DefaultScopedEntry> models) {

		List<DefaultScopedEntrySoap> soapModels =
			new ArrayList<DefaultScopedEntrySoap>(models.size());

		for (DefaultScopedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DefaultScopedEntrySoap[soapModels.size()]);
	}

	public DefaultScopedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _DefaultScopedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setDefaultScopedEntryId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getDefaultScopedEntryId() {
		return _DefaultScopedEntryId;
	}

	public void setDefaultScopedEntryId(long DefaultScopedEntryId) {
		_DefaultScopedEntryId = DefaultScopedEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _externalReferenceCode;
	private long _DefaultScopedEntryId;
	private long _companyId;

}