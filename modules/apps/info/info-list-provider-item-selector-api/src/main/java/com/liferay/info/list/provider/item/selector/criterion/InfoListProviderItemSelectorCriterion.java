/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.list.provider.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class InfoListProviderItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public String getItemType() {
		if (ListUtil.isEmpty(_itemTypes)) {
			return null;
		}

		return _itemTypes.get(0);
	}

	public List<String> getItemTypes() {
		return _itemTypes;
	}

	public long getPlid() {
		return _plid;
	}

	public void setItemType(String itemType) {
		_itemTypes.add(itemType);
	}

	public void setItemTypes(List<String> itemTypes) {
		_itemTypes = itemTypes;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	private List<String> _itemTypes = new ArrayList<>();
	private long _plid;

}