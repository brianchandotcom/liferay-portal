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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.web.internal.configuration.FFBulkTranslationConfiguration;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.url.provider.TranslationURLProvider;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public LayoutsAdminManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			LayoutsAdminDisplayContext layoutsAdminDisplayContext)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			layoutsAdminDisplayContext.getLayoutsSearchContainer());

		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;

		_ffBulkTranslationConfiguration =
			(FFBulkTranslationConfiguration)httpServletRequest.getAttribute(
				FFBulkTranslationConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_translationURLProvider =
			(TranslationURLProvider)httpServletRequest.getAttribute(
				TranslationURLProvider.class.getName());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "convertSelectedPages");

				dropdownItem.putData(
					"convertLayoutURL",
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/layout_admin/convert_layout"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).buildString());

				dropdownItem.setIcon("change");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "convert-to-content-page"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			_ffBulkTranslationConfiguration::bulkTranslationEnabled,
			dropdownItem -> {
				dropdownItem.putData("action", "exportTranslation");
				dropdownItem.putData(
					"exportTranslationURL",
					PortletURLBuilder.create(
						_translationURLProvider.getExportTranslationURL(
							_themeDisplay.getScopeGroupId(),
							PortalUtil.getClassNameId(Layout.class),
							RequestBackedPortletURLFactoryUtil.create(
								httpServletRequest))
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).buildString());
				dropdownItem.setDisabled(false);
				dropdownItem.setIcon("import-export");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "export-for-translation"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSelectedPages");

				dropdownItem.putData(
					"deleteLayoutURL",
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/layout_admin/delete_layout"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).buildString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "pagesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		long firstLayoutPageTemplateCollectionId =
			_layoutsAdminDisplayContext.
				getFirstLayoutPageTemplateCollectionId();
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();
		long selPlid = _layoutsAdminDisplayContext.getSelPlid();

		return CreationMenuBuilder.addPrimaryDropdownItem(
			() ->
				_layoutsAdminDisplayContext.isShowPublicLayouts() &&
				_layoutsAdminDisplayContext.isShowAddChildPageAction(
					selLayout) &&
				(!_layoutsAdminDisplayContext.isPrivateLayout() ||
				 _layoutsAdminDisplayContext.isFirstColumn() ||
				 !_layoutsAdminDisplayContext.hasLayouts()),
			dropdownItem -> {
				dropdownItem.setHref(
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(
							firstLayoutPageTemplateCollectionId, selPlid,
							false));
				dropdownItem.setLabel(_getLabel(false));
			}
		).addPrimaryDropdownItem(
			() ->
				_layoutsAdminDisplayContext.isShowPublicLayouts() &&
				_layoutsAdminDisplayContext.isShowAddChildPageAction(
					selLayout) &&
				(!_layoutsAdminDisplayContext.isPrivateLayout() ||
				 _layoutsAdminDisplayContext.isFirstColumn() ||
				 !_layoutsAdminDisplayContext.hasLayouts()),
			dropdownItem -> {
				dropdownItem.setHref(
					_layoutsAdminDisplayContext.getSelectLayoutCollectionURL(
						selPlid, null, false));
				dropdownItem.setLabel(_getCollectionLayoutLabel(false));
			}
		).addPrimaryDropdownItem(
			() ->
				_layoutsAdminDisplayContext.isShowUserPrivateLayouts() &&
				((_layoutsAdminDisplayContext.isShowAddChildPageAction(
					selLayout) &&
				  _layoutsAdminDisplayContext.isPrivateLayout()) ||
				 _layoutsAdminDisplayContext.isFirstColumn() ||
				 !_layoutsAdminDisplayContext.hasLayouts()),
			dropdownItem -> {
				dropdownItem.setHref(
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(
							firstLayoutPageTemplateCollectionId, selPlid,
							true));
				dropdownItem.setLabel(_getLabel(true));
			}
		).addPrimaryDropdownItem(
			() ->
				_layoutsAdminDisplayContext.isShowUserPrivateLayouts() &&
				(_layoutsAdminDisplayContext.isPrivateLayout() ||
				 _layoutsAdminDisplayContext.isFirstColumn() ||
				 !_layoutsAdminDisplayContext.hasLayouts()),
			dropdownItem -> {
				dropdownItem.setHref(
					_layoutsAdminDisplayContext.getSelectLayoutCollectionURL(
						selPlid, null, true));
				dropdownItem.setLabel(_getCollectionLayoutLabel(true));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setParameter(
			"privateLayout", _layoutsAdminDisplayContext.isPrivateLayout()
		).buildString();
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
		if (_layoutsAdminDisplayContext.isFirstColumn() ||
			Objects.equals(getOrderByCol(), "relevance")) {

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
			CreationMenu creationMenu = getCreationMenu();

			if (creationMenu.isEmpty()) {
				return false;
			}

			return _layoutsAdminDisplayContext.isShowAddRootLayoutButton();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
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
			return new String[] {"create-date", "relevance"};
		}

		return null;
	}

	private String _getCollectionLayoutLabel(boolean privateLayout) {
		Layout layout = _layoutsAdminDisplayContext.getSelLayout();

		if (layout != null) {
			return LanguageUtil.format(
				httpServletRequest, "add-child-collection-page-of-x",
				HtmlUtil.escape(layout.getName(_themeDisplay.getLocale())));
		}

		if (_isSiteTemplate()) {
			return LanguageUtil.get(
				httpServletRequest, "add-site-template-collection-page");
		}

		if (privateLayout) {
			return LanguageUtil.get(
				httpServletRequest, "private-collection-page");
		}

		if (_layoutsAdminDisplayContext.isPrivateLayoutsEnabled()) {
			return LanguageUtil.get(
				httpServletRequest, "public-collection-page");
		}

		return LanguageUtil.get(httpServletRequest, "collection-page");
	}

	private String _getLabel(boolean privateLayout) {
		Layout layout = _layoutsAdminDisplayContext.getSelLayout();

		if (layout != null) {
			return LanguageUtil.format(
				httpServletRequest, "add-child-page-of-x",
				HtmlUtil.escape(layout.getName(_themeDisplay.getLocale())));
		}

		if (_isSiteTemplate()) {
			return LanguageUtil.get(
				httpServletRequest, "add-site-template-page");
		}

		if (privateLayout) {
			return LanguageUtil.get(httpServletRequest, "private-page");
		}

		if (_layoutsAdminDisplayContext.isPrivateLayoutsEnabled()) {
			return LanguageUtil.get(httpServletRequest, "public-page");
		}

		return LanguageUtil.get(httpServletRequest, "page");
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

	private final FFBulkTranslationConfiguration
		_ffBulkTranslationConfiguration;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;
	private final TranslationURLProvider _translationURLProvider;

}