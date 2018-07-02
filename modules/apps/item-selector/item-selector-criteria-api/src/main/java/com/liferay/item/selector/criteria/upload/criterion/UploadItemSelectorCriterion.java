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
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;

/**
 * @author Ambrín Chaudhary
 */
public class UploadItemSelectorCriterion extends BaseItemSelectorCriterion {

	public UploadItemSelectorCriterion() {
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #UploadItemSelectorCriterion(String, String, String)}
	 */
	@Deprecated
	public UploadItemSelectorCriterion(String url, String repositoryName) {
		this(
			null, url, repositoryName,
			UploadServletRequestConfigurationHelperUtil.getMaxSize());
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #UploadItemSelectorCriterion(String, String, String, long)}
	 */
	@Deprecated
	public UploadItemSelectorCriterion(
		String url, String repositoryName, long maxFileSize) {

		this(null, url, repositoryName, maxFileSize);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #UploadItemSelectorCriterion(String, String, String, long,
	 *             String[])}
	 */
	@Deprecated
	public UploadItemSelectorCriterion(
		String url, String repositoryName, long maxFileSize,
		String[] extensions) {

		this(null, url, repositoryName, maxFileSize, extensions);
	}

	public UploadItemSelectorCriterion(
		String portletId, String url, String repositoryName) {

		this(
			portletId, url, repositoryName,
			UploadServletRequestConfigurationHelperUtil.getMaxSize());
	}

	public UploadItemSelectorCriterion(
		String portletId, String url, String repositoryName, long maxFileSize) {

		_portletId = portletId;
		_url = url;
		_repositoryName = repositoryName;
		_maxFileSize = maxFileSize;
	}

	public UploadItemSelectorCriterion(
		String portletId, String url, String repositoryName, long maxFileSize,
		String[] extensions) {

		_portletId = portletId;
		_url = url;
		_repositoryName = repositoryName;
		_maxFileSize = maxFileSize;
		_extensions = extensions;
	}

	public String[] getExtensions() {
		return _extensions;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getURL() {
		return _url;
	}

	public void setExtensions(String[] extensions) {
		_extensions = extensions;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String[] _extensions;
	private long _maxFileSize;
	private String _portletId;
	private String _repositoryName;
	private String _url;

}