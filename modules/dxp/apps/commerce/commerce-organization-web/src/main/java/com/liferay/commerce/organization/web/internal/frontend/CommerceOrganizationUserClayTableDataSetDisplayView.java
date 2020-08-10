/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.organization.web.internal.model.User;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceOrganizationUserClayTableDataSetDisplayView.NAME,
		"commerce.data.set.display.name=" + CommerceOrganizationUserClayTableDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceOrganizationUserClayTableDataSetDisplayView
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider, CommerceDataSetDataProvider<User> {

	public static final String NAME = "commerceOrganizationUsers";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		User user = (User)model;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (OrganizationPermissionUtil.contains(
				permissionChecker, user.getOrganizationId(), ActionKeys.VIEW)) {

			String viewURL = _getOrganizationUserViewDetailURL(
				user.getUserId(), httpServletRequest);

			ClayDataSetAction viewClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, viewURL, StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "view"), null, false,
				false);

			clayTableActions.add(viewClayDataSetAction);
		}

		if (permissionChecker.isCompanyAdmin() ||
			(user.getUserId() != themeDisplay.getUserId())) {

			StringBundler sb = new StringBundler(7);

			sb.append("javascript:deleteCommerceOrganizationUser");
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(StringPool.APOSTROPHE);
			sb.append(user.getUserId());
			sb.append(StringPool.APOSTROPHE);
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(StringPool.SEMICOLON);

			ClayDataSetAction clayTableAction = new ClayDataSetAction(
				StringPool.BLANK, sb.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"), null, false,
				false);

			clayTableActions.add(clayTableAction);
		}

		return clayTableActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		return _userService.getOrganizationUsersCount(
			organizationId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		clayTableSchemaBuilder.addField("name", "name");

		clayTableSchemaBuilder.addField("roles", "roles");

		clayTableSchemaBuilder.addField("email", "email");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<User> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		List<User> users = new ArrayList<>();

		List<com.liferay.portal.kernel.model.User> userList =
			_userService.getOrganizationUsers(
				organizationId, WorkflowConstants.STATUS_ANY,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		for (com.liferay.portal.kernel.model.User user : userList) {
			users.add(
				new User(
					user.getUserId(), organizationId,
					HtmlUtil.escape(user.getFullName()), user.getEmailAddress(),
					getUserRoles(user, themeDisplay.getPermissionChecker()),
					_getOrganizationUserViewDetailURL(
						user.getUserId(), httpServletRequest)));
		}

		return users;
	}

	protected String getUserRoles(
		com.liferay.portal.kernel.model.User user,
		PermissionChecker permissionChecker) {

		List<Role> roles = new ArrayList<>();

		List<Role> roleList = user.getRoles();

		for (Role role : roleList) {
			if (RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(), ActionKeys.VIEW) &&
				(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

				roles.add(role);
			}
		}

		return ListUtil.toString(roles, "name", StringPool.COMMA_AND_SPACE);
	}

	private String _getOrganizationUserViewDetailURL(
			long userId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL viewURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, Organization.class.getName(),
			PortletProvider.Action.MANAGE);

		viewURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrganizationUser");

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		if (organizationId > 0) {
			viewURL.setParameter(
				"organizationId", String.valueOf(organizationId));
		}

		viewURL.setParameter("userId", String.valueOf(userId));

		viewURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			_portal.getCurrentURL(httpServletRequest));

		return viewURL.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserService _userService;

}