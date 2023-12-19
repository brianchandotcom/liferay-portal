/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.legacy.sort;

import com.liferay.portal.kernel.search.Sort;

import java.util.List;

import org.opensearch.client.opensearch._types.SortOptions;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface SortTranslator {

	public List<SortOptions> translateSorts(Sort[] sorts);

}