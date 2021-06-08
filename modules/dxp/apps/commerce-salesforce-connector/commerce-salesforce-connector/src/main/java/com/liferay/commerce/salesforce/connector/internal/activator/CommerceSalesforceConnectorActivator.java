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

package com.liferay.commerce.salesforce.connector.internal.activator;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.InputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(enabled = true, immediate = true, service = {})
public class CommerceSalesforceConnectorActivator {

	@Activate
	protected void activate() throws Exception {
		for (String connectorArchive : _ETL_SALESFORCE_CONNECTOR_ZIPS) {
			_companyLocalService.forEachCompanyId(
				companyId -> _createDispatchTrigger(
					companyId, connectorArchive,
					_getInputStream(connectorArchive)));
		}
	}

	private void _createDispatchTrigger(
			long companyId, String name, InputStream inputStream)
		throws PortalException {

		long userId = _userLocalService.getDefaultUserId(companyId);

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(companyId, name);

		if (dispatchTrigger != null) {
			return;
		}

		dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
			userId, "talend", new UnicodeProperties(), name, true);

		_dispatchFileRepository.addFileEntry(
			userId, dispatchTrigger.getDispatchTriggerId(), name, 0,
			"application/zip", inputStream);
	}

	private InputStream _getInputStream(String name) {
		Class<?> clazz = getClass();

		return clazz.getResourceAsStream("/" + name);
	}

	private static final String[] _ETL_SALESFORCE_CONNECTOR_ZIPS = {
		"etl-salesforce-account-connector-0.3.zip",
		"etl-salesforce-order-connector-0.6.zip",
		"etl-salesforce-price-list-connector-0.6.zip",
		"etl-salesforce-product-connector-0.3.zip"
	};

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}