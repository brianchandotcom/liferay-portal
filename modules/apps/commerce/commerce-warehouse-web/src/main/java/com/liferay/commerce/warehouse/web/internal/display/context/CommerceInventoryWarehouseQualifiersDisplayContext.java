/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 * @author Crescenzo Rega
 */
public class CommerceInventoryWarehouseQualifiersDisplayContext
	extends CommerceInventoryWarehousesDisplayContext {

	public CommerceInventoryWarehouseQualifiersDisplayContext(
		CommerceChannelRelService commerceChannelRelService,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		HttpServletRequest httpServletRequest, Portal portal,
		ModelResourcePermission<CommerceInventoryWarehouse>
			commerceInventoryWarehouseModelResourcePermission) {

		super(
			commerceChannelRelService, commerceInventoryWarehouseService,
			httpServletRequest, portal,
			commerceInventoryWarehouseModelResourcePermission);

		this.commerceChannelRelService = commerceChannelRelService;
	}

	public String getActiveChannelEligibility() throws PortalException {
		long commerceChannelRelsCount =
			commerceChannelRelService.getCommerceChannelRelsCount(
				CommerceInventoryWarehouse.class.getName(),
				getCommerceInventoryWarehouse().
					getCommerceInventoryWarehouseId());

		if (commerceChannelRelsCount > 0) {
			return "channels";
		}

		return "none";
	}

	public List<FDSActionDropdownItem>
			getWarehouseChannelFDSActionDropdownItems()
		throws PortalException {

		return getFDSActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceChannel.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_channels/edit_commerce_channel"
			).setRedirect(
				cpRequestHelper.getCurrentURL()
			).setParameter(
				"commerceChannelId", "{channel.id}"
			).buildString(),
			false);
	}

	public String getWarehouseChannelsAPIURL() throws PortalException {
		return "/o/headless-commerce-admin-inventory/v1.0/warehouses/" +
			getCommerceInventoryWarehouse().getCommerceInventoryWarehouseId() +
				"/warehouse-channels?nestedFields=channel";
	}

	protected List<FDSActionDropdownItem> getFDSActionTemplates(
		String portletURL, boolean sidePanel) {

		List<FDSActionDropdownItem> fdsActionDropdownItems = new ArrayList<>();

		FDSActionDropdownItem fdsActionDropdownItem = new FDSActionDropdownItem(
			portletURL, "pencil", "edit",
			LanguageUtil.get(httpServletRequest, "edit"), "get", null, null);

		if (sidePanel) {
			fdsActionDropdownItem.setTarget("sidePanel");
		}

		fdsActionDropdownItems.add(fdsActionDropdownItem);

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete", "headless"));

		return fdsActionDropdownItems;
	}

}