/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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