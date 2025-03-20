/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

import Analytics from '../analytics';
import {Analytics as AnalyticsType} from '../types';
import {getContexts} from '../utils/contexts';
import {removeDups} from '../utils/events';
import {setItem} from '../utils/storage';
import BaseQueue from './baseQueue';

class BaseCreateMessageQueue extends BaseQueue {
	analyticsInstance: Analytics;
	flushTo: AnalyticsType.Queues;

	constructor({
		analyticsInstance,
		flushTo,
		name,
	}: {
		analyticsInstance: Analytics;
		flushTo: AnalyticsType.Queues;
		name: AnalyticsType.Queues;
	}) {
		super({analyticsInstance, name});

		this.analyticsInstance = analyticsInstance;
		this.flushTo = flushTo;
	}

	onFlush() {
		const items = this.getItems<AnalyticsType.Event>();
		const storedContexts = getContexts();
		const eventsByContextHash = this._groupEventsByContextHash(items);
		const userId = this.analyticsInstance._getUserId();
		const promisesArr: Promise<Analytics>[] = [];

		storedContexts.forEach((context, hash) => {
			const events = eventsByContextHash[hash];

			if (events) {
				promisesArr.push(
					this.analyticsInstance[this.flushTo].addItem(
						this._createMessage({
							context,
							events,
							userId,
						})
					) as unknown as Promise<Analytics>
				);
			}
		});

		return promisesArr;
	}

	onFlushSuccess(results?: AnalyticsType.FlushResult[]) {
		const items = this.getItems<AnalyticsType.Event>();
		const filteredResults = results?.filter(
			(message) => message && message.value && message.value.events
		);
		const updatedItems = removeDups(
			filteredResults as AnalyticsType.FlushResult[],
			items
		);

		setItem(this.name, updatedItems);

		this.analyticsInstance.resetContext();
		this.reset();
	}

	/**
	 * Add all of the context and identifier information to an event batch.
	 */
	_createMessage({
		context,
		events,
		userId,
	}: {
		context: AnalyticsType.Context;
		events: AnalyticsType.Event[];
		userId: string;
	}) {
		const {channelId, ...otherProps} = context;

		const {
			dataSourceId,
			identity: {emailAddressHashed},
		} = this.analyticsInstance.config;

		return {
			channelId,
			context: {...otherProps},
			dataSourceId,
			emailAddressHashed,
			events,
			id: uuidv4(),
			userId,
		};
	}

	/**
	 * Returns an object with keys being context hash and values
	 * being events with that context hash.
	 *
	 * @example
	 * {
	 * 	1A2B3: [event, event],
	 * 	4A5B6: [event, event]
	 * }
	 */
	_groupEventsByContextHash(events: AnalyticsType.Event[]) {
		return events.reduce(
			(
				contextEventMap: {[key: string]: AnalyticsType.Event[]},
				event
			) => {
				if (contextEventMap[event.contextHash]) {
					contextEventMap[event.contextHash].push(event);
				}
				else {
					contextEventMap[event.contextHash] = [event];
				}

				return contextEventMap;
			},
			{} as {[key: string]: AnalyticsType.Event[]}
		);
	}
}

export {BaseCreateMessageQueue};
export default BaseCreateMessageQueue;
