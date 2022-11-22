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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class ViewSharingEntryAssetEntryDisplayContext {

	public ViewSharingEntryAssetEntryDisplayContext(
		AssetRenderer<?> assetRenderer,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SharingEntry sharingEntry,
		ViewSharedAssetsDisplayContext viewSharedAssetsDisplayContext) {

		_assetRenderer = assetRenderer;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_sharingEntry = sharingEntry;
		_viewSharedAssetsDisplayContext = viewSharedAssetsDisplayContext;
	}

	public String getAssetEntryClassName() throws PortalException {
		AssetEntry assetEntry = _getAssetEntry();

		return assetEntry.getClassName();
	}

	public long getAssetEntryClassPK() throws PortalException {
		AssetEntry assetEntry = _getAssetEntry();

		return assetEntry.getClassPK();
	}

	public long getAssetEntryUserId() throws PortalException {
		AssetEntry assetEntry = _getAssetEntry();

		return assetEntry.getUserId();
	}

	public AssetRenderer<?> getAssetRenderer() {
		return _assetRenderer;
	}

	public String getAssetTitle() {
		return _assetRenderer.getTitle(_liferayPortletRequest.getLocale());
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect");

		if (Validator.isNull(redirect)) {
			return String.valueOf(_liferayPortletResponse.createRenderURL());
		}

		return redirect;
	}

	public List<DropdownItem> getSharingEntryDropdownItems()
		throws PortalException {

		return _viewSharedAssetsDisplayContext.getSharingEntryDropdownItems(
			_sharingEntry);
	}

	public boolean isCommentable() {
		return _assetRenderer.isCommentable();
	}

	public boolean isControlPanelGroup() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.equals(themeDisplay.getControlPanelGroup())) {
			return true;
		}

		return false;
	}

	private AssetEntry _getAssetEntry() throws PortalException {
		if (_assetEntry != null) {
			return _assetEntry;
		}

		AssetRendererFactory<?> assetRendererFactory =
			_assetRenderer.getAssetRendererFactory();

		_assetEntry = assetRendererFactory.getAssetEntry(
			assetRendererFactory.getClassName(), _assetRenderer.getClassPK());

		return _assetEntry;
	}

	private AssetEntry _assetEntry;
	private final AssetRenderer<?> _assetRenderer;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SharingEntry _sharingEntry;
	private final ViewSharedAssetsDisplayContext
		_viewSharedAssetsDisplayContext;

}