/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.avalara.connector.web.internal.util;

import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.commerce.avalara.connector.helper.CommerceAvalaraConnectorHelper;
import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.health.status.CommerceHealthHttpStatus;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Katie Nesterovich
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.health.status.display.order:Integer=70",
		"commerce.health.status.key=" + CommerceAvalaraConstants.AVALARA_TAX_CODES_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceHealthHttpStatus.class
)
public class AvalaraTaxCodesCommerceHealthHttpStatus
	implements CommerceHealthHttpStatus {

	@Override
	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			Callable<Object> avalaraTaxCodesCallable =
				new AvalaraTaxCodesCommerceHealthHttpStatus.
					AvalaraTaxCodesCallable(serviceContext);

			TransactionInvokerUtil.invoke(
				_transactionConfig, avalaraTaxCodesCallable);
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle,
			CommerceAvalaraConstants.
				AVALARA_TAX_CODES_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceAvalaraConstants.
			AVALARA_TAX_CODES_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle,
			CommerceAvalaraConstants.
				AVALARA_TAX_CODES_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public int getType() {
		return CommerceHealthStatusConstants.
			COMMERCE_HEALTH_STATUS_TYPE_VIRTUAL_INSTANCE;
	}

	@Override
	public boolean isFixed(long companyId, long commerceChannelId)
		throws PortalException {

		CPTaxCategory cpTaxCategory =
			_cpTaxCategoryLocalService.
				fetchCPTaxCategoryByExternalReferenceCode(
					companyId,
					CommerceAvalaraConstants.TANGIBLE_PERSONAL_PROPERTY);

		if (cpTaxCategory == null) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AvalaraTaxCodesCommerceHealthHttpStatus.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceAvalaraConnectorHelper _commerceAvalaraConnectorHelper;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference
	private Language _language;

	private class AvalaraTaxCodesCallable implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			try {
				_commerceAvalaraConnectorHelper.createTaxCategories(
					_serviceContext.getUserId());
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			return null;
		}

		private AvalaraTaxCodesCallable(ServiceContext serviceContext) {
			_serviceContext = serviceContext;
		}

		private final ServiceContext _serviceContext;

	}

}