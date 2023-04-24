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