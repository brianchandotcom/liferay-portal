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

package com.liferay.commerce.frontend.internal.search.model;

/**
 * @author Marco Leo
 */
public class SearchItemModel {

	public SearchItemModel(String type, String title) {
		_type = type;
		_title = title;
	}

	public String getIcon() {
		return _icon;
	}

	public String getImage() {
		return _image;
	}

	public String getSubtitle() {
		return _subtitle;
	}

	public String getThumbnailUrl() {
		return _thumbnailUrl;
	}

	public String getTitle() {
		return _title;
	}

	public String getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		_thumbnailUrl = thumbnailUrl;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _icon;
	private String _image;
	private String _subtitle;
	private String _thumbnailUrl;
	private final String _title;
	private final String _type;
	private String _url;

}