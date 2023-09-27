/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Drew Brokke
 */
public class ManagementToolbarDefaults {

	public static String getContentRenderer() {
		return "hiddenInputsForm";
	}

	public static Integer getItemsTotal() {
		return 0;
	}

	public static String getSearchFormMethod() {
		return "GET";
	}

	public static String getSearchInputName() {
		return DisplayTerms.KEYWORDS;
	}

	public static Integer getSelectedItems() {
		return 0;
	}

	public static Boolean isDisabled() {
		return false;
	}

	public static Boolean isSearchInputAutoFocus() {
		return false;
	}

	public static Boolean isSelectable() {
		return true;
	}

	public static Boolean isShowCreationMenu(CreationMenu creationMenu) {
		if (creationMenu != null) {
			return true;
		}

		return false;
	}

	public static Boolean isShowInfoButton(String infoPanelId) {
		return Validator.isNotNull(infoPanelId);
	}

	public static Boolean isShowSearch() {
		return true;
	}

	public static Boolean isSupportsBulkActions() {
		return true;
	}

}