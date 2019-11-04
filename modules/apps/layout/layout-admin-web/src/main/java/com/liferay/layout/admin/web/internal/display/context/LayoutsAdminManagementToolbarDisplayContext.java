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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
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
			HttpServletRequest httpServletRequest,
			LayoutsAdminDisplayContext layoutsAdminDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			layoutsAdminDisplayContext.getLayoutsSearchContainer());

		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		PortletURL convertLayoutURL = liferayPortletResponse.createActionURL();

		convertLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/convert_layout");
		convertLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		PortletURL deleteLayoutURL = liferayPortletResponse.createActionURL();

		deleteLayoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/delete_layout");
		deleteLayoutURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "convertSelectedPages");
						dropdownItem.putData(
							"convertLayoutURL", convertLayoutURL.toString());
						dropdownItem.setIcon("change");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "convertToContentPage"));
						dropdownItem.setQuickAction(true);
					});
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedPages");
						dropdownItem.putData(
							"deleteLayoutURL", deleteLayoutURL.toString());
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
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getSelectLayoutPageTemplateEntryURL(
										firstLayoutPageTemplateCollectionId,
										selPlid, false));
							dropdownItem.setLabel(_getLabel(false));
						});
				}

				if (_layoutsAdminDisplayContext.isPrivateLayout() ||
					_layoutsAdminDisplayContext.isFirstColumn() ||
					!_layoutsAdminDisplayContext.hasLayouts()) {

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								_layoutsAdminDisplayContext.
									getSelectLayoutPageTemplateEntryURL(
										firstLayoutPageTemplateCollectionId,
										selPlid, true));
							dropdownItem.setLabel(_getLabel(true));
						});
				}
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "LAYOUTS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"privateLayout",
			String.valueOf(_layoutsAdminDisplayContext.isPrivateLayout()));

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

		if (_layoutsAdminDisplayContext.isSearch()) {
			return super.getSortingOrder();
		}

		return null;
	}

	@Override
	public Boolean isDisabled() {
		if (Objects.equals(
				_layoutsAdminDisplayContext.getDisplayStyle(),
				"miller-columns")) {

			return false;
		}

		return super.isDisabled();
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
			return null;
		}

		if (_layoutsAdminDisplayContext.isSearch()) {
			return new String[] {"create-date"};
		}

		return null;
	}

	private String _getLabel(boolean privateLayout) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = _layoutsAdminDisplayContext.getSelLayout();

		if (layout != null) {
			return LanguageUtil.format(
				request, "add-child-page-of-x",
				layout.getName(themeDisplay.getLocale()));
		}

		if (_isSiteTemplate()) {
			return LanguageUtil.get(request, "add-site-template-page");
		}

		if (privateLayout) {
			return LanguageUtil.get(request, "private-page");
		}

		return LanguageUtil.get(request, "public-page");
	}

	private boolean _isSiteTemplate() {
		Group group = _layoutsAdminDisplayContext.getGroup();

		if (group == null) {
			return false;
		}

		long layoutSetPrototypeClassNameId =
			ClassNameLocalServiceUtil.getClassNameId(LayoutSetPrototype.class);

		if (layoutSetPrototypeClassNameId == group.getClassNameId()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsAdminManagementToolbarDisplayContext.class);

	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;

}