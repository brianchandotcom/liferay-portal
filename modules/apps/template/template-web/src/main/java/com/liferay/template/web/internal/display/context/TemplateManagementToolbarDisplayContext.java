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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public TemplateManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TemplateDisplayContext templateDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			templateDisplayContext.getTemplateSearchContainer());

		_templateDisplayContext = templateDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSelectedDDMTemplates");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAvailableActions(DDMTemplate ddmTemplate)
		throws PortalException {

		if (DDMTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), ddmTemplate,
				ActionKeys.DELETE)) {

			return "deleteSelectedDDMTemplates";
		}

		return StringPool.BLANK;
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
		return "templateManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!_isShowAddDDMTemplateButton()) {
			return null;
		}

		List<TemplateHandler> templateHandlers =
			_getPortletDisplayTemplateHandlers(_themeDisplay.getLocale());

		if (templateHandlers.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		PortletURL addDDMTemplateURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/edit_ddm_template.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"groupId", _themeDisplay.getScopeGroupId()
		).setParameter(
			"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY
		).build();

		for (TemplateHandler templateHandler : templateHandlers) {
			addDDMTemplateURL.setParameter(
				"classNameId",
				String.valueOf(
					PortalUtil.getClassNameId(templateHandler.getClassName())));
			addDDMTemplateURL.setParameter("classPK", "0");
			addDDMTemplateURL.setParameter(
				"resourceClassNameId",
				String.valueOf(
					PortalUtil.getClassNameId(PortletDisplayTemplate.class)));

			creationMenu.addPrimaryDropdownItem(
				_getCreationMenuDropdownItem(
					addDDMTemplateURL,
					templateHandler.getName(_themeDisplay.getLocale())));
		}

		return creationMenu;
	}

	@Override
	public String getDefaultEventHandler() {
		return "TEMPLATE_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "ddmTemplates";
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "id"};
	}

	private boolean _containsAddPortletDisplayTemplatePermission(
		String resourceName) {

		try {
			return PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLayout(),
				resourceName, ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, false,
				false);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to check permissions for resourceName: " + resourceName,
				portalException);
		}

		return false;
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCreationMenuDropdownItem(PortletURL url, String label) {

		return dropdownItem -> {
			dropdownItem.setHref(url);
			dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, label));
		};
	}

	private List<TemplateHandler> _getPortletDisplayTemplateHandlers(
		Locale locale) {

		List<TemplateHandler> templateHandlersList = new ArrayList<>();

		for (TemplateHandler templateHandler :
				TemplateHandlerRegistryUtil.getTemplateHandlers()) {

			if (_containsAddPortletDisplayTemplatePermission(
					templateHandler.getResourceName())) {

				templateHandlersList.add(templateHandler);
			}
		}

		ListUtil.sort(
			templateHandlersList, new TemplateHandlerComparator(locale));

		return templateHandlersList;
	}

	private boolean _isShowAddDDMTemplateButton() {
		if (!_templateDisplayContext.enableTemplateCreation()) {
			return false;
		}

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (!scopeGroup.hasLocalOrRemoteStagingGroup() ||
			!scopeGroup.isStagedPortlet(TemplatePortletKeys.TEMPLATE)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagementToolbarDisplayContext.class);

	private final TemplateDisplayContext _templateDisplayContext;
	private final ThemeDisplay _themeDisplay;

}