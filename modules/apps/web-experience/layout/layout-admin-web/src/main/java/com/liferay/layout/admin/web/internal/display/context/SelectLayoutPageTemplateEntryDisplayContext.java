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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class SelectLayoutPageTemplateEntryDisplayContext {

	public SelectLayoutPageTemplateEntryDisplayContext(
		LayoutsAdminDisplayContext layoutsAdminDisplayContext,
		HttpServletRequest request) {

		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;

		_request = request;

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getLayoutPageTemplateCollectionId() {
		if (_layoutPageTemplateCollectionId != null) {
			return _layoutPageTemplateCollectionId;
		}

		_layoutPageTemplateCollectionId = ParamUtil.getLong(
			_request, "layoutPageTemplateCollectionId");

		return _layoutPageTemplateCollectionId;
	}

	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			int start, int end)
		throws PortalException {

		return LayoutPageTemplateEntryLocalServiceUtil.
			getLayoutPageTemplateEntries(
				_themeDisplay.getScopeGroupId(),
				getLayoutPageTemplateCollectionId(), start, end);
	}

	public int getLayoutPageTemplateEntriesCount() {
		return LayoutPageTemplateEntryServiceUtil.
			getLayoutPageTemplateEntriesCount(
				_themeDisplay.getScopeGroupId(),
				getLayoutPageTemplateCollectionId());
	}

	public List<LayoutPrototype> getLayoutPrototypes() throws PortalException {
		if (_layoutPrototypes != null) {
			return _layoutPrototypes;
		}

		_layoutPrototypes = LayoutPrototypeServiceUtil.search(
			_themeDisplay.getCompanyId(), Boolean.TRUE, null);

		return _layoutPrototypes;
	}

	public int getLayoutPrototypesCount() throws PortalException {
		List<LayoutPrototype> layoutPrototypes = getLayoutPrototypes();

		return layoutPrototypes.size();
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem navigationItem = new NavigationItem();

		navigationItem.setActive(getLayoutPageTemplateCollectionId() == 0);
		navigationItem.setHref(
			_layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(
				0, _layoutsAdminDisplayContext.getSelPlid()));
		navigationItem.setLabel(LanguageUtil.get(_request, "basic-pages"));

		navigationItems.add(navigationItem);

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollections(
					_themeDisplay.getScopeGroupId(),
					LayoutPageTemplateCollectionTypeConstants.TYPE_BASIC);

		for (LayoutPageTemplateCollection layoutPageTemplateCollection :
				layoutPageTemplateCollections) {

			String selectLayoutPageTemplateEntryURL =
				_layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					_layoutsAdminDisplayContext.getSelPlid());

			navigationItem = new NavigationItem();

			navigationItem.setActive(
				getLayoutPageTemplateCollectionId() ==
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId());
			navigationItem.setHref(selectLayoutPageTemplateEntryURL);
			navigationItem.setLabel(layoutPageTemplateCollection.getName());

			navigationItems.add(navigationItem);
		}

		return navigationItems;
	}

	public List<String> getTypes() {
		if (_types != null) {
			return _types;
		}

		_types = ListUtil.filter(
			ListUtil.fromArray(LayoutTypeControllerTracker.getTypes()),
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String type) {
					LayoutTypeController layoutTypeController =
						LayoutTypeControllerTracker.getLayoutTypeController(
							type);

					return layoutTypeController.isInstanceable();
				}

			});

		return _types;
	}

	public int getTypesCount() {
		List<String> types = getTypes();

		return types.size();
	}

	public boolean isBasicPages() {
		if (getLayoutPageTemplateCollectionId() == 0) {
			return true;
		}

		return false;
	}

	private Long _layoutPageTemplateCollectionId;
	private List<LayoutPrototype> _layoutPrototypes;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;
	private List<String> _types;

}