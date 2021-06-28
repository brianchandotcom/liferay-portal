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

package com.liferay.commerce.salesforce.connector.internal.instance.lifecycle;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.talend.archive.TalendArchiveParserUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	enabled = false, immediate = true, property = "service.ranking:Integer=100",
	service = PortalInstanceLifecycleListener.class
)
public class AddCommerceSalesforceConnectorPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_createDispatchTriggers(
			company.getCompanyId(), "etl-salesforce-account-connector-0.3.zip",
			"etl-salesforce-order-connector-0.6.zip",
			"etl-salesforce-price-list-connector-0.6.zip",
			"etl-salesforce-product-connector-0.3.zip");
	}

	@Activate
	protected void activate() {
		if (_log.isTraceEnabled()) {
			_log.trace("Activated against release " + _release.toString());
		}
	}

	private void _createDispatchTrigger(
			long companyId, String name, InputStream inputStream)
		throws Exception {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(companyId, name);

		if (dispatchTrigger != null) {
			return;
		}

		File connectorArchiveFile = FileUtil.createTempFile(inputStream);

		try (FileInputStream fileInputStream = new FileInputStream(
				connectorArchiveFile)) {

			UnicodeProperties unicodeProperties = new UnicodeProperties();

			TalendArchiveParserUtil.updateUnicodeProperties(
				fileInputStream, unicodeProperties);

			long userId = _userLocalService.getDefaultUserId(companyId);

			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				userId, "talend", unicodeProperties, name, true);

			_dispatchFileRepository.addFileEntry(
				userId, dispatchTrigger.getDispatchTriggerId(), name, 0,
				"application/zip", new FileInputStream(connectorArchiveFile));
		}
		finally {
			FileUtil.delete(connectorArchiveFile);
		}
	}

	private void _createDispatchTriggers(long companyId, String... names)
		throws Exception {

		Class<?> clazz = getClass();

		for (String name : names) {
			_createDispatchTrigger(
				companyId, name, clazz.getResourceAsStream("/" + name));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCommerceSalesforceConnectorPortalInstanceLifecycleListener.class);

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.document.library.service)(&(release.schema.version>=3.2.2)))"
	)
	private Release _release;

	@Reference
	private UserLocalService _userLocalService;

}