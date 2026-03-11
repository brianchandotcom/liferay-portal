/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.upgrade.v1_1_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import jakarta.portlet.PortletPreferences;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testUpgradePortletPreferences() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assertUpgrade(
			HashMapBuilder.put(
				"articleExternalReferenceCode",
				journalArticle1.getExternalReferenceCode()
			).put(
				"groupExternalReferenceCode", _group.getExternalReferenceCode()
			).build(),
			HashMapBuilder.put(
				"articleExternalReferenceCode",
				journalArticle1.getExternalReferenceCode()
			).build());

		Group companyGroup = _groupLocalService.getCompanyGroup(
			_group.getCompanyId());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			companyGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assertUpgrade(
			HashMapBuilder.put(
				"articleExternalReferenceCode",
				journalArticle2.getExternalReferenceCode()
			).put(
				"groupExternalReferenceCode",
				companyGroup.getExternalReferenceCode()
			).build(),
			HashMapBuilder.put(
				"articleExternalReferenceCode",
				journalArticle2.getExternalReferenceCode()
			).put(
				"groupExternalReferenceCode",
				companyGroup.getExternalReferenceCode()
			).build());
	}

	private void _assertPortletPreferences(
			String portletId, Map<String, String> portletPreferencesMap)
		throws Exception {

		PortletPreferences portletPreferences =
			LayoutTestUtil.getPortletPreferences(_layout, portletId);

		for (Map.Entry<String, String> entry :
				portletPreferencesMap.entrySet()) {

			Assert.assertEquals(
				entry.getValue(),
				portletPreferences.getValue(entry.getKey(), null));
		}
	}

	private void _assertUpgrade(
			Map<String, String> actualPortletPreferencesMap,
			Map<String, String> expectedPortletPreferencesMapMap)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			_layout, JournalContentPortletKeys.JOURNAL_CONTENT,
			_getPreferenceMap(actualPortletPreferencesMap));

		_assertPortletPreferences(portletId, actualPortletPreferencesMap);

		_runUpgrade();

		_assertPortletPreferences(portletId, expectedPortletPreferencesMapMap);
	}

	private Map<String, String[]> _getPreferenceMap(Map<String, String> map) {
		Map<String, String[]> preferenceMap = new HashMap<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			preferenceMap.put(entry.getKey(), new String[] {entry.getValue()});
		}

		return preferenceMap;
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 1, 2));

		UpgradeProcess upgradeProcess = upgradeProcesses[0];

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.content.web.internal.upgrade.registry.JournalContentWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}