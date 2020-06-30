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

package com.liferay.portal.search.internal.index.contributor.helper;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Adam Brandizzi
 */
public class ModelIndexerWriterDocumentHelperImpl
	implements ModelIndexerWriterDocumentHelper {

	public ModelIndexerWriterDocumentHelperImpl(
		String className, IndexerDocumentBuilder indexerDocumentBuilder) {

		_className = className;
		_indexerDocumentBuilder = indexerDocumentBuilder;
	}

	@Override
	public Document getDocument(BaseModel baseModel) {
		try {
			return _indexerDocumentBuilder.getDocument(baseModel);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to index ", _className, " with primary key ",
						baseModel.getPrimaryKeyObj()),
					exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelIndexerWriterDocumentHelperImpl.class);

	private final String _className;
	private final IndexerDocumentBuilder _indexerDocumentBuilder;

}