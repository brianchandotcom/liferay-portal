/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.change.tracking.spi.display;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = CTDisplayRenderer.class)
public class AssetListEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<AssetListEntry> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			AssetListEntry assetListEntry)
		throws Exception {

		Group group = _groupLocalService.getGroup(assetListEntry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, AssetListPortletKeys.ASSET_LIST, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_asset_list_entry.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"assetListEntryId", assetListEntry.getAssetListEntryId()
		).buildString();
	}

	@Override
	public Class<AssetListEntry> getModelClass() {
		return AssetListEntry.class;
	}

	@Override
	public String getTitle(Locale locale, AssetListEntry assetListEntry) {
		return assetListEntry.getTitle();
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "collections");
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}