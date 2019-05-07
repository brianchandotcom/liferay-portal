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

import com.liferay.portal.kernel.util.HtmlUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class VerticalCardTag extends CardTag {

	public String getFooter() {
		return _footer;
	}

	public String getHeader() {
		return _header;
	}

	public String getOnClick() {
		return _onClick;
	}

	public String getStickerBottom() {
		return _stickerBottom;
	}

	public String getSubtitle() {
		return _subtitle;
	}

	public String getTitle() {
		return _title;
	}

	public boolean isBackgroundImage() {
		return _backgroundImage;
	}

	public void setBackgroundImage(boolean backgroundImage) {
		_backgroundImage = backgroundImage;
	}

	public void setFooter(String footer) {
		_footer = footer;
	}

	public void setHeader(String header) {
		_header = header;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setStickerBottom(String stickerBottom) {
		_stickerBottom = stickerBottom;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = HtmlUtil.unescape(subtitle);
	}

	public void setTitle(String title) {
		_title = HtmlUtil.unescape(title);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backgroundImage = true;
		_footer = null;
		_header = null;
		_onClick = null;
		_stickerBottom = null;
		_subtitle = null;
		_title = null;
	}

	@Override
	protected String getPage() {
		return "/card/vertical_card/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-frontend:card:backgroundImage", _backgroundImage);
		httpServletRequest.setAttribute(
			"liferay-frontend:card:footer", _footer);
		httpServletRequest.setAttribute(
			"liferay-frontend:card:header", _header);
		httpServletRequest.setAttribute(
			"liferay-frontend:card:onClick", _onClick);
		httpServletRequest.setAttribute(
			"liferay-frontend:card:stickerBottom", _stickerBottom);
		httpServletRequest.setAttribute(
			"liferay-frontend:card:subtitle", _subtitle);
		httpServletRequest.setAttribute("liferay-frontend:card:title", _title);
	}

	private boolean _backgroundImage = true;
	private String _footer;
	private String _header;
	private String _onClick;
	private String _stickerBottom;
	private String _subtitle;
	private String _title;

}