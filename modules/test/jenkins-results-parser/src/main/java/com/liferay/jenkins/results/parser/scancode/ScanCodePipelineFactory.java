/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

/**
 * @author Brittney Nguyen
 */
public class ScanCodePipelineFactory {

	public static ScanCodePipeline newPipeline(
		String buildURL, String pipelineName) {

		if (pipelineName.equals("analyze_docker_image")) {
			return new AnalyzeDockerImagePipeline(buildURL, pipelineName);
		}
		else if (pipelineName.equals("inspect_packages")) {
			return new InspectPackagesPipeline(buildURL, pipelineName);
		}
		else if (pipelineName.equals("map_deploy_to_develop")) {
			return new MapDevelopToDeployPipeline(buildURL, pipelineName);
		}

		return null;
	}

}