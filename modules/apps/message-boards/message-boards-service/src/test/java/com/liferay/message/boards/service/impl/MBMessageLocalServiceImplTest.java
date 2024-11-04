/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.impl.MBMessageImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mikel Lorza
 */
public class MBMessageLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetMessageURL() throws PortalException {
		MBMessage mbMessage = new MBMessageImpl();

		mbMessage.setMessageId(RandomTestUtil.randomLong());
		mbMessage.setCategoryId(RandomTestUtil.randomLong());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLayoutFullURL("http://localhost");

		Assert.assertEquals(
			"http://localhost/-/message_boards/view_message/" +
				mbMessage.getMessageId(),
			ReflectionTestUtil.invoke(
				new MBMessageLocalServiceImpl(), "_getMessageURL",
				new Class<?>[] {MBMessage.class, ServiceContext.class},
				new Object[] {mbMessage, serviceContext}));
	}

}