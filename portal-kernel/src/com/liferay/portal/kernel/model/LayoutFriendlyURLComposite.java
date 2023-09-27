/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLComposite {

	public LayoutFriendlyURLComposite(
		Layout layout, String friendlyURL, boolean redirect) {

		_layout = layout;
		_friendlyURL = friendlyURL;
		_redirect = redirect;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public Layout getLayout() {
		return _layout;
	}

	public boolean isRedirect() {
		return _redirect;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	private String _friendlyURL;
	private Layout _layout;
	private final boolean _redirect;

}