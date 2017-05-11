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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.WhenCanBeDuplicatedInTrash;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class WikiNodeTrashHandlerTest
	extends BaseTrashHandlerTestCase implements WhenCanBeDuplicatedInTrash {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(TrashHelper.class.getName());

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Override
	public String getBaseModelName(ClassedModel classedModel) {
		WikiNode node = (WikiNode)classedModel;

		return node.getName();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_trashHelper = _serviceTracker.getService();
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), _NODE_NAME,
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
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiNodeLocalServiceUtil.getNodesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		WikiNode node = (WikiNode)baseModel;

		return _trashHelper.getOriginalTitle(node.getName());
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiNodeLocalServiceUtil.moveNodeToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	private static final String _NODE_NAME = RandomTestUtil.randomString(75);

	private static ServiceTracker<TrashHelper, TrashHelper> _serviceTracker;

	private TrashHelper _trashHelper;

}