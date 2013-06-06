/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SCProductScreenshotSoap implements Serializable {
	public static SCProductScreenshotSoap toSoapModel(SCProductScreenshot model) {
		SCProductScreenshotSoap soapModel = new SCProductScreenshotSoap();

		soapModel.setProductScreenshotId(model.getProductScreenshotId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setProductEntryId(model.getProductEntryId());
		soapModel.setThumbnailId(model.getThumbnailId());
		soapModel.setFullImageId(model.getFullImageId());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static SCProductScreenshotSoap[] toSoapModels(
		SCProductScreenshot[] models) {
		SCProductScreenshotSoap[] soapModels = new SCProductScreenshotSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SCProductScreenshotSoap[][] toSoapModels(
		SCProductScreenshot[][] models) {
		SCProductScreenshotSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SCProductScreenshotSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SCProductScreenshotSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SCProductScreenshotSoap[] toSoapModels(
		List<SCProductScreenshot> models) {
		List<SCProductScreenshotSoap> soapModels = new ArrayList<SCProductScreenshotSoap>(models.size());

		for (SCProductScreenshot model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SCProductScreenshotSoap[soapModels.size()]);
	}

	public SCProductScreenshotSoap() {
	}

	public long getPrimaryKey() {
		return _productScreenshotId;
	}

	public void setPrimaryKey(long pk) {
		setProductScreenshotId(pk);
	}

	public long getProductScreenshotId() {
		return _productScreenshotId;
	}

	public void setProductScreenshotId(long productScreenshotId) {
		_productScreenshotId = productScreenshotId;
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

	public long getProductEntryId() {
		return _productEntryId;
	}

	public void setProductEntryId(long productEntryId) {
		_productEntryId = productEntryId;
	}

	public long getThumbnailId() {
		return _thumbnailId;
	}

	public void setThumbnailId(long thumbnailId) {
		_thumbnailId = thumbnailId;
	}

	public long getFullImageId() {
		return _fullImageId;
	}

	public void setFullImageId(long fullImageId) {
		_fullImageId = fullImageId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _productScreenshotId;
	private long _companyId;
	private long _groupId;
	private long _productEntryId;
	private long _thumbnailId;
	private long _fullImageId;
	private int _priority;
}