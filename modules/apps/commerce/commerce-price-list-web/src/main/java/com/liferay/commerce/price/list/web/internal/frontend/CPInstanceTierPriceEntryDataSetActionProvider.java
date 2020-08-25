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

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.price.list.web.internal.model.InstanceTierPriceEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
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
	property = "commerce.data.provider.key=" + CommercePriceListDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES,
	service = ClayDataSetActionProvider.class
)
public class CPInstanceTierPriceEntryDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		InstanceTierPriceEntry instanceTierPriceEntry =
			(InstanceTierPriceEntry)model;

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(
				instanceTierPriceEntry.getTierPriceEntryId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		if (_commercePriceListModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(),
				ActionKeys.UPDATE)) {

			PortletURL editURL = _getInstanceTierPriceEntryEditURL(
				commerceTierPriceEntry, httpServletRequest);

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, editURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "edit"), StringPool.BLANK,
				false, false);

			editClayDataSetAction.setTarget("modal");

			clayDataSetActions.add(editClayDataSetAction);
		}

		if (_commercePriceListModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(),
				ActionKeys.DELETE)) {

			PortletURL deleteURL = _getInstanceTierPriceEntryDeleteURL(
				commerceTierPriceEntry, httpServletRequest);

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, deleteURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"),
				StringPool.BLANK, false, false);

			clayDataSetActions.add(deleteClayDataSetAction);
		}

		return clayDataSetActions;
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
		catch (WindowStateException wse) {
			_log.error(wse, wse);
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