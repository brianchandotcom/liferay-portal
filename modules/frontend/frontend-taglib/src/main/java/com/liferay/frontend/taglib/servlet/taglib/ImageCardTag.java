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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class ImageCardTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setImageCssClass(String imageCssClass) {
		_imageCssClass = imageCssClass;
	}

	public void setImageURL(String imageURL) {
		_imageURL = imageURL;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_data = null;
		_imageURL = null;
		_imageCssClass = null;
	}

	@Override
	protected String getPage() {
		return "/card/image_card/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:image-card:cssClass", _cssClass);
		request.setAttribute("liferay-frontend:image-card:data", _data);
		request.setAttribute(
			"liferay-frontend:image-card:imageCssClass", _imageCssClass);
		request.setAttribute("liferay-frontend:image-card:imageURL", _imageURL);
	}

	private String _cssClass;
	private Map<String, Object> _data;
	private String _imageCssClass;
	private String _imageURL;

}