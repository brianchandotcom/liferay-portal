/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search;

import java.util.Collection;

/**
 * @author     Bruno Farache
 * @author     Raymond Augé
 * @author     Michael C. Han
 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
 *             SearchEngineHelperUtil}
 */
@Deprecated
public class SearchEngineUtil extends SearchEngineHelperUtil {

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             IndexWriterHelperUtil#updateDocument(String, long, Document,
	 *             boolean)}
	 */
	@Deprecated
	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		IndexWriterHelperUtil.updateDocument(
			searchEngineId, companyId, document, false);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             IndexWriterHelperUtil#updateDocuments(String, long,
	 *             Collection, boolean)}
	 */
	@Deprecated
	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		IndexWriterHelperUtil.updateDocuments(
			searchEngineId, companyId, documents, false);
	}

}