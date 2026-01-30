/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.display.context;

import com.liferay.digital.sales.room.constants.DigitalSalesRoomPortletKeys;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Stefano Motta
 */
public class ViewDigitalSalesRoomRoomListDisplayContext {

	public ViewDigitalSalesRoomRoomListDisplayContext(
		GroupService groupService, HttpServletRequest httpServletRequest,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		Portal portal) {

		_groupService = groupService;
		_httpServletRequest = httpServletRequest;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_portal = portal;
	}

	public String getAPIURL() {
		return "/o/headless-digital-sales-room/v1.0/digital-sales-rooms";
	}

	public CreationMenu getCreationMenu() throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition();

		long[] classNameIds = {
			_portal.getClassNameId(objectDefinition.getClassName())
		};

		int count = _groupService.searchCount(
			objectDefinition.getCompanyId(), classNameIds, StringPool.BLANK,
			LinkedHashMapBuilder.<String, Object>put(
				"active", true
			).put(
				"site", true
			).build());

		CreationMenu creationMenu = CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addDigitalSalesRoom");
				dropdownItem.setIcon("paste");

				if (count == 0) {
					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "new-digital-sales-room"));
				}
				else {
					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "start-from-scratch"));
				}
			}
		).build();

		if (count == 0) {
			return creationMenu;
		}

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "addDigitalSalesRoomFromTemplate");
				dropdownItem.setIcon("paste-plaintext");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "start-from-template"));
			});

		return creationMenu;
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
			FDSActionDropdownItemBuilder.setHref(
				() -> PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						_httpServletRequest,
						DigitalSalesRoomPortletKeys.
							DIGITAL_SALES_ROOM_MANAGEMENT,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/digital_sales_room/edit_digital_sales_room_room_settings"
				).setParameter(
					"digitalSalesRoomId", "{id}"
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

	private ObjectDefinition _getObjectDefinition() throws Exception {
		if (_objectDefinition != null) {
			return _objectDefinition;
		}

		_objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_TEMPLATE",
					_portal.getCompanyId(_httpServletRequest));

		return _objectDefinition;
	}

	private final GroupService _groupService;
	private final HttpServletRequest _httpServletRequest;
	private ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final Portal _portal;

}