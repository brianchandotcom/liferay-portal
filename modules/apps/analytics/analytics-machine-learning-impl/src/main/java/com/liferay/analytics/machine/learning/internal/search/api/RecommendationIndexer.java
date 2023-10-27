/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.search.api;

/**
 * @author Riccardo Ferrari
 */
public interface RecommendationIndexer {

	public void createIndex(long companyId);

	public void dropIndex(long companyId);

	public String getIndexName(long companyId);

}