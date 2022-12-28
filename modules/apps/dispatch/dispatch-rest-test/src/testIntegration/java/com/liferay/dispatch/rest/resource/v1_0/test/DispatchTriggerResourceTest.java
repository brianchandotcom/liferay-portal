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

package com.liferay.dispatch.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Nilton Vieira
 */
@RunWith(Arquillian.class)
public class DispatchTriggerResourceTest
	extends BaseDispatchTriggerResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());
	}

	@Override
	protected DispatchTrigger
		testGetDispatchTriggersPage_addDispatchTrigger(
		DispatchTrigger dispatchTrigger) throws Exception{

		return _addDispatchTrigger(dispatchTrigger);
	}

	@Override
	protected DispatchTrigger
	testPostDispatchTriggersPage_addDispatchTrigger(
		DispatchTrigger dispatchTrigger) throws Exception{

		return _addDispatchTrigger(dispatchTrigger);
	}

	private DispatchTrigger _addDispatchTrigger(
		DispatchTrigger dispatchTrigger)
		throws Exception{

		return dispatchTriggerResource.postDispatchTrigger(
			dispatchTrigger);
	}

}