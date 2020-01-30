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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.memberships.web.internal.util.GroupUtil;

import java.util.List;
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
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		UserGroupsDisplayContext userGroupsDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			userGroupsDisplayContext.getUserGroupSearchContainer());

		_userGroupsDisplayContext = userGroupsDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				try {
					if (GroupPermissionUtil.contains(
							themeDisplay.getPermissionChecker(),
							_userGroupsDisplayContext.getGroupId(),
							ActionKeys.ASSIGN_MEMBERS)) {

						add(
							dropdownItem -> {
								dropdownItem.putData(
									"action", "deleteSelectedUserGroups");
								dropdownItem.setIcon("times-circle");
								dropdownItem.setLabel(
									LanguageUtil.get(request, "delete"));
								dropdownItem.setQuickAction(true);
							});
					}
				}
				catch (Exception exception) {
				}

				try {
					if (GroupPermissionUtil.contains(
							themeDisplay.getPermissionChecker(),
							_userGroupsDisplayContext.getGroupId(),
							ActionKeys.ASSIGN_USER_ROLES)) {

						add(
							dropdownItem -> {
								dropdownItem.putData("action", "selectRole");

								PortletURL editUserGroupsRolesURL =
									liferayPortletResponse.createActionURL();

								editUserGroupsRolesURL.setParameter(
									ActionRequest.ACTION_NAME,
									"editUserGroupsRoles");
								editUserGroupsRolesURL.setParameter(
									"tabs1", "user-groups");

								dropdownItem.putData(
									"editUserGroupsRolesURL",
									editUserGroupsRolesURL.toString());

								dropdownItem.putData(
									"selectRoleURL",
									_getSelectorURL("/site_roles.jsp"));
								dropdownItem.setIcon("add-role");
								dropdownItem.setLabel(
									LanguageUtil.get(request, "assign-roles"));
								dropdownItem.setQuickAction(true);
							});

						Role role = _userGroupsDisplayContext.getRole();

						if (role != null) {
							String label = LanguageUtil.format(
								request, "remove-role-x",
								role.getTitle(themeDisplay.getLocale()), false);

							add(
								dropdownItem -> {
									dropdownItem.putData(
										"action", "removeUserGroupRole");
									dropdownItem.putData(
										"message",
										LanguageUtil.format(
											request,
											"are-you-sure-you-want-to-remove-" +
												"x-role-to-selected-user-" +
													"groups",
											role.getTitle(
												themeDisplay.getLocale())));

									PortletURL removeUserGroupRoleURL =
										liferayPortletResponse.
											createActionURL();

									removeUserGroupRoleURL.setParameter(
										ActionRequest.ACTION_NAME,
										"removeUserGroupRole");

									dropdownItem.putData(
										"removeUserGroupRoleURL",
										removeUserGroupRoleURL.toString());

									dropdownItem.setIcon("remove-role");
									dropdownItem.setLabel(label);
									dropdownItem.setQuickAction(true);
								});
						}
					}
				}
				catch (Exception exception) {
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

	@Override
	public String getComponentId() {
		return "userGroupsManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		try {
			PortletURL selectUserGroupsURL =
				liferayPortletResponse.createRenderURL();

			selectUserGroupsURL.setParameter(
				"mvcPath", "/select_user_groups.jsp");
			selectUserGroupsURL.setWindowState(LiferayWindowState.POP_UP);

			return new CreationMenu() {
				{
					addDropdownItem(
						dropdownItem -> {
							dropdownItem.putData("action", "selectUserGroups");

							ThemeDisplay themeDisplay =
								(ThemeDisplay)request.getAttribute(
									WebKeys.THEME_DISPLAY);

							dropdownItem.putData(
								"groupTypeLabel",
								GroupUtil.getGroupTypeLabel(
									_userGroupsDisplayContext.getGroupId(),
									themeDisplay.getLocale()));

							dropdownItem.putData(
								"selectUserGroupsURL",
								selectUserGroupsURL.toString());
							dropdownItem.setLabel(
								LanguageUtil.get(request, "add"));
						});
				}
			};
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public String getDefaultEventHandler() {
		return "userGroupsManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Role role = _userGroupsDisplayContext.getRole();

		return new LabelItemList() {
			{
				if (role != null) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse);

							removeLabelURL.setParameter("roleId", "0");

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

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
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					_userGroupsDisplayContext.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				return true;
			}
		}
		catch (Exception exception) {
		}

		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive"};
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
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
						dropdownItem.setLabel(LanguageUtil.get(request, "all"));
					});

				add(
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
							LanguageUtil.get(request, "roles"));
					});
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

	private final UserGroupsDisplayContext _userGroupsDisplayContext;

}