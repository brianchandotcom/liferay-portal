/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export default class EventObserver {
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
