/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.validator;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.constants.CPDefinitionLinkTypeConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceModel;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.order.validator.key=" + BundleProductCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=60"
	},
	service = CommerceOrderValidator.class
)
public class BundleProductCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "bundle-product";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			String json, BigDecimal quantity, boolean child)
		throws PortalException {

		return _getCommerceOrderValidatorResult(
			child, cpInstance, json, locale);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return new CommerceOrderValidatorResult(true);
	}

	private CommerceOrderValidatorResult _getCommerceOrderValidatorResult(
			boolean child, CPInstance cpInstance, String json, Locale locale)
		throws PortalException {

		if (child) {
			return new CommerceOrderValidatorResult(true);
		}

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		List<CommerceOptionValue> commerceOptionValues =
			_commerceOptionValueHelper.getCPDefinitionCommerceOptionValues(
				cpInstance.getCPDefinitionId(), json);

		List<Long> cpDefinitionIds = TransformUtil.transform(
			ListUtil.filter(
				TransformUtil.transform(
					commerceOptionValues,
					commerceOptionValue ->
						_cpInstanceLocalService.fetchCPInstance(
							commerceOptionValue.getCPInstanceId())),
				Objects::nonNull),
			CPInstanceModel::getCPDefinitionId);

		for (CommerceOptionValue commerceOptionValue : commerceOptionValues) {
			if (commerceOptionValue.getCPInstanceId() <= 0) {
				continue;
			}

			String languageId = LocaleUtil.toLanguageId(locale);

			CPInstance commerceOptionValueCPInstance =
				_cpInstanceLocalService.getCPInstance(
					commerceOptionValue.getCPInstanceId());

			List<CPDefinitionLink> incompatibleInBundleCPDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					commerceOptionValueCPInstance.getCPDefinitionId(),
					CPDefinitionLinkTypeConstants.INCOMPATIBLE_IN_BUNDLE,
					WorkflowConstants.STATUS_APPROVED);

			for (CPDefinitionLink cpDefinitionLink :
					incompatibleInBundleCPDefinitionLinks) {

				CProduct cProduct = cpDefinitionLink.getCProduct();

				if (cpDefinitionIds.contains(
						cProduct.getPublishedCPDefinitionId())) {

					CPDefinition cpDefinition =
						cpDefinitionLink.getCPDefinition();
					CPDefinition publishedCPDefinition =
						_cpDefinitionLocalService.getCPDefinition(
							cProduct.getPublishedCPDefinitionId());

					return new CommerceOrderValidatorResult(
						false,
						_getLocalizedMessage(
							locale, "x-cannot-be-combined-with-x",
							new Object[] {
								publishedCPDefinition.getName(languageId),
								cpDefinition.getName(languageId)
							}));
				}
			}

			List<CPDefinitionLink> requiresInBundleCPDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					commerceOptionValueCPInstance.getCPDefinitionId(),
					CPDefinitionLinkTypeConstants.REQUIRES_IN_BUNDLE,
					WorkflowConstants.STATUS_APPROVED);

			for (CPDefinitionLink cpDefinitionLink :
					requiresInBundleCPDefinitionLinks) {

				CProduct cProduct = cpDefinitionLink.getCProduct();

				if (!cpDefinitionIds.contains(
						cProduct.getPublishedCPDefinitionId())) {

					CPDefinition cpDefinition =
						cpDefinitionLink.getCPDefinition();
					CPDefinition publishedCPDefinition =
						_cpDefinitionLocalService.getCPDefinition(
							cProduct.getPublishedCPDefinitionId());

					return new CommerceOrderValidatorResult(
						false,
						_getLocalizedMessage(
							locale, "x-requires-x-to-be-purchased-also",
							new Object[] {
								cpDefinition.getName(languageId),
								publishedCPDefinition.getName(languageId)
							}));
				}
			}
		}

		return new CommerceOrderValidatorResult(true);
	}

	private String _getLocalizedMessage(
		Locale locale, String key, Object[] arguments) {

		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (arguments == null) {
			return _language.get(resourceBundle, key);
		}

		return _language.format(resourceBundle, key, arguments);
	}

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Language _language;

}