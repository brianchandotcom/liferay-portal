/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.internal.model;

/**
 * @author Marco Leo
 */
public class ClayPaginationEntry {

	public ClayPaginationEntry(String href, int label) {
		_href = href;
		_label = label;
	}

	public String getHref() {
		return _href;
	}

	public int getLabel() {
		return _label;
	}

	private final String _href;
	private final int _label;

}