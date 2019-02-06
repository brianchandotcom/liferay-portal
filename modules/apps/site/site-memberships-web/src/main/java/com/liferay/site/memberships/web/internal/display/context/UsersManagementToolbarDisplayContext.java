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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public UsersManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request, UsersDisplayContext usersDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			usersDisplayContext.getUserSearchContainer());

		_request = request;
		_usersDisplayContext = usersDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedUsers");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});

				try {
					if (GroupPermissionUtil.contains(
							themeDisplay.getPermissionChecker(),
							themeDisplay.getScopeGroupId(),
							ActionKeys.ASSIGN_USER_ROLES)) {

						add(
							SafeConsumer.ignore(
								dropdownItem -> {
									dropdownItem.putData(
										"action", "selectSiteRole");

									PortletURL editUsersSiteRolesURL =
										liferayPortletResponse.
											createActionURL();

									editUsersSiteRolesURL.setParameter(
										ActionRequest.ACTION_NAME,
										"editUsersSiteRoles");

									dropdownItem.putData(
										"editUsersSiteRolesURL",
										editUsersSiteRolesURL.toString());

									dropdownItem.putData(
										"selectSiteRoleURL",
										_getSelectorURL("/site_roles.jsp"));
									dropdownItem.setIcon("add-role");
									dropdownItem.setLabel(
										LanguageUtil.get(
											_request, "assign-site-roles"));
									dropdownItem.setQuickAction(true);
								}
							)
						);

						Role role = _usersDisplayContext.getRole();

						if (role != null) {
							String label = LanguageUtil.format(
								_request, "remove-site-role-x",
								role.getTitle(themeDisplay.getLocale()), false);

							add(
								dropdownItem -> {
									dropdownItem.putData(
										"action", "removeUserSiteRole");
									dropdownItem.putData(
										"message",
										LanguageUtil.format(
											_request,
											"are-you-sure-you-want-to-remove-" +
												"x-role-to-selected-users",
											role.getTitle(
												themeDisplay.getLocale())));

									PortletURL removeUserSiteRoleURL =
										liferayPortletResponse.
											createActionURL();

									removeUserSiteRoleURL.setParameter(
										ActionRequest.ACTION_NAME,
										"removeUserSiteRole");

									dropdownItem.putData(
										"removeUserSiteRoleURL",
										removeUserSiteRoleURL.toString());

									dropdownItem.setIcon("remove-role");
									dropdownItem.setLabel(label);
									dropdownItem.setQuickAction(true);
								});
						}
					}
				}
				catch (Exception e) {
				}
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		Map<String, Object> componentContext = new HashMap<>();

		PortletURL selectUsersURL = liferayPortletResponse.createRenderURL();

		selectUsersURL.setParameter("mvcPath", "/select_users.jsp");
		selectUsersURL.setWindowState(LiferayWindowState.POP_UP);

		componentContext.put("selectUsersURL", selectUsersURL.toString());

		return componentContext;
	}

	@Override
	public String getComponentId() {
		return "usersManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "usersManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "users";
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(_request));

		return new ViewTypeItemList(
			portletURL, _usersDisplayContext.getDisplayStyle()) {

			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}

		};
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					_usersDisplayContext.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public Boolean isShowInfoButton() {
		return true;
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "all"));
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all", "roleId",
							"0");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData("action", "selectRoles");
							dropdownItem.putData(
								"selectRolesURL",
								_getSelectorURL("/select_site_role.jsp"));

							PortletURL viewRoleURL =
								liferayPortletResponse.createRenderURL();

							viewRoleURL.setParameter("mvcPath", "/view.jsp");
							viewRoleURL.setParameter("tabs1", "users");
							viewRoleURL.setParameter("navigation", "roles");
							viewRoleURL.setParameter(
								"redirect", themeDisplay.getURLCurrent());
							viewRoleURL.setParameter(
								"groupId",
								String.valueOf(
									_usersDisplayContext.getGroupId()));

							dropdownItem.putData(
								"viewRoleURL", viewRoleURL.toString());

							dropdownItem.setActive(
								Objects.equals(getNavigation(), "roles"));

							String label = LanguageUtil.get(_request, "roles");

							Role role = _usersDisplayContext.getRole();

							if (Objects.equals(getNavigation(), "roles") &&
								(role != null)) {

								label +=
									StringPool.COLON + StringPool.SPACE +
										HtmlUtil.escape(
											role.getTitle(
												themeDisplay.getLocale()));
							}

							dropdownItem.setLabel(label);
						}
					)
				);
			}
		};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"first-name", "screen-name"};
	}

	private String _getSelectorURL(String mvcPath) throws Exception {
		PortletURL selectURL = liferayPortletResponse.createRenderURL();

		selectURL.setParameter("mvcPath", mvcPath);
		selectURL.setParameter(
			"groupId", String.valueOf(_usersDisplayContext.getGroupId()));
		selectURL.setWindowState(LiferayWindowState.POP_UP);

		return selectURL.toString();
	}

	private final HttpServletRequest _request;
	private final UsersDisplayContext _usersDisplayContext;

}