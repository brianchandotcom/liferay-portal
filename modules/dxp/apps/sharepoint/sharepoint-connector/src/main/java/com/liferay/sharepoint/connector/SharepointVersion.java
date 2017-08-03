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

package com.liferay.sharepoint.connector;

import com.liferay.portal.kernel.util.StringBundler;

import java.net.URL;

import java.util.Date;

/**
 * @author Iván Zaera
 */
public class SharepointVersion {

	public SharepointVersion(
		String comments, String createdBy, Date createdDate,
		String sharepointVersionId, long size, URL url, String version) {

		_comments = comments;
		_createdBy = createdBy;
		_createdDate = createdDate;
		_sharepointVersionId = sharepointVersionId;
		_size = size;
		_url = url;
		_version = version;
	}

	public String getComments() {
		return _comments;
	}

	public String getCreatedBy() {
		return _createdBy;
	}

	public Date getCreatedDate() {
		return _createdDate;
	}

	public String getId() {
		return _sharepointVersionId;
	}

	public long getSize() {
		return _size;
	}

	public URL getURL() {
		return _url;
	}

	public String getVersion() {
		return _version;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{comments=");
		sb.append(_comments);
		sb.append(", createdBy=");
		sb.append(_createdBy);
		sb.append(", createdDate=");
		sb.append(_createdDate);
		sb.append(", sharepointVersionId=");
		sb.append(_sharepointVersionId);
		sb.append(", size=");
		sb.append(_size);
		sb.append(", url=");
		sb.append(_url);
		sb.append(", version=");
		sb.append(_version);
		sb.append("}");

		return sb.toString();
	}

	private final String _comments;
	private final String _createdBy;
	private final Date _createdDate;
	private final String _sharepointVersionId;
	private final long _size;
	private final URL _url;
	private final String _version;

}