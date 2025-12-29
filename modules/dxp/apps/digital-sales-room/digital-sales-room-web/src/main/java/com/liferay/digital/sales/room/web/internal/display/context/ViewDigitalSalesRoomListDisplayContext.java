/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Stefano Motta
 */
public class ViewDigitalSalesRoomListDisplayContext {

	public ViewDigitalSalesRoomListDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		return "/o/headless-digital-sales-room/v1.0/digital-sales-rooms";
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addDigitalSalesRoom");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "new-digital-sales-room"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setIcon(
				"pencil"
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "edit")
			).setMethod(
				"patch"
			).build(
				"edit"
			),
			FDSActionDropdownItemBuilder.setIcon(
				"download"
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "save-as-template")
			).setMethod(
				"post"
			).build(
				"saveAsTemplate"
			),
			FDSActionDropdownItemBuilder.setIcon(
				"trash"
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "delete")
			).setMethod(
				"delete"
			).build(
				"delete"
			));
	}

	private final HttpServletRequest _httpServletRequest;

}