/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.collapse;

/**
 * @author Petteri Karttunen
 */
public class InnerCollapse {

	public InnerCollapse(String field) {
		_field = field;
	}

	public String getField() {
		return _field;
	}

	private final String _field;

}