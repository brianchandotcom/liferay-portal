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

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;

import java.io.InputStream;

import java.text.Format;

import java.util.Date;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class BaseResourceImpl implements Resource {

	public BaseResourceImpl(String parentPath, long name, long displayName) {
		this(parentPath, String.valueOf(name), String.valueOf(displayName));
	}

	public BaseResourceImpl(String parentPath, long name, String displayName) {
		this(parentPath, String.valueOf(name), displayName);
	}

	public BaseResourceImpl(
		String parentPath, String name, String displayName) {

		this(parentPath, name, displayName, null, null);
	}

	public BaseResourceImpl(
		String parentPath, String name, String displayName, Date createDate,
		Date modifiedDate) {

		this(parentPath, name, displayName, createDate, modifiedDate, 0);
	}

	public BaseResourceImpl(
		String parentPath, String name, String displayName, Date createDate,
		Date modifiedDate, long size) {

		_href = parentPath;

		if (Validator.isNotNull(name)) {
			_href += StringPool.SLASH + name;
		}

		_href = HttpUtil.encodePath(_href);

		_displayName = displayName;

		if (createDate == null) {
			_createDate = new Date();
		}
		else {
			_createDate = createDate;
		}

		if (modifiedDate == null) {
			_modifiedDate = new Date();
		}
		else {
			_modifiedDate = _createDate;
		}

		_size = size;
	}

	public String getClassName() {
		return _className;
	}

	@SuppressWarnings("unused")
	public InputStream getContentAsStream() throws WebDAVException {
		return null;
	}

	public String getContentType() {
		return ContentTypes.HTTPD_UNIX_DIRECTORY;
	}

	public String getCreateDate() {
		return _createDateFormatter.format(_createDate);
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getHREF() {
		return _href;
	}

	public Lock getLock() {
		return null;
	}

	public Object getModel() {
		return _model;
	}

	public String getModifiedDate() {
		return _modifiedDateFormatter.format(_modifiedDate);
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public long getSize() {
		return _size;
	}

	public boolean isCollection() {
		return true;
	}

	public boolean isLocked() {
		return false;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setModel(Object model) {
		_model = model;
	}

	public void setPrimaryKey(long primaryKey) {
		_primaryKey = primaryKey;
	}

	private static Format _createDateFormatter =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

	private static Format _modifiedDateFormatter =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	private String _className;
	private Date _createDate;
	private String _displayName;
	private String _href;
	private Object _model;
	private Date _modifiedDate;
	private long _primaryKey = -1;
	private long _size;

}