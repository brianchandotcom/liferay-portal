/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultContentDisplayContext;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SearchResultContentDisplayBuilder {

	public SearchResultContentDisplayContext build() throws Exception {
		SearchResultContentDisplayContext searchResultContentDisplayContext =
			new SearchResultContentDisplayContext();

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactoryByType(_type);

		searchResultContentDisplayContext.setAssetRendererFactory(
			assetRendererFactory);

		AssetEntry assetEntry;

		if (assetRendererFactory != null) {
			assetEntry = assetRendererFactory.getAssetEntry(_assetEntryId);
		}
		else {
			assetEntry = null;
		}

		searchResultContentDisplayContext.setAssetEntry(assetEntry);

		AssetRenderer<?> assetRenderer;

		if (assetEntry != null) {
			assetRenderer = assetEntry.getAssetRenderer();
		}
		else {
			assetRenderer = null;
		}

		searchResultContentDisplayContext.setAssetRenderer(assetRenderer);

		final boolean visible;

		if ((assetEntry != null) && (assetRenderer != null) &&
			assetEntry.isVisible() &&
			assetRenderer.hasViewPermission(_permissionChecker)) {

			visible = true;
		}
		else {
			visible = false;
		}

		searchResultContentDisplayContext.setVisible(visible);

		if (visible) {
			String title = assetRenderer.getTitle(_locale);

			searchResultContentDisplayContext.setHeaderTitle(title);

			boolean hasEditPermission = assetRenderer.hasEditPermission(
				_permissionChecker);

			searchResultContentDisplayContext.setHasEditPermission(
				hasEditPermission);

			if (hasEditPermission) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				searchResultContentDisplayContext.setIconEditTarget(title);

				searchResultContentDisplayContext.setIconURLString(
					PortletURLBuilder.create(
						assetRenderer.getURLEdit(
							_portal.getLiferayPortletRequest(_renderRequest),
							_portal.getLiferayPortletResponse(_renderResponse))
					).setRedirect(
						themeDisplay.getURLCurrent()
					).buildString());
			}

			searchResultContentDisplayContext.setShowExtraInfo(
				_type.equals("document"));
		}

		return searchResultContentDisplayContext;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public void setAssetRendererFactoryLookup(
		AssetRendererFactoryLookup assetRendererFactoryLookup) {

		_assetRendererFactoryLookup = assetRendererFactoryLookup;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setPermissionChecker(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setRenderResponse(RenderResponse renderResponse) {
		_renderResponse = renderResponse;
	}

	public void setType(String type) {
		_type = type;
	}

	protected AssetRendererFactory<?> getAssetRendererFactoryByType(
		String type) {

		if (_assetRendererFactoryLookup != null) {
			return _assetRendererFactoryLookup.getAssetRendererFactoryByType(
				type);
		}

		return AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
			type);
	}

	private long _assetEntryId;
	private AssetRendererFactoryLookup _assetRendererFactoryLookup;
	private Locale _locale;
	private PermissionChecker _permissionChecker;
	private Portal _portal;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private String _type;

}