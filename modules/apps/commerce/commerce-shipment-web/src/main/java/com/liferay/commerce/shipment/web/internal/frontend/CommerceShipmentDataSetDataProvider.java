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

package com.liferay.commerce.shipment.web.internal.frontend;

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountModel;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.frontend.model.Shipment;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENTS,
	service = ClayDataSetDataProvider.class
)
public class CommerceShipmentDataSetDataProvider
	implements ClayDataSetDataProvider<Shipment> {

	@Override
	public List<Shipment> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Shipment> shipments = new ArrayList<>();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			commerceOrderId);

		List<CommerceShipment> commerceShipments;

		if (commerceOrder != null) {
			commerceShipments =
				_commerceShipmentService.getCommerceShipmentsByOrderId(
					commerceOrderId, pagination.getStartPosition(),
					pagination.getEndPosition());
		}
		else {
			long companyId = _portal.getCompanyId(httpServletRequest);

			commerceShipments = _commerceShipmentService.getCommerceShipments(
				companyId, _getCommerceChannelGroupIds(companyId),
				_getCommerceAccountIds(_portal.getUserId(httpServletRequest)),
				filter.getKeywords(), null, false,
				pagination.getStartPosition(), pagination.getEndPosition());
		}

		User user = _portal.getUser(httpServletRequest);

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM,
			_portal.getLocale(httpServletRequest), user.getTimeZone());

		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, _portal.getLocale(httpServletRequest),
			user.getTimeZone());

		for (CommerceShipment commerceShipment : commerceShipments) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceShipment.getGroupId());

			String expectedDate = null;

			if (commerceShipment.getExpectedDate() != null) {
				expectedDate = dateFormat.format(
					commerceShipment.getExpectedDate());
			}

			String shippingDate = null;

			if (commerceShipment.getShippingDate() != null) {
				shippingDate = dateFormat.format(
					commerceShipment.getShippingDate());
			}

			shipments.add(
				new Shipment(
					commerceShipment.getCommerceAccountName(),
					_getDescriptiveAddress(commerceShipment),
					commerceChannel.getName(),
					dateTimeFormat.format(commerceShipment.getCreateDate()),
					expectedDate, shippingDate,
					commerceShipment.getCommerceShipmentId(),
					new LabelField(
						CommerceShipmentConstants.getShipmentLabelStyle(
							commerceShipment.getStatus()),
						LanguageUtil.get(
							httpServletRequest,
							CommerceShipmentConstants.getShipmentStatusLabel(
								commerceShipment.getStatus()))),
					commerceShipment.getTrackingNumber()));
		}

		return shipments;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			commerceOrderId);

		if (commerceOrder != null) {
			return _commerceShipmentService.getCommerceShipmentsCountByOrderId(
				commerceOrderId);
		}

		long companyId = _portal.getCompanyId(httpServletRequest);

		return _commerceShipmentService.getCommerceShipmentsCount(
			companyId, _getCommerceChannelGroupIds(companyId),
			_getCommerceAccountIds(_portal.getUserId(httpServletRequest)),
			filter.getKeywords(), null, false);
	}

	private long[] _getCommerceAccountIds(long userId) throws PortalException {
		if (!_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceAccountActionKeys.MANAGE_ALL_ACCOUNTS)) {

			List<CommerceAccount> commerceAccounts =
				_commerceAccountLocalService.getUserCommerceAccounts(
					userId, CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
					CommerceAccountConstants.SITE_TYPE_B2X, StringPool.BLANK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			return ListUtil.toLongArray(
				commerceAccounts, CommerceAccountModel::getCommerceAccountId);
		}

		return null;
	}

	private long[] _getCommerceChannelGroupIds(long companyId)
		throws PortalException {

		List<CommerceChannel> commerceChannels =
			_commerceChannelLocalService.searchCommerceChannels(companyId);

		Stream<CommerceChannel> stream = commerceChannels.stream();

		return stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();
	}

	private String _getDescriptiveAddress(CommerceShipment commerceShipment)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceShipment.fetchCommerceAddress();

		if (commerceAddress == null) {
			return StringPool.BLANK;
		}

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		StringBundler sb = new StringBundler((commerceRegion == null) ? 5 : 7);

		sb.append(commerceAddress.getStreet1());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getCity());
		sb.append(StringPool.NEW_LINE);

		if (commerceRegion != null) {
			sb.append(commerceRegion.getCode());
			sb.append(StringPool.SPACE);
		}

		sb.append(commerceAddress.getZip());

		return sb.toString();
	}

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_SHIPMENT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}