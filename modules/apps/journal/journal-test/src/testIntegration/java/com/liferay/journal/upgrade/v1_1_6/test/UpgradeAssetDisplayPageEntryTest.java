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

package com.liferay.journal.upgrade.v1_1_6.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery.PerformActionMethod;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Töreki
 */
@RunWith(Arquillian.class)
public class UpgradeAssetDisplayPageEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_groups = new ArrayList<>();
		_timestamp = new Timestamp(System.currentTimeMillis());
		_assetEntryClassUuids = new HashMap<>();

		setUpClassNameIds();
		setUpUpgradeAssetDisplayPageEntry();
	}

	@After
	public void tearDown() throws Exception {
		for (Group group : _groups) {
			cleanUpGroup(group);

			if (group.isStaged()) {
				cleanUpGroup(group.getStagingGroup());
			}
		}
	}

	@Test
	public void testUpgradeLocalStagingGroupMultipleArticlesMultipleVersions()
		throws Exception {

		testUpgradeLocalStagingGroup(3, true);
	}

	@Test
	public void testUpgradeRemoteLiveMultipleArticlesMultipleVersions()
		throws Exception {

		testUpgradeRemoteLive(3, true);
	}

	@Test
	public void testUpgradeSimpleGroupMultipleArticlesMultipleVersions()
		throws Exception {

		testUpgradeSimpleGroup(3, true);
	}

	@Test
	public void testUpgradeSimpleGroupNoArticles() throws Exception {
		testUpgradeSimpleGroup(0, false);
	}

	@Test
	public void testUpgradeSimpleGroupOneArticle() throws Exception {
		testUpgradeSimpleGroup(1, false);
	}

	@Test
	public void testUpgradeSimpleGroupOneArticleMultipleVersions()
		throws Exception {

		testUpgradeSimpleGroup(1, true);
	}

	protected void addAssetEntry(
			long groupId, long companyId, long classNameId, long classPK,
			String classUuid)
		throws Exception {

		StringBundler sb = new StringBundler(2);

		sb.append("insert into AssetEntry (entryId, groupId, companyId, ");
		sb.append("classNameId, classPK, classUuid) values (?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, _counterLocalService.increment());
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, classNameId);
			ps.setLong(5, classPK);
			ps.setString(6, classUuid);

			ps.executeUpdate();
		}
	}

	protected void addJournalArticle(
			long resourcePrimKey, long groupId, long companyId, double version,
			String layoutUuid)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into JournalArticle (uuid_, id_, resourcePrimKey, ");
		sb.append("groupId, companyId, userId, userName, createDate, ");
		sb.append("modifiedDate, folderId, classNameId, classPK, treePath, ");
		sb.append("articleId, version, layoutUuid) values (?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, _counterLocalService.increment());
			ps.setLong(3, resourcePrimKey);
			ps.setLong(4, groupId);
			ps.setLong(5, companyId);
			ps.setLong(6, TestPropsValues.getUserId());
			ps.setString(7, null);
			ps.setTimestamp(8, _timestamp);
			ps.setTimestamp(9, _timestamp);
			ps.setLong(10, 0);
			ps.setLong(11, 0);
			ps.setLong(12, 0);
			ps.setString(13, "/");
			ps.setString(14, RandomTestUtil.randomString());
			ps.setDouble(15, version);
			ps.setString(16, layoutUuid);

			ps.executeUpdate();
		}
	}

	protected void assertAssetDisplayPageEntries(
			Group group, List<Long> resourcePrimKeys)
		throws Exception {

		for (long journalResourcePrimKey : resourcePrimKeys) {
			AssetDisplayPageEntry assetDisplayPageEntry =
				fetchAssetDisplayPageEntryByClassPk(journalResourcePrimKey);

			Assert.assertNotNull(assetDisplayPageEntry);

			Assert.assertEquals(
				assetDisplayPageEntry.getCompanyId(), group.getCompanyId());
			Assert.assertEquals(
				assetDisplayPageEntry.getGroupId(), group.getGroupId());
			Assert.assertEquals(
				assetDisplayPageEntry.getType(),
				AssetDisplayPageConstants.TYPE_SPECIFIC);
		}
	}

	protected void cleanUpGroup(Group group) throws Exception {
		cleanUpGroup(group.getGroupId());
	}

	protected void cleanUpGroup(long groupId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from AssetDisplayPageEntry where groupId = " + groupId);

		db.runSQL("delete from AssetEntry where groupId = " + groupId);

		db.runSQL("delete from JournalArticle where groupId = " + groupId);
	}

	protected Group createGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	protected void createJournalArticle(
			boolean multipleArticleVersions, Group group, String layoutUuid,
			long resourcePrimKey)
		throws Exception {

		double version = 1.0;

		if (multipleArticleVersions) {
			addJournalArticle(
				resourcePrimKey, group.getGroupId(), group.getCompanyId(),
				version++, null);

			addJournalArticle(
				resourcePrimKey, group.getGroupId(), group.getCompanyId(),
				version++, PortalUUIDUtil.generate());
		}

		addJournalArticle(
			resourcePrimKey, group.getGroupId(), group.getCompanyId(), version,
			layoutUuid);
	}

	protected List<Long> createJournalArticles(
			int articleCount, boolean multipleArticleVersions, Group group,
			String layoutUuid)
		throws Exception {

		List<Long> resourcePrimKeys = new ArrayList<>();

		for (int i = 0; i < articleCount; ++i) {
			long resourcePrimKey = _counterLocalService.increment();

			createJournalArticle(
				multipleArticleVersions, group, layoutUuid, resourcePrimKey);

			String assetEntryClassUuId = PortalUUIDUtil.generate();

			addAssetEntry(
				group.getGroupId(), group.getCompanyId(),
				_classNameIdJournalArticle, resourcePrimKey,
				assetEntryClassUuId);

			resourcePrimKeys.add(resourcePrimKey);
			_assetEntryClassUuids.put(resourcePrimKey, assetEntryClassUuId);
		}

		return resourcePrimKeys;
	}

	protected List<Long> createJournalArticlesForStaging(
			List<Long> stagingResourcePrimKeys, boolean multipleArticleVersions,
			Group group, String layoutUuid)
		throws Exception {

		List<Long> resourcePrimKeys = new ArrayList<>();

		for (long stagingResourcePrimKey : stagingResourcePrimKeys) {
			long liveResourcePrimKey = _counterLocalService.increment();

			createJournalArticle(
				multipleArticleVersions, group, layoutUuid,
				liveResourcePrimKey);

			addAssetEntry(
				group.getGroupId(), group.getCompanyId(),
				_classNameIdJournalArticle, liveResourcePrimKey,
				_assetEntryClassUuids.get(stagingResourcePrimKey));

			resourcePrimKeys.add(liveResourcePrimKey);
		}

		return resourcePrimKeys;
	}

	protected AssetDisplayPageEntry fetchAssetDisplayPageEntryByClassPk(
			long classPK)
		throws Exception {

		List<AssetDisplayPageEntry> assetDisplayPageEntries = new ArrayList<>();

		ActionableDynamicQuery adq =
			AssetDisplayPageEntryLocalServiceUtil.getActionableDynamicQuery();

		adq.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"classNameId", _classNameIdJournalArticle));
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq("classPK", classPK));
			});

		adq.setPerformActionMethod(
			(PerformActionMethod<AssetDisplayPageEntry>)
				assetDisplayPageEntry -> assetDisplayPageEntries.add(
					assetDisplayPageEntry));

		adq.performActions();

		if (assetDisplayPageEntries.isEmpty()) {
			return null;
		}

		return assetDisplayPageEntries.get(0);
	}

	protected int getAssetDisplayPageEntriesCount(Group group)
		throws Exception {

		ActionableDynamicQuery adq =
			AssetDisplayPageEntryLocalServiceUtil.getActionableDynamicQuery();

		adq.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("groupId", group.getGroupId())));

		return (int)adq.performCount();
	}

	protected void setUpClassNameIds() {
		_classNameIdJournalArticle = PortalUtil.getClassNameId(
			"com.liferay.journal.model.JournalArticle");
	}

	protected void setUpUpgradeAssetDisplayPageEntry() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String bundleSymbolicName, String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					register(
						fromSchemaVersionString, toSchemaVersionString,
						upgradeSteps);
				}

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
							_upgradeAssetDisplayPageEntry =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	protected void testUpgradeLocalStagingGroup(
			int articleCount, boolean multipleArticleVersions)
		throws Exception {

		Group liveGroup = createGroup();

		GroupTestUtil.enableLocalStaging(liveGroup);
		Group stagingGroup = liveGroup.getStagingGroup();

		String layoutUuid = PortalUUIDUtil.generate();

		List<Long> stagingResourcePrimKeys = createJournalArticles(
			articleCount, multipleArticleVersions, stagingGroup, layoutUuid);

		List<Long> liveResourcePrimKeys = createJournalArticlesForStaging(
			stagingResourcePrimKeys, multipleArticleVersions, liveGroup,
			layoutUuid);

		_upgradeAssetDisplayPageEntry.upgrade();

		assertAssetDisplayPageEntries(stagingGroup, stagingResourcePrimKeys);
		assertAssetDisplayPageEntries(liveGroup, liveResourcePrimKeys);

		for (int i = 0; i < stagingResourcePrimKeys.size(); ++i) {
			long stagingResourcePrimKey = stagingResourcePrimKeys.get(i);

			long liveResourcePrimKey = liveResourcePrimKeys.get(i);

			AssetDisplayPageEntry stagingAssetDisplayPageEntry =
				fetchAssetDisplayPageEntryByClassPk(stagingResourcePrimKey);

			AssetDisplayPageEntry liveAssetDisplayPageEntry =
				fetchAssetDisplayPageEntryByClassPk(liveResourcePrimKey);

			Assert.assertEquals(
				liveAssetDisplayPageEntry.getUuid(),
				stagingAssetDisplayPageEntry.getUuid());
		}
	}

	protected void testUpgradeRemoteLive(
			int articleCount, boolean multipleArticleVersions)
		throws Exception {

		Group group = createGroup();

		group.setRemoteStagingGroupCount(1);

		group = GroupLocalServiceUtil.updateGroup(group);

		try {
			String layoutUuid = PortalUUIDUtil.generate();

			createJournalArticles(
				articleCount, multipleArticleVersions, group, layoutUuid);

			_upgradeAssetDisplayPageEntry.upgrade();

			Assert.assertEquals(0, getAssetDisplayPageEntriesCount(group));
		}
		finally {
			group.setRemoteStagingGroupCount(0);

			GroupLocalServiceUtil.updateGroup(group);
		}
	}

	protected void testUpgradeSimpleGroup(
			int articleCount, boolean multipleArticleVersions)
		throws Exception {

		Group group = createGroup();

		String layoutUuid = PortalUUIDUtil.generate();

		List<Long> resourcePrimKeys = createJournalArticles(
			articleCount, multipleArticleVersions, group, layoutUuid);

		_upgradeAssetDisplayPageEntry.upgrade();

		Assert.assertEquals(
			articleCount, getAssetDisplayPageEntriesCount(group));

		assertAssetDisplayPageEntries(group, resourcePrimKeys);
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v1_1_6." +
			"UpgradeAssetDisplayPageEntry";

	@Inject(
		filter = "(&(objectClass=com.liferay.journal.internal.upgrade.JournalServiceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Map<Long, String> _assetEntryClassUuids;
	private long _classNameIdJournalArticle;

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private Timestamp _timestamp;
	private UpgradeProcess _upgradeAssetDisplayPageEntry;

}