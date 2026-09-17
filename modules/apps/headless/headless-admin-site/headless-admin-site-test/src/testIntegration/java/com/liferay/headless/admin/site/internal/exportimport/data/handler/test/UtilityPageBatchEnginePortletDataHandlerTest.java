/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
@ExportImportScopes(Scope.SITE)
@RunWith(Arquillian.class)
public class UtilityPageBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, userId, groupId, 0, 0, false,
				RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, null,
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		layoutUtilityPageEntry.setModifiedDate(dateModified);

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry);

		return layoutUtilityPageEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_layoutUtilityPageEntryLocalService.deleteLayoutUtilityPageEntry(
			_getLayoutUtilityPageEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(groupId, externalReferenceCode);

		return layoutUtilityPageEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(groupId, externalReferenceCode);

		return layoutUtilityPageEntry.getName();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			UtilityPageResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntries(
				groupId),
			LayoutUtilityPageEntry::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(groupId, externalReferenceCode);

		return layoutUtilityPageEntry.getLayoutUtilityPageEntryId();
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
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(groupId, externalReferenceCode);

		_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
			layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				groupId, layoutUtilityPageEntry.getUserId()));
	}

	private LayoutUtilityPageEntry _getLayoutUtilityPageEntry(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _layoutUtilityPageEntryLocalService.
			fetchLayoutUtilityPageEntryByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}