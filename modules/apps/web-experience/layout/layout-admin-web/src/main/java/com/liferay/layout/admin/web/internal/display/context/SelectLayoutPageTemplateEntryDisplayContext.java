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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
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

import java.util.List;
import java.util.Objects;

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
		return new NavigationItemList() {
			{
				List<LayoutPageTemplateCollection>
					layoutPageTemplateCollections =
						LayoutPageTemplateCollectionServiceUtil.
							getLayoutPageTemplateCollections(
								_themeDisplay.getScopeGroupId(),
								LayoutPageTemplateCollectionTypeConstants.
									TYPE_BASIC);

				for (LayoutPageTemplateCollection layoutPageTemplateCollection :
						layoutPageTemplateCollections) {

					long layoutPageTemplateCollectionId =
						layoutPageTemplateCollection.
							getLayoutPageTemplateCollectionId();

					add(
						navigationItem -> {
							navigationItem.setActive(
								getLayoutPageTemplateCollectionId() ==
									layoutPageTemplateCollectionId);
							navigationItem.setHref(
								_layoutsAdminDisplayContext.
									getSelectLayoutPageTemplateEntryURL(
										layoutPageTemplateCollectionId));
							navigationItem.setLabel(
								layoutPageTemplateCollection.getName());
						});
				}

				String basicPagesURL =
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(
							0, _layoutsAdminDisplayContext.getSelPlid(),
							"basic-pages");

				add(
					navigationItem -> {
						navigationItem.setActive(isBasicPages());
						navigationItem.setHref(basicPagesURL);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "basic-pages"));
					});

				String globalTemplatesURL =
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(
							0, _layoutsAdminDisplayContext.getSelPlid(),
							"global-templates");

				add(
					navigationItem -> {
						navigationItem.setActive(isGlobalTemplates());
						navigationItem.setHref(globalTemplatesURL);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "global-templates"));
					});
			}
		};
	}

	public String getSelectedTab() {
		if (_selectedTab != null) {
			return _selectedTab;
		}

		_selectedTab = ParamUtil.getString(
			_request, "selectedTab", "basic-pages");

		return _selectedTab;
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
		if (getLayoutPageTemplateCollectionId() != 0) {
			return false;
		}

		if (!Objects.equals(getSelectedTab(), "basic-pages")) {
			return false;
		}

		return true;
	}

	public boolean isContentPages() {
		if (getLayoutPageTemplateCollectionId() != 0) {
			return true;
		}

		return false;
	}

	public boolean isGlobalTemplates() {
		if (getLayoutPageTemplateCollectionId() != 0) {
			return false;
		}

		if (!Objects.equals(getSelectedTab(), "global-templates")) {
			return false;
		}

		return true;
	}

	private Long _layoutPageTemplateCollectionId;
	private List<LayoutPrototype> _layoutPrototypes;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final HttpServletRequest _request;
	private String _selectedTab;
	private final ThemeDisplay _themeDisplay;
	private List<String> _types;

}