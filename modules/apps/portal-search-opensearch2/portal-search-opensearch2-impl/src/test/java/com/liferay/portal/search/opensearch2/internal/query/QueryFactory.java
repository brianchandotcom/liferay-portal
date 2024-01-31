/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.query;

import org.opensearch.client.opensearch._types.query_dsl.Query;

/**
 * @author André de Oliveira
 */
public interface QueryFactory {

	public Query create(String name, String text);

}