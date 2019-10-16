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
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.servlet.taglib.util.DisplayPageActionDropdownItemsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

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

		_infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)renderRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return LayoutAdminWebKeys.DISPLAY_PAGE_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		try {
			if (Objects.equals(
					_layoutPageTemplateEntry.getType(),
					LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

				LayoutPrototype layoutPrototype =
					LayoutPrototypeLocalServiceUtil.fetchLayoutPrototype(
						_layoutPageTemplateEntry.getLayoutPrototypeId());

				if (layoutPrototype == null) {
					return null;
				}

				Group layoutPrototypeGroup = layoutPrototype.getGroup();

				String layoutFullURL = layoutPrototypeGroup.getDisplayURL(
					_themeDisplay, true);

				return HttpUtil.setParameter(
					layoutFullURL, "p_l_back_url",
					_themeDisplay.getURLCurrent());
			}

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				_layoutPageTemplateEntry.getPlid());

			Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
				PortalUtil.getClassNameId(Layout.class), layout.getPlid());

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				draftLayout, _themeDisplay);

			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
		}
		catch (Exception e) {
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
		return new LabelItemList() {
			{
				add(
					labelItem -> labelItem.setStatus(
						_layoutPageTemplateEntry.getStatus()));
			}
		};
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
		catch (Exception e) {
		}

		if (Validator.isNull(subtypeLabel)) {
			return typeLabel;
		}

		return typeLabel + " - " + subtypeLabel;
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutPageTemplateEntry.getName());
	}

	private String _getSubtypeLabel() throws PortalException {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_layoutPageTemplateEntry.getClassName());

		if ((assetRendererFactory == null) ||
			(_layoutPageTemplateEntry.getClassTypeId() <= 0)) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			_layoutPageTemplateEntry.getClassTypeId(),
			_themeDisplay.getLocale());

		return classType.getName();
	}

	private String _getTypeLabel() {
		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				_layoutPageTemplateEntry.getClassName());

		if (infoDisplayContributor == null) {
			return StringPool.BLANK;
		}

		return infoDisplayContributor.getLabel(_themeDisplay.getLocale());
	}

	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}