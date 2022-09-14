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

package com.liferay.commerce.pricing.web.internal.frontend.data.set.provider;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.commerce.pricing.web.internal.model.PricingEntry;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"fds.data.provider.key=" + CommercePricingFDSNames.ACCOUNT_ENTRY_DISCOUNTS,
		"fds.data.provider.key=" + CommercePricingFDSNames.ACCOUNT_ENTRY_PRICE_LISTS
	},
	service = FDSActionProvider.class
)
public class PricingEntryCommerceChannelAccountEntryRelFDSActionProvider
	implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		PricingEntry pricingEntry = (PricingEntry)model;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return DropdownItemListBuilder.add(
			() -> _accountEntryModelResourcePermission.contains(
				permissionChecker, pricingEntry.getAccountEntryId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCommerceChannelAccountEntryRelEditURL(
						pricingEntry.getAccountEntryId(),
						pricingEntry.getCommerceChannelAccountEntryRelId(),
						httpServletRequest, pricingEntry.getType()));
				dropdownItem.setLabel(
					_language.get(httpServletRequest, Constants.EDIT));
				dropdownItem.setTarget("modal-lg");
			}
		).add(
			() -> _accountEntryModelResourcePermission.contains(
				permissionChecker, pricingEntry.getAccountEntryId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCommerceChannelAccountEntryRelDeleteURL(
						pricingEntry.getCommerceChannelAccountEntryRelId(),
						httpServletRequest, pricingEntry.getType()));
				dropdownItem.setLabel(
					_language.get(httpServletRequest, Constants.DELETE));
			}
		).build();
	}

	private String _getCommerceChannelAccountEntryRelDeleteURL(
		long commerceChannelAccountEntryRelId,
		HttpServletRequest httpServletRequest, int type) {

		String actionName = StringPool.BLANK;

		if (CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST == type) {
			actionName =
				"/commerce_pricing" +
					"/edit_account_entry_default_commerce_price_list";
		}
		else if (CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT ==
					type) {

			actionName =
				"/commerce_pricing" +
					"/edit_account_entry_default_commerce_discount";
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			actionName
		).setCMD(
			Constants.DELETE
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"commerceChannelAccountEntryRelId", commerceChannelAccountEntryRelId
		).buildString();
	}

	private String _getCommerceChannelAccountEntryRelEditURL(
		long accountEntryId, long commerceChannelAccountEntryRelId,
		HttpServletRequest httpServletRequest, int type) {

		String mvcRenderCommandName = StringPool.BLANK;

		if (CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST == type) {
			mvcRenderCommandName =
				"/commerce_pricing" +
					"/edit_account_entry_default_commerce_price_list";
		}
		else if (CommerceChannelAccountEntryRelConstants.TYPE_DISCOUNT ==
					type) {

			mvcRenderCommandName =
				"/commerce_pricing" +
					"/edit_account_entry_default_commerce_discount";
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			mvcRenderCommandName
		).setParameter(
			"accountEntryId", accountEntryId
		).setParameter(
			"commerceChannelAccountEntryRelId", commerceChannelAccountEntryRelId
		).setParameter(
			"type", type
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}