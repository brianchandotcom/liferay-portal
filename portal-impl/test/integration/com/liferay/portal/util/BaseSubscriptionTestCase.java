/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInRootContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInContainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract long addBaseModel(long containerModelId)
		throws Exception;

	protected long addContainerModel(long containerModelId) throws Exception {
		return 0;
	};

	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		return;
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

	protected long updateEntry(long baseModelId) throws Exception {
		return 0;
	};

	protected Group group;

	private static final long _PARENT_CONTAINER_MODEL_ID_DEFAULT = 0;

}