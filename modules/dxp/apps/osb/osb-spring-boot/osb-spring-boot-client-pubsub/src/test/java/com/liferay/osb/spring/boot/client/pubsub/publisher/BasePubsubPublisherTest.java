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
	public void testErrorHandling() throws Exception {
		Exception exception = new Exception(
			RandomStringUtils.randomAlphanumeric(16));

		TestPubsubPublisher testPubsubPublisher = new TestPubsubPublisher(
			exception);

		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		testPubsubPublisher.publish(message);

		Assert.assertSame(
			exception, testPubsubPublisher.getHandledErrorException());
		Assert.assertSame(
			message, testPubsubPublisher.getHandledErrorMessage());
	}

	private static class TestPubsubPublisher extends BasePubsubPublisher {

		public TestPubsubPublisher(Exception exception) {
			_exception = exception;
		}

		public Exception getHandledErrorException() {
			return _handledErrorException;
		}

		public Message getHandledErrorMessage() {
			return _handledErrorMessage;
		}

		@Override
		protected void doPublish(Message message) throws Exception {
			throw _exception;
		}

		@Override
		protected String getProjectId() {
			return "test-project";
		}

		@Override
		protected void handleError(Message message, Exception exception) {
			_handledErrorException = exception;
			_handledErrorMessage = message;
		}

		private final Exception _exception;
		private Exception _handledErrorException;
		private Message _handledErrorMessage;

	}

}