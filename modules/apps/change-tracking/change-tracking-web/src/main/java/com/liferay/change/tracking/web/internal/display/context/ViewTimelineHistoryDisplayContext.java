/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheryl Tang
 */
public class ViewTimelineHistoryDisplayContext {

	public ViewTimelineHistoryDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/change-tracking-rest/v1.0/ct-entries/history";
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		FDSActionDropdownItem discardDropdownItem = new FDSActionDropdownItem(
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/change_tracking/view_discard"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", "{ctCollectionId}"
			).setParameter(
				"modelClassNameId", "{modelClassNameId}"
			).setParameter(
				"modelClassPK", "{modelClassPK}"
			).buildString(),
			"times-circle", "view-discard",
			_language.get(_httpServletRequest, "discard"), "get",
			"view-discard", null);

		String checkoutCTCollectionURL = PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/checkout_ct_collection"
		).setRedirect(
			ParamUtil.getString(_renderRequest, "redirect")
		).setParameter(
			"ctCollectionId", "{ctCollectionId}"
		).buildString();

		FDSActionDropdownItem editDropdownItem = new FDSActionDropdownItem(
			"javascript:window.open('" + checkoutCTCollectionURL +
				"', '_top');",
			"pencil", "edit",
			_language.format(
				_httpServletRequest, "edit-in-x",
				_language.get(_httpServletRequest, "publication")),
			"post", "checkout", null);

		FDSActionDropdownItem moveDropdownItem = new FDSActionDropdownItem(
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/change_tracking/view_move_changes"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", "{ctCollectionId}"
			).setParameter(
				"modelClassNameId", "{modelClassNameId}"
			).setParameter(
				"modelClassPK", "{modelClassPK}"
			).buildString(),
			"move-folder", "move-changes",
			_language.get(_httpServletRequest, "move-changes"), "post",
			"move-changes", null);

		FDSActionDropdownItem viewDropdownItem = new FDSActionDropdownItem(
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/change_tracking/view_change"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", "{ctCollectionId}"
			).setParameter(
				"modelClassNameId", "{modelClassNameId}"
			).setParameter(
				"modelClassPK", "{modelClassPK}"
			).buildString(),
			"list-ul", "view-change",
			_language.get(_httpServletRequest, "review-change"), "get", "get",
			null);

		return ListUtil.fromArray(
			discardDropdownItem, editDropdownItem, moveDropdownItem,
			viewDropdownItem);
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}