/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.test.util.KBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.service.TrashVersionLocalServiceUtil;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.DefaultWhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenHasDraftStatus;
import com.liferay.trash.test.util.WhenHasGrandParent;
import com.liferay.trash.test.util.WhenHasMyBaseModel;
import com.liferay.trash.test.util.WhenHasRecentBaseModelCount;
import com.liferay.trash.test.util.WhenIsAssetableBaseModel;
import com.liferay.trash.test.util.WhenIsIndexableBaseModel;
import com.liferay.trash.test.util.WhenIsMoveableFromTrashBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableBaseModel;
import com.liferay.trash.test.util.WhenIsUpdatableBaseModel;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class KBArticleTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenHasDraftStatus, WhenHasGrandParent, WhenHasMyBaseModel,
			   WhenHasRecentBaseModelCount, WhenIsAssetableBaseModel,
			   WhenIsIndexableBaseModel, WhenIsMoveableFromTrashBaseModel,
			   WhenIsRestorableBaseModel, WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public BaseModel<?> addDraftBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		return KBTestUtil.addKBArticleWithWorkflow(
			kbFolder.getUserId(), false, kbFolder.getKbFolderId(),
			getSearchKeywords(), serviceContext);
	}

	@Override
	public AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		KBArticle kbArticle = (KBArticle)classedModel;

		return AssetEntryLocalServiceUtil.fetchEntry(
			kbArticle.getModelClassName(), kbArticle.getResourcePrimKey());
	}

	@Override
	public int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return KBArticleLocalServiceUtil.getGroupKBArticlesCount(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getParentBaseModelClassName() {
		return KBFolder.class.getName();
	}

	@Override
	public int getRecentBaseModelsCount(long groupId) throws Exception {
		return KBArticleLocalServiceUtil.getGroupKBArticlesCount(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getSearchKeywords() {
		return _KB_ARTICLE_TITLE;
	}

	@Override
	public boolean isAssetEntryVisible(ClassedModel classedModel, long classPK)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			classedModel.getModelClassName(), _getAssetEntryClassPK(classPK));

		return assetEntry.isVisible();
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		KBArticle kbArticle = (KBArticle)classedModel;

		KBArticleLocalServiceUtil.moveKBArticleFromTrash(
			kbArticle.getUserId(), kbArticle.getResourcePrimKey(),
			kbArticle.getParentResourceClassNameId(),
			kbArticle.getParentResourcePrimKey());

		return KBFolderLocalServiceUtil.getKBFolder(
			kbArticle.getParentResourcePrimKey());
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(primaryKey);

		KBFolderLocalServiceUtil.moveKBFolderToTrash(
			kbFolder.getUserId(), primaryKey);
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(clazz);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(StringPool.BLANK);

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getKBArticle(
			primaryKey);

		return KBArticleLocalServiceUtil.updateKBArticle(
			kbArticle.getUserId(), kbArticle.getResourcePrimKey(),
			kbArticle.getTitle(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.nextDate(), null, null, null, null, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		return KBTestUtil.addKBArticleWithWorkflow(
			kbFolder.getUserId(), true, kbFolder.getKbFolderId(),
			getSearchKeywords(), serviceContext);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		KBFolderLocalServiceUtil.deleteKBFolder(
			kbFolder.getKbFolderId(), false);
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		if (classedModel instanceof KBArticle) {
			KBArticle kbArticle = (KBArticle)classedModel;

			return kbArticle.getResourcePrimKey();
		}

		return super.getAssetClassPK(classedModel);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return KBArticleLocalServiceUtil.getKBArticle(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return KBArticle.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		KBFolder kbFolder = (KBFolder)parentBaseModel;

		return KBArticleLocalServiceUtil.getKBArticlesCount(
			kbFolder.getGroupId(), kbFolder.getKbFolderId(),
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return KBTestUtil.addKBFolder(
			group.getGroupId(), parentBaseModelId, serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		KBArticle kbArticle = (KBArticle)classedModel;

		return kbArticle.getResourcePrimKey();
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		KBArticle kbArticle = (KBArticle)baseModel;

		return _trashHelper.getOriginalTitle(kbArticle.getTitle());
	}

	@Override
	protected boolean isInTrashContainer(TrashedModel trashedModel) {
		return _trashHelper.isInTrashContainer(trashedModel);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.getKBArticle(
			primaryKey);

		KBArticleLocalServiceUtil.moveKBArticleToTrash(
			kbArticle.getUserId(), kbArticle.getResourcePrimKey());
	}

	@Override
	protected void reindex(ClassedModel classedModel) throws Exception {
		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			classedModel.getModelClassName());

		KBArticle kbArticle = (KBArticle)classedModel;

		indexer.reindex(
			classedModel.getModelClassName(), kbArticle.getResourcePrimKey());
	}

	private long _getAssetEntryClassPK(long classPK) throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			classPK);

		long status = kbArticle.getStatus();

		if (kbArticle.isInTrash()) {
			if (_trashHelper.isInTrashExplicitly(kbArticle)) {
				TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
					getBaseModelClassName(), kbArticle.getResourcePrimKey());

				status = trashEntry.getStatus();
			}
			else {
				TrashVersion trashVersion =
					TrashVersionLocalServiceUtil.fetchVersion(
						kbArticle.getModelClassName(),
						kbArticle.getResourcePrimKey());

				if (trashVersion != null) {
					status = trashVersion.getStatus();
				}
				else {
					status = WorkflowConstants.STATUS_APPROVED;
				}
			}
		}

		long assetEntryClassPK = kbArticle.getKbArticleId();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			assetEntryClassPK = kbArticle.getResourcePrimKey();
		}

		return assetEntryClassPK;
	}

	private static final String _KB_ARTICLE_TITLE = RandomTestUtil.randomString(
		255);

	@Inject
	private TrashHelper _trashHelper;

	private final WhenIsIndexableBaseModel _whenIsIndexableBaseModel =
		new DefaultWhenIsIndexableBaseModel();

}