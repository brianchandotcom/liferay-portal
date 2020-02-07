/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector;

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