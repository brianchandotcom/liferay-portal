/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTMessage;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class CTMessageLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCTMessage() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		long ctCollectionId = RandomTestUtil.nextLong();

		CTMessage ctMessage = _addCTMessage(companyId, ctCollectionId);

		Assert.assertEquals(companyId, ctMessage.getCompanyId());

		String messageContent = ctMessage.getMessageContent();

		Assert.assertFalse(messageContent.contains("\"companyId\""));
	}

	@Test
	public void testGetMessages() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		long ctCollectionId = RandomTestUtil.nextLong();

		CTMessage ctMessage = _addCTMessage(companyId, ctCollectionId);

		String messageContent = ctMessage.getMessageContent();

		Assert.assertFalse(messageContent.contains("\"companyId\""));

		List<Message> messages = _ctMessageLocalService.getMessages(
			ctCollectionId);

		Message message = messages.get(0);

		Assert.assertEquals(companyId, message.get("companyId"));
	}

	private CTMessage _addCTMessage(long companyId, long ctCollectionId) {
		Message message = new Message();

		message.put("companyId", companyId);

		message.setPayload(
			JSONUtil.put(
				"testKey", "testValue"
			).toString());

		return _ctMessageLocalService.addCTMessage(ctCollectionId, message);
	}

	@Inject
	private static CTMessageLocalService _ctMessageLocalService;

}