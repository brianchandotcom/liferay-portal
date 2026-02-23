/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Michael C. Han
 */
public interface ModelIndexerWriterContributor<T extends BaseModel<?>> {

	public default void customize(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		indexableActionableDynamicQuery.setPerformActionMethod(
			(T t) -> indexableActionableDynamicQuery.addDocument(
				modelIndexerWriterDocumentHelper.getDocument(t)));
	}

	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	public default IndexerWriterMode getIndexerWriterMode(T baseModel) {
		return null;
	}

	public default void modelDeleted(T baseModel) {
	}

	public default void modelIndexed(T baseModel) {
	}

	public default boolean shouldRun(long companyId) {
		return true;
	}

}