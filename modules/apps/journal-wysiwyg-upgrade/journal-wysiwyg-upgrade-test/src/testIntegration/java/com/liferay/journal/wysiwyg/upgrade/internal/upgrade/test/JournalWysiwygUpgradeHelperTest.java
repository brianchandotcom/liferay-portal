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

package com.liferay.journal.wysiwyg.upgrade.internal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.wysiwyg.upgrade.JournalWysiwygUpgradeHelper;
import com.liferay.journal.wysiwyg.upgrade.constants.JournalWysiwygConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class JournalWysiwygUpgradeHelperTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_layout = LayoutTestUtil.addLayout(_group);

		addWysiwygPortlet();
		setUpJournalWysiwygUpgradeHelper();
	}

	@Test
	public void testUpgradeWysiwyg() {
		int initialWysiwygArticleCount =
			JournalArticleLocalServiceUtil.getStructureArticlesCount(
				_group.getGroupId(),
				JournalWysiwygConstants.WYSIWYG_STRUCTURE_KEY);

		Assert.assertEquals(0, initialWysiwygArticleCount);

		_wysiwygUpgradeHelper.upgrade();

		int wysiwygArticleCount =
			JournalArticleLocalServiceUtil.getStructureArticlesCount(
				_group.getGroupId(),
				JournalWysiwygConstants.WYSIWYG_STRUCTURE_KEY);

		Assert.assertEquals(
			initialWysiwygArticleCount + 1, wysiwygArticleCount);
	}

	protected void addWysiwygPortlet() throws Exception {
		Map<String, String> portletPreferencesMap = new HashMap<>();

		portletPreferencesMap.put("message", _WYSIWYG_MESSAGE);

		LayoutTestUtil.updateLayoutPortletPreferences(
			_layout, JournalWysiwygConstants.WYSIWYG_PORTLET_KEY,
			portletPreferencesMap);
	}

	protected void setUpJournalWysiwygUpgradeHelper() {
		Registry registry = RegistryUtil.getRegistry();

		AssetEntryLocalService assetEntryLocalService = registry.getService(
			AssetEntryLocalService.class);

		DefaultDDMStructureHelper defaultDDMStructureHelper =
			registry.getService(DefaultDDMStructureHelper.class);

		GroupLocalService groupLocalService = registry.getService(
			GroupLocalService.class);

		JournalArticleLocalService journalArticleLocalService =
			registry.getService(JournalArticleLocalService.class);

		JournalFolderLocalService journalFolderLocalService =
			registry.getService(JournalFolderLocalService.class);

		LayoutLocalService layoutLocalService = registry.getService(
			LayoutLocalService.class);

		PortletPreferencesLocalService portletPreferencesLocalService =
			registry.getService(PortletPreferencesLocalService.class);

		UserLocalService userLocalService = registry.getService(
			UserLocalService.class);

		_wysiwygUpgradeHelper = new JournalWysiwygUpgradeHelper(
			assetEntryLocalService, defaultDDMStructureHelper,
			groupLocalService, journalArticleLocalService,
			journalFolderLocalService, layoutLocalService,
			portletPreferencesLocalService, userLocalService);
	}

	private static final String _WYSIWYG_MESSAGE =
		"What you see is what you get";

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@DeleteAfterTestRun
	private User _user;

	private JournalWysiwygUpgradeHelper _wysiwygUpgradeHelper;

}