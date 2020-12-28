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

package com.liferay.commerce.account.web.internal.display.context;

import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.web.internal.display.context.util.CommerceAccountRequestHelper;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountDisplayContext {

	public CommerceAccountDisplayContext(
		CommerceAccountService commerceAccountService,
		CommerceAddressService commerceAddressService,
		CommerceCountryService commerceCountryService,
		CommerceRegionService commerceRegionService,
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommerceAccount> modelResourcePermission,
		UserFileUploadsConfiguration userFileUploadsConfiguration,
		UserLocalService userLocalService) {

		_commerceAccountService = commerceAccountService;
		_commerceAddressService = commerceAddressService;
		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_configurationProvider = configurationProvider;
		_modelResourcePermission = modelResourcePermission;
		_userFileUploadsConfiguration = userFileUploadsConfiguration;
		_userLocalService = userLocalService;

		_commerceAccountRequestHelper = new CommerceAccountRequestHelper(
			httpServletRequest);

		_commerceContext = (CommerceContext)httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
	}

	public List<CommerceAddress> getBillingCommerceAddresses()
		throws PortalException {

		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		return _commerceAddressService.getBillingCommerceAddresses(
			commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId());
	}

	public List<CommerceAccount> getCommerceAccounts() throws PortalException {
		return _commerceAccountService.getUserCommerceAccounts(
			_commerceAccountRequestHelper.getUserId(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			_commerceContext.getCommerceSiteType(), StringPool.BLANK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			_commerceAccountRequestHelper.getCompanyId(), true);
	}

	public List<CommerceRegion> getCommerceRegions(long commerceCountryId) {
		return _commerceRegionService.getCommerceRegions(
			commerceCountryId, true);
	}

	public int getCommerceSiteType() throws PortalException {
		CommerceAccountGroupServiceConfiguration
			commerceAccountGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceAccountGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						_commerceContext.getCommerceChannelGroupId(),
						CommerceAccountConstants.SERVICE_NAME));

		return commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	public CommerceAccount getCurrentCommerceAccount() throws PortalException {
		long commerceAccountId = ParamUtil.getLong(
			_commerceAccountRequestHelper.getRequest(), "commerceAccountId");

		if (commerceAccountId > 0) {
			return _commerceAccountService.getCommerceAccount(
				commerceAccountId);
		}

		return getCurrentAccount();
	}

	public long getCurrentCommerceAccountId() throws PortalException {
		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount != null) {
			return commerceAccount.getCommerceAccountId();
		}

		return 0;
	}

	public CommerceAddress getDefaultBillingCommerceAddress()
		throws PortalException {

		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		return _commerceAddressService.fetchCommerceAddress(
			commerceAccount.getDefaultBillingAddressId());
	}

	public CommerceAddress getDefaultShippingCommerceAddress()
		throws PortalException {

		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		return _commerceAddressService.fetchCommerceAddress(
			commerceAccount.getDefaultShippingAddressId());
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceAccountRequestHelper.getRequest());

		_keywords = ParamUtil.getString(httpServletRequest, "q", null);

		if (_keywords == null) {
			return StringPool.BLANK;
		}

		return _keywords;
	}

	public String getLogo(CommerceAccount commerceAccount) {
		ThemeDisplay themeDisplay =
			_commerceAccountRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/organization_logo?img_id=");
		sb.append(commerceAccount.getLogoId());
		sb.append("&t=");
		sb.append(
			WebServerServletTokenUtil.getToken(commerceAccount.getLogoId()));

		return sb.toString();
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceAccountRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceAccountRequestHelper.getRequest());

		String backURL = ParamUtil.getString(
			httpServletRequest,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter(
				PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
				backURL);
		}

		String redirect = ParamUtil.getString(
			_commerceAccountRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(
			_commerceAccountRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceAccountRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount != null) {
			portletURL.setParameter(
				"commerceAccountId",
				String.valueOf(commerceAccount.getCommerceAccountId()));
		}

		return portletURL;
	}

	public User getSelectedUser() throws PortalException {
		if (getCommerceSiteType() == CommerceAccountConstants.SITE_TYPE_B2C) {
			return _commerceAccountRequestHelper.getUser();
		}

		return _userLocalService.getUser(getSelectedUserId());
	}

	public long getSelectedUserId() throws PortalException {
		long userId = ParamUtil.getLong(
			_commerceAccountRequestHelper.getRequest(), "userId");

		if (userId > 0) {
			return userId;
		}

		return _commerceAccountRequestHelper.getUserId();
	}

	public List<CommerceAddress> getShippingCommerceAddresses()
		throws PortalException {

		CommerceAccount commerceAccount = getCurrentCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		return _commerceAddressService.getShippingCommerceAddresses(
			commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId());
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	public boolean hasAddAccountPermissions() {
		PortletResourcePermission portletResourcePermission =
			_modelResourcePermission.getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceAccountRequestHelper.getPermissionChecker(), null,
			CommerceAccountActionKeys.ADD_ACCOUNT);
	}

	public boolean hasCommerceAccountModelPermissions(
			CommerceAccount commerceAccount, String actionId)
		throws PortalException {

		if (commerceAccount == null) {
			return false;
		}

		return _modelResourcePermission.contains(
			_commerceAccountRequestHelper.getPermissionChecker(),
			commerceAccount, actionId);
	}

	public boolean hasCommerceAccountModelPermissions(
			long commerceAccountId, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_commerceAccountRequestHelper.getPermissionChecker(),
			commerceAccountId, actionId);
	}

	public boolean hasCommerceAccountModelPermissions(String actionId)
		throws PortalException {

		return hasCommerceAccountModelPermissions(
			getCurrentCommerceAccount(), actionId);
	}

	public boolean hasCommerceChannel() throws PortalException {
		HttpServletRequest httpServletRequest =
			_commerceAccountRequestHelper.getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public boolean hasManageCommerceAccountPermissions() {
		PortletResourcePermission portletResourcePermission =
			_modelResourcePermission.getPortletResourcePermission();

		if (portletResourcePermission.contains(
				_commerceAccountRequestHelper.getPermissionChecker(), null,
				CommerceAccountActionKeys.MANAGE_ALL_ACCOUNTS) ||
			portletResourcePermission.contains(
				_commerceAccountRequestHelper.getPermissionChecker(), null,
				CommerceAccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS)) {

			return true;
		}

		return false;
	}

	protected CommerceAccount getCurrentAccount() throws PortalException {
		return _commerceContext.getCommerceAccount();
	}

	private final CommerceAccountRequestHelper _commerceAccountRequestHelper;
	private final CommerceAccountService _commerceAccountService;
	private final CommerceAddressService _commerceAddressService;
	private final CommerceContext _commerceContext;
	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final ConfigurationProvider _configurationProvider;
	private String _keywords;
	private final ModelResourcePermission<CommerceAccount>
		_modelResourcePermission;
	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;
	private final UserLocalService _userLocalService;

}