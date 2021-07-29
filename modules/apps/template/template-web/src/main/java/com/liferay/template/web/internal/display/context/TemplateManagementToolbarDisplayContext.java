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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;

import java.util.ArrayList;
import java.util.List;

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
		if (!_templateDisplayContext.isAddDDMTemplateEnabled()) {
			return null;
		}

		List<Long> addAllowedClassNameIds = _getAddAllowedClassNameIds();

		if (addAllowedClassNameIds.isEmpty()) {
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
		).buildPortletURL();

		for (long addAllowedClassNameId : addAllowedClassNameIds) {
			addDDMTemplateURL.setParameter(
				"classNameId", String.valueOf(addAllowedClassNameId));
			addDDMTemplateURL.setParameter("classPK", "0");
			addDDMTemplateURL.setParameter(
				"resourceClassNameId",
				String.valueOf(
					_templateDisplayContext.getResourceClassNameId()));

			creationMenu.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(addDDMTemplateURL);
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest,
							_templateDisplayContext.getTemplateTypeLabel(
								addAllowedClassNameId)));
				});
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
		long classNameId) {

		try {
			return PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLayout(),
				_templateDisplayContext.getResourceName(classNameId),
				_templateDisplayContext.getAddPermissionActionId(), false,
				false);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to check permission for resource name " +
						_templateDisplayContext.getResourceName(classNameId),
					portalException);
			}
		}

		return false;
	}

	private List<Long> _getAddAllowedClassNameIds() {
		List<Long> addAllowedClassNameIds = new ArrayList<>();

		for (long classNameId : _templateDisplayContext.getClassNameIds()) {
			if (_containsAddPortletDisplayTemplatePermission(classNameId)) {
				addAllowedClassNameIds.add(classNameId);
			}
		}

		return addAllowedClassNameIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagementToolbarDisplayContext.class);

	private final TemplateDisplayContext _templateDisplayContext;
	private final ThemeDisplay _themeDisplay;

}