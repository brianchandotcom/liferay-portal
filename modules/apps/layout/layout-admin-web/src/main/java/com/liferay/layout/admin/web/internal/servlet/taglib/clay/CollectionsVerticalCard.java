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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CollectionsVerticalCard extends BaseVerticalCard {

	public CollectionsVerticalCard(
		AssetListEntry assetListEntry, long groupId,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(null, renderRequest, null);

		_assetListEntry = assetListEntry;
		_groupId = groupId;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public String getCssClass() {
		return "select-collection-action-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		Map<String, String> data = new HashMap<>();

		try {
			PortletURL selectLayoutMasterLayoutURL =
				_renderResponse.createRenderURL();

			selectLayoutMasterLayoutURL.setParameter(
				"mvcPath", "/select_layout_master_layout.jsp");

			String redirect = ParamUtil.getString(
				_httpServletRequest, "redirect");

			selectLayoutMasterLayoutURL.setParameter("redirect", redirect);

			selectLayoutMasterLayoutURL.setParameter(
				"backURL", themeDisplay.getURLCurrent());
			selectLayoutMasterLayoutURL.setParameter(
				"groupId", String.valueOf(_groupId));

			long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

			selectLayoutMasterLayoutURL.setParameter(
				"selPlid", String.valueOf(selPlid));

			boolean privateLayout = ParamUtil.getBoolean(
				_httpServletRequest, "privateLayout");

			selectLayoutMasterLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));

			selectLayoutMasterLayoutURL.setParameter(
				"collectionPK",
				String.valueOf(_assetListEntry.getAssetListEntryId()));
			selectLayoutMasterLayoutURL.setParameter(
				"collectionType",
				InfoListItemSelectorReturnType.class.getName());

			data.put(
				"data-select-layout-master-layout-url",
				selectLayoutMasterLayoutURL.toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		data.put("role", "button");
		data.put("tabIndex", "0");

		return data;
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getImageSrc() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		String subtitle = ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), _assetListEntry.getAssetEntryType());

		if (Validator.isNull(_assetListEntry.getAssetEntrySubtype())) {
			return subtitle;
		}

		String subtypeLabel = _getAssetEntrySubtypeSubtypeLabel();

		if (Validator.isNull(subtypeLabel)) {
			return subtitle;
		}

		return subtitle + " - " + subtypeLabel;
	}

	@Override
	public String getTitle() {
		try {
			return _assetListEntry.getUnambiguousTitle(
				themeDisplay.getLocale());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public Boolean isFlushHorizontal() {
		return true;
	}

	private String _getAssetEntrySubtypeSubtypeLabel() {
		long classTypeId = GetterUtil.getLong(
			_assetListEntry.getAssetEntrySubtype(), -1);

		if (classTypeId < 0) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_assetListEntry.getAssetEntryType());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, themeDisplay.getLocale());

			return classType.getName();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionsVerticalCard.class);

	private final AssetListEntry _assetListEntry;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}