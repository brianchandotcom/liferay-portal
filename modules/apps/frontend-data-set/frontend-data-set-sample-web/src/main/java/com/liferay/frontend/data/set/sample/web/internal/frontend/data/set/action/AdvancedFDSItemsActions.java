/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set.action;

import com.liferay.frontend.data.set.FDSEntryItemImportPolicy;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

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

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		String href = "/o/c/fdssamples/{id}";

		FDSActionDropdownItem sidePanel1FDSActionDropdownItem =
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCRenderCommandName(
					"/side_panel/empty"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"rectangle-split", "open-side-panel-no-title",
				"Side Panel With Action Title", null, null, "sidePanel");

		sidePanel1FDSActionDropdownItem.putData("disableHeader", false);
		sidePanel1FDSActionDropdownItem.putData(
			"title", "Side Panel Title Provided by Action");

		FDSActionDropdownItem sidePanel2FDSActionDropdownItem =
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCRenderCommandName(
					"/side_panel/full"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"rectangle-split", "open-side-panel-title",
				"Side Panel With Action and Content Title", null, null,
				"sidePanel");

		sidePanel2FDSActionDropdownItem.putData("disableHeader", false);
		sidePanel2FDSActionDropdownItem.putData(
			"title", "Side Panel Title Provided by Action");

		FDSActionDropdownItem sidePanel3FDSActionDropdownItem =
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCRenderCommandName(
					"/side_panel/full"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"rectangle-split", "open-side-panel-title",
				"Side Panel With Content Title", null, null, "sidePanel");

		sidePanel3FDSActionDropdownItem.putData("disableHeader", true);

		return Arrays.asList(
			new FDSActionDropdownItem(
				null, "view", "sampleMessage", "Sample View", null, null, null),
			new FDSActionDropdownItem(
				"#test-pencil", "pencil", "sampleEditMessage", "Sample Edit",
				null, null, null),
			new FDSActionDropdownItem(
				"#test-delete", "times-circle", "sampleDeleteMessage",
				"Sample Delete", null, null, null),
			new FDSActionDropdownItem(
				"#test-copy", "copy", "sampleMoveFolderMessage", "Sample Copy",
				null, null, null),
			new FDSActionDropdownItem(
				href, "truck", "asyncSuccess", "Async Success", "get", null,
				"async"),
			new FDSActionDropdownItem(
				"http://localhost", "times-circle",
				"asyncErrorConnectionRefused", "Async Connection Refused",
				"get", null, "async"),
			sidePanel1FDSActionDropdownItem, sidePanel2FDSActionDropdownItem,
			sidePanel3FDSActionDropdownItem,
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCRenderCommandName(
					"/side_panel/empty"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"rectangle-split", "open-side-panel-without-title",
				"Side Panel With No Title", null, null, "sidePanel"),
			new FDSActionDropdownItem(
				href + "/abc", "staging", "asyncErrorResourceNotFound",
				"Async Resource Not Found", "get", null, "async"),
			new FDSActionDropdownItem(
				null, "reload", "reload", "Reload Data", null, null, null),
			new FDSActionDropdownItem(
				null, "rectangle-split", "openSidePanel", "Open Side Panel",
				null, null, null));
	}

	@Override
	public FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.DETACHED;
	}

	@Reference
	private Language _language;

}