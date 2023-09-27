/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.batch;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = DynamicQueryBatchIndexingActionableFactory.class)
public class DynamicQueryBatchIndexingActionableFactoryImpl
	implements DynamicQueryBatchIndexingActionableFactory {

	@Override
	public BatchIndexingActionable getBatchIndexingActionable(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery) {

		return new DynamicQueryBatchIndexingActionableAdapter(
			indexableActionableDynamicQuery);
	}

}