/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSSortItemBuilder;
import com.liferay.frontend.data.set.model.FDSSortItemList;
import com.liferay.frontend.data.set.model.FDSSortItemListBuilder;
import com.liferay.frontend.data.set.sample.web.internal.display.context.helper.FDSRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
public class FDSSampleDisplayContext {

	public FDSSampleDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_renderResponse = renderResponse;

		_fdsRequestHelper = new FDSRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/c/fdssamples?sort=title:asc";
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, null, null, true, "#", "document", "sampleBulkAction",
				LanguageUtil.get(_fdsRequestHelper.getRequest(), "label"), null,
				"lg", null, null, null, "modal", null, null, null),
			new FDSActionDropdownItem(
				null, null, null, false, "#", "trash", "delete",
				LanguageUtil.get(_fdsRequestHelper.getRequest(), "delete"),
				null, "lg", null, null, null, "modal", null, null, null),
			new FDSActionDropdownItem(
				"/o/c/fdssamples/", "check", "testBulkAction",
				LanguageUtil.get(_fdsRequestHelper.getRequest(), "test"),
				"POST", null, null));
	}

	public CreationMenu getCreationMenu() throws Exception {
		return new CreationMenu();
	}

	public FDSSortItemList getFDSSortItemList() {
		return FDSSortItemListBuilder.add(
			FDSSortItemBuilder.setDirection(
				"asc"
			).setKey(
				"title"
			).build()
		).build();
	}

	private final FDSRequestHelper _fdsRequestHelper;
	private final RenderResponse _renderResponse;

}