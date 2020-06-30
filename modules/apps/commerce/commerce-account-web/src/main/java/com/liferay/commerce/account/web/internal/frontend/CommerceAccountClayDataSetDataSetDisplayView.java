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
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
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
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceAccountClayDataSetDataSetDisplayView.NAME,
		"commerce.data.set.display.name=" + CommerceAccountClayDataSetDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceAccountClayDataSetDataSetDisplayView
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider, CommerceDataSetDataProvider<Account> {

	public static final String NAME = "commerceAccounts";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		Account account = (Account)model;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), account.getAccountId(),
				ActionKeys.VIEW)) {

			String viewURL = _getAccountViewDetailURL(
				account.getAccountId(), httpServletRequest);

			ClayDataSetAction clayTableViewAction = new ClayDataSetAction(
				StringPool.BLANK, viewURL, StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "view"), null, false,
				false);

			clayDataSetActions.add(clayTableViewAction);

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount currentCommerceAccount =
				commerceContext.getCommerceAccount();

			if (((currentCommerceAccount == null) ||
				 (account.getAccountId() !=
					 currentCommerceAccount.getCommerceAccountId())) &&
				account.getActive()) {

				ClayDataSetAction clayTableSetActiveAction =
					new ClayDataSetAction(
						StringPool.BLANK, StringPool.POUND, StringPool.BLANK,
						LanguageUtil.get(httpServletRequest, "select"),
						"setCurrentAccount('" + account.getAccountId() + "')",
						false, false);

				clayDataSetActions.add(clayTableSetActiveAction);
			}
		}

		if (_modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), account.getAccountId(),
				ActionKeys.UPDATE)) {

			String toggleActiveJavascript =
				"toggleActiveCommerceAccount('" + account.getAccountId() + "')";

			ClayDataSetAction clayTableSetActiveAction = new ClayDataSetAction(
				"commerce-button--good", StringPool.POUND, StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "activate"),
				toggleActiveJavascript, false, false);

			if (account.getActive()) {
				clayTableSetActiveAction = new ClayDataSetAction(
					"commerce-button--bad", StringPool.POUND, StringPool.BLANK,
					LanguageUtil.get(httpServletRequest, "deactivate"),
					toggleActiveJavascript, false, false);
			}

			clayDataSetActions.add(clayTableSetActiveAction);
		}

		return clayDataSetActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
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

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField nameField = clayTableSchemaBuilder.addField(
			"name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("accountId", "id");

		clayTableSchemaBuilder.addField("email", "email");

		clayTableSchemaBuilder.addField("address", "address");

		ClayTableSchemaField statusField = clayTableSchemaBuilder.addField(
			"statusLabel", "status");

		statusField.setContentRenderer("label");

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

		PortletURL viewURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceAccount.class.getName(),
			PortletProvider.Action.VIEW);

		viewURL.setParameter(
			"commerceAccountId", String.valueOf(commerceAccountId));

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