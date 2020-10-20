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

package com.liferay.commerce.shipment.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountServiceUtil;
import com.liferay.commerce.address.CommerceAddressFormatter;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.frontend.model.StepModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.shipment.web.internal.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class CommerceShipmentDisplayContext
	extends BaseCommerceShipmentDisplayContext<CommerceShipment> {

	public CommerceShipmentDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceAddressFormatter commerceAddressFormatter,
		CommerceAddressService commerceAddressService,
		CommerceChannelService commerceChannelService,
		CommerceCountryService commerceCountryService,
		CommerceOrderItemService commerceOrderItemService,
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceRegionService commerceRegionService,
		PortletResourcePermission portletResourcePermission) {

		super(actionHelper, httpServletRequest, portletResourcePermission);

		_commerceAddressFormatter = commerceAddressFormatter;
		_commerceAddressService = commerceAddressService;
		_commerceChannelService = commerceChannelService;
		_commerceCountryService = commerceCountryService;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceRegionService = commerceRegionService;
	}

	public List<CommerceAccount> getCommerceAccountsWithShippableOrders()
		throws PortalException {

		List<CommerceOrder> commerceOrders = getCommerceOrders();

		Stream<CommerceOrder> stream = commerceOrders.stream();

		long[] commerceAccountIds = stream.mapToLong(
			CommerceOrder::getCommerceAccountId
		).toArray();

		commerceAccountIds = ArrayUtil.unique(commerceAccountIds);

		List<CommerceAccount> commerceAccounts = new ArrayList<>();

		for (long commerceAccountId : commerceAccountIds) {
			commerceAccounts.add(
				CommerceAccountServiceUtil.getCommerceAccount(
					commerceAccountId));
		}

		return commerceAccounts;
	}

	public String getCommerceAccountThumbnailURL(
		CommerceAccount commerceAccount, String pathImage) {

		StringBundler sb = new StringBundler(5);

		sb.append(pathImage);
		sb.append("/organization_logo?img_id=");
		sb.append(commerceAccount.getLogoId());

		if (commerceAccount.getLogoId() > 0) {
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(
					commerceAccount.getLogoId()));
		}

		return sb.toString();
	}

	public String getCommerceChannelName() throws PortalException {
		CommerceShipment commerceShipment = getCommerceShipment();

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannelByOrderGroupId(
				commerceShipment.getGroupId());

		return commerceChannel.getName();
	}

	public List<CommerceChannel> getCommerceChannels() throws PortalException {
		return _commerceChannelService.searchCommerceChannels(
			cpRequestHelper.getCompanyId());
	}

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			cpRequestHelper.getCompanyId(), true);
	}

	public List<CommerceOrder> getCommerceOrders() throws PortalException {
		SearchContext searchContext = _buildSearchContext();

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		return baseModelSearchResult.getBaseModels();
	}

	public List<CommerceRegion> getCommerceRegions(long commerceCountryId) {
		return _commerceRegionService.getCommerceRegions(
			commerceCountryId, true);
	}

	public String getDatasetView() throws PortalException {
		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment.getStatus() >
				CommerceShipmentConstants.SHIPMENT_STATUS_READY_TO_BE_SHIPPED) {

			return CommerceShipmentDataSetConstants.
				COMMERCE_DATA_SET_KEY_SHIPPED_SHIPMENT_ITEMS;
		}

		return CommerceShipmentDataSetConstants.
			COMMERCE_DATA_SET_KEY_PROCESSING_SHIPMENT_ITEMS;
	}

	public String getDescriptiveShippingAddress() throws PortalException {
		CommerceShipment commerceShipment = getCommerceShipment();

		if (commerceShipment.getCommerceAddressId() == 0) {
			return StringPool.BLANK;
		}

		CommerceAddress commerceAddress = getShippingAddress();

		if (commerceAddress == null) {
			return StringPool.BLANK;
		}

		return _commerceAddressFormatter.getDescriptiveAddress(
			commerceAddress, true);
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PortalException {

		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		CommerceShipment commerceShipment = getCommerceShipment();

		int currentShipmentStatus = commerceShipment.getStatus();

		if (currentShipmentStatus !=
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED) {

			int[] shipmentStatuses =
				CommerceShipmentConstants.SHIPMENT_STATUSES;

			int[] availableShipmentStatuses = new int[0];

			if (currentShipmentStatus ==
					CommerceShipmentConstants.
						SHIPMENT_STATUS_READY_TO_BE_SHIPPED) {

				availableShipmentStatuses = ArrayUtil.append(
					availableShipmentStatuses,
					CommerceShipmentConstants.SHIPMENT_STATUS_PROCESSING);
			}

			availableShipmentStatuses = ArrayUtil.append(
				availableShipmentStatuses,
				shipmentStatuses[currentShipmentStatus + 1]);

			for (int shipmentStatus : availableShipmentStatuses) {
				String label =
					CommerceShipmentConstants.getShipmentTransitionLabel(
						shipmentStatus);

				PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
					httpServletRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
					PortletRequest.ACTION_PHASE);

				portletURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/commerce_shipment/edit_commerce_shipment");
				portletURL.setParameter(Constants.CMD, "transition");
				portletURL.setParameter(
					"redirect", PortalUtil.getCurrentURL(httpServletRequest));
				portletURL.setParameter(
					"commerceShipmentId",
					String.valueOf(getCommerceShipmentId()));
				portletURL.setParameter(
					"transitionName", String.valueOf(shipmentStatus));

				String buttonClass = "btn-primary";

				int availableStatusesLength = availableShipmentStatuses.length;

				if ((availableStatusesLength > 1) &&
					(shipmentStatus !=
						availableShipmentStatuses
							[availableStatusesLength - 1])) {

					buttonClass = "btn-secondary";
				}

				headerActionModels.add(
					new HeaderActionModel(
						buttonClass, null, portletURL.toString(), null, label));
			}
		}

		return headerActionModels;
	}

	public String getNavigation() {
		return ParamUtil.getString(
			cpRequestHelper.getRequest(), "navigation", "all");
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public List<DropdownItem> getShipmentItemBulkActions()
		throws PortalException {

		List<DropdownItem> dropdownItems = new ArrayList<>();

		CommerceShipment commerceShipment = getCommerceShipment();

		if (hasManageCommerceShipmentsPermission() &&
			(commerceShipment.getStatus() ==
				CommerceShipmentConstants.SHIPMENT_STATUS_PROCESSING)) {

			dropdownItems.add(new DropdownItem());
		}

		return dropdownItems;
	}

	public CreationMenu getShipmentItemCreationMenu()
		throws PortalException, WindowStateException {

		CreationMenu creationMenu = new CreationMenu();

		CommerceShipment commerceShipment = getCommerceShipment();

		if (hasManageCommerceShipmentsPermission() &&
			(commerceShipment.getStatus() ==
				CommerceShipmentConstants.SHIPMENT_STATUS_PROCESSING)) {

			PortletURL portletURL = getPortletURL();

			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(httpServletRequest));
			portletURL.setParameter(
				"commerceShipmentId",
				String.valueOf(commerceShipment.getCommerceShipmentId()));
			portletURL.setParameter(
				"mvcRenderCommandName",
				"/commerce_shipment/add_commerce_shipment_items");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(portletURL.toString());
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest,
							"add-products-to-this-shipment"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public List<StepModel> getShipmentSteps() throws PortalException {
		CommerceShipment commerceShipment = getCommerceShipment();

		List<StepModel> steps = new ArrayList<>();

		for (int shipmentStatus : CommerceShipmentConstants.SHIPMENT_STATUSES) {
			StepModel step = new StepModel();

			step.setId(String.valueOf(shipmentStatus));
			step.setLabel(
				LanguageUtil.get(
					httpServletRequest,
					CommerceShipmentConstants.getShipmentStatusLabel(
						shipmentStatus)));

			if ((commerceShipment.getStatus() == shipmentStatus) &&
				(shipmentStatus !=
					CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

				step.setState("active");
			}
			else if ((commerceShipment.getStatus() > shipmentStatus) ||
					 (commerceShipment.getStatus() ==
						 CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED)) {

				step.setState("completed");
			}
			else {
				step.setState("inactive");
			}

			steps.add(step);
		}

		return steps;
	}

	public CommerceAddress getShippingAddress() throws PortalException {
		CommerceShipment commerceShipment = getCommerceShipment();

		return _commerceAddressService.fetchCommerceAddress(
			commerceShipment.getCommerceAddressId());
	}

	private SearchContext _buildSearchContext() throws PortalException {
		SearchContext searchContext = new SearchContext();

		int[] orderStatuses = {
			CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED
		};

		searchContext.setAttribute("orderStatuses", orderStatuses);

		searchContext.setAttribute(
			"useSearchResultPermissionFilter", Boolean.FALSE);

		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		searchContext.setCompanyId(cpRequestHelper.getCompanyId());
		searchContext.setEnd(QueryUtil.ALL_POS);

		long[] commerceChannelGroupIds = _getCommerceChannelGroupIds();

		if ((commerceChannelGroupIds != null) &&
			(commerceChannelGroupIds.length > 0)) {

			searchContext.setGroupIds(commerceChannelGroupIds);
		}

		searchContext.setStart(QueryUtil.ALL_POS);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private long[] _getCommerceChannelGroupIds() throws PortalException {
		List<CommerceChannel> commerceChannels = getCommerceChannels();

		Stream<CommerceChannel> stream = commerceChannels.stream();

		return stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();
	}

	private final CommerceAddressFormatter _commerceAddressFormatter;
	private final CommerceAddressService _commerceAddressService;
	private final CommerceChannelService _commerceChannelService;
	private final CommerceCountryService _commerceCountryService;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceRegionService _commerceRegionService;

}