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

package com.liferay.portal.search.solr7.internal.document;

import com.liferay.portal.search.document.Document;

import org.apache.solr.common.SolrInputDocument;

/**
 * @author Michael C. Han
 */
public interface SolrDocumentFactory {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public SolrInputDocument getSolrInputDocument(
		com.liferay.portal.kernel.search.Document document);

	public SolrInputDocument getSolrInputDocument(Document document);

}