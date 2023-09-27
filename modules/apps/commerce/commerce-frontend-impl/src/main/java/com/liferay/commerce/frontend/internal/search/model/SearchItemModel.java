/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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