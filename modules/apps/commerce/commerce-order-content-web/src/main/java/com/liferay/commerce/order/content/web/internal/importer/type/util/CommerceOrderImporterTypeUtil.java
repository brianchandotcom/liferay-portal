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

package com.liferay.commerce.order.content.web.internal.importer.type.util;

import com.liferay.commerce.configuration.CommerceOrderImporterTypeConfiguration;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItemImpl;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.apache.commons.csv.CSVFormat;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class CommerceOrderImporterTypeUtil {

	public static List<CommerceOrderImporterItem> getCommerceOrderImporterItems(
			CommerceContextFactory commerceContextFactory,
			CommerceOrder commerceOrder,
			CommerceOrderImporterItemImpl[] commerceOrderImporterItemImpls,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderPriceCalculation commerceOrderPriceCalculation,
			CommerceOrderService commerceOrderService,
			UserLocalService userLocalService)
		throws Exception {

		CommerceOrder tempCommerceOrder = commerceOrderService.addCommerceOrder(
			commerceOrder.getGroupId(), commerceOrder.getCommerceAccountId(),
			commerceOrder.getCommerceCurrencyId(),
			commerceOrder.getCommerceOrderTypeId());

		tempCommerceOrder.setManuallyAdjusted(true);

		tempCommerceOrder = commerceOrderService.updateCommerceOrder(
			tempCommerceOrder);

		CommerceContext commerceContext = commerceContextFactory.create(
			tempCommerceOrder.getCompanyId(), tempCommerceOrder.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			tempCommerceOrder.getCommerceOrderId(),
			tempCommerceOrder.getCommerceAccountId());

		ServiceContext serviceContext = _getServiceContext(userLocalService);

		_addPreviousCommerceOrderItems(
			commerceContext, commerceOrder,
			tempCommerceOrder.getCommerceOrderId(), commerceOrderItemService,
			serviceContext);

		for (CommerceOrderImporterItemImpl commerceOrderImporterItemImpl :
				commerceOrderImporterItemImpls) {

			try {

				// Temporary commerce order item

				CommerceOrderItem commerceOrderItem =
					commerceOrderItemService.addOrUpdateCommerceOrderItem(
						tempCommerceOrder.getCommerceOrderId(),
						commerceOrderImporterItemImpl.getCPInstanceId(),
						commerceOrderImporterItemImpl.getJSON(),
						commerceOrderImporterItemImpl.getQuantity(), 0,
						commerceContext, serviceContext);

				commerceOrderImporterItemImpl.setCommerceOrderItemPrice(
					commerceOrderPriceCalculation.getCommerceOrderItemPrice(
						tempCommerceOrder.getCommerceCurrency(),
						commerceOrderItem));
			}
			catch (Exception exception) {
				if (exception instanceof CommerceOrderValidatorException) {
					CommerceOrderValidatorException
						commerceOrderValidatorException =
							(CommerceOrderValidatorException)exception;

					commerceOrderImporterItemImpl.setErrorMessages(
						TransformUtil.transformToArray(
							commerceOrderValidatorException.
								getCommerceOrderValidatorResults(),
							commerceOrderValidatorResult ->
								commerceOrderValidatorResult.
									getLocalizedMessage(),
							String.class));
				}

				if (exception instanceof PrincipalException) {
					commerceOrderImporterItemImpl.setErrorMessages(
						new String[] {
							LanguageUtil.get(
								serviceContext.getLocale(),
								"the-product-is-no-longer-available")
						});
				}
				else {
					String[] errorMessages =
						commerceOrderImporterItemImpl.getErrorMessages();

					if ((errorMessages == null) ||
						ArrayUtil.isNotEmpty(errorMessages)) {

						commerceOrderImporterItemImpl.setErrorMessages(
							TransformUtil.transform(
								errorMessages,
								errorMessage -> LanguageUtil.get(
									serviceContext.getLocale(), errorMessage),
								String.class));
					}
				}
			}
		}

		// Delete temporary commerce order

		commerceOrderService.deleteCommerceOrder(
			tempCommerceOrder.getCommerceOrderId());

		return ListUtil.fromArray(commerceOrderImporterItemImpls);
	}

	public static CSVFormat getCSVFormat(
		CommerceOrderImporterTypeConfiguration
			commerceOrderImporterTypeConfiguration) {

		CSVFormat csvFormat = CSVFormat.DEFAULT;

		String csvSeparator =
			commerceOrderImporterTypeConfiguration.csvSeparator();

		if (StringPool.SEMICOLON.equals(csvSeparator)) {
			csvFormat = csvFormat.withDelimiter(CharPool.SEMICOLON);
		}

		csvFormat = csvFormat.withFirstRecordAsHeader();
		csvFormat = csvFormat.withIgnoreEmptyLines();
		csvFormat = csvFormat.withIgnoreSurroundingSpaces();
		csvFormat = csvFormat.withNullString(StringPool.BLANK);

		return csvFormat;
	}

	private static void _addPreviousCommerceOrderItems(
		CommerceContext commerceContext, CommerceOrder commerceOrder,
		long tempCommerceOrderId,
		CommerceOrderItemService commerceOrderItemService,
		ServiceContext serviceContext) {

		try {
			for (CommerceOrderItem commerceOrderItem :
					commerceOrder.getCommerceOrderItems()) {

				commerceOrderItemService.addCommerceOrderItem(
					tempCommerceOrderId, commerceOrderItem.getCPInstanceId(),
					commerceOrderItem.getJson(),
					commerceOrderItem.getQuantity(), 0, commerceContext,
					serviceContext);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static ServiceContext _getServiceContext(
			UserLocalService userLocalService)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		User user = userLocalService.getUser(PrincipalThreadLocal.getUserId());

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setLanguageId(user.getLanguageId());
		serviceContext.setScopeGroupId(user.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderImporterTypeUtil.class);

}