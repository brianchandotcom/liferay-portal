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
import com.liferay.dispatch.rest.client.dto.v1_0.DispatchTrigger;
import com.liferay.portal.kernel.service.ServiceContext;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
	@Override
	protected DispatchTrigger
	testPostDispatchTriggersPageRun_addDispatchTrigger(
		DispatchTrigger dispatchTrigger) throws Exception{

		return _addDispatchTrigger(dispatchTrigger);
	}

	@Override
	protected DispatchTrigger
			testGraphQLDispatchTriggersPage_addDispatchTriggersPage()
		throws Exception {

		return _addDispatchTriggersPage(randomDispatchTriggersPage());
	}


	private DispatchTrigger _addDispatchTrigger(
		DispatchTrigger dispatchTrigger)
		throws Exception{

		return dispatchTriggerResource.postDispatchTrigger(
			dispatchTrigger);
	}

	@Override
	protected DispatchTrigger randomDispatchTrigger()
	throws Exception {

	DispatchTrigger dispatchTrigger =
		super.randomDispatchTrigger();

	dispatchTrigger.setExternalReferenceCode(StringUtil.toLowerCase(
		RandomTestUtil.randomString()));
	dispatchTrigger.setCompanyId(RandomTestUtil.randomLong());
	dispatchTrigger.setActive(RandomTestUtil.randomBoolean());
	dispatchTrigger.setCronExpression(StringUtil.toLowerCase(
		RandomTestUtil.randomString()));
	dispatchTrigger.setDispatchTaskClusterMode(RandomTestUtil.randomInt());
	dispatchTrigger.setDispatchTaskExecutorType(StringUtil.toLowerCase(
		RandomTestUtil.randomString()));
	dispatchTrigger.setEndDate(RandomTestUtil.nextDate());
	dispatchTrigger.setId(RandomTestUtil.randomLong());
	dispatchTrigger.setName(StringUtil.toLowerCase(RandomTestUtil.randomString()));
	dispatchTrigger.setTimeZoneId(StringUtil.toLowerCase(
		RandomTestUtil.randomString()));

	return dispatchTrigger;
}

}