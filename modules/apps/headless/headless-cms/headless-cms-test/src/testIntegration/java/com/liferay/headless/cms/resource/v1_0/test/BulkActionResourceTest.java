/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.headless.cms.client.dto.v1_0.BulkAction;
import com.liferay.headless.cms.client.dto.v1_0.BulkActionItem;
import com.liferay.headless.cms.client.dto.v1_0.BulkActionTask;
import com.liferay.headless.cms.client.dto.v1_0.DefaultPermissionBulkAction;
import com.liferay.headless.cms.client.dto.v1_0.DeleteBulkAction;
import com.liferay.headless.cms.client.problem.Problem;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @author Crescenzo Rega
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class BulkActionResourceTest extends BaseBulkActionResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		if (!_isCMSSiteInitialized()) {

			// These tests require the instance to be created with the feature
			// flag LPD-17564 enabled. On CI, feature flags are enabled on
			// demand for each test, but not during instance initialization.
			// Until the feature flag LPD-17564 is removed, run the batch
			// engine unit processor manually so that the object definitions
			// are created.

			Bundle testBundle = FrameworkUtil.getBundle(
				BulkActionResourceTest.class);

			BundleContext bundleContext = testBundle.getBundleContext();

			for (Bundle bundle : bundleContext.getBundles()) {
				if (!Objects.equals(
						bundle.getSymbolicName(),
						"com.liferay.site.initializer.cms")) {

					continue;
				}

				_deleteFile(bundle, "01.object.folder");
				_deleteFile(bundle, "02.object.definition");

				CompletableFuture<Void> completableFuture =
					_batchEngineUnitProcessor.processBatchEngineUnits(
						_batchEngineUnitReader.getBatchEngineUnits(bundle));

				completableFuture.join();
			}
		}

		_basicWebContentObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_BASIC_WEB_CONTENT", testCompany.getCompanyId());
		_bulkActionTaskObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_BULK_ACTION_TASK", testCompany.getCompanyId());
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));
		_importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).parameter(
			"siteId", String.valueOf(TestPropsValues.getGroupId())
		).build();
	}

	@Override
	@Test
	public void testPostBulkAction() throws Exception {
		_testPostBulkActionWithTypeDefaultPermission();
		_testPostBulkActionWithTypeDelete();
	}

	private void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.site.initializer.cms.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	private JSONObject _getDefaultPermissionsJSONObject(
			ObjectDefinition objectDefinition,
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		Predicate predicate = _filterFactory.create(
			StringBundler.concat(
				"(classExternalReferenceCode eq '",
				objectEntryFolder.getExternalReferenceCode(),
				"') and (className eq '", objectEntryFolder.getModelClassName(),
				"')"),
			objectDefinition);

		List<Long> primaryKeys = _objectEntryLocalService.getPrimaryKeys(
			new Long[0], _depotEntry.getCompanyId(),
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), predicate, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			primaryKeys.get(0));

		Map<String, Serializable> values = objectEntry.getValues();

		return JSONFactoryUtil.createJSONObject(
			String.valueOf(values.get("defaultPermissions")));
	}

	private Map<String, Serializable> _getImportTaskValues(
			BulkActionTask bulkActionTask)
		throws Exception {

		ObjectEntry bulkActionTaskObjectEntry =
			_objectEntryLocalService.getObjectEntry(bulkActionTask.getId());

		List<ObjectEntry> objectEntries = ListUtil.filter(
			_objectEntryLocalService.getOneToManyObjectEntries(
				bulkActionTaskObjectEntry.getGroupId(),
				_objectRelationshipLocalService.getObjectRelationship(
					_bulkActionTaskObjectDefinition.getObjectDefinitionId(),
					"bulkActionTaskToBulkActionTaskItems"
				).getObjectRelationshipId(),
				null, bulkActionTaskObjectEntry.getObjectEntryId(), true, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
			objectEntry -> Objects.equals(
				GetterUtil.getLong(
					objectEntry.getValues(
					).get(
						"r_bulkActionTaskToBulkActionTaskItems_c_" +
							"bulkActionTaskId"
					)),
				bulkActionTaskObjectEntry.getObjectEntryId()));

		ObjectEntry objectEntry = objectEntries.get(0);

		return objectEntry.getValues();
	}

	private boolean _isCMSSiteInitialized() throws Exception {
		ObjectFolder objectFolder =
			_objectFolderLocalService.fetchObjectFolderByExternalReferenceCode(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
				TestPropsValues.getCompanyId());

		if (objectFolder != null) {
			return true;
		}

		return false;
	}

	private void _testPostBulkActionWithTypeDefaultPermission()
		throws Exception {

		DefaultPermissionBulkAction defaultPermissionBulkAction =
			new DefaultPermissionBulkAction();

		defaultPermissionBulkAction.setDefaultPermissions(
			"{\"test1\": \"test1\"}");
		defaultPermissionBulkAction.setSelectAll(true);
		defaultPermissionBulkAction.setType(
			BulkAction.Type.DEFAULT_PERMISSION_BULK_ACTION);

		try {
			bulkActionResource.postBulkAction(
				null, null, defaultPermissionBulkAction);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertNotNull(problemException);
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_DEFAULT_PERMISSION", _depotEntry.getCompanyId());

		ObjectEntryFolder objectEntryFolder1 =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					_depotEntry.getGroupId(), _depotEntry.getCompanyId());

		ObjectEntryFolder objectEntryFolder2 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _depotEntry.getGroupId(),
				TestPropsValues.getUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder2);

		Assert.assertTrue(
			jsonObject.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS));

		ObjectEntryFolder objectEntryFolder3 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _depotEntry.getGroupId(),
				TestPropsValues.getUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder2);

		Assert.assertTrue(
			jsonObject.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS));

		defaultPermissionBulkAction.setDepotGroupId(_depotEntry.getGroupId());

		BulkActionTask bulkActionTask = bulkActionResource.postBulkAction(
			null, null, defaultPermissionBulkAction);

		Map<String, Serializable> values = _getImportTaskValues(bulkActionTask);

		_waitForFinish(GetterUtil.getLong(values.get("importTaskId")));

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder2);

		Assert.assertFalse(
			jsonObject.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS));
		Assert.assertTrue(jsonObject.has("test1"));

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder3);

		Assert.assertFalse(
			jsonObject.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS));
		Assert.assertTrue(jsonObject.has("test1"));

		ObjectEntryFolder objectEntryFolder4 =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _depotEntry.getGroupId(),
				TestPropsValues.getUserId(),
				objectEntryFolder3.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder4);

		Assert.assertFalse(
			jsonObject.has(
				ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS));
		Assert.assertTrue(jsonObject.has("test1"));

		defaultPermissionBulkAction.setDefaultPermissions(
			"{\"test2\": \"test2\"}");
		defaultPermissionBulkAction.setTreePath(
			objectEntryFolder3.getTreePath());

		bulkActionTask = bulkActionResource.postBulkAction(
			null, null, defaultPermissionBulkAction);

		values = _getImportTaskValues(bulkActionTask);

		_waitForFinish(GetterUtil.getLong(values.get("importTaskId")));

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder2);

		Assert.assertTrue(jsonObject.has("test1"));
		Assert.assertFalse(jsonObject.has("test2"));

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder3);

		Assert.assertFalse(jsonObject.has("test1"));
		Assert.assertTrue(jsonObject.has("test2"));

		jsonObject = _getDefaultPermissionsJSONObject(
			objectDefinition, objectEntryFolder4);

		Assert.assertFalse(jsonObject.has("test1"));
		Assert.assertTrue(jsonObject.has("test2"));
	}

	private void _testPostBulkActionWithTypeDelete() throws Exception {
		BulkAction bulkAction = new DeleteBulkAction();

		bulkAction.setType(BulkAction.Type.DELETE_BULK_ACTION);

		BulkActionTask bulkActionTask = bulkActionResource.postBulkAction(
			null, null, bulkAction);

		Assert.assertNull(bulkActionTask.getId());

		bulkAction.setSelectAll(true);

		try {
			bulkActionResource.postBulkAction(null, null, bulkAction);
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals("Filter is null", problem.getTitle());
		}

		ObjectEntry basicWebContentObjectEntry =
			ObjectEntryTestUtil.addObjectEntry(
				_depotEntry.getGroupId(), _basicWebContentObjectDefinition,
				Collections.emptyMap());

		bulkAction.setBulkActionItems(
			new BulkActionItem[] {
				new BulkActionItem() {
					{
						setClassExternalReferenceCode(
							basicWebContentObjectEntry.
								getExternalReferenceCode());
						setClassName(
							_basicWebContentObjectDefinition.getClassName());
						setClassPK(
							basicWebContentObjectEntry.getObjectEntryId());
						setName(basicWebContentObjectEntry.getTitleValue());
					}
				}
			});
		bulkAction.setSelectAll(false);

		bulkActionTask = bulkActionResource.postBulkAction(
			null, null, bulkAction);

		Assert.assertEquals("STARTED", bulkActionTask.getExecuteStatus());
		Assert.assertNotNull(bulkActionTask.getId());

		Map<String, Serializable> values = _getImportTaskValues(bulkActionTask);

		Assert.assertEquals(
			basicWebContentObjectEntry.getExternalReferenceCode(),
			values.get("classExternalReferenceCode"));
		Assert.assertEquals(
			basicWebContentObjectEntry.getObjectEntryId(),
			values.get("classPK"));
		Assert.assertEquals("BasicWebContent", values.get("type"));

		_waitForFinish(GetterUtil.getLong(values.get("importTaskId")));

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				basicWebContentObjectEntry.getObjectEntryId()));
	}

	private void _waitForFinish(long importTaskId) throws Exception {
		while (true) {
			ImportTask importTask = _importTaskResource.getImportTask(
				importTaskId);

			ImportTask.ExecuteStatus executeStatus =
				importTask.getExecuteStatus();

			if (StringUtil.equals(executeStatus.getValue(), "COMPLETED") ||
				StringUtil.equals(executeStatus.getValue(), "FAILED")) {

				Assert.assertEquals("COMPLETED", executeStatus.getValue());

				return;
			}
		}
	}

	private ObjectDefinition _basicWebContentObjectDefinition;

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	private ObjectDefinition _bulkActionTaskObjectDefinition;
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private FilterFactory<Predicate> _filterFactory;

	private ImportTaskResource _importTaskResource;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}