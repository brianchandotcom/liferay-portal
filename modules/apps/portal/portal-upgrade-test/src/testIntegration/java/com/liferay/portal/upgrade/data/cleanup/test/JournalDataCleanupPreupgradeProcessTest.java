/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.JournalDataCleanupPreupgradeProcess;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class JournalDataCleanupPreupgradeProcessTest
	extends JournalDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_classNames = _classNameLocalService.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		_resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@After
	public void tearDown() throws Exception {
		List<ClassName> classNames = ListUtil.remove(
			_classNameLocalService.getClassNames(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			_classNames);

		for (ClassName className : classNames) {
			_classNameLocalService.deleteClassName(className);
		}

		List<ResourcePermission> resourcePermissions = ListUtil.remove(
			_resourcePermissionLocalService.getResourcePermissions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			_resourcePermissions);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			Collections.emptyMap());

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		JournalFeed journalFeed = JournalTestUtil.addFeed(
			group.getGroupId(), layout.getPlid(), RandomTestUtil.randomString(),
			journalArticle.getDDMStructureId(),
			journalArticle.getDDMTemplateKey(),
			journalArticle.getDDMTemplateKey());

		runSQL(
			"delete from JournalArticle where articleId = '" +
				journalArticle.getArticleId() + "'");
		runSQL(
			"delete from JournalFeed where feedId = '" +
				journalFeed.getFeedId() + "'");
		runSQL("delete from Layout where plid = " + layout.getPlid());

		upgrade();

		_groupLocalService.deleteGroup(group);
	}

	private static List<ClassName> _classNames;
	private static List<ResourcePermission> _resourcePermissions;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}