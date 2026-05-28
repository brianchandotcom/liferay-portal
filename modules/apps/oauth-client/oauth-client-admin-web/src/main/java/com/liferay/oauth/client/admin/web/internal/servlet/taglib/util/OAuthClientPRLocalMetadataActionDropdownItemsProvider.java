/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Jorge García Jiménez
 */
public class OAuthClientPRLocalMetadataActionDropdownItemsProvider {

	public OAuthClientPRLocalMetadataActionDropdownItemsProvider(
		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_oAuthClientPRLocalMetadata = oAuthClientPRLocalMetadata;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			_getEditUnsafeConsumer()
		).add(
			_getDeleteUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception> _getDeleteUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.putData("action", "deleteOAuthClientPRLocalMetadata");
			dropdownItem.putData(
				"deleteOAuthClientPRLocalMetadataURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/oauth_client_admin/delete_oauth_client_pr_local_metadata"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"oAuthClientPRLocalMetadataId",
					_oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getEditUnsafeConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/oauth_client_admin/update_oauth_client_pr_local_metadata"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"oAuthClientPRLocalMetadataId",
					_oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId()
				).buildString());
			dropdownItem.setIcon("pencil");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final OAuthClientPRLocalMetadata _oAuthClientPRLocalMetadata;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}