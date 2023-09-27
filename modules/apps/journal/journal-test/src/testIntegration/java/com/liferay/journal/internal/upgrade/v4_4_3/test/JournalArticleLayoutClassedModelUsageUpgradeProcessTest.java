/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.List;

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
			_publicLayout, "dynamic",
			new String[] {_assetEntry.getClassUuid()});
		_addJournalContentPortletToLayout(
			RandomTestUtil.randomString(), _journalArticle.getGroupId(),
			_publicLayout);

		List<String> expectedPrivateLayoutPortletIds = new ArrayList<>();

		expectedPrivateLayoutPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_privateLayout, "manual",
				new String[] {_assetEntry.getClassUuid()}));
		expectedPrivateLayoutPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_privateLayout));

		_assertAssetPublisherPortletPreferencesCount(1, true);

		List<String> expectedPublicLayoutPortletIds = new ArrayList<>();

		expectedPublicLayoutPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_publicLayout, "manual",
				new String[] {_assetEntry.getClassUuid()}));
		expectedPublicLayoutPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_publicLayout));

		_assertAssetPublisherPortletPreferencesCount(2, false);
		_assertJournalContentSearchesCount(_journalArticle.getArticleId(), 2);
		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 0);

		_runUpgrade();

		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 4);

		long portletClassNameId = _classNameLocalService.getClassNameId(
			Portlet.class.getName());

		for (String expectedPortletId : expectedPublicLayoutPortletIds) {
			_assertLayoutClassedModelUsage(
				expectedPortletId, portletClassNameId, _publicLayout.getPlid(),
				_journalArticle.getResourcePrimKey());
		}

		for (String expectedPortletId : expectedPrivateLayoutPortletIds) {
			_assertLayoutClassedModelUsage(
				expectedPortletId, portletClassNameId, _privateLayout.getPlid(),
				_journalArticle.getResourcePrimKey());
		}
	}

	@Test
	public void testUpgradeProcessExistingDefaultLayoutClassedModelUsage()
		throws Exception {

		_addAssetPublisherPortletToLayout(
			_publicLayout, "dynamic",
			new String[] {_assetEntry.getClassUuid()});
		_addJournalContentPortletToLayout(
			RandomTestUtil.randomString(), _journalArticle.getGroupId(),
			_publicLayout);

		List<String> expectedPrivateLayoutPortletIds = new ArrayList<>();

		expectedPrivateLayoutPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_privateLayout, "manual",
				new String[] {_assetEntry.getClassUuid()}));
		expectedPrivateLayoutPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_privateLayout));

		_assertAssetPublisherPortletPreferencesCount(1, true);

		List<String> expectedPublicLayoutPortletIds = new ArrayList<>();

		expectedPublicLayoutPortletIds.add(
			_addAssetPublisherPortletToLayout(
				_publicLayout, "manual",
				new String[] {_assetEntry.getClassUuid()}));
		expectedPublicLayoutPortletIds.add(
			_addJournalContentPortletToLayout(
				_journalArticle.getArticleId(), _journalArticle.getGroupId(),
				_publicLayout));

		_assertAssetPublisherPortletPreferencesCount(2, false);
		_assertJournalContentSearchesCount(_journalArticle.getArticleId(), 2);
		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 0);

		_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
			_journalArticle.getGroupId(), _journalArticleClassNameId,
			_journalArticle.getResourcePrimKey(), StringPool.BLANK,
			StringPool.BLANK, 0, 0, new ServiceContext());

		_runUpgrade();

		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 0);
	}

	@Test
	public void testUpgradeProcessManualAssetPublisherSelection()
		throws Exception {

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry1 = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry2 = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		String[] assetEntryXml = {
			assetEntry1.getClassUuid(), _assetEntry.getClassUuid(),
			assetEntry2.getClassUuid()
		};

		String expectedPrivateLayoutPortletId =
			_addAssetPublisherPortletToLayout(
				_privateLayout, "manual", assetEntryXml);

		_assertAssetPublisherPortletPreferencesCount(1, true);

		String expectedPublicLayoutPortletId =
			_addAssetPublisherPortletToLayout(
				_publicLayout, "manual", assetEntryXml);

		_assertAssetPublisherPortletPreferencesCount(1, false);
		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 0);
		_assertLayoutClassedModelUsagesCount(
			journalArticle1.getResourcePrimKey(), 0);
		_assertLayoutClassedModelUsagesCount(
			journalArticle2.getResourcePrimKey(), 0);

		_runUpgrade();

		_assertLayoutClassedModelUsagesCount(
			_journalArticle.getResourcePrimKey(), 2);
		_assertLayoutClassedModelUsagesCount(
			journalArticle1.getResourcePrimKey(), 2);
		_assertLayoutClassedModelUsagesCount(
			journalArticle2.getResourcePrimKey(), 2);

		long portletClassNameId = _classNameLocalService.getClassNameId(
			Portlet.class.getName());

		_assertLayoutClassedModelUsage(
			expectedPublicLayoutPortletId, portletClassNameId,
			_publicLayout.getPlid(), _journalArticle.getResourcePrimKey(),
			journalArticle1.getResourcePrimKey(),
			journalArticle2.getResourcePrimKey());
		_assertLayoutClassedModelUsage(
			expectedPrivateLayoutPortletId, portletClassNameId,
			_privateLayout.getPlid(), _journalArticle.getResourcePrimKey(),
			journalArticle1.getResourcePrimKey(),
			journalArticle2.getResourcePrimKey());
	}

	private String _addAssetPublisherPortletToLayout(
			Layout layout, String selectionStyle, String[] assetEntryClassUuids)
		throws Exception {

		List<String> assetEntryXMLs = new ArrayList<>();

		for (String assetEntryClassUuid : assetEntryClassUuids) {
			assetEntryXMLs.add(_getAssetEntryXml(assetEntryClassUuid));
		}

		return LayoutTestUtil.addPortletToLayout(
			layout, AssetPublisherPortletKeys.ASSET_PUBLISHER,
			HashMapBuilder.put(
				"assetEntryXml", assetEntryXMLs.toArray(new String[0])
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
		String containerKey, long containerType, long plid, long... classPKs) {

		for (long classPK : classPKs) {
			Assert.assertNotNull(
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						_journalArticleClassNameId, classPK, StringPool.BLANK,
						containerKey, containerType, plid));
		}
	}

	private void _assertLayoutClassedModelUsagesCount(long classPK, int count) {
		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.getLayoutClassedModelUsages(
				_journalArticleClassNameId, classPK);

		Assert.assertEquals(
			layoutClassedModelUsages.toString(), count,
			layoutClassedModelUsages.size());
	}

	private String _getAssetEntryXml(String assetEntryUuid) throws Exception {
		Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

		Element assetEntryElement = document.addElement("asset-entry");

		assetEntryElement.addElement("asset-entry-type");

		Element assetEntryUuidElement = assetEntryElement.addElement(
			"asset-entry-uuid");

		assetEntryUuidElement.addText(assetEntryUuid);

		return document.formattedString(StringPool.BLANK);
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
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
	private MultiVMPool _multiVMPool;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private Layout _privateLayout;
	private Layout _publicLayout;

}