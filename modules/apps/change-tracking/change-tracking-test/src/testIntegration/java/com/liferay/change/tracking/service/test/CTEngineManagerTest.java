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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistrar;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.test.model.TestResourceModelClass;
import com.liferay.change.tracking.service.test.model.TestVersionModelClass;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class CTEngineManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_user = UserTestUtil.addUser();

		// If the test environment has change tracking enabled, then disable
		// change tracking for the first run

		if (_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId())) {

			_originallyEnabled = true;

			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}

		_testVersionClassName = _classNameLocalService.addClassName(
			TestVersionModelClass.class.getName());

		_testResourceClassName = _classNameLocalService.addClassName(
			TestResourceModelClass.class.getName());

		_ctDefinition = _ctDefinitionBuilder.setContentType(
			"Test Object"
		).setContentTypeLanguageKey(
			"test-object"
		).setEntityClasses(
			TestResourceModelClass.class, TestVersionModelClass.class
		).setResourceEntitiesByCompanyIdFunction(
			id -> Collections.emptyList()
		).setResourceEntityByResourceEntityIdFunction(
			id -> new TestResourceModelClass()
		).setEntityIdsFromResourceEntityFunctions(
			testResource -> 0L, testResource -> 0L
		).setVersionEntitiesFromResourceEntityFunction(
			testResource -> Collections.emptyList()
		).setVersionEntityByVersionEntityIdFunction(
			id -> new TestVersionModelClass()
		).setVersionEntityDetails(
			Collections.emptyList(), o -> RandomTestUtil.randomString(),
			o -> RandomTestUtil.randomString(), o -> 1L
		).setEntityIdsFromVersionEntityFunctions(
			testVersion -> 0L, testVersion -> 0L
		).setVersionEntityStatusInfo(
			new Integer[] {WorkflowConstants.STATUS_APPROVED},
			testVersion -> WorkflowConstants.STATUS_APPROVED
		).build();

		_ctDefinitionRegistrar.register(_ctDefinition);
	}

	@After
	public void tearDown() throws Exception {
		if (_ctDefinition != null) {
			_ctDefinitionRegistrar.unregister(_ctDefinition);
		}

		// If the change tracking was enabled originally, then leave it in the
		// same state

		if (_originallyEnabled) {
			_ctEngineManager.enableChangeTracking(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());
		}
		else {
			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}
	}

	@Test
	public void testCheckoutCTCollection() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getUserId(), "Test Change Tracking Collection",
			StringPool.BLANK, new ServiceContext());

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_user.getUserId(), !_user.isDefaultUser());

		String recentCTCollectionId = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId");

		Assert.assertNull(
			"Users's recent change tracking collection must be null",
			recentCTCollectionId);

		_ctEngineManager.checkoutCTCollection(
			_user.getUserId(), ctCollection.getCtCollectionId());

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_user.getUserId(), !_user.isDefaultUser());

		recentCTCollectionId = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId");

		Assert.assertEquals(
			"Users's recent change tracking collection must be properly set",
			GetterUtil.getLong(recentCTCollectionId),
			ctCollection.getCtCollectionId());
	}

	@Test
	public void testCheckoutCTCollectionWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_user.getUserId(), !_user.isDefaultUser());

		String originalRecentCTCollectionId = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId");

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getUserId(), "Test Change Tracking Collection",
			StringPool.BLANK, new ServiceContext());

		_ctEngineManager.checkoutCTCollection(
			_user.getUserId(), ctCollection.getCtCollectionId());

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_user.getUserId(), !_user.isDefaultUser());

		String recentCTCollectionId = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId");

		Assert.assertEquals(
			"Recent change tracking collection must not be changed",
			originalRecentCTCollectionId, recentCTCollectionId);
	}

	@Test
	public void testCreateCTCollection() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), name, description);

		Assert.assertTrue(ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		Assert.assertEquals(name, ctCollection.getName());
		Assert.assertEquals(description, ctCollection.getDescription());
	}

	@Test
	public void testCreateCTCollectionWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertFalse(
			"Change tracking collection must be null",
			ctCollectionOptional.isPresent());
	}

	@Test
	public void testDeleteCTCollection() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		_ctEngineManager.deleteCTCollection(ctCollection.getCtCollectionId());

		ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertNull(
			"Change tracking collection must be null", ctCollection);
	}

	@Ignore
	@Test
	public void testDeleteCTCollectionWhenInvalidCollectionId() {
		_ctEngineManager.deleteCTCollection(RandomTestUtil.randomInt());
	}

	@Test
	public void testDeleteProductionCTCollection() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		_ctEngineManager.deleteCTCollection(ctCollection.getCtCollectionId());

		ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertNotNull(
			"Change tracking collection must have a value", ctCollection);
	}

	@Test
	public void testDisableChangeTracking() throws PortalException {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		List<CTCollection> ctCollections =
			_ctCollectionLocalService.getCTCollections(
				TestPropsValues.getCompanyId(), null);

		Assert.assertEquals(
			"Change tracking collections must have one entry", 1,
			ctCollections.size());

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());

		ctCollections = _ctCollectionLocalService.getCTCollections(
			TestPropsValues.getCompanyId(), null);

		Assert.assertTrue(
			"Change tracking collection must not exist",
			ListUtil.isEmpty(ctCollections));
	}

	@Test
	public void testDisableChangeTrackingWhenChangeTrackingIsDisabled()
		throws PortalException {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testEnableChangeTracking() throws PortalException {
		int ctCollectionsCount =
			_ctCollectionLocalService.getCTCollectionsCount();

		Assert.assertEquals(
			"Change tracking collection number must be zero", 0,
			ctCollectionsCount);

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		List<CTCollection> ctCollections =
			_ctCollectionLocalService.getCTCollections(
				TestPropsValues.getCompanyId(), null);

		Assert.assertEquals(
			"Change tracking collections must have one entry", 1,
			ctCollections.size());

		CTCollection ctCollection = ctCollections.get(0);

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_NAME_PRODUCTION, ctCollection.getName());
	}

	@Test
	public void testEnableChangeTrackingWhenChangeTrackingIsEnabled()
		throws PortalException {

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testGetActiveCTCollectionOptional() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_user.getUserId(), !_user.isDefaultUser());

		portalPreferences.setValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId",
			String.valueOf(
				ctCollectionOptional.map(
					CTCollection::getCtCollectionId
				).orElse(
					0L
				)));

		Optional<CTCollection> activeCTCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(
				TestPropsValues.getCompanyId(), _user.getUserId());

		Assert.assertTrue(activeCTCollectionOptional.isPresent());
		Assert.assertEquals(
			"Change tracking collections must be equal",
			ctCollectionOptional.get(), activeCTCollectionOptional.get());
	}

	@Test
	public void testGetActiveCTCollectionOptionalWhenChangeTrackingIsDisabled()
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_user.getUserId(), !_user.isDefaultUser());

		portalPreferences.setValue(
			CTPortletKeys.CHANGE_LISTS, "recentCTCollectionId",
			String.valueOf(ctCollection.getCtCollectionId()));

		Optional<CTCollection> activeCTCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(
				TestPropsValues.getCompanyId(), _user.getUserId());

		Assert.assertFalse(
			"Change tracking collection must be null",
			activeCTCollectionOptional.isPresent());
	}

	@Test
	public void testGetCTCollectionOptional() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional1 =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(
			"Change tracking collection must not be null",
			ctCollectionOptional1.isPresent());

		CTCollection ctCollection = ctCollectionOptional1.get();

		Optional<CTCollection> ctCollectionOptional2 =
			_ctEngineManager.getCTCollectionOptional(
				ctCollection.getCtCollectionId());

		Assert.assertEquals(
			"Change tracking collections must be equal", ctCollection,
			ctCollectionOptional2.get());
	}

	@Test
	public void testGetCTCollections() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		List<CTCollection> ctCollections = _ctEngineManager.getCTCollections(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(
			"Change collections must have two entries", 2,
			ctCollections.size());
		Assert.assertTrue(ctCollections.contains(ctCollectionOptional.get()));

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(
			ctCollections.contains(productionCTCollectionOptional.get()));
	}

	@Test
	public void testGetCTCollectionsWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		_ctEngineManager.createCTCollection(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		List<CTCollection> collections = _ctEngineManager.getCTCollections(
			TestPropsValues.getCompanyId());

		Assert.assertTrue(
			"There must not be any change tracking collections",
			ListUtil.isEmpty(collections));
	}

	@Test
	public void testGetCTEntries() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(
			"Change tracking collection must be null",
			ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(
			ctCollection.getCtCollectionId());

		Assert.assertTrue(
			"There must not be any change tracking entries",
			ListUtil.isEmpty(ctEntries));

		CTEntry ctEntry = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			0, 0, CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollection.getCtCollectionId(), new ServiceContext());

		ctEntries = _ctEngineManager.getCTEntries(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			"There must be one change tracking entry", 1, ctEntries.size());
		Assert.assertEquals(ctEntry, ctEntries.get(0));
	}

	@Test
	public void testGetCTEntriesWithIndexing() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());

		CTEntry ctEntry1 = _ctEntryLocalService.addCTEntry(
			_user.getUserId(), _testVersionClassName.getClassNameId(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollection.getCtCollectionId(), new ServiceContext());
		CTEntry ctEntry2 = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			CTConstants.CT_CHANGE_TYPE_DELETION,
			ctCollection.getCtCollectionId(), new ServiceContext());

		OrderByComparator<CTEntry> orderByComparator =
			OrderByComparatorFactoryUtil.create("CTEntry", "createDate", true);

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setOrderByComparator(orderByComparator);

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(
			ctCollection, null, null,
			new long[] {_testVersionClassName.getClassNameId()}, null, false,
			queryDefinition);

		Assert.assertEquals(
			"There must be two change entries", 2, ctEntries.size());
		Assert.assertEquals(ctEntry1, ctEntries.get(0));
		Assert.assertEquals(ctEntry2, ctEntries.get(1));

		int ctEntriesCount = _ctEngineManager.getCTEntriesCount(
			ctCollection, null, null,
			new long[] {_testVersionClassName.getClassNameId()}, null, false,
			queryDefinition);

		Assert.assertEquals(
			"There must be two change entries", 2, ctEntriesCount);

		ctEntries = _ctEngineManager.getCTEntries(
			ctCollection, null, new long[] {_user.getUserId()}, null, null,
			false, queryDefinition);

		Assert.assertEquals(
			"There must be one change entry", 1, ctEntries.size());
		Assert.assertEquals(ctEntry1, ctEntries.get(0));

		ctEntriesCount = _ctEngineManager.getCTEntriesCount(
			ctCollection, null, new long[] {_user.getUserId()}, null, null,
			false, queryDefinition);

		Assert.assertEquals(
			"There must be one change entry", 1, ctEntriesCount);

		ctEntries = _ctEngineManager.getCTEntries(
			ctCollection, null, null, null,
			new int[] {CTConstants.CT_CHANGE_TYPE_DELETION}, false,
			queryDefinition);

		Assert.assertEquals(
			"There must be one change entry", 1, ctEntries.size());
		Assert.assertEquals(ctEntry2, ctEntries.get(0));

		ctEntriesCount = _ctEngineManager.getCTEntriesCount(
			ctCollection, null, null, null,
			new int[] {CTConstants.CT_CHANGE_TYPE_DELETION}, false,
			queryDefinition);

		Assert.assertEquals(
			"There must be one change entry", 1, ctEntriesCount);
	}

	@Test
	public void testGetProductionCTCollectionOptional() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(productionCTCollectionOptional.isPresent());

		CTCollection productionCTCollection =
			productionCTCollectionOptional.get();

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_NAME_PRODUCTION,
			productionCTCollection.getName());
	}

	@Test
	public void testGetProductionCTCollectionOptionalWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		Optional<CTCollection> productionCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertFalse(productionCollectionOptional.isPresent());
	}

	@Test
	public void testIsChangeTrackingEnabledWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testIsChangeTrackingEnabledWhenChangeTrackingIsEnabled()
		throws Exception {

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testIsChangeTrackingSupported() throws Exception {
		_ctDefinitionRegistrar.unregister(_ctDefinition);

		boolean changeTrackingSupported =
			_ctEngineManager.isChangeTrackingSupported(
				TestPropsValues.getCompanyId(), TestVersionModelClass.class);

		Assert.assertFalse(changeTrackingSupported);

		changeTrackingSupported = _ctEngineManager.isChangeTrackingSupported(
			TestPropsValues.getCompanyId(),
			_testVersionClassName.getClassNameId());

		Assert.assertFalse(changeTrackingSupported);

		_ctDefinitionRegistrar.register(_ctDefinition);

		changeTrackingSupported = _ctEngineManager.isChangeTrackingSupported(
			TestPropsValues.getCompanyId(), TestVersionModelClass.class);

		Assert.assertTrue(changeTrackingSupported);

		changeTrackingSupported = _ctEngineManager.isChangeTrackingSupported(
			TestPropsValues.getCompanyId(),
			_testVersionClassName.getClassNameId());

		Assert.assertTrue(changeTrackingSupported);
	}

	@Test
	public void testPublishCTCollection() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		CTEntry ctEntry = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			0, 0, CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollection.getCtCollectionId(), new ServiceContext());

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(productionCTCollectionOptional.isPresent());

		CTCollection productionCTCollection =
			productionCTCollectionOptional.get();

		List<CTEntry> productionCTEntries = _ctEngineManager.getCTEntries(
			productionCTCollection.getCtCollectionId());

		int originalCTEntriesCount = productionCTEntries.size();

		_ctEngineManager.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId(),
			true);

		productionCTEntries = _ctEngineManager.getCTEntries(
			productionCTCollection.getCtCollectionId());

		Assert.assertEquals(
			"Production change tracking collection must one more entry",
			originalCTEntriesCount + 1, productionCTEntries.size());
		Assert.assertTrue(
			"CTEntry must be published", productionCTEntries.contains(ctEntry));
	}

	@Test
	public void testPublishCTCollectionWhenChangeTrackingIsDisabled()
		throws Exception {

		Assert.assertFalse(
			_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId()));

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());

		_ctEngineManager.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId(),
			true);

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertFalse(
			"Production change tracking collection must be null",
			productionCTCollectionOptional.isPresent());
	}

	@Test
	public void testPublishCTCollectionWhenDraftEntryCollides()
		throws Exception {

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		long modelResourcePrimKey = RandomTestUtil.nextLong();

		Optional<CTCollection> ctCollectionOptionalA =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(ctCollectionOptionalA.isPresent());

		CTCollection ctCollectionA = ctCollectionOptionalA.get();

		CTEntry ctEntryA = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			0, modelResourcePrimKey, CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollectionA.getCtCollectionId(), new ServiceContext());

		Assert.assertFalse(ctEntryA.isCollision());

		Optional<CTCollection> ctCollectionOptionalB =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(ctCollectionOptionalB.isPresent());

		CTCollection ctCollectionB = ctCollectionOptionalB.get();

		CTEntry ctEntryB = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			1, modelResourcePrimKey, CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollectionB.getCtCollectionId(), new ServiceContext());

		Assert.assertFalse(ctEntryB.isCollision());

		_ctEngineManager.publishCTCollection(
			TestPropsValues.getUserId(), ctCollectionB.getCtCollectionId(),
			true);

		CTProcess ctProcess = _ctProcessLocalService.fetchLatestCTProcess(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL, ctProcess.getStatus());

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(productionCTCollectionOptional.isPresent());

		CTCollection productionCTCollection =
			productionCTCollectionOptional.get();

		List<CTEntry> productionCTEntries =
			_ctEntryLocalService.getCTCollectionCTEntries(
				productionCTCollection.getCtCollectionId());

		Assert.assertFalse(productionCTEntries.contains(ctEntryA));
		Assert.assertTrue(productionCTEntries.contains(ctEntryB));

		ctEntryA = _ctEntryLocalService.getCTEntry(ctEntryA.getCtEntryId());

		Assert.assertTrue(ctEntryA.isCollision());
	}

	@Ignore
	@Test
	public void testPublishCTCollectionWhenPublishedEntryCollides()
		throws Exception {

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		Assert.assertTrue(ctCollectionOptional.isPresent());

		CTCollection ctCollection = ctCollectionOptional.get();

		CTEntry ctEntry = _ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _testVersionClassName.getClassNameId(),
			0, 0, CTConstants.CT_CHANGE_TYPE_ADDITION,
			ctCollection.getCtCollectionId(), new ServiceContext());

		ctEntry.setCollision(true);

		ctEntry = _ctEntryLocalService.updateCTEntry(ctEntry);

		_ctEngineManager.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId(),
			true);

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(productionCTCollectionOptional.isPresent());

		CTCollection productionCTCollection =
			productionCTCollectionOptional.get();

		List<CTEntry> productionCTEntries =
			_ctEntryLocalService.getCTCollectionCTEntries(
				productionCTCollection.getCtCollectionId());

		Assert.assertFalse(productionCTEntries.contains(ctEntry));

		CTProcess ctProcess = _ctProcessLocalService.fetchLatestCTProcess(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED, ctProcess.getStatus());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private CTDefinition _ctDefinition;

	@Inject
	private CTDefinitionBuilder<TestResourceModelClass, TestVersionModelClass>
		_ctDefinitionBuilder;

	@Inject
	private CTDefinitionRegistrar _ctDefinitionRegistrar;

	@Inject
	private CTEngineManager _ctEngineManager;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CTManager _ctManager;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	private boolean _originallyEnabled;

	@DeleteAfterTestRun
	private ClassName _testResourceClassName;

	@DeleteAfterTestRun
	private ClassName _testVersionClassName;

	@DeleteAfterTestRun
	private User _user;

}