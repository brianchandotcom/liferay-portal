/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public <T extends BaseModel<?>> Document getDocument(T baseModel) {
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