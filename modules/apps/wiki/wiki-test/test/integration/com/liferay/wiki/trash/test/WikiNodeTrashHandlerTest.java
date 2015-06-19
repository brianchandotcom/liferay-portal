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

package com.liferay.wiki.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class WikiNodeTrashHandlerTest extends BaseTrashHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testDeleteTrashVersions() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreUniqueTitle()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndDeleteWithParentIsNotRestorable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteGroupTrashEntries()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteParentNoMoveableFromTrash()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndRestoreModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndRestoreModelIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentIsInContainerBaseModel()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndTrashParentAndDeleteParentIsNotRestorable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndTrashParentIsNotRestorable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelIsInTrashContainer() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelWithParentIsRestorable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModel()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashIsRestorableBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndDeleteGroupTrashEntries() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndDeleteParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndRestoreBaseModelIsVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndRestoreIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentWithBaseModelIsInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentWithBaseModelIsIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDeleteIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndDeleteIsNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIndexable()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndCustomRestore()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), getSearchKeywords(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return WikiNodeLocalServiceUtil.getNode(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiNode.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		WikiNode node = (WikiNode)classedModel;

		return node.getName();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiNodeLocalServiceUtil.getNodesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected String getSearchKeywords() {
		return _NODE_NAME;
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		WikiNode node = (WikiNode)baseModel;

		return TrashUtil.getOriginalTitle(node.getName());
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiNodeLocalServiceUtil.moveNodeToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	private static final String _NODE_NAME = RandomTestUtil.randomString(75);

}