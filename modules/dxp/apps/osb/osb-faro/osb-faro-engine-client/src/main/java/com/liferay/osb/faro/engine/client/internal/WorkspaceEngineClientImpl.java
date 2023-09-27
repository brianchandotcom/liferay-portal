/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.internal;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.WorkspaceEngineClient;
import com.liferay.osb.faro.engine.client.model.Workspace;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shinn Lok
 */
@Component(service = WorkspaceEngineClient.class)
public class WorkspaceEngineClientImpl implements WorkspaceEngineClient {

	@Override
	public Workspace getWorkspace(String weDeployKey) throws Exception {
		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByWeDeployKey(weDeployKey);

		if (faroProject == null) {
			throw new Exception("Could not find project " + weDeployKey);
		}

		Workspace workspace = new Workspace();

		workspace.setReady(_isReady(faroProject));
		workspace.setWeDeployKey(weDeployKey);

		return workspace;
	}

	private boolean _isReady(FaroProject faroProject) {
		try {
			_contactsEngineClient.getIndividuals(
				faroProject, (String)null, false, 1, 0, null);

			return true;
		}
		catch (Exception exception) {
			_log.error(
				String.format(
					"Failed to check if workspace %s is ready",
					faroProject.getWeDeployKey()),
				exception);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkspaceEngineClientImpl.class);

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}