/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class SemVerModulesSegmentTestClassGroup
	extends ModulesSegmentTestClassGroup {

	protected SemVerModulesSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected SemVerModulesSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

}