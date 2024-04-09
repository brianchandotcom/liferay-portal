/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 */
@Component(
	property = "batch.engine.task.item.delegate.name=ExportImportTaskResourcePerformanceTest",
	service = BatchEngineTaskItemDelegate.class
)
public class TestEntityBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<TestEntity> {

	@Override
	public Page<TestEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return Page.of(
			_testEntities.subList(
				pagination.getStartPosition(),
				Math.min(pagination.getEndPosition(), _testEntities.size())),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_testEntities.size());
	}

	protected void generate(int count) {
		for (int i = 0; i < count; i++) {
			TestEntity testEntity = new TestEntity();

			testEntity.setIntValue(RandomTestUtil.nextInt());
			testEntity.setTextValue(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

			_testEntities.add(testEntity);
		}
	}

	private final List<TestEntity> _testEntities = new ArrayList<>();

}