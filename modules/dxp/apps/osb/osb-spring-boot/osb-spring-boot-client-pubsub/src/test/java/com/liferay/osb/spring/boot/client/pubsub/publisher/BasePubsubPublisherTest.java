/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.publisher;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BasePubsubPublisherTest {

	@Test
	public void testBuildRetrySettingsDefaultsToNull() {
		RecordingPublisher recordingPublisher = new RecordingPublisher();

		Assert.assertNull(recordingPublisher.buildRetrySettings());
	}

	@Test
	public void testDefaultHandleErrorRethrows() {
		String exceptionMessage = RandomStringUtils.randomAlphanumeric(16);

		ThrowingPublisher throwingPublisher = new ThrowingPublisher(
			exceptionMessage);

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		Exception exception = Assert.assertThrows(
			Exception.class, () -> throwingPublisher.publish(message));

		Assert.assertEquals(exceptionMessage, exception.getMessage());
	}

	@Test
	public void testGetOrderingKeyReturnsClassName() {
		RecordingPublisher recordingPublisher = new RecordingPublisher();

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		Assert.assertEquals(
			RecordingPublisher.class.getName(),
			recordingPublisher.getOrderingKey(message));
	}

	@Test
	public void testGetPublishTimeoutSecondsDefault() {
		RecordingPublisher recordingPublisher = new RecordingPublisher();

		Assert.assertEquals(60, recordingPublisher.getPublishTimeoutSeconds());
	}

	@Test
	public void testPublishDelegatesToDoPublish() throws Exception {
		RecordingPublisher recordingPublisher = new RecordingPublisher();

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		recordingPublisher.publish(message);

		Assert.assertEquals(1, recordingPublisher.getPublishCount());
		Assert.assertSame(message, recordingPublisher.getMessage());
	}

	@Test
	public void testPublishRoutesExceptionToHandleError() throws Exception {
		HandleErrorPublisher handleErrorPublisher = new HandleErrorPublisher();

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		handleErrorPublisher.publish(message);

		Assert.assertEquals(1, handleErrorPublisher.getHandledCount());
		Assert.assertSame(message, handleErrorPublisher.getMessage());
	}

	private static class HandleErrorPublisher extends BasePubsubPublisher {

		public int getHandledCount() {
			return _handledCount;
		}

		public Message getMessage() {
			return _message;
		}

		@Override
		protected void doPublish(Message message) throws Exception {
			throw new Exception(RandomStringUtils.randomAlphanumeric(12));
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		@Override
		protected void handleError(Message message, Exception exception) {
			_handledCount++;
			_message = message;
		}

		private int _handledCount;
		private Message _message;

	}

	private static class RecordingPublisher extends BasePubsubPublisher {

		public Message getMessage() {
			return _message;
		}

		public int getPublishCount() {
			return _publishCount;
		}

		@Override
		protected void doPublish(Message message) {
			_message = message;
			_publishCount++;
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		private Message _message;
		private int _publishCount;

	}

	private static class ThrowingPublisher extends BasePubsubPublisher {

		public ThrowingPublisher(String exceptionMessage) {
			_exceptionMessage = exceptionMessage;
		}

		@Override
		protected void doPublish(Message message) throws Exception {
			throw new Exception(_exceptionMessage);
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		private final String _exceptionMessage;

	}

}