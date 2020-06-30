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

package com.liferay.portal.webserver;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.text.Format;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class WebServerEntry {

	public WebServerEntry(String path, String name) {
		this(path, name, null, null, null, 0);
	}

	public WebServerEntry(
		String path, String name, Date createDate, Date modifiedDate,
		String description, long size) {

		_path = getPath(path, name);
		_name = name;
		_createDate = createDate;
		_modifiedDate = modifiedDate;

		if (modifiedDate != null) {
			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				"d MMM yyyy HH:mm z");

			_modifiedDateString = format.format(modifiedDate);
		}

		_description = GetterUtil.getString(description);
		_size = size;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getModifiedDateString() {
		return _modifiedDateString;
	}

	public String getName() {
		return _name;
	}

	public String getPath() {
		return _path;
	}

	public long getSize() {
		return _size;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setModifiedDateString(String modifiedDateString) {
		_modifiedDateString = modifiedDateString;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setSize(long size) {
		_size = size;
	}

	protected String getPath(String path, String name) {
		if (name.endsWith(StringPool.SLASH)) {
			name = HttpUtil.fixPath(name, false, true);

			return getPath(path, name) + StringPool.SLASH;
		}

		if (path.endsWith(StringPool.SLASH)) {
			path = path + URLCodec.encodeURL(name, true);
		}
		else {
			path = path + StringPool.SLASH + URLCodec.encodeURL(name, true);
		}

		return path;
	}

	private Date _createDate;
	private String _description;
	private Date _modifiedDate;
	private String _modifiedDateString;
	private String _name;
	private String _path;
	private long _size;

}