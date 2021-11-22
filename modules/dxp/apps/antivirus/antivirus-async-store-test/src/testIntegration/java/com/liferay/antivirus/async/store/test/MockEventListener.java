/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.test;

import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.portal.kernel.messaging.Message;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Raymond Augé
 */
public class MockEventListener
	implements BiConsumer<String, Map.Entry<Message, Object[]>> {

	@Override
	public void accept(
		String eventName, Map.Entry<Message, Object[]> eventPayload) {

		AntivirusAsyncEvent antivirusAsyncEvent = AntivirusAsyncEvent.valueOf(
			eventName);

		if (antivirusAsyncEvent == AntivirusAsyncEvent.MISSING) {
			_missingConsumer.accept(eventName, eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PREPARE) {
			_prepareConsumer.accept(eventName, eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_processingErrorConsumer.accept(eventName, eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) {
			_sizeExceededConsumer.accept(eventName, eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) {
			_successConsumer.accept(eventName, eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND) {
			_virusFoundConsumer.accept(eventName, eventPayload);
		}
	}

	public static class Builder {

		public MockEventListener build() {
			return new MockEventListener(
				_missingConsumer, _prepareConsumer, _processingErrorConsumer,
				_sizeExceededConsumer, _successConsumer, _virusFoundConsumer);
		}

		public Builder missingConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>> missingConsumer) {

			_missingConsumer = missingConsumer;

			return this;
		}

		public Builder prepareConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>> prepareConsumer) {

			_prepareConsumer = prepareConsumer;

			return this;
		}

		public Builder processingErrorConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>>
				processingErrorConsumer) {

			_processingErrorConsumer = processingErrorConsumer;

			return this;
		}

		public Builder sizeExceededConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>>
				sizeExceededConsumer) {

			_sizeExceededConsumer = sizeExceededConsumer;

			return this;
		}

		public Builder successConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>> successConsumer) {

			_successConsumer = successConsumer;

			return this;
		}

		public Builder virusFoundConsumer(
			BiConsumer<String, Map.Entry<Message, Object[]>>
				virusFoundConsumer) {

			_virusFoundConsumer = virusFoundConsumer;

			return this;
		}

		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_missingConsumer = (n, p) -> {
			};
		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_prepareConsumer = (n, p) -> {
			};
		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_processingErrorConsumer = (n, p) -> {
			};
		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_sizeExceededConsumer = (n, p) -> {
			};
		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_successConsumer = (n, p) -> {
			};
		private BiConsumer<String, Map.Entry<Message, Object[]>>
			_virusFoundConsumer = (n, p) -> {
			};

	}

	private MockEventListener(
		BiConsumer<String, Map.Entry<Message, Object[]>> missingConsumer,
		BiConsumer<String, Map.Entry<Message, Object[]>> prepareConsumer,
		BiConsumer<String, Map.Entry<Message, Object[]>>
			processingErrorConsumer,
		BiConsumer<String, Map.Entry<Message, Object[]>> sizeExceededConsumer,
		BiConsumer<String, Map.Entry<Message, Object[]>> successConsumer,
		BiConsumer<String, Map.Entry<Message, Object[]>> virusFoundConsumer) {

		_missingConsumer = missingConsumer;
		_prepareConsumer = prepareConsumer;
		_processingErrorConsumer = processingErrorConsumer;
		_sizeExceededConsumer = sizeExceededConsumer;
		_successConsumer = successConsumer;
		_virusFoundConsumer = virusFoundConsumer;
	}

	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_missingConsumer;
	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_prepareConsumer;
	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_processingErrorConsumer;
	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_sizeExceededConsumer;
	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_successConsumer;
	private final BiConsumer<String, Map.Entry<Message, Object[]>>
		_virusFoundConsumer;

}