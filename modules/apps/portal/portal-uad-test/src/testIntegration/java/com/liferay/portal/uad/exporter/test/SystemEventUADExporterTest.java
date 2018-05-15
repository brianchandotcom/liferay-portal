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

package com.liferay.portal.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.uad.test.SystemEventUADTestHelper;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class SystemEventUADExporterTest extends BaseUADExporterTestCase<SystemEvent> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_systemEventUADTestHelper.cleanUpDependencies(_systemEvents);
	}

	@Override
	protected SystemEvent addBaseModel(long userId) throws Exception {
		SystemEvent systemEvent = _systemEventUADTestHelper.addSystemEvent(userId);

		_systemEvents.add(systemEvent);

		return systemEvent;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "systemEventId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<SystemEvent> _systemEvents = new ArrayList<SystemEvent>();
	@Inject
	private SystemEventUADTestHelper _systemEventUADTestHelper;
	@Inject(filter = "component.name=*.SystemEventUADExporter")
	private UADExporter _uadExporter;
}