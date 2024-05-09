/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.rest.client.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.client.serdes.v1_0.NotificationQueueEntrySerDes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class NotificationQueueEntryResourceTest
	extends BaseNotificationQueueEntryResourceTestCase {

	@Override
	@Test
	public void testClientSerDesToDTO() throws Exception {
		super.testClientSerDesToDTO();

		NotificationQueueEntry notificationQueueEntry1 =
			new NotificationQueueEntry();

		notificationQueueEntry1.setBody("Something...");
		notificationQueueEntry1.setSubject("Something...");
		notificationQueueEntry1.setType("email");

		notificationQueueEntry1.setRecipients(
			new Object[] {
				new TreeMap<>(
					HashMapBuilder.put(
						"from", "first@liferay.com"
					).put(
						"fromName", "first"
					).put(
						"to", "first@liferay.com"
					).build()),
				new TreeMap<>(
					HashMapBuilder.put(
						"from", "second@liferay.com"
					).put(
						"fromName", "second"
					).put(
						"to", "second@liferay.com"
					).build()),
				new Object[] {
					new TreeMap<>(
						HashMapBuilder.put(
							"from", "third@liferay.com"
						).put(
							"fromName", "third"
						).put(
							"to", "third@liferay.com"
						).build())
				}
			});

		JSONAssert.assertEquals(
			JSONUtil.put(
				"body", "Something..."
			).put(
				"recipients",
				JSONUtil.putAll(
					JSONUtil.put(
						"from", "first@liferay.com"
					).put(
						"fromName", "first"
					).put(
						"to", "first@liferay.com"
					),
					JSONUtil.put(
						"from", "second@liferay.com"
					).put(
						"fromName", "second"
					).put(
						"to", "second@liferay.com"
					),
					JSONUtil.putAll(
						JSONUtil.put(
							"from", "third@liferay.com"
						).put(
							"fromName", "third"
						).put(
							"to", "third@liferay.com"
						)))
			).put(
				"subject", "Something..."
			).put(
				"type", "email"
			).toString(),
			notificationQueueEntry1.toString(), JSONCompareMode.LENIENT);

		NotificationQueueEntrySerDes.NotificationQueueEntryJSONParser
			notificationQueueEntryJSONParser =
				new NotificationQueueEntrySerDes.
					NotificationQueueEntryJSONParser();

		NotificationQueueEntry notificationQueueEntry2 =
			notificationQueueEntryJSONParser.parseToDTO(
				String.valueOf(notificationQueueEntry1));

		Assert.assertEquals(notificationQueueEntry1, notificationQueueEntry2);
	}

}