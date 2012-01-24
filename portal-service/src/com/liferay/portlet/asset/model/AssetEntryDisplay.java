/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.json.JSON;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetEntryDisplay implements Serializable {

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public String getPortletTitle() {
		return _portletTitle;
	}

	public void setPortletTitle(String portletTitle) {
		_portletTitle = portletTitle;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getSummary() {
		return _summary;
	}

	public void setSummary(String summary) {
		_summary = summary;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getViewCount() {
		return _viewCount;
	}

	public void setViewCount(int viewCount) {
		_viewCount = viewCount;
	}

	@JSON
	public long[] getCategoryIds() {
		return _categoryIds;
	}

	public void setCategoryIds(long[] categoryIds) {
		_categoryIds = categoryIds;
	}

	public String getTagNames() {
		return _tagNames;
	}

	public void setTagNames(String tagNames) {
		_tagNames = tagNames;
	}

	private long[] _categoryIds;
	private String _className;
	private long _classNameId;
	private long _classPK;
	private long _companyId;
	private Date _createDate;
	private String _description;
	private Date _endDate;
	private long _entryId;
	private Date _expirationDate;
	private int _height;
	private String _mimeType;
	private Date _modifiedDate;
	private String _portletId;
	private String _portletTitle;
	private double _priority;
	private Date _publishDate;
	private Date _startDate;
	private String _summary;
	private String _tagNames;
	private String _title;
	private String _url;
	private long _userId;
	private String _userName;
	private int _viewCount;
	private int _width;

}