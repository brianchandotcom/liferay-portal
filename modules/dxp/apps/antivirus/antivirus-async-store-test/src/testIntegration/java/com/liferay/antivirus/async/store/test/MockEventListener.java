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
import com.liferay.antivirus.async.store.events.AntivirusAsyncEventListener;
import com.liferay.portal.kernel.messaging.Message;

import java.util.function.Consumer;

/**
 * @author Raymond Augé
 */
public class MockEventListener implements AntivirusAsyncEventListener {

	@Override
	public void receive(Message eventPayload) {
		AntivirusAsyncEvent antivirusAsyncEvent =
			(AntivirusAsyncEvent)eventPayload.get("antivirusAsyncEvent");

		if (antivirusAsyncEvent == AntivirusAsyncEvent.MISSING) {
			_missingConsumer.accept(eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PREPARE) {
			_prepareConsumer.accept(eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_processingErrorConsumer.accept(eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) {
			_sizeExceededConsumer.accept(eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) {
			_successConsumer.accept(eventPayload);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND) {
			_virusFoundConsumer.accept(eventPayload);
		}
	}

	public static class Builder {

		public MockEventListener build() {
			return new MockEventListener(
				_missingConsumer, _prepareConsumer, _processingErrorConsumer,
				_sizeExceededConsumer, _successConsumer, _virusFoundConsumer);
		}

		public Builder missingConsumer(Consumer<Message> missingConsumer) {
			_missingConsumer = missingConsumer;

			return this;
		}

		public Builder prepareConsumer(Consumer<Message> prepareConsumer) {
			_prepareConsumer = prepareConsumer;

			return this;
		}

		public Builder processingErrorConsumer(
			Consumer<Message> processingErrorConsumer) {

			_processingErrorConsumer = processingErrorConsumer;

			return this;
		}

		public Builder sizeExceededConsumer(
			Consumer<Message> sizeExceededConsumer) {

			_sizeExceededConsumer = sizeExceededConsumer;

			return this;
		}

		public Builder successConsumer(Consumer<Message> successConsumer) {
			_successConsumer = successConsumer;

			return this;
		}

		public Builder virusFoundConsumer(
			Consumer<Message> virusFoundConsumer) {

			_virusFoundConsumer = virusFoundConsumer;

			return this;
		}

		private Consumer<Message> _missingConsumer = message -> {
		};
		private Consumer<Message> _prepareConsumer = message -> {
		};
		private Consumer<Message> _processingErrorConsumer = message -> {
		};
		private Consumer<Message> _sizeExceededConsumer = message -> {
		};
		private Consumer<Message> _successConsumer = message -> {
		};
		private Consumer<Message> _virusFoundConsumer = message -> {
		};

	}

	private MockEventListener(
		Consumer<Message> missingConsumer, Consumer<Message> prepareConsumer,
		Consumer<Message> processingErrorConsumer,
		Consumer<Message> sizeExceededConsumer,
		Consumer<Message> successConsumer,
		Consumer<Message> virusFoundConsumer) {

		_missingConsumer = missingConsumer;
		_prepareConsumer = prepareConsumer;
		_processingErrorConsumer = processingErrorConsumer;
		_sizeExceededConsumer = sizeExceededConsumer;
		_successConsumer = successConsumer;
		_virusFoundConsumer = virusFoundConsumer;
	}

	private final Consumer<Message> _missingConsumer;
	private final Consumer<Message> _prepareConsumer;
	private final Consumer<Message> _processingErrorConsumer;
	private final Consumer<Message> _sizeExceededConsumer;
	private final Consumer<Message> _successConsumer;
	private final Consumer<Message> _virusFoundConsumer;

}