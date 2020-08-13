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

package com.liferay.view.count.model;

import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Preston Crary
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ViewCountEntrySoap implements Serializable {

	public static ViewCountEntrySoap toSoapModel(ViewCountEntry model) {
		ViewCountEntrySoap soapModel = new ViewCountEntrySoap();

		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setViewCount(model.getViewCount());

		return soapModel;
	}

	public static ViewCountEntrySoap[] toSoapModels(ViewCountEntry[] models) {
		ViewCountEntrySoap[] soapModels = new ViewCountEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ViewCountEntrySoap[][] toSoapModels(
		ViewCountEntry[][] models) {

		ViewCountEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ViewCountEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ViewCountEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ViewCountEntrySoap[] toSoapModels(
		List<ViewCountEntry> models) {

		List<ViewCountEntrySoap> soapModels = new ArrayList<ViewCountEntrySoap>(
			models.size());

		for (ViewCountEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ViewCountEntrySoap[soapModels.size()]);
	}

	public ViewCountEntrySoap() {
	}

	public ViewCountEntryPK getPrimaryKey() {
		return new ViewCountEntryPK(_companyId, _classNameId, _classPK);
	}

	public void setPrimaryKey(ViewCountEntryPK pk) {
		setCompanyId(pk.companyId);
		setClassNameId(pk.classNameId);
		setClassPK(pk.classPK);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public long getViewCount() {
		return _viewCount;
	}

	public void setViewCount(long viewCount) {
		_viewCount = viewCount;
	}

	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _viewCount;

}