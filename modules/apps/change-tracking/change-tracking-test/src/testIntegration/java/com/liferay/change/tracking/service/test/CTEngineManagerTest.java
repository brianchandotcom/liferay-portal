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
import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.configuration.CTConfigurationRegistrar;
import com.liferay.change.tracking.configuration.builder.CTConfigurationBuilder;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
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
	}

	@After
	public void tearDown() throws Exception {

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
	public void testDisableChangeTracking() throws PortalException {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		List<CTCollection> ctCollections =
			_ctCollectionLocalService.getCTCollections(
				TestPropsValues.getCompanyId());

		Assert.assertEquals(
			"Change tracking collections must have one entry", 1,
			ctCollections.size());

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());

		ctCollections = _ctCollectionLocalService.getCTCollections(
			TestPropsValues.getCompanyId());

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
				TestPropsValues.getCompanyId());

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
			_ctEngineManager.getActiveCTCollectionOptional(_user.getUserId());

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
			_ctEngineManager.getActiveCTCollectionOptional(_user.getUserId());

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
			TestPropsValues.getUserId(), 0, 0, 0,
			ctCollection.getCtCollectionId(), new ServiceContext());

		ctEntries = _ctEngineManager.getCTEntries(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			"There must be one change tracking entry", 1, ctEntries.size());
		Assert.assertEquals(ctEntry, ctEntries.get(0));
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
		ClassName testVersionClassName = null;
		CTConfiguration ctConfiguration = null;

		try {
			boolean changeTrackingSupported =
				_ctEngineManager.isChangeTrackingSupported(
					TestPropsValues.getCompanyId(), TestVersionClass.class);

			Assert.assertFalse(changeTrackingSupported);

			testVersionClassName = _classNameLocalService.addClassName(
				TestVersionClass.class.getName());

			changeTrackingSupported =
				_ctEngineManager.isChangeTrackingSupported(
					TestPropsValues.getCompanyId(),
					testVersionClassName.getClassNameId());

			Assert.assertFalse(changeTrackingSupported);

			ctConfiguration = _ctConfigurationBuilder.setContentType(
				"Test Object"
			).setContentTypeLanguageKey(
				"test-object"
			).setEntityClasses(
				Object.class, TestVersionClass.class
			).setResourceEntityByResourceEntityIdFunction(
				id -> new Object()
			).setEntityIdsFromResourceEntityFunctions(
				testResource -> 0L, testResource -> 0L
			).setVersionEntityByVersionEntityIdFunction(
				id -> new TestVersionClass()
			).setEntityIdsFromVersionEntityFunctions(
				testVersion -> 0L, testVersion -> 0L
			).setVersionEntityStatusInfo(
				new Integer[] {WorkflowConstants.STATUS_APPROVED},
				testVersion -> WorkflowConstants.STATUS_APPROVED
			).build();

			_ctConfigurationRegistrar.register(ctConfiguration);

			changeTrackingSupported =
				_ctEngineManager.isChangeTrackingSupported(
					TestPropsValues.getCompanyId(), TestVersionClass.class);

			Assert.assertTrue(changeTrackingSupported);

			changeTrackingSupported =
				_ctEngineManager.isChangeTrackingSupported(
					TestPropsValues.getCompanyId(),
					testVersionClassName.getClassNameId());

			Assert.assertTrue(changeTrackingSupported);
		}
		finally {
			if (testVersionClassName != null) {
				_classNameLocalService.deleteClassName(
					testVersionClassName.getClassNameId());
			}

			if (ctConfiguration != null) {
				_ctConfigurationRegistrar.unregister(ctConfiguration);
			}
		}
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
			TestPropsValues.getUserId(), 0, 0, 0,
			ctCollection.getCtCollectionId(), new ServiceContext());

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertTrue(productionCTCollectionOptional.isPresent());

		CTCollection productionCTCollection =
			productionCTCollectionOptional.get();

		List<CTEntry> productionCTEntries = _ctEngineManager.getCTEntries(
			productionCTCollection.getCtCollectionId());

		Assert.assertTrue(
			"Production change tracking collection must be empty",
			ListUtil.isEmpty(productionCTEntries));

		_ctEngineManager.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

		productionCTEntries = _ctEngineManager.getCTEntries(
			productionCTCollection.getCtCollectionId());

		Assert.assertEquals(
			"Production change tracking collection must have one entry", 1,
			productionCTEntries.size());
		Assert.assertEquals(ctEntry, productionCTEntries.get(0));
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
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				TestPropsValues.getCompanyId());

		Assert.assertFalse(
			"Production change tracking collection must be null",
			productionCTCollectionOptional.isPresent());
	}

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTConfigurationBuilder<Object, TestVersionClass>
		_ctConfigurationBuilder;

	@Inject
	private CTConfigurationRegistrar _ctConfigurationRegistrar;

	@Inject
	private CTEngineManager _ctEngineManager;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	private boolean _originallyEnabled;

	@DeleteAfterTestRun
	private User _user;

	private class TestVersionClass implements BaseModel<TestVersionClass> {

		@Override
		public Object clone() {
			return null;
		}

		@Override
		public int compareTo(TestVersionClass testVersionClass) {
			return 0;
		}

		@Override
		public ExpandoBridge getExpandoBridge() {
			return null;
		}

		@Override
		public Map<String, Object> getModelAttributes() {
			return null;
		}

		@Override
		public Class<?> getModelClass() {
			return null;
		}

		@Override
		public String getModelClassName() {
			return null;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return null;
		}

		@Override
		public boolean isCachedModel() {
			return false;
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return false;
		}

		@Override
		public boolean isEscapedModel() {
			return false;
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return false;
		}

		@Override
		public boolean isNew() {
			return false;
		}

		@Override
		public void resetOriginalValues() {
		}

		@Override
		public void setCachedModel(boolean cachedModel) {
		}

		@Override
		public void setExpandoBridgeAttributes(BaseModel baseModel) {
		}

		@Override
		public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		}

		@Override
		public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		}

		@Override
		public void setModelAttributes(Map attributes) {
		}

		@Override
		public void setNew(boolean n) {
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		}

		@Override
		public CacheModel<TestVersionClass> toCacheModel() {
			return null;
		}

		@Override
		public TestVersionClass toEscapedModel() {
			return null;
		}

		@Override
		public TestVersionClass toUnescapedModel() {
			return null;
		}

		@Override
		public String toXmlString() {
			return null;
		}

	}

}