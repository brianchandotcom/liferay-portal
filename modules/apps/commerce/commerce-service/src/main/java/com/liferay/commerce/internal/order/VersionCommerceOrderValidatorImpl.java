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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.order.validator.key=" + VersionCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=30"
	},
	service = CommerceOrderValidator.class
)
public class VersionCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "version";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			int quantity)
		throws PortalException {

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		if ((commerceOrderItem.getCPInstanceId() != 0) &&
			(commerceOrderItem.getCProductId() != 0)) {

			CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

			CProduct cProduct = commerceOrderItem.fetchCProduct();

			if ((cpInstance != null) && (cProduct != null) &&
				(cpInstance.getCPDefinitionId() !=
					cProduct.getPublishedCPDefinitionId()) &&
				(cProduct.getPublishedCPDefinitionId() != 0)) {

				boolean instanceUpdated = _updateInstance(
					commerceOrderItem, cProduct);

				if (instanceUpdated) {
					return new CommerceOrderValidatorResult(
						commerceOrderItem.getCommerceOrderItemId(), false,
						_getLocalizedMessage(
							locale,
							"this-product-will-be-automatically-updated-to-a-" +
								"newer-version"));
				}

				return new CommerceOrderValidatorResult(
					commerceOrderItem.getCommerceOrderItemId(), false,
					_getLocalizedMessage(
						locale,
						"there-is-a-newer-version-of-this-product-available"));
			}
		}

		return new CommerceOrderValidatorResult(true);
	}

	protected void setCommerceOrderItemLocalService(
		CommerceOrderItemLocalService commerceOrderItemLocalService) {

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	protected void setCPInstanceLocalService(
		CPInstanceLocalService cpInstanceLocalService) {

		_cpInstanceLocalService = cpInstanceLocalService;
	}

	private String _getLocalizedMessage(Locale locale, String key) {
		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, key);
	}

	private boolean _updateInstance(
			CommerceOrderItem commerceOrderItem, CProduct cProduct)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cProduct.getPublishedCPDefinitionId(), commerceOrderItem.getSku());

		if (cpInstance != null) {
			commerceOrderItem.setCPInstanceId(cpInstance.getCPInstanceId());

			_commerceOrderItemLocalService.updateCommerceOrderItem(
				commerceOrderItem);

			return true;
		}

		return false;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Language _language;

}