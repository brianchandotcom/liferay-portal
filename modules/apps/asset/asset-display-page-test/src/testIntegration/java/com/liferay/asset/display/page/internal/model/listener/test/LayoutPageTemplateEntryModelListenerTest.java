/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_layoutPageTemplateEntry = _addLayoutPageTemplateEntry(
			group, journalArticle);

		_addAssetDisplayPageEntry(
			group, journalArticle, _layoutPageTemplateEntry);
	}

	@Test(expected = ModelListenerException.class)
	public void testCheckLayoutPageTemplateWithAssetDisplayPageEntry() {
		_modelListener.onBeforeRemove(_layoutPageTemplateEntry);
	}

	@Test
	public void testCheckLayoutPageTemplateWithAssetDisplayPageEntryWhenDeletingGroup() {
		boolean deleteInProcess = GroupThreadLocal.isDeleteInProcess();

		try {
			GroupThreadLocal.setDeleteInProcess(true);

			_modelListener.onBeforeRemove(_layoutPageTemplateEntry);
		}
		finally {
			GroupThreadLocal.setDeleteInProcess(deleteInProcess);
		}
	}

	private AssetDisplayPageEntry _addAssetDisplayPageEntry(
			Group group, JournalArticle journalArticle,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			journalArticle.getUserId(), group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			Group group, JournalArticle journalArticle)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(), 0,
			_portal.getClassNameId(JournalArticle.class),
			journalArticle.getDDMStructureId(), RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0, 0, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.asset.display.page.internal.model.listener.LayoutPageTemplateEntryModelListener"
	)
	private ModelListener<LayoutPageTemplateEntry> _modelListener;

	@Inject
	private Portal _portal;

}