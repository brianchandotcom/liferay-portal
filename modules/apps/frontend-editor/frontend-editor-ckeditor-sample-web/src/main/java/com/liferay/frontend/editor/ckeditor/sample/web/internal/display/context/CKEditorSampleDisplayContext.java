/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;

import java.util.List;

/**
 * @author Marko Cikos
 */
public class CKEditorSampleDisplayContext {

	public List<TabsItem> getTabsItems() {
		if (_tabsItems != null) {
			return _tabsItems;
		}

		_tabsItems = TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel("Balloon");
				tabsItem.setPanelId("balloon");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Classic");
				tabsItem.setPanelId("classic");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Legacy");
				tabsItem.setPanelId("legacy");
			}
		).build();

		return _tabsItems;
	}

	private List<TabsItem> _tabsItems;

}