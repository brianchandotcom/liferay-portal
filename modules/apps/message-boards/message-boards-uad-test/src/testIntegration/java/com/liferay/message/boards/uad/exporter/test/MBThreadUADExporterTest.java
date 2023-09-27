/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.test.MBThreadUADTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class MBThreadUADExporterTest
	extends BaseUADExporterTestCase<MBThread>
	implements WhenHasStatusByUserIdField<MBThread> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public MBThread addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBThread mbThread = MBThreadUADTestUtil.addMBThreadWithStatusByUserId(
			_mbCategoryLocalService, _mbMessageLocalService,
			_mbThreadLocalService, userId, statusByUserId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	@After
	public void tearDown() throws Exception {
		MBThreadUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, _mbThreads);
	}

	@Override
	protected MBThread addBaseModel(long userId) throws Exception {
		MBThread mbThread = MBThreadUADTestUtil.addMBThread(
			_mbCategoryLocalService, _mbMessageLocalService,
			_mbThreadLocalService, userId);

		_mbThreads.add(mbThread);

		return mbThread;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "threadId";
	}

	@Override
	protected UADExporter<MBThread> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@DeleteAfterTestRun
	private final List<MBThread> _mbThreads = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.exporter.MBThreadUADExporter"
	)
	private UADExporter<MBThread> _uadExporter;

}