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

import ProcessLock from 'browser-tabs-lock';

import {QUEUE_STORAGE_LIMIT} from '../utils/constants';
import {getRetryDelay} from '../utils/delay';
import {getItem, setItem, verifyStorageLimitForKey} from '../utils/storage';

class BaseQueue {
	constructor({analyticsInstance, name}) {
		this.maxSize = QUEUE_STORAGE_LIMIT;
		this.name = name;
		this.lock = new ProcessLock();
		this.analyticsInstance = analyticsInstance;

		if (!getItem(this.name)) {
			setItem(this.name, []);
		}

		this.addItem = this.addItem.bind(this);
	}

	/**
	 * Adds an item to the queue.
	 *
	 * @param {AnalyticsMessage} item
	 * @returns {Promise}
	 */
	addItem(item) {
		this._enqueue(item);

		return verifyStorageLimitForKey(this.name, this.maxSize);
	}

	/**
	 * Remove an item from the queue by id.
	 *
	 * @param {string} id - The Message ID.
	 */
	_dequeue(id) {
		const queue = this.getItems();

		setItem(
			this.name,
			queue.filter(({id: idMessage, item}) => {
				if (item) {
					return item.id !== id;
				}

				return id !== idMessage;
			})
		);
	}

	/**
	 * Add a message to the queue and process messages.
	 *
	 * @param {Message} entry
	 * @param {boolean} - Whether _processMessages should run immediately after enqueuing message.
	 */
	_enqueue(entry) {
		const queue = this.getItems();

		queue.push(entry);

		setItem(this.name, queue);
	}

	/**
	 * Get queued messages.
	 *
	 * @returns {Array.<AnalyticsMessage>}
	 */
	getItems() {
		return getItem(this.name) || [];
	}

	hasItems() {
		return !!this.getItems().length;
	}

	acquireLock() {
		return this.lock.acquireLock(this.name);
	}

	releaseLock() {
		return this.lock.releaseLock(this.name);
	}

	reset() {
		setItem(this.name, []);
	}

	shouldFlush() {
		if (this.analyticsInstance._isTrackingDisabled()) {
			this.analyticsInstance._disposeInternal();

			return false;
		}
		else {
			return true;
		}
	}

	onFlush() {
		throw Error('onFlush should be implemented on the new class');
	}

	onFlushFail() {}

	onFlushSuccess() {}
}

export {BaseQueue, getRetryDelay};
export default BaseQueue;
