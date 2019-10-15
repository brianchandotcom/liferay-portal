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

package com.liferay.portal.plugin.deployment.extension;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.tools.deploy.BaseDeployer;
import com.liferay.portal.tools.deploy.extension.DeploymentExtension;

import java.io.File;

/**
 * @author Shuyang Zhou
 */
public class WebLogicDeploymentExtension implements DeploymentExtension {

	@Override
	public void copyXmls(BaseDeployer baseDeployer, File srcFile)
		throws Exception {

		baseDeployer.copyDependencyXml("weblogic.xml", srcFile + "/WEB-INF");
	}

	@Override
	public String getServerId() {
		return ServerDetector.WEBLOGIC_ID;
	}

	@Override
	public void postDeploy(String destDir, String deployDir) {
	}

}