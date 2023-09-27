/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Juan Fernández
 */
public class AssetTagNameComparator extends OrderByComparator<AssetTag> {

	public static final String ORDER_BY_ASC = "AssetTag.name ASC";

	public static final String ORDER_BY_DESC = "AssetTag.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public AssetTagNameComparator() {
		this(true, false);
	}

	public AssetTagNameComparator(boolean ascending) {
		this(ascending, false);
	}

	public AssetTagNameComparator(boolean ascending, boolean caseSensitive) {
		_ascending = ascending;
		_caseSensitive = caseSensitive;
	}

	@Override
	public int compare(AssetTag assetTag1, AssetTag assetTag2) {
		String name1 = assetTag1.getName();
		String name2 = assetTag2.getName();

		int value = 0;

		if (_caseSensitive) {
			value = name1.compareTo(name2);
		}
		else {
			value = name1.compareToIgnoreCase(name2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final boolean _caseSensitive;

}