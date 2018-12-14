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

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public LayoutsAdminManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request,
			LayoutsAdminDisplayContext layoutsAdminDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			layoutsAdminDisplayContext.getLayoutsSearchContainer());

		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedPages");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "pagesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				long firstLayoutPageTemplateCollectionId =
					_layoutsAdminDisplayContext.
						getFirstLayoutPageTemplateCollectionId();
				long selPlid = _layoutsAdminDisplayContext.getSelPlid();

				if (_layoutsAdminDisplayContext.isShowPublicPages() &&
					(!_layoutsAdminDisplayContext.isPrivateLayout() ||
					 _layoutsAdminDisplayContext.isFirstColumn() ||
					 !_layoutsAdminDisplayContext.hasLayouts())) {

					addPrimaryDropdownItem(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									_layoutsAdminDisplayContext.
										getSelectLayoutPageTemplateEntryURL(
											firstLayoutPageTemplateCollectionId,
											selPlid, false));
								dropdownItem.setLabel(_getLabel(false));
							}));
				}

				if (_layoutsAdminDisplayContext.isPrivateLayout() ||
					_layoutsAdminDisplayContext.isFirstColumn() ||
					!_layoutsAdminDisplayContext.hasLayouts()) {

					addPrimaryDropdownItem(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									_layoutsAdminDisplayContext.
										getSelectLayoutPageTemplateEntryURL(
											firstLayoutPageTemplateCollectionId,
											selPlid, true));
								dropdownItem.setLabel(_getLabel(true));
							}));
				}
			}
		};
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return portletURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "pages";
	}

	@Override
	public String getSearchFormName() {
		return "fm";
	}

	@Override
	public String getSortingOrder() {
		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return null;
		}

		return super.getSortingOrder();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(
			getPortletURL(), _layoutsAdminDisplayContext.getDisplayStyle()) {

			{
				addTableViewTypeItem();

				ViewTypeItem viewTypeItem = new ViewTypeItem();

				viewTypeItem.setActive(
					Objects.equals(
						_layoutsAdminDisplayContext.getDisplayStyle(),
						"miller-columns"));
				viewTypeItem.setIcon("columns");
				viewTypeItem.setHref(
					getPortletURL(), "displayStyle", "miller-columns");
				viewTypeItem.setLabel(
					LanguageUtil.get(
						LocaleUtil.getMostRelevantLocale(), "columns"));

				add(viewTypeItem);
			}

		};
	}

	@Override
	public Boolean isSelectable() {
		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return false;
		}

		return super.isSelectable();
	}

	@Override
	public Boolean isShowCreationMenu() {
		try {
			return _layoutsAdminDisplayContext.isShowAddRootLayoutButton();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return super.getOrderByKeys();
		}

		if (_layoutsAdminDisplayContext.isSearch()) {
			return new String[] {"create-date"};
		}

		if (_layoutsAdminDisplayContext.isFlattenedView()) {
			return new String[] {"create-date", "path"};
		}

		return super.getOrderByKeys();
	}

	private String _getLabel(boolean privateLayout) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_layoutsAdminDisplayContext.getSelPlid() > 0) {
			Layout layout = _layoutsAdminDisplayContext.getSelLayout();

			return LanguageUtil.format(
				request, "add-child-page-of-x",
				layout.getName(themeDisplay.getLocale()));
		}

		if (privateLayout) {
			return LanguageUtil.get(request, "private-page");
		}

		return LanguageUtil.get(request, "public-page");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsAdminManagementToolbarDisplayContext.class);

	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;

}