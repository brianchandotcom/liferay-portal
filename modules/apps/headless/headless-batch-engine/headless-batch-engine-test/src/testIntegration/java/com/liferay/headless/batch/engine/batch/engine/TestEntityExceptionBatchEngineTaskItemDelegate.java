/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.batch.engine;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.batch.engine.entity.TestEntity;
import com.liferay.headless.batch.engine.exception.TestEntityException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(
	property = "batch.engine.task.item.delegate.name=export-import-task-resource-exception",
	service = BatchEngineTaskItemDelegate.class
)
public class TestEntityExceptionBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<TestEntity> {

	@Override
	public TestEntity createItem(
			TestEntity testEntity, Map<String, Serializable> parameters)
		throws Exception {

		throw new TestEntityException(
			"error message for TestEntity '" + testEntity.getTextValue() + "'");
	}

	@Override
	public Page<TestEntity> read(
		Filter filter, Pagination pagination, Sort[] sorts,
		Map<String, Serializable> parameters, String search) {

		return null;
	}

}