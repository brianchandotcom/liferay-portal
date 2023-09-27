/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.uad.test.MBMessageUADTestUtil;
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
public class MBMessageUADExporterTest
	extends BaseUADExporterTestCase<MBMessage>
	implements WhenHasStatusByUserIdField<MBMessage> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public MBMessage addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBMessage mbMessage =
			MBMessageUADTestUtil.addMBMessageWithStatusByUserId(
				_mbCategoryLocalService, _mbMessageLocalService, userId,
				statusByUserId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	@After
	public void tearDown() throws Exception {
		MBMessageUADTestUtil.cleanUpDependencies(
			_mbCategoryLocalService, _mbMessages);
	}

	@Override
	protected MBMessage addBaseModel(long userId) throws Exception {
		MBMessage mbMessage = MBMessageUADTestUtil.addMBMessage(
			_mbCategoryLocalService, _mbMessageLocalService, userId);

		_mbMessages.add(mbMessage);

		return mbMessage;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "messageId";
	}

	@Override
	protected UADExporter<MBMessage> getUADExporter() {
		return _uadExporter;
	}

	@Inject
	private MBCategoryLocalService _mbCategoryLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@DeleteAfterTestRun
	private final List<MBMessage> _mbMessages = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.message.boards.uad.exporter.MBMessageUADExporter"
	)
	private UADExporter<MBMessage> _uadExporter;

}