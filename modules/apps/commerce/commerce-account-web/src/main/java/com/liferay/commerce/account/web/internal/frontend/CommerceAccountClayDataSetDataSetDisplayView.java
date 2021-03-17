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

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.web.internal.model.Account;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
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
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
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
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceAccountClayDataSetDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceAccountClayDataSetDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceAccountClayDataSetDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider, ClayDataSetDataProvider<Account> {

	public static final String NAME = "commerceAccounts";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("accountId", "id");

		clayTableSchemaBuilder.addClayTableSchemaField("email", "email");

		clayTableSchemaBuilder.addClayTableSchemaField("address", "address");

		ClayTableSchemaField statusField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"statusLabel", "status");

		statusField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		Account account = (Account)model;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return DropdownItemListBuilder.add(
			() -> _modelResourcePermission.contains(
				permissionChecker, account.getAccountId(), ActionKeys.VIEW),
			dropdownItem -> {
				dropdownItem.setHref(
					_getAccountViewDetailURL(
						account.getAccountId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "view"));
			}
		).add(
			() -> {
				CommerceContext commerceContext =
					(CommerceContext)httpServletRequest.getAttribute(
						CommerceWebKeys.COMMERCE_CONTEXT);

				CommerceAccount currentCommerceAccount =
					commerceContext.getCommerceAccount();

				if (_modelResourcePermission.contains(
						permissionChecker, account.getAccountId(),
						ActionKeys.VIEW) &&
					((currentCommerceAccount == null) ||
					 (account.getAccountId() !=
						 currentCommerceAccount.getCommerceAccountId())) &&
					account.getActive()) {

					return true;
				}

				return false;
			},
			dropdownItem -> {
				dropdownItem.setHref(
					"javascript:setCurrentAccount('" + account.getAccountId() +
						"')");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "select"));
			}
		).add(
			() -> _modelResourcePermission.contains(
				permissionChecker, account.getAccountId(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					"javascript:toggleActiveCommerceAccount('" +
						account.getAccountId() + "')");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "activate"));

				if (account.getActive()) {
					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, "deactivate"));
				}
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

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId()),
			_portal.getUserId(httpServletRequest), 0, 0);

		List<CommerceAccount> commerceAccounts =
			_commerceAccountService.getUserCommerceAccounts(
				_portal.getUserId(httpServletRequest),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				commerceContext.getCommerceSiteType(), filter.getKeywords(),
				pagination.getStartPosition(), pagination.getEndPosition());

		for (CommerceAccount commerceAccount : commerceAccounts) {
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

			String statusLabel = "inactive";
			String statusDisplayStyle = "danger";

			if (commerceAccount.isActive()) {
				statusLabel = "active";
				statusDisplayStyle = "success";
			}

			accounts.add(
				new Account(
					commerceAccount.getCommerceAccountId(),
					commerceAccount.isActive(), commerceAccount.getName(),
					commerceAccount.getEmail(),
					_getDefaultBillingCommerceAddress(commerceAccount),
					new LabelField(statusDisplayStyle, statusLabel),
					thumbnailSB.toString(),
					_getAccountViewDetailURL(
						commerceAccount.getCommerceAccountId(),
						httpServletRequest)));
		}

		return accounts;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId()),
			_portal.getUserId(httpServletRequest), 0, 0);

		return _commerceAccountService.getUserCommerceAccountsCount(
			_portal.getUserId(httpServletRequest),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			commerceContext.getCommerceSiteType(), filter.getKeywords());
	}

	private String _getAccountViewDetailURL(
			long commerceAccountId, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), commerceAccountId,
				ActionKeys.VIEW)) {

			return StringPool.BLANK;
		}

		PortletURL viewURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceAccount.class.getName(),
				PortletProvider.Action.VIEW)
		).setParameter(
			"commerceAccountId", commerceAccountId
		).build();

		PortletURL backURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceAccount.class.getName(),
			PortletProvider.Action.MANAGE);

		viewURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL.toString());

		return viewURL.toString();
	}

	private String _getDefaultBillingCommerceAddress(
			CommerceAccount commerceAccount)
		throws PortalException {

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchCommerceAddress(
				commerceAccount.getDefaultBillingAddressId());

		if (commerceAddress == null) {
			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(commerceAddress.getStreet1());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getCity());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getZip());

		return sb.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.account.model.CommerceAccount)"
	)
	private ModelResourcePermission<CommerceAccount> _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}