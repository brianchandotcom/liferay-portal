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
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.dispatch.rest.client.dto.v1_0.DispatchTrigger;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nilton Vieira
 */
@RunWith(Arquillian.class)
public class DispatchTriggerResourceTest
	extends BaseDispatchTriggerResourceTestCase {

	@Override
	@Test
	public void testPostDispatchTrigger() throws Exception {
		_addDispatchTrigger(randomDispatchTrigger());
	}

	@Override
	@Test
	public void testPostDispatchTriggerRun() throws Exception {
		DispatchTrigger dispatchTrigger = _addDispatchTrigger(
			randomDispatchTrigger());

		dispatchTriggerResource.postDispatchTriggerRun(dispatchTrigger.getId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "dispatchTaskExecutorType"};
	}

	@Override
	protected DispatchTrigger randomDispatchTrigger() throws Exception {
		return new DispatchTrigger() {
			{
				active = RandomTestUtil.randomBoolean();
				companyId = RandomTestUtil.randomLong();
				cronExpression = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dispatchTaskClusterMode = RandomTestUtil.randomInt();
				dispatchTaskExecutorType = _getRandomDispatchExecutorType();
				dispatchTaskSettings = LocalizedMapUtil.getI18nMap(
					RandomTestUtil.randomLocaleStringMap());
				endDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				overlapAllowed = RandomTestUtil.randomBoolean();
				startDate = RandomTestUtil.nextDate();
				system = RandomTestUtil.randomBoolean();
				timeZoneId = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	@Override
	protected DispatchTrigger testGetDispatchTriggersPage_addDispatchTrigger(
			DispatchTrigger dispatchTrigger)
		throws Exception {

		return _addDispatchTrigger(dispatchTrigger);
	}

	@Override
	protected DispatchTrigger testGraphQLDispatchTrigger_addDispatchTrigger()
		throws Exception {

		return _addDispatchTrigger(randomDispatchTrigger());
	}

	private DispatchTrigger _addDispatchTrigger(DispatchTrigger dispatchTrigger)
		throws Exception {

		return dispatchTriggerResource.postDispatchTrigger(dispatchTrigger);
	}

	private String _getRandomDispatchExecutorType() {
		Set<String> dispatchTaskExecutorTypes =
			_dispatchTaskExecutorRegistry.getDispatchTaskExecutorTypes();

		int index = 0;
		int randomIndex = RandomTestUtil.randomInt(
			0, dispatchTaskExecutorTypes.size() - 1);

		for (String dispatchTaskExecutorType : dispatchTaskExecutorTypes) {
			if (index++ == randomIndex) {
				return dispatchTaskExecutorType;
			}
		}

		return "test";
	}

	@Inject
	private DispatchTaskExecutorRegistry _dispatchTaskExecutorRegistry;

}