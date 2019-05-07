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

package com.liferay.asset.taglib.servlet.taglib;

import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Julio Camarero
 */
public class AssetAddonEntrySelectorTag extends IncludeTag {

	public List<AssetAddonEntry> getAssetAddonEntries() {
		return _assetAddonEntries;
	}

	public String getHiddenInput() {
		return _hiddenInput;
	}

	public List<AssetAddonEntry> getSelectedAssetAddonEntries() {
		return _selectedAssetAddonEntries;
	}

	public String getTitle() {
		return _title;
	}

	public void setAssetAddonEntries(List<AssetAddonEntry> assetAddonEntries) {
		_assetAddonEntries = assetAddonEntries;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSelectedAssetAddonEntries(
		List<AssetAddonEntry> selectedAssetAddonEntries) {

		_selectedAssetAddonEntries = selectedAssetAddonEntries;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_assetAddonEntries = null;
		_hiddenInput = null;
		_id = null;
		_selectedAssetAddonEntries = null;
		_title = "select-entries";
	}

	protected String getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String id = PortalUtil.generateRandomKey(
			request, "taglib_ui_asset_addon_entry_selector_page");

		return id + StringPool.UNDERLINE;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			WebKeys.ASSET_ADDON_ENTRIES, _assetAddonEntries);
		httpServletRequest.setAttribute(
			"liferay-asset:asset-addon-entry-selector:hiddenInput",
			_hiddenInput);
		httpServletRequest.setAttribute(
			"liferay-asset:asset-addon-entry-selector:id", getId());
		httpServletRequest.setAttribute(
			"liferay-asset:asset-addon-entry-selector:" +
				"selectedAssetAddonEntries",
			_getFilteredSelectedAssetAddonEntries());
		httpServletRequest.setAttribute(
			"liferay-asset:asset-addon-entry-selector:title", _title);
	}

	private List<AssetAddonEntry> _getFilteredSelectedAssetAddonEntries() {
		Stream<AssetAddonEntry> stream = _selectedAssetAddonEntries.stream();

		stream = stream.filter(_assetAddonEntries::contains);

		return stream.collect(Collectors.toList());
	}

	private static final String _PAGE = "/asset_addon_entry_selector/page.jsp";

	private List<AssetAddonEntry> _assetAddonEntries;
	private String _hiddenInput;
	private String _id;
	private List<AssetAddonEntry> _selectedAssetAddonEntries;
	private String _title = "select-entries";

}