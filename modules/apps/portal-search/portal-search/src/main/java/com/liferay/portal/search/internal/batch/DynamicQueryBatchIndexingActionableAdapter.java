/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.batch;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;

import java.util.function.Consumer;

/**
 * @author André de Oliveira
 */
public class DynamicQueryBatchIndexingActionableAdapter
	implements BatchIndexingActionable {

	public DynamicQueryBatchIndexingActionableAdapter(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery) {

		_indexableActionableDynamicQuery = indexableActionableDynamicQuery;
	}

	@Override
	public void addDocuments(Document... documents) {
		try {
			_indexableActionableDynamicQuery.addDocuments(documents);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public void performActions() {
		try {
			_indexableActionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public void setAddCriteriaMethod(Consumer<DynamicQuery> consumer) {
		_indexableActionableDynamicQuery.setAddCriteriaMethod(consumer::accept);
	}

	@Override
	public void setCompanyId(long companyId) {
		_indexableActionableDynamicQuery.setCompanyId(companyId);
	}

	@Override
	public void setInterval(int interval) {
		_indexableActionableDynamicQuery.setInterval(interval);
	}

	@Override
	public <T> void setPerformActionMethod(Consumer<T> consumer) {
		_indexableActionableDynamicQuery.setPerformActionMethod(
			(T x) -> consumer.accept(x));
	}

	private final IndexableActionableDynamicQuery
		_indexableActionableDynamicQuery;

}