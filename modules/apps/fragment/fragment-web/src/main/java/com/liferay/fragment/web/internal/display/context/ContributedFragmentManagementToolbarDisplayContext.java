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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContributedFragmentManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ContributedFragmentManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			fragmentDisplayContext.
				getContributedFragmentEntriesSearchContainer());

		_fragmentDisplayContext = fragmentDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", "all");

		return clearResultsURL.toString();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				if (_fragmentDisplayContext.isNavigationSections()) {
					add(
						labelItem -> labelItem.setLabel(
							LanguageUtil.get(request, "sections")));
				}

				if (_fragmentDisplayContext.isNavigationComponents()) {
					add(
						labelItem -> labelItem.setLabel(
							LanguageUtil.get(request, "components")));
				}
			}
		};
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "sections", "components"};
	}

	private final FragmentDisplayContext _fragmentDisplayContext;

}