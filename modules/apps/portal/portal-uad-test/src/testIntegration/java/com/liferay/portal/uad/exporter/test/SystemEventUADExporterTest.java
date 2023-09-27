/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class SystemEventUADExporterTest
	extends BaseUADExporterTestCase<SystemEvent> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected SystemEvent addBaseModel(long userId) throws Exception {
		SystemEvent systemEvent = _systemEventLocalService.addSystemEvent(
			userId, TestPropsValues.getGroupId(), Group.class.getName(),
			RandomTestUtil.nextLong(), PortalUUIDUtil.generate(),
			StringPool.BLANK, SystemEventConstants.TYPE_DELETE,
			StringPool.BLANK);

		_systemEvents.add(systemEvent);

		return systemEvent;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "systemEventId";
	}

	@Override
	protected UADExporter<SystemEvent> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private SystemEventLocalService _systemEventLocalService;

	@DeleteAfterTestRun
	private final List<SystemEvent> _systemEvents = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.portal.uad.exporter.SystemEventUADExporter"
	)
	private UADExporter<SystemEvent> _uadExporter;

}