/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.messaging;

import com.liferay.osb.patcher.configuration.PatcherConfiguration;
import com.liferay.osb.patcher.util.PatcherUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

/**
 * @author Zsolt Balogh
 */
public class PatcherBuildSchedulerMessageListener extends BaseMessageListener {

	public static PatcherBuildSchedulerMessageListener getInstance() {
		return _patcherBuildSchedulerMessageListener;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		PatcherUtil.processOSBPatcherMessageQueue(companyId);

		PatcherConfiguration patcherConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				PatcherConfiguration.class, companyId);

		PatcherUtil.processOSBPatcherStatusFiles(
			companyId, patcherConfiguration.patcherStatusBuildJenkinsPath());

		PatcherUtil.processOSBPatcherStatusFiles(
			companyId,
			patcherConfiguration.patcherStatusBuildJenkinsTestPath());

		PatcherUtil.processOSBPatcherStatusFiles(
			companyId, patcherConfiguration.patcherStatusBuildPath());
	}

	private static final PatcherBuildSchedulerMessageListener
		_patcherBuildSchedulerMessageListener =
			new PatcherBuildSchedulerMessageListener();

}