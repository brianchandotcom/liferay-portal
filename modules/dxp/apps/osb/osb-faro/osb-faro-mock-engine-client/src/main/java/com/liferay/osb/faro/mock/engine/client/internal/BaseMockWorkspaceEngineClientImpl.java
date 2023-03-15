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

package com.liferay.osb.faro.mock.engine.client.internal;

import com.liferay.osb.faro.engine.client.WorkspaceEngineClient;
import com.liferay.osb.faro.engine.client.model.Workspace;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
public abstract class BaseMockWorkspaceEngineClientImpl
	implements WorkspaceEngineClient {

	@Override
	public Workspace getWorkspace(String weDeployKey) throws Exception {
		return workspaceEngineClient.getWorkspace(weDeployKey);
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.engine.client.internal.WorkspaceEngineClientImpl)"
	)
	protected WorkspaceEngineClient workspaceEngineClient;

}