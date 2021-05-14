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

package com.liferay.commerce.shop.by.diagram.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.shop.by.diagram.web.internal.constants.CommerceOrganizationScreenNavigationConstants;
import com.liferay.commerce.shop.by.diagram.web.internal.model.Account;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
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
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("name", "name");
		clayTableSchemaBuilder.addClayTableSchemaField("path", "path");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		return DropdownItemListBuilder.add(
			() -> {
				long organizationId = ParamUtil.getLong(
					httpServletRequest, "organizationId");

				return OrganizationPermissionUtil.contains(
					PermissionThreadLocal.getPermissionChecker(),
					organizationId, ActionKeys.UPDATE);
			},
			dropdownItem -> {
				Account account = (Account)model;

				StringBundler sb = new StringBundler(7);

				sb.append("javascript:deleteCommerceOrganizationAccount");
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.APOSTROPHE);
				sb.append(account.getAccountId());
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append(StringPool.SEMICOLON);

				dropdownItem.setHref(sb.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
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

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceAccount.class.getName(),
				PortletProvider.Action.VIEW)
		).setParameter(
			"commerceAccountId", commerceAccountId
		).setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, Organization.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_organization/view_commerce_organization"
			).setParameter(
				"organizationId", organizationId
			).setParameter(
				"screenNavigationCategoryKey",
				CommerceOrganizationScreenNavigationConstants.
					CATEGORY_KEY_ORGANIZATION_ACCOUNTS
			).buildString()
		).buildString();
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