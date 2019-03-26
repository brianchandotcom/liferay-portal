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

package com.liferay.asset.publisher.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetPublisherViewContentDisplayContext {

	public AssetPublisherViewContentDisplayContext(
		RenderRequest renderRequest, boolean enablePermissions) {

		_renderRequest = renderRequest;
		_enablePermissions = enablePermissions;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setAssetObjects();
	}

	public AssetEntry getAssetEntry() {
		return _assetEntry;
	}

	public AssetRenderer<?> getAssetRenderer() {
		return _assetRenderer;
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(
			_renderRequest, "groupId", _themeDisplay.getScopeGroupId());

		return _groupId;
	}

	public boolean getPrint() {
		if (Objects.equals(_getViewMode(), Constants.PRINT)) {
			return true;
		}

		return false;
	}

	public String getReturnToFullPageURL() {
		if (_returnToFullPageURL != null) {
			return _returnToFullPageURL;
		}

		_returnToFullPageURL = ParamUtil.getString(
			_renderRequest, "returnToFullPageURL");

		return _returnToFullPageURL;
	}

	public boolean isAssetEntryVisible() {
		if ((_assetEntry == null) || (_assetRenderer == null)) {
			return false;
		}

		if (_assetEntry.isVisible() && !_enablePermissions) {
			return true;
		}

		try {
			if (_assetRenderer.hasViewPermission(
					_themeDisplay.getPermissionChecker()) &&
				_assetRenderer.isDisplayable()) {

				return true;
			}
		}
		catch (Exception e) {
			SessionErrors.add(
				_renderRequest,
				PrincipalException.MustHavePermission.class.getName());
		}

		return false;
	}

	public boolean isShowBackURL() {
		boolean print = getPrint();

		return !print;
	}

	private long _getAssetEntryId() {
		if (_assetEntryId != null) {
			return _assetEntryId;
		}

		_assetEntryId = ParamUtil.getLong(_renderRequest, "assetEntryId");

		return _assetEntryId;
	}

	private String _getType() {
		if (_type != null) {
			return _type;
		}

		_type = ParamUtil.getString(_renderRequest, "type");

		return _type;
	}

	private String _getURLTitle() {
		if (_urlTitle != null) {
			return _urlTitle;
		}

		_urlTitle = ParamUtil.getString(_renderRequest, "urlTitle");

		return _urlTitle;
	}

	private String _getViewMode() {
		if (_viewMode != null) {
			return _viewMode;
		}

		_viewMode = ParamUtil.getString(_renderRequest, "viewMode");

		return _viewMode;
	}

	private void _setAssetObjects() {
		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
				_getType());

		if (_assetRendererFactory == null) {
			SessionErrors.add(
				_renderRequest, NoSuchModelException.class.getName());

			return;
		}

		try {
			if (Validator.isNotNull(_getURLTitle())) {
				_assetRenderer = _assetRendererFactory.getAssetRenderer(
					getGroupId(), _getURLTitle());

				_assetEntry = _assetRendererFactory.getAssetEntry(
					_assetRendererFactory.getClassName(),
					_assetRenderer.getClassPK());
			}
			else {
				_assetEntry = _assetRendererFactory.getAssetEntry(
					_getAssetEntryId());

				_assetRenderer = _assetRendererFactory.getAssetRenderer(
					_assetEntry.getClassPK());
			}
		}
		catch (Exception e) {
			SessionErrors.add(
				_renderRequest, NoSuchModelException.class.getName());
		}
	}

	private AssetEntry _assetEntry;
	private Long _assetEntryId;
	private AssetRenderer<?> _assetRenderer;
	private AssetRendererFactory<?> _assetRendererFactory;
	private final boolean _enablePermissions;
	private Long _groupId;
	private final RenderRequest _renderRequest;
	private String _returnToFullPageURL;
	private final ThemeDisplay _themeDisplay;
	private String _type;
	private String _urlTitle;
	private String _viewMode;

}