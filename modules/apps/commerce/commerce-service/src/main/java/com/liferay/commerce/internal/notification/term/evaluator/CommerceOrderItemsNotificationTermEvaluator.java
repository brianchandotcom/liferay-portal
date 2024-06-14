/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
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
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.io.Writer;

import java.math.BigDecimal;

import java.net.URL;

import java.util.ArrayList;
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

		return _getOrderItemsTable(
			_commerceOrderLocalService.getCommerceOrder(
				GetterUtil.getLong(termValues.get("id"))));
	}

	private long _getCommerceOrderItemCPDefinitionId(
		CommerceOrderItem commerceOrderItem) {

		if (!commerceOrderItem.hasParentCommerceOrderItem()) {
			return commerceOrderItem.getCPDefinitionId();
		}

		return commerceOrderItem.getParentCommerceOrderItemCPDefinitionId();
	}

	private String _getOptions(
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

	private Map<String, Object> _getOrderItem(
			CommerceOrderItem commerceOrderItem,
			CommerceCurrency commerceCurrency, Locale locale)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"finalPrice",
			() -> {
				CommerceOrderItemPrice commerceOrderItemPrice =
					_commerceOrderPriceCalculation.getCommerceOrderItemPrice(
						commerceCurrency, commerceOrderItem);

				CommerceMoney finalPriceCommerceMoney =
					commerceOrderItemPrice.getFinalPrice();

				return finalPriceCommerceMoney.format(locale);
			}
		).put(
			"imageURL",
			() -> {
				User user = _userLocalService.getUser(
					commerceOrderItem.getUserId());

				Company company = _companyLocalService.getCompany(
					user.getCompanyId());

				return company.getPortalURL(0) +
					_cpInstanceHelper.getCPInstanceThumbnailSrc(
						AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
						commerceOrderItem.getCPInstanceId());
			}
		).put(
			"name", commerceOrderItem.getName(locale)
		).put(
			"options", _getOptions(commerceOrderItem, _cpInstanceHelper, locale)
		).put(
			"originalPrice",
			_getOriginalPrice(commerceOrderItem, commerceCurrency, locale)
		).put(
			"qty",
			_commerceOrderItemQuantityFormatter.format(
				commerceOrderItem,
				_cpInstanceUnitOfMeasureLocalService.
					fetchCPInstanceUnitOfMeasure(
						commerceOrderItem.getCPInstanceId(),
						commerceOrderItem.getUnitOfMeasureKey()),
				locale)
		).put(
			"sku", commerceOrderItem.getSku()
		).put(
			"uom", commerceOrderItem.getUnitOfMeasureKey()
		).build();
	}

	private List<Object> _getOrderItems(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		List<Object> orderItems = new ArrayList<>();

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			orderItems.add(
				_getOrderItem(
					commerceOrderItem, commerceOrder.getCommerceCurrency(),
					locale));
		}

		return orderItems;
	}

	private String _getOrderItemsTable(CommerceOrder commerceOrder)
		throws PortalException {

		Writer writer = new UnsyncStringWriter();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(
			"/META-INF/resources/notification/term/evaluator" +
				"/commerce_order_order_items.ftl");

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new URLTemplateResource(templateURL.getPath(), templateURL), false);

		_populateParameters(template, commerceOrder);

		template.processTemplate(writer);

		return writer.toString();
	}

	private String _getOriginalPrice(
			CommerceOrderItem commerceOrderItem,
			CommerceCurrency commerceCurrency, Locale locale)
		throws PortalException {

		BigDecimal discountAmount = commerceOrderItem.getDiscountAmount();
		BigDecimal finalPrice = commerceOrderItem.getFinalPrice();
		BigDecimal promoPrice = commerceOrderItem.getPromoPrice();

		if (discountAmount.compareTo(new BigDecimal(0)) > 0) {
			CommerceMoney originalPriceCommerceMoney =
				_commerceMoneyFactory.create(
					commerceCurrency,
					finalPrice.add(
						discountAmount.multiply(
							commerceOrderItem.getQuantity())));

			return originalPriceCommerceMoney.format(locale);
		}
		else if (promoPrice.compareTo(new BigDecimal(0)) > 0) {
			CommerceMoney originalPriceCommerceMoney =
				_commerceMoneyFactory.create(
					commerceCurrency,
					finalPrice.add(
						promoPrice.multiply(commerceOrderItem.getQuantity())));

			return originalPriceCommerceMoney.format(locale);
		}

		return StringPool.BLANK;
	}

	private void _populateParameters(
			Template template, CommerceOrder commerceOrder)
		throws PortalException {

		User user = _userLocalService.getUser(commerceOrder.getUserId());

		Locale locale = user.getLocale();

		template.put("orderItems", _getOrderItems(commerceOrder, locale));
		template.put("optionLabel", _language.get(locale, "option"));
		template.put("qtyLabel", _language.get(locale, "qty"));
		template.put("skuLabel", _language.get(locale, "sku"));
		template.put("tableLabel", _language.get(locale, "order-items"));
		template.put("uomLabel", _language.get(locale, "uom"));
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