/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.resource.v1_0.test;

import com.liferay.analytics.cms.rest.client.dto.v1_0.AssetDeletionOverview;
import com.liferay.analytics.cms.rest.client.pagination.Page;
import com.liferay.analytics.cms.rest.client.pagination.Pagination;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Thiago Buarque
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-21926"),
		@FeatureFlag("LPD-31149"), @FeatureFlag("LPD-34594"),
		@FeatureFlag("LPS-179669")
	}
)
@RunWith(Arquillian.class)
public class AssetDeletionOverviewResourceTest
	extends BaseAssetDeletionOverviewResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testPostAssetDeletionOverviewsPage() throws Exception {
		_setUpCMSContext();

		ObjectDefinition objectDefinition = _getObjectDefinition(
			"L_BASIC_DOCUMENT");

		DLFolder dlFolder = DLTestUtil.addDLFolder(_depotEntry.getGroupId());

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			RandomTestUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			null,
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId()));

		ObjectEntry objectEntry = _addObjectEntry(
			objectDefinition, _getObjectEntryFolder("L_FILES"),
			HashMapBuilder.<String, Serializable>put(
				"file", String.valueOf(dlFileEntry.getFileEntryId())
			).build());

		_addLayoutClassedModelUsage(objectDefinition, objectEntry);

		Page<AssetDeletionOverview> assetDeletionOverviewsPage =
			assetDeletionOverviewResource.postAssetDeletionOverviewsPage(
				_LANGUAGE_ID, Pagination.of(1, 20),
				new Long[] {objectEntry.getObjectEntryId()});

		List<AssetDeletionOverview> items = ListUtil.fromCollection(
			assetDeletionOverviewsPage.getItems());

		Assert.assertEquals(items.toString(), 1, items.size());

		Assert.assertEquals(
			items.toString(), 1, assetDeletionOverviewsPage.getTotalCount());

		_assertAssetDeletionOverview(
			items.get(0), "document-vector", objectEntry, 1);
	}

	@Test
	public void testPostAssetDeletionOverviewsPageWithPagination()
		throws Exception {

		_setUpCMSContext();

		ObjectDefinition objectDefinition = _getObjectDefinition(
			"L_BASIC_WEB_CONTENT");

		ObjectEntry objectEntry1 = _addObjectEntry(
			objectDefinition, _getObjectEntryFolder("L_CONTENTS"),
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build());

		_addLayoutClassedModelUsage(objectDefinition, objectEntry1);

		ObjectEntry objectEntry2 = _addObjectEntry(
			objectDefinition, _getObjectEntryFolder("L_CONTENTS"),
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build());

		Page<AssetDeletionOverview> assetDeletionOverviewsPage =
			assetDeletionOverviewResource.postAssetDeletionOverviewsPage(
				_LANGUAGE_ID, Pagination.of(1, 1),
				new Long[] {
					objectEntry1.getObjectEntryId(),
					objectEntry2.getObjectEntryId()
				});

		List<AssetDeletionOverview> items = ListUtil.fromCollection(
			assetDeletionOverviewsPage.getItems());

		Assert.assertEquals(items.toString(), 1, items.size());

		Assert.assertEquals(
			items.toString(), 2, assetDeletionOverviewsPage.getTotalCount());

		_assertAssetDeletionOverview(
			items.get(0), "basic-web-content", objectEntry1, 1);

		assetDeletionOverviewsPage =
			assetDeletionOverviewResource.postAssetDeletionOverviewsPage(
				_LANGUAGE_ID, Pagination.of(2, 1),
				new Long[] {
					objectEntry1.getObjectEntryId(),
					objectEntry2.getObjectEntryId()
				});

		items = ListUtil.fromCollection(assetDeletionOverviewsPage.getItems());

		Assert.assertEquals(items.toString(), 1, items.size());

		Assert.assertEquals(
			items.toString(), 2, assetDeletionOverviewsPage.getTotalCount());

		_assertAssetDeletionOverview(
			items.get(0), "basic-web-content", objectEntry2, 0);
	}

	private void _addLayoutClassedModelUsage(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry) {

		_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
			testGroup.getGroupId(), StringPool.BLANK,
			_portal.getClassNameId(objectDefinition.getClassName()),
			objectEntry.getObjectEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomInt(),
			_serviceContext);
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition,
			ObjectEntryFolder objectEntryFolder,
			Map<String, Serializable> values)
		throws Exception {

		_serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		return _objectEntryLocalService.addObjectEntry(
			_depotEntry.getGroupId(), _depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), _LANGUAGE_ID, values,
			_serviceContext);
	}

	private void _assertAssetDeletionOverview(
			AssetDeletionOverview assetDeletionOverview,
			String expectedMimeType, ObjectEntry objectEntry, long usages1)
		throws Exception {

		AssetDeletionOverview.DeletionType deletionType =
			assetDeletionOverview.getDeletionType();

		Assert.assertEquals(
			AssetDeletionOverview.DeletionType.PERMANENT_DELETION.getValue(),
			deletionType.getValue());

		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			(long)assetDeletionOverview.getId());

		Assert.assertEquals(
			expectedMimeType, assetDeletionOverview.getMimeType());

		Assert.assertEquals(
			objectEntry.getTitleValue(_LANGUAGE_ID),
			assetDeletionOverview.getTitle());

		Integer usages2 = assetDeletionOverview.getUsages();

		Assert.assertEquals(usages1, usages2.intValue());
	}

	private void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.site.initializer.cms.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	private ObjectDefinition _getObjectDefinition(String externalReferenceCode)
		throws Exception {

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				externalReferenceCode, testCompany.getCompanyId());
	}

	private ObjectEntryFolder _getObjectEntryFolder(
			String externalReferenceCode)
		throws Exception {

		return _objectEntryFolderLocalService.
			getObjectEntryFolderByExternalReferenceCode(
				externalReferenceCode, testGroup.getGroupId(),
				testCompany.getCompanyId());
	}

	private void _setUpCMSContext() throws Exception {
		Bundle testBundle = FrameworkUtil.getBundle(OverviewResourceTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.site.initializer.cms")) {

				_deleteFile(bundle, "01.object.folder");
				_deleteFile(bundle, "02.object.definition");

				CompletableFuture<Void> completableFuture =
					_batchEngineUnitProcessor.processBatchEngineUnits(
						_batchEngineUnitReader.getBatchEngineUnits(bundle));

				completableFuture.join();

				break;
			}
		}

		testGroup.setType(GroupConstants.TYPE_DEPOT);

		testGroup = _groupLocalService.updateGroup(testGroup);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testGroup.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setAttribute("staging", Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			testGroup, _serviceContext);
	}

	private static final String _LANGUAGE_ID = "en_US";

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}