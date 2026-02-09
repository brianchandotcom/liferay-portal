/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.DDMDataCleanupPreupgradeProcess;

import java.sql.Connection;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DDMDataCleanupPreupgradeProcessTest
	extends DDMDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);
	}

	@AfterClass
	public static void tearDownClass() {
		DataAccess.cleanUp(_connection);
	}

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

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		runSQL(
			"delete from DDMStructure where structureId = " +
				ddmStructure.getStructureId());

		upgrade();

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				group.getGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				journalArticle.getResourcePrimKey());

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				friendlyURLEntry);
		}

		_journalArticleResourceLocalService.deleteArticleResource(
			group.getGroupId(), journalArticle.getArticleId());

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testUpgradeFrom62() throws Exception {
		connection = _connection;

		try {
			alterTableAddColumn(
				"JournalArticle", "structureId", "VARCHAR(75) null");
			alterTableAddColumn(
				"JournalFeed", "structureId", "VARCHAR(75) null");

			String structureId = RandomTestUtil.randomString();

			_test(
				() -> {
					_insertJournalArticle(
						0, 0, "structureId", "'" + structureId + "'");
					_insertJournalFeed(
						0, 0, "structureId", "'" + structureId + "'");
				},
				messages -> {
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "structureId", "JournalArticle",
								"structureKey", "DDMStructure", structureId)));
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "structureId", "JournalFeed", "structureKey",
								"DDMStructure", structureId)));
				});
		}
		finally {
			alterTableDropColumn("JournalArticle", "structureId");
			alterTableDropColumn("JournalFeed", "structureId");
		}
	}

	@Test
	public void testUpgradeFrom70to73() throws Exception {
		connection = _connection;

		try {
			alterTableAddColumn(
				"JournalArticle", "DDMStructureKey", "VARCHAR(75) null");
			alterTableAddColumn(
				"JournalFeed", "DDMStructureKey", "VARCHAR(75) null");

			String structureId = RandomTestUtil.randomString();

			_test(
				() -> {
					_insertJournalArticle(
						0, 0, "DDMStructureKey", "'" + structureId + "'");
					_insertJournalFeed(
						0, 0, "DDMStructureKey", "'" + structureId + "'");
				},
				messages -> {
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "DDMStructureKey", "JournalArticle",
								"structureKey", "DDMStructure", structureId)));
					Assert.assertTrue(
						messages.contains(
							_getExpectedMessage(
								1, "DDMStructureKey", "JournalFeed",
								"structureKey", "DDMStructure", structureId)));
				});
		}
		finally {
			alterTableDropColumn("JournalArticle", "DDMStructureKey");
			alterTableDropColumn("JournalFeed", "DDMStructureKey");
		}
	}

	@Test
	public void testUpgradeFrom74() throws Exception {
		long structureId = RandomTestUtil.nextLong();

		_test(
			() -> {
				_insertJournalArticle(
					0, 0, "DDMStructureId", String.valueOf(structureId));
				_insertJournalFeed(
					0, 0, "DDMStructureId", String.valueOf(structureId));
			},
			messages -> {
				Assert.assertTrue(
					messages.contains(
						_getExpectedMessage(
							1, "DDMStructureId", "JournalArticle",
							"structureId", "DDMStructure", structureId)));
				Assert.assertTrue(
					messages.contains(
						_getExpectedMessage(
							1, "DDMStructureId", "JournalFeed", "structureId",
							"DDMStructure", structureId)));
			});
	}

	@Test
	public void testUpgradeWithOrphanDDMTemplateVersion() throws Exception {
		Group group = GroupTestUtil.addGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		runSQL(
			"delete from DDMTemplate where templateId = " +
				ddmTemplate.getTemplateId());

		upgrade();

		_groupLocalService.deleteGroup(group);
	}

	@Test
	public void testUpgradeWithOrphanNonancestorDDMStructureFrom70to73()
		throws Exception {

		connection = _connection;

		long companyId = _getNonexistentCompanyId();

		long childGroupId = RandomTestUtil.nextLong();
		long globalGroupId = RandomTestUtil.nextLong();
		long orphanGroupId = RandomTestUtil.nextLong();
		long otherGroupId = RandomTestUtil.nextLong();
		long parentGroupId = RandomTestUtil.nextLong();

		String globalStructureKey = RandomTestUtil.randomString();
		String orphanStructureKey = RandomTestUtil.randomString();
		String parentStructureKey = RandomTestUtil.randomString();

		try {
			alterTableAddColumn(
				"JournalArticle", "DDMStructureKey", "VARCHAR(75) null");

			_test(
				DDMDataCleanupPreupgradeProcess.class.getName(),
				() -> {
					_insertGroup(
						companyId, "/global", globalGroupId, "global", 0);
					_insertGroup(
						companyId, "/parent", parentGroupId, "parent", 0);
					_insertGroup(
						companyId, "/child", childGroupId, "child",
						parentGroupId);
					_insertGroup(companyId, "/other", otherGroupId, "other", 0);

					_insertDDMStructure(
						companyId, parentGroupId, parentStructureKey);
					_insertDDMStructure(
						companyId, globalGroupId, globalStructureKey);
					_insertDDMStructure(
						companyId, orphanGroupId, orphanStructureKey);

					_insertJournalArticle(
						companyId, childGroupId, "DDMStructureKey",
						"'" + parentStructureKey + "'");
					_insertJournalArticle(
						companyId, otherGroupId, "DDMStructureKey",
						"'" + globalStructureKey + "'");
					_insertJournalArticle(
						companyId, otherGroupId, "DDMStructureKey",
						"'" + orphanStructureKey + "'");
				},
				messages -> {
					Assert.assertFalse(
						messages.toString(
						).contains(
							globalStructureKey
						));
					Assert.assertFalse(
						messages.toString(
						).contains(
							parentStructureKey
						));

					Assert.assertTrue(
						messages.contains(
							StringBundler.concat(
								"Table ",
								_dbInspector.normalizeName("JournalArticle"),
								", 1 row deleted because ",
								_dbInspector.normalizeName("DDMStructureKey"),
								StringPool.SPACE, orphanStructureKey,
								" was not found in ",
								_dbInspector.normalizeName("groupId"),
								StringPool.SPACE, otherGroupId,
								" or its ancestors")));
				});
		}
		finally {
			alterTableDropColumn("JournalArticle", "DDMStructureKey");

			runSQL("delete from DDMStructure where companyId = " + companyId);
			runSQL("delete from Group_ where companyId = " + companyId);
			runSQL("delete from JournalArticle where companyId = " + companyId);
		}
	}

	private String _getExpectedMessage(
			long count, String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName, Object targetValue)
		throws Exception {

		return StringBundler.concat(
			"Table ", _dbInspector.normalizeName(sourceTableName), ", ", count,
			(count > 1) ? " rows " : " row ", "deleted because ",
			_dbInspector.normalizeName(sourceColumnName), StringPool.SPACE,
			targetValue, " was not found in column ",
			_dbInspector.normalizeName(targetColumnName), " from table ",
			_dbInspector.normalizeName(targetTableName));
	}

	private long _getNonexistentCompanyId() throws Exception {
		Set<Long> companyIds = SetUtil.fromArray(
			PortalInstancePool.getCompanyIds());

		while (true) {
			long nonexistentCompanyId = RandomTestUtil.randomLong();

			if (!companyIds.contains(nonexistentCompanyId)) {
				return nonexistentCompanyId;
			}
		}
	}

	private void _insertDDMStructure(
			long companyId, long groupId, String structureKey)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"insert into DDMStructure (mvccVersion, ctCollectionId, ",
				"uuid_, structureId, companyId, groupId, structureKey) values ",
				"(0, 0, '", RandomTestUtil.randomString(), "', ",
				RandomTestUtil.nextLong(), ", ", companyId, ", ", groupId,
				", '", structureKey, "')"));
	}

	private void _insertGroup(
			long companyId, String friendlyURL, long groupId, String groupKey,
			long parentGroupId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"insert into Group_ (mvccVersion, ctCollectionId, uuid_, ",
				"groupId, classPK, companyId, externalReferenceCode, ",
				"friendlyURL, groupKey, name, parentGroupId) values (0, 0, '",
				RandomTestUtil.randomString(), "', ", groupId, ", ",
				RandomTestUtil.nextLong(), ", ", companyId, ", '",
				RandomTestUtil.randomString(), "', '", friendlyURL, "', '",
				groupKey, "', '", groupKey, "', ", parentGroupId, ")"));
	}

	private void _insertJournalArticle(
			long companyId, long groupId, String structureColumnName,
			String structureColumnValue)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"insert into JournalArticle (mvccVersion, ctCollectionId, ",
				"uuid_, id_, articleId, companyId, ", structureColumnName,
				", externalReferenceCode, groupId, urlTitle, version) values ",
				"(0, 0, '", RandomTestUtil.randomString(), "', ",
				RandomTestUtil.nextLong(), ", '", RandomTestUtil.randomString(),
				"', ", companyId, ", ", structureColumnValue, ", '",
				RandomTestUtil.randomString(), "', ",
				(groupId > 0) ? groupId : RandomTestUtil.nextLong(), ", '",
				RandomTestUtil.randomString(), "', 1.0)"));
	}

	private void _insertJournalFeed(
			long companyId, long groupId, String structureColumnName,
			String structureColumnValue)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"insert into JournalFeed (mvccVersion, ctCollectionId, uuid_, ",
				"id_, companyId, ", structureColumnName, ", groupId) values ",
				"(0, 0, '", RandomTestUtil.randomString(), "', ",
				RandomTestUtil.nextLong(), ", ", companyId, ", ",
				structureColumnValue, ", ",
				(groupId > 0) ? groupId : RandomTestUtil.nextLong(), ")"));
	}

	private void _test(
			String loggerName,
			UnsafeRunnable<Exception> preupgradeUnsafeRunnable,
			UnsafeConsumer<List<String>, Exception> verifyUnsafeConsumer)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				loggerName, LoggerTestUtil.INFO)) {

			preupgradeUnsafeRunnable.run();

			doUpgrade();

			verifyUnsafeConsumer.accept(logCapture.getMessages());
		}
	}

	private void _test(
			UnsafeRunnable<Exception> preupgradeUnsafeRunnable,
			UnsafeConsumer<List<String>, Exception> verifyUnsafeConsumer)
		throws Exception {

		_test(
			OrphanReferencesDataCleanupUtil.class.getName(),
			preupgradeUnsafeRunnable, verifyUnsafeConsumer);
	}

	private static List<ClassName> _classNames;
	private static Connection _connection;
	private static DBInspector _dbInspector;
	private static List<ResourcePermission> _resourcePermissions;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}