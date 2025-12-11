/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify;

import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "run.on.portal.upgrade=true", service = VerifyProcess.class
)
public class PostUpgradeDataCleanupVerifyProcess extends VerifyProcess {

	@Override
	public void verify() throws VerifyException {
		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				try {
					super.verify();
				}
				finally {
					DBUpgrader.stopUpgradeLogAppender();
				}

				return null;
			});
	}

	@Override
	protected void doVerify() throws Exception {
		for (PostUpgradeDataCleanupProcess postUpgradeDataCleanupProcess :
				_getPostUpgradedataCleanupProcesses()) {

			postUpgradeDataCleanupProcess.cleanUp();
		}
	}

	private List<PostUpgradeDataCleanupProcess>
		_getPostUpgradedataCleanupProcesses() {

		return ListUtil.fromArray(
			new ClassNamePostUpgradeDataCleanupProcess(
				_classNameLocalService, connection),
			new PortletPreferencesPostUpgradeDataCleanupProcess(
				connection, _portletLocalService),
			new ResourceActionPostUpgradeDataCleanupProcess(
				connection, _resourceActionLocalService),
			new ServiceComponentPostUpgradeDataCleanupProcess(
				connection, _serviceComponentLocalService));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

}