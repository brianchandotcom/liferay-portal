/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.commerce.address.CommerceAddressFormatter;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceShipment",
	service = ModelDocumentContributor.class
)
public class CommerceShipmentModelDocumentContributor
	implements ModelDocumentContributor<CommerceShipment> {

	@Override
	public void contribute(
		Document document, CommerceShipment commerceShipment) {

		try {
			document.addNumberSortable(
				Field.ENTRY_CLASS_PK, commerceShipment.getCommerceShipmentId());
			document.addKeyword(Field.STATUS, commerceShipment.getStatus());
			document.addKeyword("carrier", commerceShipment.getCarrier());
			document.addTextSortable("carrier", commerceShipment.getCarrier());
			document.addKeyword(
				"commerceAccountId", commerceShipment.getCommerceAccountId());
			document.addKeyword(
				"commerceAccountName", commerceShipment.getAccountEntryName(),
				true);

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceShipment.getGroupId());

			document.addKeyword(
				"commerceChannelId", commerceChannel.getCommerceChannelId());
			document.addKeyword(
				"commerceChannelName", commerceChannel.getName(), true);

			document.addKeyword(
				"commerceOrderIds",
				_getCommerceOrderIds(commerceShipment.getCommerceShipmentId()));

			Date expectedDate = commerceShipment.getExpectedDate();

			if (expectedDate != null) {
				document.addDate(
					"expectedDate", commerceShipment.getExpectedDate());
				document.addDateSortable(
					"expectedDate", commerceShipment.getExpectedDate());
			}

			document.addNumber(
				"itemsCount",
				_commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
					commerceShipment.getCommerceShipmentId()));

			CommerceAddress commerceAddress =
				_commerceAddressLocalService.fetchCommerceAddress(
					commerceShipment.getCommerceAddressId());

			if (commerceAddress != null) {
				document.addKeyword(
					"oneLineAddress",
					_commerceAddressFormatter.getOneLineAddress(
						commerceAddress));
			}

			Date shippingDate = commerceShipment.getShippingDate();

			if (shippingDate != null) {
				document.addDate(
					"shippingDate", commerceShipment.getShippingDate());
				document.addDateSortable(
					"shippingDate", commerceShipment.getShippingDate());
			}

			document.addKeyword(
				"trackingNumber", commerceShipment.getTrackingNumber());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index commerce shipment " +
						commerceShipment.getCommerceShipmentId(),
					exception);
			}
		}
	}

	private String[] _getCommerceOrderIds(long commerceShipmentId) {
		Set<String> commerceOrderIds = new HashSet<>();

		for (CommerceShipmentItem commerceShipmentItem :
				_commerceShipmentItemLocalService.getCommerceShipmentItems(
					commerceShipmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.fetchCommerceOrderItem(
					commerceShipmentItem.getCommerceOrderItemId());

			if (commerceOrderItem == null) {
				continue;
			}

			commerceOrderIds.add(
				String.valueOf(commerceOrderItem.getCommerceOrderId()));
		}

		return commerceOrderIds.toArray(new String[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentModelDocumentContributor.class);

	@Reference
	private CommerceAddressFormatter _commerceAddressFormatter;

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

}