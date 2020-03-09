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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.servlet.taglib.util.BasicFragmentCompositionActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class BasicFragmentCompositionVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public BasicFragmentCompositionVerticalCard(
		FragmentComposition fragmentComposition, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(fragmentComposition, rowChecker);

		_fragmentComposition = fragmentComposition;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		BasicFragmentCompositionActionDropdownItemsProvider
			basicFragmentCompositionActionDropdownItemsProvider =
				new BasicFragmentCompositionActionDropdownItemsProvider(
					_fragmentComposition, _renderRequest, _renderResponse);

		return basicFragmentCompositionActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentWebKeys.
			FRAGMENT_COMPOSITION_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getIcon() {
		return "code";
	}

	@Override
	public String getImageSrc() {
		return _fragmentComposition.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getInputName() {
		return rowChecker.getRowIds() +
			FragmentComposition.class.getSimpleName();
	}

	@Override
	public String getInputValue() {
		return String.valueOf(_fragmentComposition.getFragmentCompositionId());
	}

	@Override
	public List<LabelItem> getLabels() {
		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(_fragmentComposition.getStatus())
		).build();
	}

	@Override
	public String getSubtitle() {
		Date statusDate = _fragmentComposition.getStatusDate();

		String statusDateDescription = LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - statusDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "x-ago", statusDateDescription);
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_fragmentComposition.getName());
	}

	private final FragmentComposition _fragmentComposition;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}