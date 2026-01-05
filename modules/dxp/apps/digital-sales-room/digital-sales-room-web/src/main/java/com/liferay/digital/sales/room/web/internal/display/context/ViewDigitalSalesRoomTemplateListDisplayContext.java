/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.display.context;

import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomPortletKeys;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.Portal;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Stefano Motta
 */
public class ViewDigitalSalesRoomTemplateListDisplayContext {

	public ViewDigitalSalesRoomTemplateListDisplayContext(
		HttpServletRequest httpServletRequest, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_portal = portal;
	}

	public String getAPIURL() {
		return "/o/headless-digital-sales-room/v1.0" +
			"/digital-sales-room-templates";
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addDigitalSalesRoomTemplate");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						"new-digital-sales-room-template"));
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
			FDSActionDropdownItemBuilder.setHref(
				() -> PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						_httpServletRequest,
						DigitalSalesRoomPortletKeys.
							DIGITAL_SALES_ROOM_MANAGEMENT,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/digital_sales_room" +
						"/edit_digital_sales_room_template_settings"
				).setParameter(
					"digitalSalesRoomTemplateId", "{id}"
				).buildString()
			).setIcon(
				"cog"
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "settings")
			).setMethod(
				"get"
			).build(
				"settings"
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
	private final Portal _portal;

}