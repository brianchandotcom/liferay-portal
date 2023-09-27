/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			!!this.subscribes[event].length
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
