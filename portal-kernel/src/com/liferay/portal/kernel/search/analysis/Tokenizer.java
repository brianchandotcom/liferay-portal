/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.analysis;

import com.liferay.portal.kernel.search.SearchException;

import java.util.List;

/**
 * @author David Mendez Gonzalez
 */
public interface Tokenizer {

	public List<String> tokenize(
			String fieldName, String input, String languageId)
		throws SearchException;

}