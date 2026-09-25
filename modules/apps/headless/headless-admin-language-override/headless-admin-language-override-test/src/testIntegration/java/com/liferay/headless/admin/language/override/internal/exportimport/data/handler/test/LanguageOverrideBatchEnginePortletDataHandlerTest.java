/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.language.override.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.language.override.resource.v1_0.LanguageOverrideResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-49852"))
@RunWith(Arquillian.class)
public class LanguageOverrideBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			null, _getCompanyId(groupId), userId, RandomTestUtil.randomString(),
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			RandomTestUtil.randomString());

		ploEntry.setModifiedDate(dateModified);

		ploEntry = _ploEntryLocalService.updatePLOEntry(ploEntry);

		return ploEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_ploEntryLocalService.deletePLOEntry(
			_getPLOEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		PLOEntry ploEntry = _getPLOEntry(groupId, externalReferenceCode);

		return ploEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		PLOEntry ploEntry = _getPLOEntry(groupId, externalReferenceCode);

		return ploEntry.getValue();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			LanguageOverrideResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_ploEntryLocalService.getPLOEntries(_getCompanyId(groupId)),
			PLOEntry::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		PLOEntry ploEntry = _getPLOEntry(groupId, externalReferenceCode);

		return ploEntry.getPloEntryId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return false;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		PLOEntry ploEntry = _getPLOEntry(groupId, externalReferenceCode);

		ploEntry.setValue(RandomTestUtil.randomString());

		_ploEntryLocalService.updatePLOEntry(ploEntry);
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private PLOEntry _getPLOEntry(long groupId, String externalReferenceCode)
		throws Exception {

		return _ploEntryLocalService.fetchPLOEntryByExternalReferenceCode(
			externalReferenceCode, _getCompanyId(groupId));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

}