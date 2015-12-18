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

package com.liferay.portlet.blogs.trash;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.test.DefaultWhenIsAssetable;
import com.liferay.portlet.trash.test.DefaultWhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenHasDraftStatus;
import com.liferay.portlet.trash.test.WhenIsAssetable;
import com.liferay.portlet.trash.test.WhenIsAssetableBaseModel;
import com.liferay.portlet.trash.test.WhenIsIndexableBaseModel;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 */
@Sync
public class BlogsEntryTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenHasDraftStatus, WhenIsAssetableBaseModel,
			   WhenIsIndexableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public BaseModel<?> addDraftBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), getSearchKeywords(), false,
			serviceContext);
	}

	@Override
	public AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		return _whenIsAssetable.fetchAssetEntry(classedModel);
	}

	@Override
	public String getSearchKeywords() {
		return _whenIsIndexableBaseModel.getSearchKeywords();
	}

	@Override
	public boolean isAssetEntryVisible(ClassedModel classedModel, long classPK)
		throws Exception {

		return _whenIsAssetable.isAssetEntryVisible(classedModel, classPK);
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return _whenIsIndexableBaseModel.searchBaseModelsCount(clazz, groupId);
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), getSearchKeywords(), true,
			serviceContext);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return BlogsEntryLocalServiceUtil.getEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		return BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), queryDefinition);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	private final WhenIsAssetable _whenIsAssetable =
		new DefaultWhenIsAssetable();
	private final WhenIsIndexableBaseModel _whenIsIndexableBaseModel =
		new DefaultWhenIsIndexableBaseModel();

}