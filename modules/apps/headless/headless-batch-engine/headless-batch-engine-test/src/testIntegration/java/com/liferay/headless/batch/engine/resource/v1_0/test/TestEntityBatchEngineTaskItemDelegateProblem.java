/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(
	property = "batch.engine.task.item.delegate.name=export-import-problem-thrower",
	service = BatchEngineTaskItemDelegate.class
)
public class TestEntityBatchEngineTaskItemDelegateProblem
	extends BaseBatchEngineTaskItemDelegate<TestEntity> {

	@Override
	public void create(
			Collection<TestEntity> items, Map<String, Serializable> parameters)
		throws Exception {

		batchEngineImportStrategy.apply(
			items, testEntity -> createTestEntityItem(testEntity, parameters));
	}

	public TestEntity createTestEntityItem(
			TestEntity testEntity, Map<String, Serializable> parameters)
		throws Exception {

		if (Objects.equals(
				parameters.get("importStrategy"),
				ImportTask.ImportStrategy.ON_ERROR_CONTINUE.getValue())) {

			throw new TestEntityException(
				"error message for TestEntity '" + testEntity.getTextValue() +
					"'");
		}

		throw new TestEntityException("error message");
	}

	@Override
	public Page<TestEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

}