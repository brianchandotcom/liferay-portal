/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

import java.util.function.Supplier;

/**
 * @author Michael C. Han
 */
public class ModelIndexerWriterContributor<T extends BaseModel<?>> {

	public ModelIndexerWriterContributor(
		IndexerWriterMode indexerWriterMode,
		Supplier<IndexableActionableDynamicQuery> supplier) {

		_indexerWriterMode = indexerWriterMode;
		_supplier = supplier;
	}

	public ModelIndexerWriterContributor(
		Supplier<IndexableActionableDynamicQuery> supplier) {

		this(null, supplier);
	}

	public void customize(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery,
		IndexerDocumentBuilder indexerDocumentBuilder) {

		indexableActionableDynamicQuery.setPerformActionMethod(
			(T t) -> indexerDocumentBuilder.getDocument(t));
	}

	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _supplier.get();
	}

	public IndexerWriterMode getIndexerWriterMode(T baseModel) {
		return _indexerWriterMode;
	}

	public void modelDeleted(T baseModel) {
	}

	public void modelIndexed(T baseModel) {
	}

	public boolean shouldRun(long companyId) {
		return true;
	}

	private final IndexerWriterMode _indexerWriterMode;
	private final Supplier<IndexableActionableDynamicQuery> _supplier;

}