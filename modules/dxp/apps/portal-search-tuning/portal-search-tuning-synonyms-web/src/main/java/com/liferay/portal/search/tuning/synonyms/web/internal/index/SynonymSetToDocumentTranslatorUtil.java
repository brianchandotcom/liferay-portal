/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;

/**
 * @author Adam Brandizzi
 */
public class SynonymSetToDocumentTranslatorUtil {

	public static Document translate(
		DocumentBuilderFactory documentBuilderFactory, SynonymSet synonymSet) {

		return documentBuilderFactory.builder(
		).setString(
			SynonymSetFields.SYNONYMS, synonymSet.getSynonyms()
		).setString(
			SynonymSetFields.UID, synonymSet.getSynonymSetDocumentId()
		).build();
	}

}