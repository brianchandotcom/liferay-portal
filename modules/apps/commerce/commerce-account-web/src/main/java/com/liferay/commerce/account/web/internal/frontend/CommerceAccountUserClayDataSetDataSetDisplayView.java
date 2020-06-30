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

package com.liferay.commerce.account.web.internal.frontend;

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.commerce.account.web.internal.model.Member;
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

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
		"commerce.data.provider.key=" + CommerceAccountUserClayDataSetDataSetDisplayView.NAME,
		"commerce.data.set.display.name=" + CommerceAccountUserClayDataSetDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceAccountUserClayDataSetDataSetDisplayView
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider, CommerceDataSetDataProvider<Member> {

	public static final String NAME = "commerceAccountUsers";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		Member member = (Member)model;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (_accountModelResourcePermission.contains(
				permissionChecker, member.getAccountId(),
				CommerceAccountActionKeys.MANAGE_MEMBERS)) {

			String viewURL = _getAccountUserViewDetailURL(
				member.getMemberId(), httpServletRequest);

			ClayDataSetAction viewClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, viewURL, StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "view"), null, false,
				false);

			clayDataSetActions.add(viewClayDataSetAction);

			if (permissionChecker.isCompanyAdmin() ||
				(member.getMemberId() != themeDisplay.getUserId())) {

				ClayDataSetAction removeClayDataSetAction =
					new ClayDataSetAction(
						StringPool.BLANK, StringPool.POUND, StringPool.BLANK,
						LanguageUtil.get(httpServletRequest, "remove"),
						"removeCommerceAccountUser('" + member.getMemberId() +
							"')",
						false, false);

				clayDataSetActions.add(removeClayDataSetAction);
			}
		}

		return clayDataSetActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		return _commerceAccountUserRelService.getCommerceAccountUserRelsCount(
			commerceAccountId);
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
	public List<Member> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Member> members = new ArrayList<>();

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_commerceAccountUserRelService.getCommerceAccountUserRels(
				commerceAccountId, pagination.getStartPosition(),
				pagination.getEndPosition());

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			User user = commerceAccountUserRel.getUser();

			CommerceAccount commerceAccount =
				_commerceAccountService.getCommerceAccount(commerceAccountId);

			members.add(
				new Member(
					user.getUserId(), commerceAccountId,
					HtmlUtil.escape(user.getFullName()), user.getEmailAddress(),
					getUserRoles(
						user, commerceAccount.getCommerceAccountGroupId()),
					_getAccountUserViewDetailURL(
						user.getUserId(), httpServletRequest)));
		}

		return members;
	}

	protected String getUserRoles(User user, long groupId)
		throws PortalException {

		List<Role> roles = new ArrayList<>();

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(
				user.getUserId(), groupId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			roles.add(userGroupRole.getRole());
		}

		return ListUtil.toString(
			roles, Role.NAME_ACCESSOR, StringPool.COMMA_AND_SPACE);
	}

	private String _getAccountUserViewDetailURL(
			long userId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL viewURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceAccount.class.getName(),
			PortletProvider.Action.VIEW);

		viewURL.setParameter("mvcRenderCommandName", "viewCommerceAccountUser");

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		if (commerceAccountId > 0) {
			viewURL.setParameter(
				"commerceAccountId", String.valueOf(commerceAccountId));
		}

		viewURL.setParameter("userId", String.valueOf(userId));

		String backURL = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		viewURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL);

		return viewURL.toString();
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.account.model.CommerceAccount)"
	)
	private ModelResourcePermission<CommerceAccount>
		_accountModelResourcePermission;

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}