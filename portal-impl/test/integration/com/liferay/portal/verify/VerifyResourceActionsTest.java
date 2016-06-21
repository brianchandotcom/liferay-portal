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

package com.liferay.portal.verify;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.ResourceActionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class VerifyResourceActionsTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	@Transactional
	public void setUp() throws Exception {
		super.setUp();

		createResourceAction(_NAME_1, _ACTION_ID_1, 2);
		createResourceAction(_NAME_1, _ACTION_ID_2, 2);
		createResourceAction(_NAME_2, _ACTION_ID_1, 2);
		createResourceAction(_NAME_2, _ACTION_ID_2, 4);
	}

	@Test
	public void testDeleteDuplicateBitwiseValuesOnResource() throws Throwable {
		ResourceActionLocalServiceUtil.checkResourceActions();

		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				_NAME_1, _ACTION_ID_1);

		Assert.assertNotNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_1, _ACTION_ID_2);

		Assert.assertNotNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_2, _ACTION_ID_1);

		Assert.assertNotNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_2, _ACTION_ID_2);

		Assert.assertNotNull(resourceAction);

		doVerify();

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_1, _ACTION_ID_1);

		Assert.assertNotNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_1, _ACTION_ID_2);

		Assert.assertNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_2, _ACTION_ID_1);

		Assert.assertNotNull(resourceAction);

		resourceAction = ResourceActionLocalServiceUtil.fetchResourceAction(
			_NAME_2, _ACTION_ID_2);

		Assert.assertNotNull(resourceAction);
	}

	protected void createResourceAction(
		final String name, final String actionId, final long bitwiseValue) {

		long resourceActionId = CounterLocalServiceUtil.increment(
			ResourceAction.class.getName());

		ResourceAction resourceAction = ResourceActionUtil.create(
			resourceActionId);

		resourceAction.setName(name);
		resourceAction.setActionId(actionId);
		resourceAction.setBitwiseValue(bitwiseValue);

		_resourceActions.add(ResourceActionUtil.update(resourceAction));
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyResourceActions();
	}

	private static final String _ACTION_ID_1 = "action1";

	private static final String _ACTION_ID_2 = "action2";

	private static final String _NAME_1 = "portlet1";

	private static final String _NAME_2 = "portlet2";

	@DeleteAfterTestRun
	private final List<ResourceAction> _resourceActions = new ArrayList<>();

}