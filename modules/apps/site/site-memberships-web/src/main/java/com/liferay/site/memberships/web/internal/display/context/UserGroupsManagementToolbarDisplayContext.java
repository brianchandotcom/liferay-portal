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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
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
public class UserGroupsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public UserGroupsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request,
		UserGroupsDisplayContext userGroupsDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			userGroupsDisplayContext.getUserGroupSearchContainer());

		_request = request;
		_userGroupsDisplayContext = userGroupsDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedUserGroups");
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

									PortletURL editUserGroupsSiteRolesURL =
										liferayPortletResponse.
											createActionURL();

									editUserGroupsSiteRolesURL.setParameter(
										ActionRequest.ACTION_NAME,
										"editUserGroupsSiteRoles");
									editUserGroupsSiteRolesURL.setParameter(
										"tabs1", "user-groups");

									dropdownItem.putData(
										"editUserGroupsSiteRolesURL",
										editUserGroupsSiteRolesURL.toString());

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

						Role role = _userGroupsDisplayContext.getRole();

						if (role != null) {
							String label = LanguageUtil.format(
								_request, "remove-site-role-x",
								role.getTitle(themeDisplay.getLocale()), false);

							add(
								dropdownItem -> {
									dropdownItem.putData(
										"action", "removeUserGroupSiteRole");
									dropdownItem.putData(
										"message",
										LanguageUtil.format(
											_request,
											"are-you-sure-you-want-to-remove-" +
												"x-role-to-selected-user-" +
													"groups",
											role.getTitle(
												themeDisplay.getLocale())));

									PortletURL removeUserGroupSiteRoleURL =
										liferayPortletResponse.
											createActionURL();

									removeUserGroupSiteRoleURL.setParameter(
										ActionRequest.ACTION_NAME,
										"removeUserGroupSiteRole");

									dropdownItem.putData(
										"removeUserGroupSiteRoleURL",
										removeUserGroupSiteRoleURL.toString());

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

		clearResultsURL.setParameter("navigation", "all");
		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("roleId", "0");

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		Map<String, Object> componentContext = new HashMap<>();

		PortletURL selectUserGroupsURL =
			liferayPortletResponse.createRenderURL();

		selectUserGroupsURL.setParameter("mvcPath", "/select_user_groups.jsp");
		selectUserGroupsURL.setWindowState(LiferayWindowState.POP_UP);

		componentContext.put(
			"selectUserGroupsURL", selectUserGroupsURL.toString());

		return componentContext;
	}

	@Override
	public String getComponentId() {
		return "userGroupsManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "userGroupsManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Role role = _userGroupsDisplayContext.getRole();

		return new LabelItemList() {
			{
				if (role != null) {
					add(
						labelItem -> {
							labelItem.setLabel(
								role.getTitle(themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "userGroups";
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(_request));

		return new ViewTypeItemList(
			portletURL, _userGroupsDisplayContext.getDisplayStyle()) {

			{
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
					_userGroupsDisplayContext.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
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
							dropdownItem.setActive(
								Objects.equals(getNavigation(), "roles"));
							dropdownItem.putData("action", "selectRoles");
							dropdownItem.putData(
								"selectRolesURL",
								_getSelectorURL("/select_site_role.jsp"));

							PortletURL viewRoleURL =
								liferayPortletResponse.createRenderURL();

							viewRoleURL.setParameter("mvcPath", "/view.jsp");
							viewRoleURL.setParameter("tabs1", "user-groups");
							viewRoleURL.setParameter("navigation", "roles");
							viewRoleURL.setParameter(
								"redirect", themeDisplay.getURLCurrent());
							viewRoleURL.setParameter(
								"groupId",
								String.valueOf(
									_userGroupsDisplayContext.getGroupId()));

							dropdownItem.putData(
								"viewRoleURL", viewRoleURL.toString());

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "roles"));
						}
					)
				);
			}
		};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "description"};
	}

	private String _getSelectorURL(String mvcPath) throws Exception {
		PortletURL selectURL = liferayPortletResponse.createRenderURL();

		selectURL.setParameter("mvcPath", mvcPath);
		selectURL.setParameter(
			"groupId", String.valueOf(_userGroupsDisplayContext.getGroupId()));
		selectURL.setWindowState(LiferayWindowState.POP_UP);

		return selectURL.toString();
	}

	private final HttpServletRequest _request;
	private final UserGroupsDisplayContext _userGroupsDisplayContext;

}