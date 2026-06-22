/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal;

/**
 * @author André de Oliveira
 */
public class SolrUnitTestRequirements {

	public static boolean isSolrExternallyStartedByDeveloper() {
		return Boolean.valueOf(
			System.getProperty(
				"com.liferay.portal.search.solr8.test.unit.started"));
	}

}