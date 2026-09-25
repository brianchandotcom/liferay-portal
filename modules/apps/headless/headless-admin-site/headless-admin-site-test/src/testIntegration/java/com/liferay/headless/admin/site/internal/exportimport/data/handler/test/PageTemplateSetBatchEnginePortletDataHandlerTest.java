/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
@RunWith(Arquillian.class)
public class PageTemplateSetBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, userId, groupId,
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					null, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					ServiceContextTestUtil.getServiceContext(groupId, userId));

		layoutPageTemplateCollection.setModifiedDate(dateModified);

		layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection);

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				_getLayoutPageTemplateCollection(
					groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(groupId, externalReferenceCode);

		return layoutPageTemplateCollection.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(groupId, externalReferenceCode);

		return layoutPageTemplateCollection.getName();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			PageTemplateSetResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_layoutPageTemplateCollectionLocalService.
				getLayoutPageTemplateCollections(
					groupId, LayoutPageTemplateCollectionTypeConstants.BASIC,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			LayoutPageTemplateCollection::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(groupId, externalReferenceCode);

		return layoutPageTemplateCollection.getLayoutPageTemplateCollectionId();
	}

	@Override
	protected Scope getScope() {
		return Scope.SITE;
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

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(groupId, externalReferenceCode);

		_layoutPageTemplateCollectionLocalService.
			updateLayoutPageTemplateCollection(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString());
	}

	private LayoutPageTemplateCollection _getLayoutPageTemplateCollection(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _layoutPageTemplateCollectionLocalService.
			fetchLayoutPageTemplateCollectionByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}