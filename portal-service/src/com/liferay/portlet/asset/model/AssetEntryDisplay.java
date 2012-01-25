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

	@JSON
	public long[] getCategoryIds() {
		return _categoryIds;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public long getEntryId() {
		return _entryId;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public int getHeight() {
		return _height;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getPortletTitle() {
		return _portletTitle;
	}

	public double getPriority() {
		return _priority;
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public String getSummary() {
		return _summary;
	}

	public String getTagNames() {
		return _tagNames;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public int getViewCount() {
		return _viewCount;
	}

	public int getWidth() {
		return _width;
	}

	public void setCategoryIds(long[] categoryIds) {
		_categoryIds = categoryIds;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setPortletTitle(String portletTitle) {
		_portletTitle = portletTitle;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setSummary(String summary) {
		_summary = summary;
	}

	public void setTagNames(String tagNames) {
		_tagNames = tagNames;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setViewCount(int viewCount) {
		_viewCount = viewCount;
	}

	public void setWidth(int width) {
		_width = width;
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