/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

import com.liferay.petra.string.StringBundler;

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
		return StringBundler.concat(
			"{comments=", _comments, ", createdBy=", _createdBy,
			", createdDate=", _createdDate, ", sharepointVersionId=",
			_sharepointVersionId, ", size=", _size, ", url=", _url,
			", version=", _version, "}");
	}

	private final String _comments;
	private final String _createdBy;
	private final Date _createdDate;
	private final String _sharepointVersionId;
	private final long _size;
	private final URL _url;
	private final String _version;

}