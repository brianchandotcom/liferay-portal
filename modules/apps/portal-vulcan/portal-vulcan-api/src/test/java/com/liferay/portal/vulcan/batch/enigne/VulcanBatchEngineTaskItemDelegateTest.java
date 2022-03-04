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

package com.liferay.portal.vulcan.batch.enigne;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.strategy.BatchStrategy;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier de Arcos
 */
public class VulcanBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDefaultGetEntityClassName() {
		TestVulcanBatchEngineTaskItemDelegate
			testVulcanBatchEngineTaskItemDelegate =
				new TestVulcanBatchEngineTaskItemDelegate();

		Assert.assertEquals(
			TestEntity.class.getName(),
			testVulcanBatchEngineTaskItemDelegate.getEntityClassName());
	}

	private static class TestEntity {
	}

	private static class TestVulcanBatchEngineTaskItemDelegate
		implements VulcanBatchEngineTaskItemDelegate<TestEntity> {

		@Override
		public void create(
				Collection<TestEntity> items,
				Map<String, Serializable> parameters)
			throws Exception {
		}

		@Override
		public void delete(
				Collection<TestEntity> items,
				Map<String, Serializable> parameters)
			throws Exception {
		}

		@Override
		public EntityModel getEntityModel(
				Map<String, List<String>> multivaluedMap)
			throws Exception {

			return null;
		}

		@Override
		public Page<TestEntity> read(
				Filter filter, Pagination pagination, Sort[] sorts,
				Map<String, Serializable> parameters, String search)
			throws Exception {

			return null;
		}

		@Override
		public void setContextBatchStrategy(
			BatchStrategy contextBatchStrategy) {
		}

		@Override
		public void setContextCompany(Company contextCompany) {
		}

		@Override
		public void setContextUser(User contextUser) {
		}

		@Override
		public void setLanguageId(String languageId) {
		}

		@Override
		public void update(
				Collection<TestEntity> items,
				Map<String, Serializable> parameters)
			throws Exception {
		}

	}

}