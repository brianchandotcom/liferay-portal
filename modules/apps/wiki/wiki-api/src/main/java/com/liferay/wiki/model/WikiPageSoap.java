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

package com.liferay.wiki.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.wiki.service.http.WikiPageServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WikiPageSoap implements Serializable {

	public static WikiPageSoap toSoapModel(WikiPage model) {
		WikiPageSoap soapModel = new WikiPageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setPageId(model.getPageId());
		soapModel.setResourcePrimKey(model.getResourcePrimKey());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setNodeId(model.getNodeId());
		soapModel.setTitle(model.getTitle());
		soapModel.setVersion(model.getVersion());
		soapModel.setMinorEdit(model.isMinorEdit());
		soapModel.setContent(model.getContent());
		soapModel.setSummary(model.getSummary());
		soapModel.setFormat(model.getFormat());
		soapModel.setHead(model.isHead());
		soapModel.setParentTitle(model.getParentTitle());
		soapModel.setRedirectTitle(model.getRedirectTitle());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static WikiPageSoap[] toSoapModels(WikiPage[] models) {
		WikiPageSoap[] soapModels = new WikiPageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WikiPageSoap[][] toSoapModels(WikiPage[][] models) {
		WikiPageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WikiPageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WikiPageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WikiPageSoap[] toSoapModels(List<WikiPage> models) {
		List<WikiPageSoap> soapModels = new ArrayList<WikiPageSoap>(
			models.size());

		for (WikiPage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WikiPageSoap[soapModels.size()]);
	}

	public WikiPageSoap() {
	}

	public long getPrimaryKey() {
		return _pageId;
	}

	public void setPrimaryKey(long pk) {
		setPageId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getPageId() {
		return _pageId;
	}

	public void setPageId(long pageId) {
		_pageId = pageId;
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	public boolean getMinorEdit() {
		return _minorEdit;
	}

	public boolean isMinorEdit() {
		return _minorEdit;
	}

	public void setMinorEdit(boolean minorEdit) {
		_minorEdit = minorEdit;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public String getSummary() {
		return _summary;
	}

	public void setSummary(String summary) {
		_summary = summary;
	}

	public String getFormat() {
		return _format;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public boolean getHead() {
		return _head;
	}

	public boolean isHead() {
		return _head;
	}

	public void setHead(boolean head) {
		_head = head;
	}

	public String getParentTitle() {
		return _parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		_parentTitle = parentTitle;
	}

	public String getRedirectTitle() {
		return _redirectTitle;
	}

	public void setRedirectTitle(String redirectTitle) {
		_redirectTitle = redirectTitle;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private String _externalReferenceCode;
	private long _pageId;
	private long _resourcePrimKey;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _nodeId;
	private String _title;
	private double _version;
	private boolean _minorEdit;
	private String _content;
	private String _summary;
	private String _format;
	private boolean _head;
	private String _parentTitle;
	private String _redirectTitle;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}