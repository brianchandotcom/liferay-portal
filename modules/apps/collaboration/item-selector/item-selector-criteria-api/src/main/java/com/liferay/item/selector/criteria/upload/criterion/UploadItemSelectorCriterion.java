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

package com.liferay.item.selector.criteria.upload.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.util.PropsValues;

/**
 * @author Ambrín Chaudhary
 */
public class UploadItemSelectorCriterion extends BaseItemSelectorCriterion {

	public UploadItemSelectorCriterion() {
	}

	public UploadItemSelectorCriterion(String url, String repositoryName) {
		this(
			url, repositoryName,
			PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
	}

	public UploadItemSelectorCriterion(
		String url, String repositoryName, long maxFileSize) {

		_url = url;
		_repositoryName = repositoryName;
		_maxFileSize = maxFileSize;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getURL() {
		return _url;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	public void setURL(String url) {
		_url = url;
	}

	private long _maxFileSize;
	private String _repositoryName;
	private String _url;

}