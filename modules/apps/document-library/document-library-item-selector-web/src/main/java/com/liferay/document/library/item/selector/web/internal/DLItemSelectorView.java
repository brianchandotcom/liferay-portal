/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.PortletItemSelectorView;

/**
 * @author Roberto Díaz
 */
public interface DLItemSelectorView<T extends ItemSelectorCriterion>
	extends PortletItemSelectorView<T> {

	public static final String DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT =
		"DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT";

	public String[] getExtensions();

	public String[] getMimeTypes();

}