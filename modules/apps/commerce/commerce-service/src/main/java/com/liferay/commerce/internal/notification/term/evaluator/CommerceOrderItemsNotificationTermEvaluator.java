/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.util.CommerceOrderItemQuantityFormatter;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Danny Situ
 */
public class CommerceOrderItemsNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public CommerceOrderItemsNotificationTermEvaluator(
		CommerceMoneyFactory commerceMoneyFactory,
		CommerceOrderItemQuantityFormatter commerceOrderItemQuantityFormatter,
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceOrderPriceCalculation commerceOrderPriceCalculation,
		CompanyLocalService companyLocalService,
		CPInstanceUnitOfMeasureLocalService cpInstanceUnitOfMeasureLocalService,
		CPInstanceHelper cpInstanceHelper, Language language,
		ObjectDefinition objectDefinition, UserLocalService userLocalService) {

		_commerceMoneyFactory = commerceMoneyFactory;
		_commerceOrderItemQuantityFormatter =
			commerceOrderItemQuantityFormatter;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderPriceCalculation = commerceOrderPriceCalculation;
		_companyLocalService = companyLocalService;
		_cpInstanceUnitOfMeasureLocalService =
			cpInstanceUnitOfMeasureLocalService;
		_cpInstanceHelper = cpInstanceHelper;
		_language = language;
		_objectDefinition = objectDefinition;
		_userLocalService = userLocalService;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) ||
			!termName.equals("[%COMMERCEORDER_ORDER_ITEMS%]") ||
			!"CommerceOrder".equalsIgnoreCase(
				_objectDefinition.getShortName())) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				GetterUtil.getLong(termValues.get("id")));

		User user = _userLocalService.getUser(commerceOrder.getUserId());

		Locale locale = user.getLocale();

		StringBundler orderItemsTableSB = new StringBundler(
			"<table><tr><th style=\"text-align: left;");

		orderItemsTableSB.append("padding-bottom: 20px;\">");
		orderItemsTableSB.append(_language.get(locale, "order-items"));
		orderItemsTableSB.append("</th></tr>");

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CommerceOrderItemPrice commerceOrderItemPrice =
				_commerceOrderPriceCalculation.getCommerceOrderItemPrice(
					commerceOrder.getCommerceCurrency(), commerceOrderItem);

			orderItemsTableSB.append("<tr><td style=\"padding-bottom:15px;\"");
			orderItemsTableSB.append("><img src=\"");

			Company company = _companyLocalService.getCompany(
				user.getCompanyId());

			orderItemsTableSB.append(company.getPortalURL(0));

			try {
				String imageSource =
					_cpInstanceHelper.getCPInstanceThumbnailSrc(
						AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
						commerceOrderItem.getCPInstanceId());

				orderItemsTableSB.append(imageSource);
			}
			catch (Exception exception) {
				throw new PortalException(exception.getMessage());
			}

			orderItemsTableSB.append("\" style=\"border-radius: 8px;");
			orderItemsTableSB.append("width: 100px; height: 100px;\"></td>");
			orderItemsTableSB.append("<td style=\"vertical-align: top; ");
			orderItemsTableSB.append("padding-right: 100px;\"><table>");
			orderItemsTableSB.append("<tr><th style=\"text-align: left;");
			orderItemsTableSB.append("font-size: 16px;\">");
			orderItemsTableSB.append(commerceOrderItem.getName(locale));
			orderItemsTableSB.append("</th></tr>");

			String options = getOptions(
				commerceOrderItem, _cpInstanceHelper, locale);

			if (Validator.isNotNull(options)) {
				orderItemsTableSB.append("<tr style=\"font-size: 12px;\">");
				orderItemsTableSB.append("<td><strong>");
				orderItemsTableSB.append(_language.get(locale, "option"));
				orderItemsTableSB.append(":</strong> ");
				orderItemsTableSB.append(options);
				orderItemsTableSB.append("</td></tr>");
			}

			orderItemsTableSB.append("<tr style=\"font-size: 12px;\">");
			orderItemsTableSB.append("<td><strong>");
			orderItemsTableSB.append(_language.get(locale, "sku"));
			orderItemsTableSB.append(":</strong> ");
			orderItemsTableSB.append(commerceOrderItem.getSku());
			orderItemsTableSB.append("</td></tr>");

			String uom = commerceOrderItem.getUnitOfMeasureKey();

			if (Validator.isNotNull(uom)) {
				orderItemsTableSB.append("<tr style=\"font-size: 12px;\">");
				orderItemsTableSB.append("<td><strong>");
				orderItemsTableSB.append(_language.get(locale, "uom"));
				orderItemsTableSB.append(":</strong> ");
				orderItemsTableSB.append(uom);
				orderItemsTableSB.append("</td></tr>");
			}

			orderItemsTableSB.append("<tr style=\"font-size: 12px;\">");
			orderItemsTableSB.append("<td><strong>");
			orderItemsTableSB.append(_language.get(locale, "qty"));
			orderItemsTableSB.append(":</strong> ");
			orderItemsTableSB.append(
				_commerceOrderItemQuantityFormatter.format(
					commerceOrderItem,
					_cpInstanceUnitOfMeasureLocalService.
						fetchCPInstanceUnitOfMeasure(
							commerceOrderItem.getCPInstanceId(),
							commerceOrderItem.getUnitOfMeasureKey()),
					locale));
			orderItemsTableSB.append("</td></tr></table></td>");
			orderItemsTableSB.append("<td style=\"vertical-align: top;\">");
			orderItemsTableSB.append("<table>");
			orderItemsTableSB.append("<tr><td style=\"font-size: 16px;\">");

			BigDecimal discountAmount = commerceOrderItem.getDiscountAmount();
			BigDecimal finalPrice = commerceOrderItem.getFinalPrice();
			CommerceMoney finalPriceCommerceMoney =
				commerceOrderItemPrice.getFinalPrice();
			BigDecimal promoPrice = commerceOrderItem.getPromoPrice();

			if (discountAmount.compareTo(new BigDecimal(0)) > 0) {
				orderItemsTableSB.append("<s>");

				CommerceMoney originalPriceCommerceMoney =
					_commerceMoneyFactory.create(
						commerceOrder.getCommerceCurrency(),
						finalPrice.add(
							discountAmount.multiply(
								commerceOrderItem.getQuantity())));

				orderItemsTableSB.append(
					originalPriceCommerceMoney.format(locale));

				orderItemsTableSB.append("</s></td></tr>");
				orderItemsTableSB.append("<tr><td style=\"color:#FF0000;");
				orderItemsTableSB.append("font-size: 16px;\">");
				orderItemsTableSB.append(
					finalPriceCommerceMoney.format(locale));
				orderItemsTableSB.append("</td></tr>");
			}
			else if (promoPrice.compareTo(new BigDecimal(0)) > 0) {
				orderItemsTableSB.append("<s>");

				CommerceMoney originalPriceCommerceMoney =
					_commerceMoneyFactory.create(
						commerceOrder.getCommerceCurrency(),
						finalPrice.add(
							promoPrice.multiply(
								commerceOrderItem.getQuantity())));

				orderItemsTableSB.append(
					originalPriceCommerceMoney.format(locale));

				orderItemsTableSB.append("</s></td></tr>");
				orderItemsTableSB.append("<tr><td style=\"color:#FF0000;");
				orderItemsTableSB.append("font-size: 16px;\">");
				orderItemsTableSB.append(
					finalPriceCommerceMoney.format(locale));
				orderItemsTableSB.append("</td></tr>");
			}
			else {
				orderItemsTableSB.append(
					finalPriceCommerceMoney.format(locale));
				orderItemsTableSB.append("</td></tr>");
			}

			orderItemsTableSB.append("</table></td></tr>");
		}

		orderItemsTableSB.append("</table>");

		return orderItemsTableSB.toString();
	}

	public String getOptions(
			CommerceOrderItem commerceOrderItem,
			CPInstanceHelper cpInstanceHelper, Locale locale)
		throws PortalException {

		StringJoiner stringJoiner = new StringJoiner(
			StringPool.COMMA_AND_SPACE);

		List<KeyValuePair> commerceOptionValueKeyValuePairs =
			cpInstanceHelper.getKeyValuePairs(
				_getCommerceOrderItemCPDefinitionId(commerceOrderItem),
				commerceOrderItem.getJson(), locale);

		for (KeyValuePair keyValuePair : commerceOptionValueKeyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}

		return stringJoiner.toString();
	}

	private long _getCommerceOrderItemCPDefinitionId(
		CommerceOrderItem commerceOrderItem) {

		if (!commerceOrderItem.hasParentCommerceOrderItem()) {
			return commerceOrderItem.getCPDefinitionId();
		}

		return commerceOrderItem.getParentCommerceOrderItemCPDefinitionId();
	}

	private final CommerceMoneyFactory _commerceMoneyFactory;
	private final CommerceOrderItemQuantityFormatter
		_commerceOrderItemQuantityFormatter;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderPriceCalculation _commerceOrderPriceCalculation;
	private final CompanyLocalService _companyLocalService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;
	private final Language _language;
	private final ObjectDefinition _objectDefinition;
	private final UserLocalService _userLocalService;

}