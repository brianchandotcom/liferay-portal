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

package com.liferay.journal.internal.upgrade.v4_4_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class JournalArticleLayoutClassedModelUsageUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey());

		_journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());
		_privateLayout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), true);
		_publicLayout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false);
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		_addAssetPublisherPortletToLayout(
			_publicLayout, "dynamic", _assetEntry.getClassUuid());
		_addJournalContentPortletToLayout(
			RandomTestUtil.randomString(), _journalArticle.getGroupId(),
			_publicLayout);

		List<String> privateLayoutExpectedPortletIds = new ArrayList<>();

		privateLayoutExpectedPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_privateLayout, "manual", _assetEntry.getClassUuid()));
		privateLayoutExpectedPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_privateLayout));

		_assertAssetPublisherPortletPreferencesCount(1, true);

		List<String> publicLayoutExpectedPortletIds = new ArrayList<>();

		publicLayoutExpectedPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_publicLayout, "manual", _assetEntry.getClassUuid()));
		publicLayoutExpectedPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_publicLayout));

		_assertAssetPublisherPortletPreferencesCount(2, false);

		_assertJournalContentSearchesCount(_journalArticle.getArticleId(), 2);

		_assertLayoutClassedModelUsagesCount(0);

		_runUpgrade();

		_assertLayoutClassedModelUsagesCount(4);

		long portletClassNameId = _classNameLocalService.getClassNameId(
			Portlet.class.getName());

		for (String expectedPortletId : publicLayoutExpectedPortletIds) {
			_assertLayoutClassedModelUsage(
				expectedPortletId, portletClassNameId, _publicLayout.getPlid());
		}

		for (String expectedPortletId : privateLayoutExpectedPortletIds) {
			_assertLayoutClassedModelUsage(
				expectedPortletId, portletClassNameId,
				_privateLayout.getPlid());
		}
	}

	@Test
	public void testUpgradeProcessExistingDefaultLayoutClassedModelUsage()
		throws Exception {

		_addAssetPublisherPortletToLayout(
			_publicLayout, "dynamic", _assetEntry.getClassUuid());
		_addJournalContentPortletToLayout(
			RandomTestUtil.randomString(), _journalArticle.getGroupId(),
			_publicLayout);

		List<String> privateLayoutExpectedPortletIds = new ArrayList<>();

		privateLayoutExpectedPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_privateLayout, "manual", _assetEntry.getClassUuid()));
		privateLayoutExpectedPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_privateLayout));

		_assertAssetPublisherPortletPreferencesCount(1, true);

		List<String> publicLayoutExpectedPortletIds = new ArrayList<>();

		publicLayoutExpectedPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_publicLayout, "manual", _assetEntry.getClassUuid()));
		publicLayoutExpectedPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_publicLayout));

		_assertAssetPublisherPortletPreferencesCount(2, false);

		_assertJournalContentSearchesCount(_journalArticle.getArticleId(), 2);

		_assertLayoutClassedModelUsagesCount(0);

		_layoutClassedModelUsageLocalService.addDefaultLayoutClassedModelUsage(
			_journalArticle.getGroupId(), _journalArticleClassNameId,
			_journalArticle.getResourcePrimKey(), new ServiceContext());

		Assert.assertTrue(
			_layoutClassedModelUsageLocalService.
				hasDefaultLayoutClassedModelUsage(
					_journalArticleClassNameId,
					_journalArticle.getResourcePrimKey()));

		_runUpgrade();

		_assertLayoutClassedModelUsagesCount(0);
	}

	private String _addAssetPublisherPortletToLayout(
			Layout layout, String selectionStyle, String assetEntryXml)
		throws Exception {

		return LayoutTestUtil.addPortletToLayout(
			layout, AssetPublisherPortletKeys.ASSET_PUBLISHER,
			HashMapBuilder.put(
				"assetEntryXml", new String[] {assetEntryXml}
			).put(
				"selectionStyle", new String[] {selectionStyle}
			).build());
	}

	private String _addJournalContentPortletToLayout(
			String articleId, long groupId, Layout layout)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			layout, JournalContentPortletKeys.JOURNAL_CONTENT,
			HashMapBuilder.put(
				"articleId", new String[] {articleId}
			).put(
				"groupId", new String[] {String.valueOf(groupId)}
			).build());

		_journalContentSearchLocalService.updateContentSearch(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			portletId, articleId, true);

		return portletId;
	}

	private void _assertAssetPublisherPortletPreferencesCount(
		int count, boolean privateLayout) {

		List<PortletPreferences> portletPreferences =
			_portletPreferencesLocalService.getPortletPreferences(
				_group.getCompanyId(), _group.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				AssetPublisherPortletKeys.ASSET_PUBLISHER, privateLayout);

		Assert.assertEquals(
			portletPreferences.toString(), count, portletPreferences.size());
	}

	private void _assertJournalContentSearchesCount(
		String articleId, int count) {

		List<JournalContentSearch> articleContentSearches =
			_journalContentSearchLocalService.getArticleContentSearches(
				articleId);

		Assert.assertEquals(
			articleContentSearches.toString(), count,
			articleContentSearches.size());
	}

	private void _assertLayoutClassedModelUsage(
		String containerKey, long containerType, long plid) {

		Assert.assertNotNull(
			_layoutClassedModelUsageLocalService.fetchLayoutClassedModelUsage(
				_journalArticleClassNameId,
				_journalArticle.getResourcePrimKey(), containerKey,
				containerType, plid));
	}

	private void _assertLayoutClassedModelUsagesCount(int count) {
		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.getLayoutClassedModelUsages(
				_journalArticleClassNameId,
				_journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			layoutClassedModelUsages.toString(), count,
			layoutClassedModelUsages.size());
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v4_4_3." +
			"JournalArticleLayoutClassedModelUsageUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private AssetEntry _assetEntry;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;
	private long _journalArticleClassNameId;

	@Inject
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private Layout _privateLayout;
	private Layout _publicLayout;

}