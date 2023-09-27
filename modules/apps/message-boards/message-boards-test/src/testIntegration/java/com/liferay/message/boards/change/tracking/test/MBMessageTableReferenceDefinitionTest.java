/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class MBMessageTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		MBMessage rootMBMessage = MBTestUtil.addMessage(
			group.getGroupId(), TestPropsValues.getUserId(),
			MBThread.class.getSimpleName(), MBThread.class.getName());

		_mbThread = rootMBMessage.getThread();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		User user = TestPropsValues.getUser();

		MBMessage mbMessage = _mbMessageLocalService.addMessage(
			null, user.getUserId(), user.getScreenName(), group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			_mbThread.getThreadId(),
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			MBMessage.class.getSimpleName(), MBMessage.class.getName(),
			MBMessageConstants.DEFAULT_FORMAT, null, false, 0.0, false,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return mbMessage.getThread();
	}

	@Inject
	private static MBMessageLocalService _mbMessageLocalService;

	private MBThread _mbThread;

}