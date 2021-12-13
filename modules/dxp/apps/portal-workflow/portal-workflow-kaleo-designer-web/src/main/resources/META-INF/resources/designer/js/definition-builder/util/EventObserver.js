/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

class EventObserver {
	constructor() {
		this.subscribes = {};
	}

	subscribe(event, callback) {
		if (!Array.isArray(this.subscribes[event])) {
			this.subscribes[event] = [];
		}

		const length = this.subscribes[event].push(callback);

		return {event, index: length - 1};
	}

	length(event) {
		if (this.subscribes[event]) {
			return this.subscribes[event].filter((callback) => !!callback)
				.length;
		}

		return 0;
	}

	notify(event, data) {
		if (
			Array.isArray(this.subscribes[event]) &&
			this.subscribes[event].length > 0
		) {
			try {
				this.subscribes[event].map(
					(callback) => callback && callback(data)
				);
			}
			catch (error) {}
		}
	}

	unsubscribe({event, index}) {
		if (this.subscribes[event] && this.subscribes[event][index]) {
			delete this.subscribes[event][index];
		}
	}
}

const singleEventObserver = new EventObserver();

export {singleEventObserver};

export default EventObserver;
