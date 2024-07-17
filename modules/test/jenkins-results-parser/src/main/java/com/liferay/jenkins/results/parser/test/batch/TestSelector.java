/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.test.suite.RelevantRuleConfigurationException;

/**
 * @author Kenji Heigel
 */
public interface TestSelector {

	public void merge(TestSelector testSelector);

	public void validate() throws RelevantRuleConfigurationException;

}