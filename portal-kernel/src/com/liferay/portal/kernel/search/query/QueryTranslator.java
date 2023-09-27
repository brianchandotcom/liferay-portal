/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
public interface QueryTranslator<T> {

	public T translate(Query query, SearchContext searchContext);

}