/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.salesforce.connector.internal.instance.lifecycle;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.talend.archive.TalendArchiveParserUtil;
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.FileInputStream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = "service.ranking:Integer=100",
	service = PortalInstanceLifecycleListener.class
)
public class AddSalesforceConnectorInitialRequestPortalInstanceLifecycleListener
	extends InitialRequestPortalInstanceLifecycleListener {

	@Activate
	@Override
	protected void activate(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(null);

		try {
			_addDispatchTrigger(
				companyId, "etl-salesforce-account-connector-0.4.zip");
			_addDispatchTrigger(
				companyId, "etl-salesforce-order-connector-0.7.zip");
			_addDispatchTrigger(
				companyId, "etl-salesforce-price-list-connector-0.7.zip");
			_addDispatchTrigger(
				companyId, "etl-salesforce-product-connector-0.4.zip");
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private void _addDispatchTrigger(long companyId, String name)
		throws Exception {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(companyId, name);

		if (dispatchTrigger != null) {
			return;
		}

		Class<?> clazz = getClass();

		File connectorArchiveFile = FileUtil.createTempFile(
			clazz.getResourceAsStream("/" + name));

		try (FileInputStream fileInputStream = new FileInputStream(
				connectorArchiveFile)) {

			UnicodeProperties unicodeProperties = new UnicodeProperties();

			TalendArchiveParserUtil.updateUnicodeProperties(
				fileInputStream, unicodeProperties);

			long userId = _userLocalService.getGuestUserId(companyId);

			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				null, userId, _dispatchTaskExecutor, "talend",
				unicodeProperties, name, true);

			_dispatchFileRepository.addFileEntry(
				userId, dispatchTrigger.getDispatchTriggerId(), name, 0,
				"application/zip", new FileInputStream(connectorArchiveFile));
		}
		finally {
			FileUtil.delete(connectorArchiveFile);
		}
	}

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference(target = "(dispatch.task.executor.type=talend)")
	private DispatchTaskExecutor _dispatchTaskExecutor;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}