/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.document;

import com.liferay.portal.search.document.Document;

import org.opensearch.client.json.JsonData;

/**
 * @author Michael C. Han
 */
public interface OpenSearchDocumentFactory {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public JsonData getOpenSearchDocument(
		com.liferay.portal.kernel.search.Document document);

	public JsonData getOpenSearchDocument(Document document);

}