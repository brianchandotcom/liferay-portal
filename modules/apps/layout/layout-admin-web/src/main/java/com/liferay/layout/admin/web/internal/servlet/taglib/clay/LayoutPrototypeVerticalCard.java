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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPrototypeVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public LayoutPrototypeVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_layoutPrototype = (LayoutPrototype)baseModel;
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public String getHref() {
		if (_layoutPrototype == null) {
			return null;
		}

		try {
			Group layoutPrototypeGroup = _layoutPrototype.getGroup();

			return layoutPrototypeGroup.getDisplayURL(_themeDisplay, true);
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "page-template";
	}

	@Override
	public List<LabelItem> getLabels() {
		return new LabelItemList() {
			{
				add(
					labelItem -> {
						String label = "not-active";

						if (_layoutPrototype.isActive()) {
							label = "active";
						}

						labelItem.setLabel(LanguageUtil.get(_request, label));

						String style = "warning";

						if (_layoutPrototype.isActive()) {
							style = "success";
						}

						labelItem.setStyle(style);
					});
			}
		};
	}

	@Override
	public String getSubtitle() {
		Date createDate = _layoutPrototype.getModifiedDate();

		String createdDateDescription = LanguageUtil.getTimeDescription(
			_request, System.currentTimeMillis() - createDate.getTime(), true);

		return LanguageUtil.format(
			_request, "created-x-ago", createdDateDescription);
	}

	@Override
	public String getTitle() {
		if (_layoutPrototype != null) {
			return _layoutPrototype.getName(_themeDisplay.getLocale());
		}

		return null;
	}

	private final LayoutPrototype _layoutPrototype;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}