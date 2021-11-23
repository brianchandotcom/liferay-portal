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

import java.util.function.BiConsumer;

/**
 * @author Raymond Augé
 */
public class MockEventListener implements BiConsumer<String, Message> {

	@Override
	public void accept(String eventName, Message eventPayload) {
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
			BiConsumer<String, Message> missingConsumer) {

			_missingConsumer = missingConsumer;

			return this;
		}

		public Builder prepareConsumer(
			BiConsumer<String, Message> prepareConsumer) {

			_prepareConsumer = prepareConsumer;

			return this;
		}

		public Builder processingErrorConsumer(
			BiConsumer<String, Message> processingErrorConsumer) {

			_processingErrorConsumer = processingErrorConsumer;

			return this;
		}

		public Builder sizeExceededConsumer(
			BiConsumer<String, Message> sizeExceededConsumer) {

			_sizeExceededConsumer = sizeExceededConsumer;

			return this;
		}

		public Builder successConsumer(
			BiConsumer<String, Message> successConsumer) {

			_successConsumer = successConsumer;

			return this;
		}

		public Builder virusFoundConsumer(
			BiConsumer<String, Message> virusFoundConsumer) {

			_virusFoundConsumer = virusFoundConsumer;

			return this;
		}

		private BiConsumer<String, Message> _missingConsumer = (n, p) -> {
		};
		private BiConsumer<String, Message> _prepareConsumer = (n, p) -> {
		};
		private BiConsumer<String, Message> _processingErrorConsumer =
			(n, p) -> {
			};
		private BiConsumer<String, Message> _sizeExceededConsumer = (n, p) -> {
		};
		private BiConsumer<String, Message> _successConsumer = (n, p) -> {
		};
		private BiConsumer<String, Message> _virusFoundConsumer = (n, p) -> {
		};

	}

	private MockEventListener(
		BiConsumer<String, Message> missingConsumer,
		BiConsumer<String, Message> prepareConsumer,
		BiConsumer<String, Message> processingErrorConsumer,
		BiConsumer<String, Message> sizeExceededConsumer,
		BiConsumer<String, Message> successConsumer,
		BiConsumer<String, Message> virusFoundConsumer) {

		_missingConsumer = missingConsumer;
		_prepareConsumer = prepareConsumer;
		_processingErrorConsumer = processingErrorConsumer;
		_sizeExceededConsumer = sizeExceededConsumer;
		_successConsumer = successConsumer;
		_virusFoundConsumer = virusFoundConsumer;
	}

	private final BiConsumer<String, Message> _missingConsumer;
	private final BiConsumer<String, Message> _prepareConsumer;
	private final BiConsumer<String, Message> _processingErrorConsumer;
	private final BiConsumer<String, Message> _sizeExceededConsumer;
	private final BiConsumer<String, Message> _successConsumer;
	private final BiConsumer<String, Message> _virusFoundConsumer;

}