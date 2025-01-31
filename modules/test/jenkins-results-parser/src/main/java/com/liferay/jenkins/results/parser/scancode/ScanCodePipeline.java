/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author Brittney Nguyen
 */
public interface ScanCodePipeline {

	public void execute() throws IOException, TimeoutException;

}