/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.SearchException;

/**
 * @author Michael C. Han
 */
public interface NGramHolderBuilder {

	public NGramHolder buildNGramHolder(String input) throws SearchException;

	public NGramHolder buildNGramHolder(String input, int nGramMaxLength)
		throws SearchException;

	public NGramHolder buildNGramHolder(
			String input, int nGramMinLength, int nGramMaxLength)
		throws SearchException;

}