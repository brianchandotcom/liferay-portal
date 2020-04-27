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

export default class EventEmitter {
	constructor() {
		this.events = {};
	}

	on(event, listener) {
		if (typeof this.events[event] !== 'object') {
			this.events[event] = [];
		}

		this.events[event].push(listener);

		return () => this.removeListener(event, listener);
	}

	removeListener(event, listener) {
		if (typeof this.events[event] === 'object') {
			const idx = this.events[event].indexOf(listener);

			if (idx > -1) {
				this.events[event].splice(idx, 1);
			}
		}
	}

	emit(event, ...args) {
		if (typeof this.events[event] === 'object') {
			this.events[event].forEach((listener) =>
				listener.apply(this, args)
			);
		}
	}

	once(event, listener) {
		const remove = this.on(event, (...args) => {
			remove();

			listener.apply(this, args);
		});
	}
}
