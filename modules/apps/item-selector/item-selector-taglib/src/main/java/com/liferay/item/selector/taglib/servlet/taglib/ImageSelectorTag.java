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

package com.liferay.item.selector.taglib.servlet.taglib;

import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class ImageSelectorTag extends IncludeTag {

	public String getDraggableImage() {
		return _draggableImage;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public String getItemSelectorEventName() {
		return _itemSelectorEventName;
	}

	public String getItemSelectorURL() {
		return _itemSelectorURL;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public String getParamName() {
		return _paramName;
	}

	public String getUploadURL() {
		return _uploadURL;
	}

	public String getValidExtensions() {
		return _validExtensions;
	}

	public void setDraggableImage(String draggableImage) {
		_draggableImage = draggableImage;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public void setItemSelectorEventName(String itemSelectorEventName) {
		_itemSelectorEventName = itemSelectorEventName;
	}

	public void setItemSelectorURL(String itemSelectorURL) {
		_itemSelectorURL = itemSelectorURL;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setParamName(String paramName) {
		_paramName = paramName;
	}

	public void setUploadURL(String uploadURL) {
		_uploadURL = uploadURL;
	}

	public void setValidExtensions(String validExtensions) {
		_validExtensions = validExtensions;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_draggableImage = "none";
		_fileEntryId = 0;
		_itemSelectorEventName = null;
		_itemSelectorURL = null;
		_maxFileSize = 0;
		_paramName = "imageSelectorFileEntryId";
		_uploadURL = null;
		_validExtensions = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:draggableImage", _draggableImage);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:fileEntryId", _fileEntryId);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:itemSelectorEventName",
			_itemSelectorEventName);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:itemSelectorURL", _itemSelectorURL);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:maxFileSize", _maxFileSize);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:paramName", _paramName);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:uploadURL", _uploadURL);
		httpServletRequest.setAttribute(
			"liferay-ui:image-selector:validExtensions", _validExtensions);
	}

	private static final String _PAGE = "/image_selector/page.jsp";

	private String _draggableImage = "none";
	private long _fileEntryId;
	private String _itemSelectorEventName;
	private String _itemSelectorURL;
	private long _maxFileSize;
	private String _paramName = "imageSelectorFileEntryId";
	private String _uploadURL;
	private String _validExtensions;

}