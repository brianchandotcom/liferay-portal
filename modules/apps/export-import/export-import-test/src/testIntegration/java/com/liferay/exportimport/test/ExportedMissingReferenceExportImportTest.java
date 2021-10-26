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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.exportimport.test.util.constants.DummyFolderPortletKeys;
import com.liferay.exportimport.test.util.exportimport.data.handler.DummyFolderPortletDataHandler;
import com.liferay.exportimport.test.util.exportimport.data.handler.DummyFolderWithMissingDummyPortletDataHandler;
import com.liferay.exportimport.test.util.exportimport.data.handler.DummyFolderWithMissingLayoutPortletDataHandler;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.exportimport.test.util.model.DummyReference;
import com.liferay.exportimport.test.util.model.util.DummyFolderTestUtil;
import com.liferay.exportimport.test.util.model.util.DummyReferenceTestUtil;
import com.liferay.exportimport.test.util.model.util.DummyTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.dao.orm.hibernate.DynamicQueryFactoryImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class ExportedMissingReferenceExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();

		_dummyFolderStagedModelRepository =
			(StagedModelRepository<DummyFolder>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyFolder.class.getName());

		_dummyReferenceStagedModelRepository =
			(StagedModelRepository<DummyReference>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyReference.class.getName());

		_dummyStagedModelRepository =
			(StagedModelRepository<Dummy>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					Dummy.class.getName());

		DynamicQueryFactoryUtil dynamicQueryFactoryUtil =
			new DynamicQueryFactoryUtil();

		dynamicQueryFactoryUtil.setDynamicQueryFactory(
			new DynamicQueryFactoryImpl() {

				@Override
				protected Class<?> getImplClass(
					Class<?> clazz, ClassLoader classLoader) {

					if (clazz.equals(DummyFolder.class)) {
						return DummyFolder.class;
					}

					return super.getImplClass(clazz, classLoader);
				}

			});

		DummyFolder dummyFolder =
			_dummyFolderStagedModelRepository.addStagedModel(
				null,
				DummyFolderTestUtil.createDummyFolder(group.getGroupId()));

		List<Dummy> dummies = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			dummies.add(
				_dummyStagedModelRepository.addStagedModel(
					null,
					DummyTestUtil.createDummy(
						group.getGroupId(), dummyFolder.getId())));
		}

		for (Dummy dummy : dummies) {
			List<DummyReference> dummyReferences = dummy.getDummyReferences();

			for (int i = 0; i < 3; i++) {
				dummyReferences.add(
					_dummyReferenceStagedModelRepository.addStagedModel(
						null,
						DummyReferenceTestUtil.createDummyReference(
							group.getGroupId())));
			}
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		_dummyFolderStagedModelRepository.deleteStagedModels(null);
		_dummyStagedModelRepository.deleteStagedModels(null);
	}

	@Test
	public void testMissingDummy() throws Exception {
		List<PortletDataHandler> portletDataHandlers = setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			DummyFolderWithMissingDummyPortletDataHandler.class);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		assertMissingReferences();

		setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			portletDataHandlers);
	}

	@Test
	public void testMissingDummyMissingDummyPDHFirst() throws Exception {
		_testMissingDummyOrder(true);
	}

	@Test
	public void testMissingDummyMissingDummyPDHSecond() throws Exception {
		_testMissingDummyOrder(false);
	}

	@Test
	public void testMissingLayout() throws Exception {
		List<PortletDataHandler> portletDataHandlers = setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			DummyFolderWithMissingLayoutPortletDataHandler.class);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		assertMissingReferences();

		setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			portletDataHandlers);
	}

	protected void assertMissingReferences() throws Exception {
		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				TestPropsValues.getCompanyId(), group.getGroupId(),
				getImportParameterMap(),
				ExportImportHelperUtil.getUserIdStrategy(
					TestPropsValues.getUserId(),
					TestUserIdStrategy.CURRENT_USER_ID),
				ZipReaderFactoryUtil.getZipReader(larFile));

		Element missingReferencesElement =
			portletDataContext.getMissingReferencesElement();

		List<Element> missingReferenceElements =
			missingReferencesElement.elements();

		Assert.assertFalse(
			missingReferenceElements.toString(),
			missingReferenceElements.isEmpty());
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		return getExportParameterMap();
	}

	protected int getPortletDataHandlerRank(Class<?> portletDataHandlerClass) {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportedMissingReferenceExportImportTest.class);

		ServiceTrackerList<PortletDataHandler> portletDataHandlerInstances =
			ServiceTrackerListFactory.open(
				bundle.getBundleContext(),
				(Class<PortletDataHandler>)portletDataHandlerClass);

		Assert.assertEquals(
			portletDataHandlerInstances.toString(), 1,
			portletDataHandlerInstances.size());

		Iterator<PortletDataHandler> iterator =
			portletDataHandlerInstances.iterator();

		PortletDataHandler portletDataHandlerInstance = iterator.next();

		return portletDataHandlerInstance.getRank();
	}

	protected List<PortletDataHandler> setPortletDataHandler(
			String portletId, Class<?> portletDataHandlerClass)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ExportedMissingReferenceExportImportTest.class);

		ServiceTrackerList<PortletDataHandler> portletDataHandlerInstances =
			ServiceTrackerListFactory.open(
				bundle.getBundleContext(),
				(Class<PortletDataHandler>)portletDataHandlerClass);

		Iterator<PortletDataHandler> iterator =
			portletDataHandlerInstances.iterator();

		List<PortletDataHandler> portletDataHandlerList = new ArrayList<>();

		iterator.forEachRemaining(portletDataHandlerList::add);

		return setPortletDataHandler(portletId, portletDataHandlerList);
	}

	protected List<PortletDataHandler> setPortletDataHandler(
			String portletId,
			List<PortletDataHandler> portletDataHandlerInstances)
		throws Exception {

		PortletBag portletBag = PortletBagPool.get(portletId);

		List<PortletDataHandler> oldDataHandlerInstances =
			portletBag.getPortletDataHandlerInstances();

		ReflectionTestUtil.setFieldValue(
			portletBag, "_portletDataHandlerInstances",
			new ServiceTrackerList<PortletDataHandler>() {

				@Override
				public void close() {
				}

				@Override
				public Iterator<PortletDataHandler> iterator() {
					return portletDataHandlerInstances.iterator();
				}

				@Override
				public int size() {
					return portletDataHandlerInstances.size();
				}

			});

		return oldDataHandlerInstances;
	}

	protected void setPortletDataHandlerRank(
		Class<?> portletDataHandlerClass, int rank) {

		Bundle bundle = FrameworkUtil.getBundle(
			ExportedMissingReferenceExportImportTest.class);

		ServiceTrackerList<PortletDataHandler> portletDataHandlerInstances =
			ServiceTrackerListFactory.open(
				bundle.getBundleContext(),
				(Class<PortletDataHandler>)portletDataHandlerClass);

		Assert.assertEquals(
			portletDataHandlerInstances.toString(), 1,
			portletDataHandlerInstances.size());

		Iterator<PortletDataHandler> iterator =
			portletDataHandlerInstances.iterator();

		PortletDataHandler portletDataHandlerInstance = iterator.next();

		portletDataHandlerInstance.setRank(rank);
	}

	private void _testMissingDummyOrder(boolean missingFirst) throws Exception {
		int dummyFolderPortletDataHandlerRank = getPortletDataHandlerRank(
			DummyFolderPortletDataHandler.class);
		int dummyFolderWithMissingDummyPortletDataHandlerRank =
			getPortletDataHandlerRank(
				DummyFolderWithMissingDummyPortletDataHandler.class);

		List<PortletDataHandler> portletDataHandlers = null;

		try {
			setPortletDataHandlerRank(
				DummyFolderPortletDataHandler.class, missingFirst ? 200 : 100);
			setPortletDataHandlerRank(
				DummyFolderWithMissingDummyPortletDataHandler.class,
				missingFirst ? 100 : 200);

			portletDataHandlers = setPortletDataHandler(
				DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
				DummyFolderWithMissingDummyPortletDataHandler.class);

			LayoutTestUtil.addPortletToLayout(
				layout,
				DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE);

			exportImportLayouts(
				new long[] {layout.getLayoutId()}, getImportParameterMap());

			if (missingFirst) {
				assertMissingReferences();
			}
		}
		finally {
			setPortletDataHandler(
				DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
				portletDataHandlers);

			setPortletDataHandlerRank(
				DummyFolderPortletDataHandler.class,
				dummyFolderPortletDataHandlerRank);
			setPortletDataHandlerRank(
				DummyFolderWithMissingDummyPortletDataHandler.class,
				dummyFolderWithMissingDummyPortletDataHandlerRank);
		}
	}

	private StagedModelRepository<DummyFolder>
		_dummyFolderStagedModelRepository;
	private StagedModelRepository<DummyReference>
		_dummyReferenceStagedModelRepository;
	private StagedModelRepository<Dummy> _dummyStagedModelRepository;

}