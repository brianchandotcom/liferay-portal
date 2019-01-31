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

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class DisplayPageVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public DisplayPageVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_assetDisplayContributorTracker =
			(AssetDisplayContributorTracker)renderRequest.getAttribute(
				LayoutAdminWebKeys.ASSET_DISPLAY_CONTRIBUTOR_TRACKER);
		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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

				return layoutPrototypeGroup.getDisplayURL(_themeDisplay, true);
			}

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				_layoutPageTemplateEntry.getPlid());

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				layout, _themeDisplay);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);
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
	public String getStickerIcon() {
		if (_layoutPageTemplateEntry.getDefaultTemplate()) {
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
		return _layoutPageTemplateEntry.getName();
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
		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				_layoutPageTemplateEntry.getClassName());

		if (assetDisplayContributor == null) {
			return StringPool.BLANK;
		}

		return assetDisplayContributor.getLabel(_themeDisplay.getLocale());
	}

	private final AssetDisplayContributorTracker
		_assetDisplayContributorTracker;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final ThemeDisplay _themeDisplay;

}