/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.router;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.subscriber.MessageSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BaseMessageRouterTest {

	@Test
	public void testFanOutToMultipleMatchingSubscribers() {
		String matchedTopic = "topic-" + RandomStringUtils.randomAlphabetic(6);
		String otherTopic = "other-" + RandomStringUtils.randomAlphabetic(6);

		BaseMessageRouter router = new BaseMessageRouter();

		RecordingSubscriber subscriber1 = new RecordingSubscriber();
		RecordingSubscriber subscriber2 = new RecordingSubscriber();
		RecordingSubscriber subscriber3 = new RecordingSubscriber();

		router.addRoute(subscriber1, Collections.singletonList(matchedTopic));
		router.addRoute(subscriber2, Collections.singletonList(matchedTopic));
		router.addRoute(subscriber3, Collections.singletonList(otherTopic));

		router.route(
			matchedTopic,
			new Message(RandomStringUtils.randomAlphanumeric(12)));

		Assert.assertEquals(
			1,
			subscriber1.getReceived(
			).size());
		Assert.assertEquals(
			1,
			subscriber2.getReceived(
			).size());
		Assert.assertEquals(
			0,
			subscriber3.getReceived(
			).size());
	}

	@Test
	public void testNoSubscribersIsHarmless() {
		BaseMessageRouter router = new BaseMessageRouter();

		router.route(
			"unknown-" + RandomStringUtils.randomAlphabetic(6),
			new Message(RandomStringUtils.randomAlphanumeric(12)));
	}

	@Test
	public void testRegexRoutingDispatchesToMatchingSubscriber() {
		BaseMessageRouter router = new BaseMessageRouter();

		RecordingSubscriber subscriber = new RecordingSubscriber();

		router.addRoute(subscriber, Collections.singletonList("matched\\..*"));

		router.route(
			"matched.event.created",
			new Message(RandomStringUtils.randomAlphanumeric(12)));
		router.route(
			"unmatched-event",
			new Message(RandomStringUtils.randomAlphanumeric(12)));

		Assert.assertEquals(
			1,
			subscriber.getReceived(
			).size());
	}

	@Test
	public void testSubscriberWithMultiplePatterns() {
		String pattern1 = RandomStringUtils.randomAlphabetic(6);
		String pattern2 = RandomStringUtils.randomAlphabetic(6);
		String noMatchTopic = RandomStringUtils.randomAlphabetic(6);

		BaseMessageRouter router = new BaseMessageRouter();

		RecordingSubscriber subscriber = new RecordingSubscriber();

		router.addRoute(subscriber, Arrays.asList(pattern1, pattern2));

		router.route(
			pattern1, new Message(RandomStringUtils.randomAlphanumeric(12)));
		router.route(
			pattern2, new Message(RandomStringUtils.randomAlphanumeric(12)));
		router.route(
			noMatchTopic,
			new Message(RandomStringUtils.randomAlphanumeric(12)));

		Assert.assertEquals(
			2,
			subscriber.getReceived(
			).size());
	}

	private static class RecordingSubscriber implements MessageSubscriber {

		public List<Message> getReceived() {
			return _received;
		}

		@Override
		public void receive(Message message) {
			_received.add(message);
		}

		private final List<Message> _received = new ArrayList<>();

	}

}