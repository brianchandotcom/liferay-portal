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

package com.liferay.commerce.shop.by.diagram.web.internal.organization.resource;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.Account;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.AccountList;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.Organization;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.User;
import com.liferay.commerce.shop.by.diagram.web.internal.organization.model.UserList;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceOrganizationResourceUtil.class)
public class CommerceOrganizationResourceUtil {

	public Organization getOrganization(
			long userId, long companyId,
			com.liferay.portal.kernel.model.Organization organization)
		throws Exception {

		if (organization == null) {
			List<com.liferay.portal.kernel.model.Organization> organizations =
				_organizationService.getOrganizations(
					companyId,
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			if (organizations.isEmpty()) {
				organizations = _organizationLocalService.getOrganizations(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}

			return new Organization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, "Root",
				_toOrganizations(organizations),
				_organizationService.getOrganizationsCount(
					companyId,
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID),
				0, 0);
		}

		return new Organization(
			organization.getOrganizationId(), organization.getName(),
			_toOrganizations(organization.getSuborganizations()),
			_organizationService.getOrganizationsCount(
				companyId, organization.getOrganizationId()),
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsByOrganizationIdCount(
					organization.getOrganizationId()),
			_userService.getOrganizationUsersCount(
				organization.getOrganizationId(),
				WorkflowConstants.STATUS_APPROVED));
	}

	protected AccountList getAccountList(
			long organizationId, Pagination pagination)
		throws Exception {

		List<Account> accounts = new ArrayList<>();

		List<CommerceAccountOrganizationRel> commerceAccountOrganizationRels =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsByOrganizationId(
					organizationId, pagination.getStartPosition(),
					pagination.getEndPosition());

		for (CommerceAccountOrganizationRel commerceAccountOrganizationRel :
				commerceAccountOrganizationRels) {

			CommerceAccount commerceAccount =
				_commerceAccountService.getCommerceAccount(
					commerceAccountOrganizationRel.getCommerceAccountId());

			accounts.add(
				new Account(
					commerceAccount.getCommerceAccountId(),
					commerceAccount.getName(),
					_getAccountImageURL(commerceAccount.getLogoId()),
					commerceAccount.getEmail()));
		}

		int total =
			_commerceAccountOrganizationRelService.
				getCommerceAccountOrganizationRelsByOrganizationIdCount(
					organizationId);

		return new AccountList(accounts, total);
	}

	protected UserList getUserList(long organizationId, Pagination pagination)
		throws Exception {

		List<User> users = new ArrayList<>();

		List<com.liferay.portal.kernel.model.User> userList =
			_userService.getOrganizationUsers(
				organizationId, WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		for (com.liferay.portal.kernel.model.User user : userList) {
			users.add(
				new User(
					user.getUserId(), user.getFullName(),
					UserConstants.getPortraitURL(
						StringPool.BLANK, user.isMale(), user.getPortraitId(),
						user.getUserUuid()),
					user.getEmailAddress()));
		}

		int total = _userService.getOrganizationUsersCount(
			organizationId, WorkflowConstants.STATUS_APPROVED);

		return new UserList(users, total);
	}

	private String _getAccountImageURL(long logoId) {
		StringBundler sb = new StringBundler(4);

		sb.append("/organization_logo?img_id=");
		sb.append(logoId);
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(logoId));

		return sb.toString();
	}

	private List<Organization> _toOrganizations(
			List<com.liferay.portal.kernel.model.Organization> organizations)
		throws Exception {

		List<Organization> organizationList = new ArrayList<>();

		for (com.liferay.portal.kernel.model.Organization organization :
				organizations) {

			organizationList.add(
				new Organization(
					organization.getOrganizationId(), organization.getName(),
					Collections.emptyList(),
					_organizationService.getOrganizationsCount(
						organization.getCompanyId(),
						organization.getOrganizationId()),
					_commerceAccountOrganizationRelService.
						getCommerceAccountOrganizationRelsByOrganizationIdCount(
							organization.getOrganizationId()),
					_userService.getOrganizationUsersCount(
						organization.getOrganizationId(),
						WorkflowConstants.STATUS_APPROVED)));
		}

		return organizationList;
	}

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserService _userService;

}