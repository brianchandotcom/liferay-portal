/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProcessLock from 'browser-tabs-lock';

import Analytics from '../analytics';
import {Analytics as AnalyticsType} from '../types';
import {QUEUE_STORAGE_LIMIT} from '../utils/constants';
import {getRetryDelay} from '../utils/delay';
import {getItem, setItem, verifyStorageLimitForKey} from '../utils/storage';

class BaseQueue {
	maxSize: number;
	name: AnalyticsType.Queues;
	lock: ProcessLock;
	analyticsInstance: Analytics;

	constructor({
		analyticsInstance,
		name,
	}: {
		analyticsInstance: Analytics;
		name: AnalyticsType.Queues;
	}) {
		this.maxSize = QUEUE_STORAGE_LIMIT;
		this.name = name;
		this.lock = new ProcessLock();
		this.analyticsInstance = analyticsInstance;

		if (!getItem<string>(this.name)) {
			setItem(this.name, []);
		}

		this.addItem = this.addItem.bind(this);
	}

	/**
	 * Adds an item to the queue.
	 */
	addItem(item: AnalyticsType.Event | AnalyticsType.Identity) {
		this._enqueue(item);

		verifyStorageLimitForKey(this.name, this.maxSize);
	}

	/**
	 * Remove an item from the queue by id.
	 */
	_dequeue(id: string) {
		const queue = this.getItems<
			AnalyticsType.Message & {
				item?: AnalyticsType.Message;
			}
		>();

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
	 */
	_enqueue(entry: AnalyticsType.Event | AnalyticsType.Identity) {
		const queue = this.getItems<
			AnalyticsType.Event | AnalyticsType.Identity
		>();

		queue.push(entry);

		setItem(this.name, queue);
	}

	/**
	 * Get queued messages.
	 */
	getItems<T>(): T[] {
		return getItem<T[]>(this.name) || [];
	}

	hasItems() {
		return !!this.getItems<AnalyticsType.Event | AnalyticsType.Identity>()
			.length;
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
