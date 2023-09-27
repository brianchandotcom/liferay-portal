/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public class DeleteMenuItem extends MenuItem {

	public String getURL() {
		return _url;
	}

	public boolean isTrash() {
		return _trash;
	}

	public void setTrash(boolean trash) {
		_trash = trash;
	}

	public void setURL(String url) {
		_url = url;
	}

	private boolean _trash;
	private String _url;

}