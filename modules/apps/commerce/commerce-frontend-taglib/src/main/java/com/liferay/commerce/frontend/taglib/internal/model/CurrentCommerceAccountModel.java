/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.internal.model;

/**
 * @author Marco Leo
 */
public class CurrentCommerceAccountModel {

	public CurrentCommerceAccountModel(long id, String logoURL, String name) {
		_id = id;
		_logoURL = logoURL;
		_name = name;
	}

	public long getId() {
		return _id;
	}

	public String getLogoURL() {
		return _logoURL;
	}

	public String getName() {
		return _name;
	}

	private final long _id;
	private final String _logoURL;
	private final String _name;

}