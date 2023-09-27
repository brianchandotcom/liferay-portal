/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium.internal.product.model;

/**
 * @author Marco Leo
 */
public class ProductCompareModel {

	public ProductCompareModel(long id, String thumbnail, String visibility) {
		_id = id;
		_thumbnail = thumbnail;
		_visibility = visibility;
	}

	public long getId() {
		return _id;
	}

	public String getThumbnail() {
		return _thumbnail;
	}

	public String getVisibility() {
		return _visibility;
	}

	private final long _id;
	private final String _thumbnail;
	private final String _visibility;

}