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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheMissEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheMissEntryUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Minhchau Dang
 */
@RunWith(Arquillian.class)
public class CacheMissPersistenceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_persistence = CacheMissEntryUtil.getPersistence();

		Bundle bundle = FrameworkUtil.getBundle(
			CacheMissPersistenceImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			CTService.class,
			new CTService<CacheMissEntry>() {

				@Override
				public CTPersistence<CacheMissEntry> getCTPersistence() {
					return _persistence;
				}

				@Override
				public Class<CacheMissEntry> getModelClass() {
					return CacheMissEntry.class;
				}

				@Override
				public <R, E extends Throwable> R updateWithUnsafeFunction(
					UnsafeFunction<CTPersistence<CacheMissEntry>, R, E>
						updateUnsafeFunction) {

					return null;
				}

			},
			null);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testCacheMissIfCTProductionModeDisabled() throws Throwable {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			CacheMissPersistenceImplTest.class.getSimpleName(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_ctEntryLocalService.addCTEntry(
				_ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(
					CacheMissEntry.class.getName()),
				CacheMissEntryUtil.create(RandomTestUtil.nextLong()),
				TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_ADDITION);

			Assert.assertTrue(_fetchByPrimaryKeys().isEmpty());
		}
	}

	@Test
	public void testCacheMissIfCTProductionModeEnabled() throws Throwable {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			Assert.assertTrue(_fetchByPrimaryKeys().isEmpty());
		}
	}

	private Map<Serializable, CacheMissEntry> _fetchByPrimaryKeys() {
		Set<Serializable> primaryKeys = new HashSet<>();

		for (long pk = -1; pk > -2000; pk--) {
			primaryKeys.add(pk);
		}

		return _persistence.fetchByPrimaryKeys(primaryKeys);
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	private static CacheMissEntryPersistence _persistence;
	private static ServiceRegistration<CTService> _serviceRegistration;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTEntry _ctEntry;

}