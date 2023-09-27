/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.taglib.internal.list.renderer;

/**
 * @author Pavel Savinov
 */
public enum BasicListInfoListStyle {

	BORDERED("bordered"), BULLETED("bulleted"), INLINE("inline"),
	NUMBERED("numbered"), UNSTYLED("unstyled");

	public String getKey() {
		return _key;
	}

	private BasicListInfoListStyle(String key) {
		_key = key;
	}

	private final String _key;

}