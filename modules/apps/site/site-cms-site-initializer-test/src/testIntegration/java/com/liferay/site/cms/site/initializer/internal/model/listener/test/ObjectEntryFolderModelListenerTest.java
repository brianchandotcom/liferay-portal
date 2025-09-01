/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.depot.model.DepotEntry;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.File;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jürgen Kappler
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {

			// Manually initialize the CMS site initializer until the feature
			// flag LPD-17564 is removed

			Role role = _roleLocalService.fetchRole(
				_group.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

			if (role == null) {
				SiteInitializer siteInitializer =
					_siteInitializerRegistry.getSiteInitializer(
						"com.liferay.site.initializer.cms");

				siteInitializer.initialize(_group.getGroupId());

				Bundle testBundle = FrameworkUtil.getBundle(
					ObjectEntryFolderModelListenerTest.class);

				BundleContext bundleContext = testBundle.getBundleContext();

				for (Bundle bundle : bundleContext.getBundles()) {
					if (!Objects.equals(
							bundle.getSymbolicName(),
							"com.liferay.site.initializer.cms")) {

						continue;
					}

					_setUpProcessedFile(bundle, "01.object.folder");
					_setUpProcessedFile(bundle, "02.object.definition");

					CompletableFuture<Void> completableFuture =
						_batchEngineUnitProcessor.processBatchEngineUnits(
							_batchEngineUnitReader.getBatchEngineUnits(bundle));

					completableFuture.join();
				}
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		_objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
				_group.getGroupId(), _group.getCreatorUserId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				"",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());
	}

	@FeatureFlag("LPD-17564")
	@Test
	public void testAddObjectEntryFolder() throws Exception {
		Map<Long, Set<String>> sourceRoleIdsToActionIds =
			_resourcePermissionLocalService.
				getAvailableResourcePermissionActionIds(
					_objectEntryFolder.getCompanyId(),
					ObjectEntryFolder.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(_objectEntryFolder.getObjectEntryFolderId()),
					TransformUtil.transform(
						_resourceActionLocalService.getResourceActions(
							ObjectEntryFolder.class.getName()),
						ResourceAction::getActionId));

		Role role = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		Set<String> actionIds = sourceRoleIdsToActionIds.get(role.getRoleId());

		for (ResourceAction resourceAction :
				_resourceActionLocalService.getResourceActions(
					ObjectEntryFolder.class.getName())) {

			Assert.assertTrue(actionIds.contains(resourceAction.getActionId()));
		}

		Group group = _groupLocalService.getGroup(
			_objectEntryFolder.getGroupId());

		JSONObject jsonObject1 =
			CMSDefaultPermissionUtil.
				getCMSDefaultPermissionDefaultPermissionsJSONObject(
					group.getCompanyId(), group.getCreatorUserId(),
					group.getExternalReferenceCode(),
					DepotEntry.class.getName(), _filterFactory);

		ObjectEntry objectEntry =
			CMSDefaultPermissionUtil.fetchCMSDefaultPermission(
				_objectEntryFolder.getCompanyId(),
				_objectEntryFolder.getUserId(),
				_objectEntryFolder.getExternalReferenceCode(),
				_objectEntryFolder.getModelClassName(), _filterFactory);

		JSONObject jsonObject2 =
			CMSDefaultPermissionUtil.
				getCMSDefaultPermissionDefaultPermissionsJSONObject(
					_objectEntryFolder.getCompanyId(),
					_objectEntryFolder.getUserId(),
					_objectEntryFolder.getExternalReferenceCode(),
					_objectEntryFolder.getModelClassName(), _filterFactory);

		Assert.assertEquals(jsonObject1.toString(), jsonObject2.toString());

		jsonObject2.put(
			ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES,
			JSONUtil.put(
				RoleConstants.CMS_ADMINISTRATOR,
				JSONUtil.putAll(ActionKeys.UPDATE, ActionKeys.VIEW)));

		CMSDefaultPermissionUtil.addOrUpdateCMSDefaultPermission(
			objectEntry.getExternalReferenceCode(), objectEntry.getCompanyId(),
			objectEntry.getUserId(),
			_objectEntryFolder.getExternalReferenceCode(),
			_objectEntryFolder.getModelClassName(), jsonObject2);

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _group.getGroupId(),
				_group.getCreatorUserId(),
				_objectEntryFolder.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject3 =
			CMSDefaultPermissionUtil.
				getCMSDefaultPermissionDefaultPermissionsJSONObject(
					objectEntryFolder.getCompanyId(),
					objectEntryFolder.getUserId(),
					objectEntryFolder.getExternalReferenceCode(),
					objectEntryFolder.getModelClassName(), _filterFactory);

		Assert.assertEquals(jsonObject2.toString(), jsonObject3.toString());
		Assert.assertTrue(
			jsonObject3.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES));
	}

	@FeatureFlag("LPD-17564")
	@Test
	public void testDeleteObjectEntryFolder() throws Exception {
		int sharingEntriesCount =
			_sharingEntryLocalService.getSharingEntriesCount();

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _group.getGroupId(),
				_group.getCreatorUserId(),
				_objectEntryFolder.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		User user = UserTestUtil.addGroupAdminUser(_group);

		_sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, user.getUserId(),
			_portal.getClassNameId(ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), _group.getGroupId(),
			true, Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			sharingEntriesCount + 1,
			_sharingEntryLocalService.getSharingEntriesCount());

		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			objectEntryFolder.getObjectEntryFolderId());

		Assert.assertEquals(
			sharingEntriesCount,
			_sharingEntryLocalService.getSharingEntriesCount());

		Assert.assertNull(
			CMSDefaultPermissionUtil.fetchCMSDefaultPermission(
				objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
				objectEntryFolder.getExternalReferenceCode(),
				objectEntryFolder.getModelClassName(), _filterFactory));
	}

	private void _setUpProcessedFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.site.initializer.cms.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private FilterFactory<Predicate> _filterFactory;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

}