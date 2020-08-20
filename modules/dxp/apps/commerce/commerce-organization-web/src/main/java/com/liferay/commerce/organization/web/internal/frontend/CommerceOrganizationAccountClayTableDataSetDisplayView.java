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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.organization.web.internal.model.Account;
import com.liferay.commerce.organization.web.internal.servlet.taglib.ui.constants.CommerceOrganizationScreenNavigationConstants;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

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
		"clay.data.provider.key=" + CommerceOrganizationAccountClayTableDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceOrganizationAccountClayTableDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceOrganizationAccountClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider, ClayDataSetDataProvider<Account> {

	public static final String NAME = "commerceOrganizationAccounts";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		if (OrganizationPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), organizationId,
				ActionKeys.UPDATE)) {

			Account account = (Account)model;

			StringBundler sb = new StringBundler(7);

			sb.append("javascript:deleteCommerceOrganizationAccount");
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(StringPool.APOSTROPHE);
			sb.append(account.getAccountId());
			sb.append(StringPool.APOSTROPHE);
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(StringPool.SEMICOLON);

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, sb.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"), null, false,
				false);

			clayDataSetActions.add(deleteClayDataSetAction);
		}

		return clayDataSetActions;
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("name", "name");
		clayTableSchemaBuilder.addClayTableSchemaField("path", "path");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<Account> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<Account> accounts = new ArrayList<>();

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

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

			StringBundler thumbnailSB = new StringBundler(5);

			thumbnailSB.append(themeDisplay.getPathImage());

			if (commerceAccount.getLogoId() == 0) {
				thumbnailSB.append("/organization_logo?img_id=0");
			}
			else {
				thumbnailSB.append("/organization_logo?img_id=");
				thumbnailSB.append(commerceAccount.getLogoId());
				thumbnailSB.append("&t=");
				thumbnailSB.append(
					WebServerServletTokenUtil.getToken(
						commerceAccount.getLogoId()));
			}

			accounts.add(
				new Account(
					commerceAccount.getCommerceAccountId(),
					commerceAccount.getName(), commerceAccount.getEmail(),
					_getDefaultBillingCommerceAddress(
						commerceAccount, themeDisplay.getScopeGroupId()),
					thumbnailSB.toString(),
					_getAccountViewDetailURL(
						commerceAccount.getCommerceAccountId(), organizationId,
						httpServletRequest)));
		}

		return accounts;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	private String _getAccountViewDetailURL(
			long commerceAccountId, long organizationId,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL viewURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceAccount.class.getName(),
			PortletProvider.Action.VIEW);

		viewURL.setParameter(
			"commerceAccountId", String.valueOf(commerceAccountId));

		PortletURL backURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, Organization.class.getName(),
			PortletProvider.Action.MANAGE);

		backURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrganization");
		backURL.setParameter("organizationId", String.valueOf(organizationId));
		backURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceOrganizationScreenNavigationConstants.
				CATEGORY_KEY_ORGANIZATION_ACCOUNTS);

		viewURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL.toString());

		return viewURL.toString();
	}

	private String _getDefaultBillingCommerceAddress(
			CommerceAccount commerceAccount, long groupId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			_commerceAddressService.getCommerceAddresses(
				groupId, commerceAccount.getModelClassName(),
				commerceAccount.getCommerceAccountId());

		for (CommerceAddress commerceAddress : commerceAddresses) {
			if (commerceAddress.isDefaultBilling()) {
				StringBundler sb = new StringBundler(5);

				sb.append(commerceAddress.getStreet1());
				sb.append(StringPool.SPACE);
				sb.append(commerceAddress.getCity());
				sb.append(StringPool.SPACE);
				sb.append(commerceAddress.getZip());

				return sb.toString();
			}
		}

		return null;
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

}