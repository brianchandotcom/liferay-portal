/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.sample.web.internal.display.context.helper.FDSRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
public class FDSSampleDisplayContext {

	public FDSSampleDisplayContext(HttpServletRequest httpServletRequest) {
		_fdsRequestHelper = new FDSRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/c/fdssamples";
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"#", "document", "sampleBulkAction",
				LanguageUtil.get(_fdsRequestHelper.getRequest(), "label"), null,
				null, null));
	}

	public CreationMenu getCreationMenu() throws Exception {
		return new CreationMenu();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		HttpServletRequest httpServletRequest = _fdsRequestHelper.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		String portalURL = company.getPortalURL(
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

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
				portalURL, "truck", "asyncSuccess", "Async Success", "get",
				null, "async"),
			new FDSActionDropdownItem(
				"http://localhost", "times-circle",
				"asyncErrorConnectionRefused", "Async Connection Refused",
				"get", null, "async"),
			new FDSActionDropdownItem(
				portalURL + "/abc", "staging", "asyncErrorResourceNotFound",
				"Async Resource Not Found", "get", null, "async"),
			new FDSActionDropdownItem(
				null, "reload", "reload", "Reload Data", null, null, null),
			new FDSActionDropdownItem(
				null, "rectangle-split", "openSidePanel", "Open Side Panel",
				null, null, null));
	}

	private final FDSRequestHelper _fdsRequestHelper;

}