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

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util.DisplayPageActionDropdownItemsProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class DisplayPageVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public DisplayPageVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_infoItemServiceTracker =
			(InfoItemServiceTracker)renderRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_SERVICE_TRACKER);
		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			_layoutPageTemplateEntry.getPlid());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		try {
			DisplayPageActionDropdownItemsProvider
				displayPageActionDropdownItemsProvider =
					new DisplayPageActionDropdownItemsProvider(
						_layoutPageTemplateEntry, _renderRequest,
						_renderResponse);

			return displayPageActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getHref() {
		try {
			String layoutFullURL = PortalUtil.getLayoutFullURL(
				_draftLayout, _themeDisplay);

			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public List<LabelItem> getLabels() {
		if (_draftLayout == null) {
			return Collections.emptyList();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(_draftLayout.getStatus())
		).build();
	}

	@Override
	public String getStickerIcon() {
		if (_layoutPageTemplateEntry.isDefaultTemplate()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getStickerStyle() {
		return "primary";
	}

	@Override
	public String getSubtitle() {
		String typeLabel = _getTypeLabel();

		if (Validator.isNull(typeLabel)) {
			return StringPool.DASH;
		}

		String subtypeLabel = StringPool.BLANK;

		try {
			subtypeLabel = _getSubtypeLabel();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		if (Validator.isNull(subtypeLabel)) {
			return typeLabel;
		}

		return typeLabel + " - " + subtypeLabel;
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	private String _getSubtypeLabel() {
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				_layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				_layoutPageTemplateEntry.getGroupId(),
				String.valueOf(_layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	private String _getTypeLabel() {
		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				_layoutPageTemplateEntry.getClassName());

		if (infoItemDetailsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		return infoItemClassDetails.getLabel(_themeDisplay.getLocale());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageVerticalCard.class);

	private final Layout _draftLayout;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}