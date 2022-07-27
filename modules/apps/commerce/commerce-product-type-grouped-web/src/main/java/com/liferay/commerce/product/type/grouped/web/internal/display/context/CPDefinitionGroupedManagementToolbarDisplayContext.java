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

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CPDefinitionGroupedManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public CPDefinitionGroupedManagementToolbarDisplayContext(
			CPDefinitionGroupedEntriesDisplayContext
				cpDefinitionGroupedEntriesDisplayContext,
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			cpDefinitionGroupedEntriesDisplayContext.getSearchContainer());

		_cpDefinitionGroupedEntriesDisplayContext =
			cpDefinitionGroupedEntriesDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getComponentId() {
		return "cpDefinitionGroupedEntriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			CPDefinition cpDefinition =
				_cpDefinitionGroupedEntriesDisplayContext.getCPDefinition();

			return CreationMenuBuilder.addDropdownItem(
				dropdownItem -> {
					dropdownItem.putData("action", "addDefinitionGroupedEntry");
					dropdownItem.putData(
						"addDefinitionGroupedEntryItemSelectorURL",
						_cpDefinitionGroupedEntriesDisplayContext.
							getItemSelectorUrl());
					dropdownItem.putData(
						"dialogTitle",
						LanguageUtil.format(
							httpServletRequest, "add-new-grouped-entry-to-x",
							cpDefinition.getName(
								themeDisplay.getLanguageId())));
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest, "add-grouped-entry"));
				}
			).build();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	@Override
	public String getInfoPanelId() {
		if (_cpDefinitionGroupedEntriesDisplayContext.isShowInfoPanel()) {
			return "infoPanelId";
		}

		return null;
	}

	@Override
	public String getSearchContainerId() {
		return "cpDefinitionGroupedEntries";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"priority", "quantity"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedManagementToolbarDisplayContext.class);

	private final CPDefinitionGroupedEntriesDisplayContext
		_cpDefinitionGroupedEntriesDisplayContext;

}