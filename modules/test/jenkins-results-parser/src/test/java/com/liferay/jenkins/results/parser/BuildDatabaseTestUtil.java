/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseTestUtil {

	public static BuildDatabase addPortalAcceptancePR(
		BuildDatabaseArgs.Consumer... consumers) {

		BuildDatabaseArgs buildDatabaseArgs = new BuildDatabaseArgs();

		for (BuildDatabaseArgs.Consumer consumer : consumers) {
			consumer.accept(buildDatabaseArgs);
		}

		return _addBuildDatabase(buildDatabaseArgs);
	}

	private static BuildDatabase _addBuildDatabase(
		BuildDatabaseArgs buildDatabaseArgs) {

		InMemoryBuildDatabase inMemoryBuildDatabase =
			new InMemoryBuildDatabase();

		inMemoryBuildDatabase.setJSONObject(buildDatabaseArgs.getJSONObject());

		return inMemoryBuildDatabase;
	}

}