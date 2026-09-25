/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.object.resource.v1_0.ObjectEntryFolderResource;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getOrAddEmptyObjectEntryFolder(
				RandomTestUtil.randomString(), groupId, _getCompanyId(groupId),
				userId,
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		return objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), groupId, userId,
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		objectEntryFolder.setModifiedDate(dateModified);

		objectEntryFolder =
			_objectEntryFolderLocalService.updateObjectEntryFolder(
				objectEntryFolder);

		return objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			_getObjectEntryFolder(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			groupId, externalReferenceCode);

		return objectEntryFolder.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			groupId, externalReferenceCode);

		return objectEntryFolder.getDescription();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			ObjectEntryFolderResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_objectEntryFolderLocalService.getObjectEntryFolders(
				groupId, _getCompanyId(groupId),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			ObjectEntryFolder::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			groupId, externalReferenceCode);

		return objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected Scope getScope() {
		return Scope.DEPOT;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			groupId, externalReferenceCode);

		return objectEntryFolder.getStatus();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return true;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			groupId, externalReferenceCode);

		_objectEntryFolderLocalService.updateObjectEntryFolder(
			objectEntryFolder.getUserId(),
			objectEntryFolder.getObjectEntryFolderId(),
			objectEntryFolder.getParentObjectEntryFolderId(),
			RandomTestUtil.randomString(), objectEntryFolder.getLabelMap(),
			objectEntryFolder.getName(),
			ServiceContextTestUtil.getServiceContext(
				groupId, objectEntryFolder.getUserId()));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private ObjectEntryFolder _getObjectEntryFolder(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _objectEntryFolderLocalService.
			fetchObjectEntryFolderByExternalReferenceCode(
				externalReferenceCode, groupId, _getCompanyId(groupId));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}