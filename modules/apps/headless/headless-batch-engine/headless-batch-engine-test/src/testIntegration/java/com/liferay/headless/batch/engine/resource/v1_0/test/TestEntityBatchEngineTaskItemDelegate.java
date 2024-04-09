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
	property = "batch.engine.task.item.delegate.name=dummy-entity-performance-test",
	service = BatchEngineTaskItemDelegate.class
)
public class TestEntityBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<TestEntity> {

	public void generateTestData(int count) {
		for (int i = 0; i < count; i++) {
			TestEntity dummyEntity = new TestEntity();

			dummyEntity.setTextValue(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

			dummyEntity.setIntValue(RandomTestUtil.nextInt());

			_items.add(dummyEntity);
		}
	}

	@Override
	public Page<TestEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return Page.of(
			_items.subList(
				pagination.getStartPosition(),
				Math.min(pagination.getEndPosition(), _items.size())),
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_items.size());
	}

	private final List<TestEntity> _items = new ArrayList<>();

}