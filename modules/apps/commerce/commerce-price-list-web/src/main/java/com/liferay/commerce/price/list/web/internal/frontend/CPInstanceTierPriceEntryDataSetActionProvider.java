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

package com.liferay.commerce.price.list.web.internal.frontend;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.price.list.web.internal.model.InstanceTierPriceEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + CommercePriceListDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES,
	service = ClayDataSetActionProvider.class
)
public class CPInstanceTierPriceEntryDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		InstanceTierPriceEntry instanceTierPriceEntry =
			(InstanceTierPriceEntry)model;

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(
				instanceTierPriceEntry.getTierPriceEntryId());

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		return DropdownItemListBuilder.add(
			() -> _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getInstanceTierPriceEntryEditURL(
						commerceTierPriceEntry, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("modal");
			}
		).add(
			() -> _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.DELETE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getInstanceTierPriceEntryDeleteURL(
						commerceTierPriceEntry, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getInstanceTierPriceEntryDeleteURL(
			CommerceTierPriceEntry commerceTierPriceEntry,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CPPortletKeys.CP_DEFINITIONS,
			PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCPInstanceCommerceTierPriceEntry");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"commercePriceEntryId",
			String.valueOf(commerceTierPriceEntry.getCommercePriceEntryId()));
		portletURL.setParameter(
			"commerceTierPriceEntryId",
			String.valueOf(
				commerceTierPriceEntry.getCommerceTierPriceEntryId()));

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		return portletURL;
	}

	private PortletURL _getInstanceTierPriceEntryEditURL(
			CommerceTierPriceEntry commerceTierPriceEntry,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCPInstanceCommerceTierPriceEntry");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"commercePriceEntryId",
			String.valueOf(commerceTierPriceEntry.getCommercePriceEntryId()));
		portletURL.setParameter(
			"commerceTierPriceEntryId",
			String.valueOf(
				commerceTierPriceEntry.getCommerceTierPriceEntryId()));

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceTierPriceEntryDataSetActionProvider.class);

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private Portal _portal;

}