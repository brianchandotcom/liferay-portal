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

package com.liferay.commerce.account.web.internal.portlet.action;

import com.liferay.account.model.AccountEntry;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.CommerceAccountNameException;
import com.liferay.commerce.account.exception.DuplicateCommerceAccountException;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT,
		"mvc.command.name=/commerce_account/edit_commerce_account"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, CommerceAccount.class.getName(),
				PortletProvider.Action.MANAGE)
		).setParameters(
			new HashMap<>()
		).buildString();

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<CommerceAccount> commerceAccountCallable =
					new CommerceAccountCallable(actionRequest);

				CommerceAccount commerceAccount = TransactionInvokerUtil.invoke(
					_transactionConfig, commerceAccountCallable);

				redirect = getSaveAndContinueRedirect(
					actionRequest, commerceAccount);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
			else if (cmd.equals("setCurrentAccount")) {
				setCurrentAccount(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchAccountException ||
				throwable instanceof NoSuchAddressException ||
				throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof CommerceAccountNameException ||
					 throwable instanceof DuplicateCommerceAccountException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());
			}
			else {
				_log.error(throwable, throwable);
			}
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CommerceAccount commerceAccount)
		throws PortalException {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, CommerceAccount.class.getName(),
				PortletProvider.Action.VIEW)
		).setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			() -> {
				PortletURL managePortletURL = PortletProviderUtil.getPortletURL(
					actionRequest, CommerceAccount.class.getName(),
					PortletProvider.Action.MANAGE);

				return managePortletURL.toString();
			}
		).setParameter(
			"commerceAccountId", commerceAccount.getCommerceAccountId()
		).buildString();
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(commerceAccountId);

		_commerceAccountService.setActive(
			commerceAccountId, !commerceAccount.isActive());
	}

	protected void setCurrentAccount(ActionRequest actionRequest)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		_commerceAccountHelper.setCurrentCommerceAccount(
			_portal.getHttpServletRequest(actionRequest),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(actionRequest)),
			commerceAccountId);
	}

	protected CommerceAccount updateCommerceAccount(ActionRequest actionRequest)
		throws Exception {

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		String name = ParamUtil.getString(actionRequest, "name");
		String email = ParamUtil.getString(actionRequest, "email");
		String taxId = ParamUtil.getString(actionRequest, "taxId");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = _getServiceContext(
			actionRequest, commerceAccountId);

		CommerceAccount commerceAccount;

		if (commerceAccountId > 0) {
			boolean deleteLogo = ParamUtil.getBoolean(
				actionRequest, "deleteLogo");
			long defaultBillingAddressId = ParamUtil.getLong(
				actionRequest, "defaultBillingAddressId");
			long defaultShippingAddressId = ParamUtil.getLong(
				actionRequest, "defaultShippingAddressId");

			commerceAccount = _commerceAccountService.updateCommerceAccount(
				commerceAccountId, name, !deleteLogo, logoBytes, email, taxId,
				true, defaultBillingAddressId, defaultShippingAddressId,
				serviceContext);
		}
		else {
			boolean active = ParamUtil.getBoolean(actionRequest, "active");
			String externalReferenceCode = ParamUtil.getString(
				actionRequest, "externalReferenceCode");
			long[] userIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "userIds"), 0L);
			String[] emailAddresses = ParamUtil.getStringValues(
				actionRequest, "emailAddresses");

			commerceAccount =
				_commerceAccountService.addBusinessCommerceAccount(
					name, CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
					email, taxId, active, externalReferenceCode, userIds,
					emailAddresses, serviceContext);
		}

		return commerceAccount;
	}

	private ServiceContext _getServiceContext(
			ActionRequest actionRequest, long commerceAccountId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_portal.getCompanyId(actionRequest));
		serviceContext.setScopeGroupId(_portal.getScopeGroupId(actionRequest));
		serviceContext.setUserId(_portal.getUserId(actionRequest));

		if (commerceAccountId > 0) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				_classNameLocalService.getClassNameId(AccountEntry.class),
				commerceAccountId);

			serviceContext.setAssetCategoryIds(assetEntry.getCategoryIds());
			serviceContext.setAssetTagNames(assetEntry.getTagNames());
		}

		Map<String, Serializable> expandoBridgeAttributes =
			_portal.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(),
					AccountEntry.class.getName()),
				actionRequest);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceAccountMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

	private class CommerceAccountCallable implements Callable<CommerceAccount> {

		@Override
		public CommerceAccount call() throws Exception {
			return updateCommerceAccount(_actionRequest);
		}

		private CommerceAccountCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}