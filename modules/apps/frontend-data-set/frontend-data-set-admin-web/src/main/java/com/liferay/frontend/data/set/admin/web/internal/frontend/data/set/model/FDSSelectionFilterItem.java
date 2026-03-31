/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.model;

/**
 * @author Miguel Arroyo
 */
public class FDSSelectionFilterItem {

	public FDSSelectionFilterItem(String itemKey, String itemLabel) {
		_itemKey = itemKey;
		_itemLabel = itemLabel;
	}

	public String getItemKey() {
		return _itemKey;
	}

	public String getItemLabel() {
		return _itemLabel;
	}

	private final String _itemKey;
	private final String _itemLabel;

}