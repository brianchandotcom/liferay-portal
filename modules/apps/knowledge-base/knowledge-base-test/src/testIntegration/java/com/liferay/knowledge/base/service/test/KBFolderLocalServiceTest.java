/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.DuplicateKBFolderExternalReferenceCodeException;
import com.liferay.knowledge.base.exception.InvalidKBFolderNameException;
import com.liferay.knowledge.base.exception.KBFolderParentException;
import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.util.comparator.KBObjectsModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsPriorityComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsTitleComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsViewCountComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class KBFolderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();

		_kbFolder = addKBFolder(KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test(expected = DuplicateKBFolderExternalReferenceCodeException.class)
	public void testAddKBFolderWithExistingExternalReferenceCode()
		throws Exception {

		KBFolder kbFolder = addKBFolder(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_kbFolderLocalService.addKBFolder(
			kbFolder.getExternalReferenceCode(), _user.getUserId(),
			_group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@Test
	public void testAddKBFolderWithExternalReferenceCode() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		KBFolder kbFolder = _kbFolderLocalService.addKBFolder(
			externalReferenceCode, _user.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		Assert.assertEquals(
			externalReferenceCode, kbFolder.getExternalReferenceCode());
	}

	@Test
	public void testAddKBFolderWithoutExternalReferenceCode() throws Exception {
		KBFolder kbFolder1 = _kbFolderLocalService.addKBFolder(
			null, _user.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		String externalReferenceCode = kbFolder1.getExternalReferenceCode();

		Assert.assertEquals(externalReferenceCode, kbFolder1.getUuid());

		KBFolder kbFolder2 =
			_kbFolderLocalService.getKBFolderByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertEquals(kbFolder1, kbFolder2);
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInKBFolder()
		throws Exception {

		addKBArticle(_kbFolder.getKbFolderId(), RandomTestUtil.randomString());
		addKBArticle(_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		addKBFolder(_kbFolder.getKbFolderId());

		Assert.assertEquals(
			3,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInRootKBFolder()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			2,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInRootKBFolderByDraftStatus()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			1,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_DRAFT));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountKBArticleImmediateChildren()
		throws Exception {

		KBArticle parentKBArticle = addKBArticle(
			_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		addChildKBArticle(parentKBArticle, RandomTestUtil.randomString());
		addChildKBArticle(parentKBArticle, RandomTestUtil.randomString());

		addKBFolder(_kbFolder.getKbFolderId());

		Assert.assertEquals(
			2,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), parentKBArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountKBFolderImmediateChildren()
		throws Exception {

		KBArticle parentKBArticle = addKBArticle(
			_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		addChildKBArticle(parentKBArticle, RandomTestUtil.randomString());

		addKBFolder(_kbFolder.getKbFolderId());

		Assert.assertEquals(
			2,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountWithMultipleKBArticleVersions()
		throws Exception {

		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		updateKBArticle(kbArticle, RandomTestUtil.randomString());

		Assert.assertEquals(
			2,
			_kbFolderLocalService.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInKBFolder() throws Exception {
		KBArticle kbArticle1 = addKBArticle(_kbFolder.getKbFolderId(), "A");
		KBArticle kbArticle2 = addKBArticle(_kbFolder.getKbFolderId(), "B");

		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsTitleComparator<KBArticle>(true, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInRootKBFolder() throws Exception {
		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle = (KBArticle)kbFoldersAndKBArticles.get(1);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle.getKbArticleId(), currentKBArticle.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInRootKBFolderByDraftStatus()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_DRAFT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);

		Assert.assertEquals(
			kbFoldersAndKBArticles.toString(), 1,
			kbFoldersAndKBArticles.size());

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByAscendingModifiedDate()
		throws Exception {

		Date date = new Date();

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + Time.SECOND));
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + (Time.SECOND * 3)));
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + (Time.SECOND * 2)));

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsModifiedDateComparator(true, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByAscendingPriority()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		_kbArticleLocalService.updatePriority(
			kbArticle2.getResourcePrimKey(), 10.0);

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsPriorityComparator(true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByAscendingTitle()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "A");
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "C");
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "B");

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator(true, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByAscendingViewCount()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "A");
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "B");
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "C");

		_kbArticleLocalService.incrementViewCount(
			kbArticle2.getUserId(), kbArticle2.getResourcePrimKey(), 1000);

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsViewCountComparator(true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByDescendingModifiedDate()
		throws Exception {

		Date date = new Date();

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + Time.SECOND));
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + (Time.SECOND * 3)));
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new Date(date.getTime() + (Time.SECOND * 2)));

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsModifiedDateComparator(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByDescendingPriority()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		_kbArticleLocalService.updatePriority(
			kbArticle2.getResourcePrimKey(), 10.0);

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsPriorityComparator(false));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByDescendingTitle()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "A");
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "C");
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "B");

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(3);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(2);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByDescendingViewCount()
		throws Exception {

		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "A");
		KBArticle kbArticle2 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "B");
		KBArticle kbArticle3 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "C");

		_kbArticleLocalService.incrementViewCount(
			kbArticle2.getUserId(), kbArticle2.getResourcePrimKey(), 1000);

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsViewCountComparator(false));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(2);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(1);
		KBArticle currentKBArticle3 = (KBArticle)kbFoldersAndKBArticles.get(3);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
		Assert.assertEquals(
			kbArticle3.getKbArticleId(), currentKBArticle3.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesOrderedByTypeWithNullComparator()
		throws Exception {

		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		KBArticle newKBArticle = updateKBArticle(
			kbArticle, RandomTestUtil.randomString());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			newKBArticle.getKbArticleId(), currentKBArticle1.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesReturnKBArticleImmediateChildren()
		throws Exception {

		KBArticle parentKBArticle = addKBArticle(
			_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		KBArticle kbArticle1 = addChildKBArticle(parentKBArticle, "A");
		KBArticle kbArticle2 = addChildKBArticle(parentKBArticle, "B");

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), parentKBArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsTitleComparator<KBArticle>(true, true));

		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle2 = (KBArticle)kbFoldersAndKBArticles.get(1);

		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesReturnKBFolderImmediateChildren()
		throws Exception {

		KBArticle parentKBArticle = addKBArticle(
			_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		addChildKBArticle(parentKBArticle, RandomTestUtil.randomString());

		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsTitleComparator<KBArticle>(true, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);

		Assert.assertEquals(
			kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			parentKBArticle.getKbArticleId(),
			currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbFoldersAndKBArticles.toString(), 2,
			kbFoldersAndKBArticles.size());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesWithMultipleKBArticleVersions()
		throws Exception {

		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		KBArticle newKBArticle = updateKBArticle(
			kbArticle, RandomTestUtil.randomString());

		List<Object> kbFoldersAndKBArticles =
			_kbFolderLocalService.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFoldersAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFoldersAndKBArticles.get(1);

		Assert.assertEquals(
			kbFoldersAndKBArticles.toString(), 2,
			kbFoldersAndKBArticles.size());

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			newKBArticle.getKbArticleId(), currentKBArticle1.getKbArticleId());
	}

	@Test(expected = KBFolderParentException.class)
	public void testMoveKBArticleToInvalidParentKBFolder() throws Exception {
		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());

		KBFolder kbSubfolder = addKBFolder(kbFolder.getKbFolderId());

		_kbFolderLocalService.moveKBFolder(
			kbFolder.getKbFolderId(), kbSubfolder.getKbFolderId());
	}

	@Test(expected = NoSuchFolderException.class)
	public void testMoveKBFolderToParentKBArticle() throws Exception {
		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());
		KBArticle kbArticle = addKBArticle(
			_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		_kbFolderLocalService.moveKBFolder(
			kbFolder.getKbFolderId(), kbArticle.getResourcePrimKey());
	}

	@Test
	public void testMoveKBFolderToParentKBFolderInHomeKBFolder()
		throws Exception {

		KBFolder kbFolder = addKBFolder(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		KBFolder parentKBFolder = addKBFolder(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_kbFolderLocalService.moveKBFolder(
			kbFolder.getKbFolderId(), parentKBFolder.getKbFolderId());

		kbFolder = _kbFolderLocalService.getKBFolder(kbFolder.getKbFolderId());

		Assert.assertEquals(
			parentKBFolder.getKbFolderId(), kbFolder.getParentKBFolderId());
	}

	@Test
	public void testMoveKBFolderToParentKBFolderInKBFolder() throws Exception {
		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());
		KBFolder parentKBFolder = addKBFolder(_kbFolder.getKbFolderId());

		_kbFolderLocalService.moveKBFolder(
			kbFolder.getKbFolderId(), parentKBFolder.getKbFolderId());

		kbFolder = _kbFolderLocalService.getKBFolder(kbFolder.getKbFolderId());

		Assert.assertEquals(
			parentKBFolder.getKbFolderId(), kbFolder.getParentKBFolderId());
	}

	@Test(expected = InvalidKBFolderNameException.class)
	public void testUpdateKBFolderWithEmptyName() throws Exception {
		KBFolder kbFolder = addKBFolder(_kbFolder.getKbFolderId());

		_kbFolderLocalService.updateKBFolder(
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			_kbFolder.getKbFolderId(), kbFolder.getKbFolderId(),
			StringPool.BLANK, kbFolder.getDescription(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	protected KBArticle addChildKBArticle(KBArticle kbArticle, String title)
		throws Exception {

		return _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(),
			PortalUtil.getClassNameId(KBArticleConstants.getClassName()),
			kbArticle.getResourcePrimKey(), title, title,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new String[0], null, new Date(), null, null, new String[0],
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	protected KBArticle addKBArticle(long parentKbFolderId, Date createDate)
		throws Exception {

		String title = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(createDate);

		return _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentKbFolderId, title, title, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new String[0], null, new Date(),
			null, null, new String[0], serviceContext);
	}

	protected KBArticle addKBArticle(long parentKbFolderId, String title)
		throws Exception {

		return _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentKbFolderId, title, title, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new String[0], null, new Date(),
			null, null, new String[0],
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	protected KBFolder addKBFolder(long parentResourcePrimKey)
		throws PortalException {

		return _kbFolderLocalService.addKBFolder(
			null, _user.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentResourcePrimKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	protected KBArticle updateKBArticle(KBArticle kbArticle, String title)
		throws Exception {

		return _kbArticleLocalService.updateKBArticle(
			kbArticle.getUserId(), kbArticle.getResourcePrimKey(), title,
			kbArticle.getContent(), kbArticle.getDescription(), null,
			kbArticle.getSourceURL(), kbArticle.getDisplayDate(),
			kbArticle.getExpirationDate(), kbArticle.getReviewDate(),
			new String[0], new long[0],
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	private KBFolder _kbFolder;

	@Inject
	private KBFolderLocalService _kbFolderLocalService;

	private User _user;

}