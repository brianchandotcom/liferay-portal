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

package com.liferay.portal.workflow.rest.internal.model;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowAssetModel {

	public WorkflowAssetModel() {
		_className = null;
		_classPK = 0;
		_createTime = 0;
		_modifiedTime = 0;
		_summary = null;
		_title = null;
		_url = null;
		_type = null;
		_workflowUserModel = null;
	}

	public WorkflowAssetModel(
			AssetEntry assetEntry, String type,
			WorkflowUserModel workflowUserModel, Locale locale)
		throws PortalException {

		_type = type;
		_workflowUserModel = workflowUserModel;

		_className = assetEntry.getClassName();
		_classPK = assetEntry.getClassPK();

		Date createDate = assetEntry.getCreateDate();

		_createTime = createDate.getTime();

		Date modifiedDate = assetEntry.getModifiedDate();

		_modifiedTime = modifiedDate.getTime();

		_summary = assetEntry.getSummary(locale);
		_title = assetEntry.getTitle(locale);
		_url = assetEntry.getUrl();
	}

	@XmlElement
	public String getClassName() {
		return _className;
	}

	@XmlElement
	public long getClassPK() {
		return _classPK;
	}

	@XmlElement
	public long getCreateTime() {
		return _createTime;
	}

	@XmlElement
	public long getModifiedTime() {
		return _modifiedTime;
	}

	@XmlElement
	public String getSummary() {
		return _summary;
	}

	@XmlElement
	public String getTitle() {
		return _title;
	}

	@XmlElement
	public String getType() {
		return _type;
	}

	@XmlElement
	public String getUrl() {
		return _url;
	}

	@XmlElement(name = "author")
	public WorkflowUserModel getWorkflowUserModel() {
		return _workflowUserModel;
	}

	private final String _className;
	private final long _classPK;
	private final long _createTime;
	private final long _modifiedTime;
	private final String _summary;
	private final String _title;
	private final String _type;
	private final String _url;
	private final WorkflowUserModel _workflowUserModel;

}