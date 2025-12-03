/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set.action;

import com.liferay.frontend.data.set.FDSEntryItemImportPolicy;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.portlet.PortletResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.ADVANCED,
	service = FDSItemsActions.class
)
public class AdvancedFDSItemsActions implements FDSItemsActions {

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems(
		HttpServletRequest httpServletRequest) {

		String href = "/o/c/fdssamples/{id}";

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setFDSActionDropdownItems(
				FDSActionDropdownItemList.of(
					FDSActionDropdownItemBuilder.setHref(
						"#test-visibility-filter"
					).setIcon(
						"sun"
					).setId(
						"sampleVisibilityFilterMessage"
					).setLabel(
						"Sample Visibility Filter"
					).setTarget(
						"link"
					).setVisibilityFilters(
						HashMapBuilder.<String, Object>put(
							"color", "Yellow"
						).build()
					).build(),
					FDSActionDropdownItemBuilder.setIcon(
						"view"
					).setId(
						"infoPanel"
					).setLabel(
						"View Details"
					).setTarget(
						"infoPanel"
					).build(),
					FDSActionDropdownItemBuilder.setIcon(
						"view"
					).setId(
						"sampleMessage"
					).setLabel(
						"Sample View"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						"#test-pencil"
					).setIcon(
						"pencil"
					).setId(
						"sampleEditMessage"
					).setLabel(
						"Sample Edit"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						"#test-delete"
					).setIcon(
						"times-circle"
					).setId(
						"sampleDeleteMessage"
					).setLabel(
						"Sample Delete"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						"#test-copy"
					).setIcon(
						"copy"
					).setId(
						"sampleMoveFolderMessage"
					).setLabel(
						"Sample Copy"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						href
					).setIcon(
						"truck"
					).setId(
						"asyncSuccess"
					).setLabel(
						"Async Success"
					).setMethod(
						"get"
					).setTarget(
						"async"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						"http://localhost"
					).setIcon(
						"times-circle"
					).setId(
						"asyncErrorConnectionRefused"
					).setLabel(
						"Async Connection Refused"
					).setMethod(
						"get"
					).setTarget(
						"async"
					).build(),
					FDSActionDropdownItemBuilder.setData(
						HashMapBuilder.<String, Object>put(
							"disableHeader", false
						).build()
					).putData(
						"title", "Side Panel Title Provided by Action"
					).setHref(
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCRenderCommandName(
							"/side_panel/empty"
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					).setIcon(
						"rectangle-split"
					).setId(
						"open-side-panel-no-title"
					).setLabel(
						"Side Panel With Action Title"
					).setTarget(
						"sidePanel"
					).build(),
					FDSActionDropdownItemBuilder.setData(
						HashMapBuilder.<String, Object>put(
							"disableHeader", false
						).build()
					).putData(
						"title", "Side Panel Title Provided by Action"
					).setHref(
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCRenderCommandName(
							"/side_panel/full"
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					).setIcon(
						"rectangle-split"
					).setId(
						"open-side-panel-no-title"
					).setLabel(
						"Side Panel With Action and Content Title"
					).setTarget(
						"sidePanel"
					).build(),
					FDSActionDropdownItemBuilder.putData(
						"disableHeader", "true"
					).setHref(
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCRenderCommandName(
							"/side_panel/full"
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					).setIcon(
						"rectangle-split"
					).setId(
						"open-side-panel-no-title"
					).setLabel(
						"Side Panel With Content Title"
					).setTarget(
						"sidePanel"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCRenderCommandName(
							"/side_panel/empty"
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					).setIcon(
						"rectangle-split"
					).setId(
						"open-side-panel-without-title"
					).setLabel(
						"Side Panel With No Title"
					).setTarget(
						"sidePanel"
					).build(),
					FDSActionDropdownItemBuilder.setHref(
						href + "/abc"
					).setIcon(
						"staging"
					).setId(
						"asyncErrorResourceNotFound"
					).setLabel(
						"Async Resource Not Found"
					).setMethod(
						"get"
					).setTarget(
						"async"
					).build(),
					FDSActionDropdownItemBuilder.setIcon(
						"reload"
					).setId(
						"reload"
					).setLabel(
						"Reload Data"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setIcon(
						"rectangle-split"
					).setId(
						"openSidePanel"
					).setLabel(
						"Open Side Panel"
					).setTarget(
						"link"
					).build())
			).setId(
				"groupItems"
			).setSeparator(
				true
			).setType(
				"group"
			).build(),
			FDSActionDropdownItemBuilder.setIcon(
				"hidden"
			).setId(
				"groupPermissionTest"
			).setSeparator(
				false
			).setType(
				"group"
			).build(),
			FDSActionDropdownItemBuilder.setFDSActionDropdownItems(
				FDSActionDropdownItemList.of(
					FDSActionDropdownItemBuilder.setIcon(
						"separator"
					).setId(
						"sampleMessage"
					).setLabel(
						"Group Item"
					).setTarget(
						"link"
					).build(),
					FDSActionDropdownItemBuilder.setFDSActionDropdownItems(
						FDSActionDropdownItemList.of(
							FDSActionDropdownItemBuilder.setIcon(
								"exclamation-circle"
							).setId(
								"sampleMessage"
							).setLabel(
								"Contextual Sub Item 1"
							).setTarget(
								"link"
							).build(),
							FDSActionDropdownItemBuilder.setIcon(
								"exclamation-circle"
							).setId(
								"sampleMessage"
							).setLabel(
								"Contextual Sub Item 2"
							).setTarget(
								"link"
							).build())
					).setIcon(
						"nodes"
					).setId(
						"contextualItem"
					).setLabel(
						"Contextual Item"
					).setSeparator(
						false
					).setType(
						"contextual"
					).build())
			).setId(
				"groupItems"
			).setSeparator(
				true
			).setType(
				"group"
			).build());
	}

	@Override
	public FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.ITEM_PROXY;
	}

	@Reference
	private Language _language;

}