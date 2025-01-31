/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

/**
 * @author Brittney Nguyen
 */
public class DefaultScanCodePipeline extends BaseScanCodePipeline {

	@Override
	public void execute() {
	}

	protected DefaultScanCodePipeline(String buildURL, String pipelineName) {
		super(buildURL, pipelineName);
	}

}