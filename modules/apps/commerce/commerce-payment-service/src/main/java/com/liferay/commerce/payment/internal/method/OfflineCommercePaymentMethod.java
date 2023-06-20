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

package com.liferay.commerce.payment.internal.method;

import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentMethodConstants;
import com.liferay.commerce.payment.internal.configuration.OfflineCommercePaymentMethodConfiguration;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	configurationPid = "com.liferay.commerce.payment.internal.configuration.OfflineCommercePaymentMethodConfiguration",
	service = CommercePaymentMethod.class
)
public class OfflineCommercePaymentMethod implements CommercePaymentMethod {

	@Override
	public CommercePaymentResult completePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		return new CommercePaymentResult(
			commercePaymentRequest.getTransactionId(),
			commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderPaymentConstants.STATUS_PENDING, false, null, null,
			Collections.emptyList(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getKey() {
		return _offlineCommercePaymentMethodConfiguration.key();
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(
			_getResourceBundle(locale),
			_offlineCommercePaymentMethodConfiguration.key());
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentMethodConstants.TYPE_OFFLINE;
	}

	@Override
	public String getServletPath() {
		return null;
	}

	@Override
	public boolean isCompleteEnabled() {
		return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return true;
	}

	@Override
	public CommercePaymentResult processPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		return new CommercePaymentResult(
			commercePaymentRequest.getTransactionId(),
			commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderPaymentConstants.STATUS_AUTHORIZED, false, null, null,
			Collections.emptyList(), true);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_offlineCommercePaymentMethodConfiguration =
			ConfigurableUtil.createConfigurable(
				OfflineCommercePaymentMethodConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		String key = getKey();

		if (key == null) {
			return;
		}

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRels(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			if (key.equals(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey())) {

				_commercePaymentMethodGroupRelLocalService.
					deleteCommercePaymentMethodGroupRel(
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId());
			}
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties)
		throws PortalException {

		_offlineCommercePaymentMethodConfiguration =
			ConfigurableUtil.createConfigurable(
				OfflineCommercePaymentMethodConfiguration.class, properties);

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRels(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			String key = (String)properties.get("key");

			if (key.equals(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey())) {

				_commercePaymentMethodGroupRelLocalService.
					deleteCommercePaymentMethodGroupRel(
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId());
			}
		}
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private Language _language;

	private volatile OfflineCommercePaymentMethodConfiguration
		_offlineCommercePaymentMethodConfiguration;

}