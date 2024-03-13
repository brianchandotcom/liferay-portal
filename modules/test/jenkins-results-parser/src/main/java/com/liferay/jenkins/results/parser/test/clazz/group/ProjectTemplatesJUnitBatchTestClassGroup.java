/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalTestClassJob;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ProjectTemplatesJUnitBatchTestClassGroup
	extends JUnitBatchTestClassGroup {

	protected ProjectTemplatesJUnitBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected ProjectTemplatesJUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected boolean ignore() {
		if (!isStableTestSuiteBatch() && testRelevantJUnitTestsOnly) {
			return true;
		}

		if (isStableTestSuiteBatch() && testRelevantJUnitTestsOnlyInStable) {
			return true;
		}

		return false;
	}

}