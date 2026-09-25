/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.list.type.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.getOrAddEmptyListTypeDefinition(
				RandomTestUtil.randomString(), _getCompanyId(groupId), userId,
				false);

		return listTypeDefinition.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				RandomTestUtil.randomString(), userId,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				false, Collections.emptyList(),
				ServiceContextTestUtil.getServiceContext(
					_getCompanyId(groupId), groupId, userId));

		listTypeDefinition.setModifiedDate(dateModified);

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		return listTypeDefinition.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_getListTypeDefinition(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(
			groupId, externalReferenceCode);

		return listTypeDefinition.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(
			groupId, externalReferenceCode);

		return listTypeDefinition.getName(LocaleUtil.getDefault());
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			ListTypeDefinitionResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		return TransformUtil.transform(
			ListUtil.filter(
				_listTypeDefinitionLocalService.getListTypeDefinitions(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				listTypeDefinition ->
					listTypeDefinition.getCompanyId() == companyId),
			ListTypeDefinition::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(
			groupId, externalReferenceCode);

		return listTypeDefinition.getListTypeDefinitionId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(
			groupId, externalReferenceCode);

		return listTypeDefinition.getStatus();
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

		ListTypeDefinition listTypeDefinition = _getListTypeDefinition(
			groupId, externalReferenceCode);

		_listTypeDefinitionLocalService.updateListTypeDefinition(
			listTypeDefinition.getExternalReferenceCode(),
			listTypeDefinition.getListTypeDefinitionId(),
			listTypeDefinition.getUserId(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			Collections.emptyList(),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId,
				listTypeDefinition.getUserId()));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private ListTypeDefinition _getListTypeDefinition(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _listTypeDefinitionLocalService.
			fetchListTypeDefinitionByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

}