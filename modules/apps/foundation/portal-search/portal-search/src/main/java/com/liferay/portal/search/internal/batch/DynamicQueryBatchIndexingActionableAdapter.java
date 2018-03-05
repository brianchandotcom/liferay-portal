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
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public void performActions() {
		try {
			_indexableActionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
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

	@Override
	public void setSearchEngineId(String searchEngineId) {
		_indexableActionableDynamicQuery.setSearchEngineId(searchEngineId);
	}

	private final IndexableActionableDynamicQuery
		_indexableActionableDynamicQuery;

}