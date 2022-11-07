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

import uuidv4 from 'uuid/v4';

import {getContexts} from '../utils/contexts';
import {removeDups} from '../utils/events';
import {setItem} from '../utils/storage';
import BaseQueue from './baseQueue';

class BaseCreateMessageQueue extends BaseQueue {
	constructor({analyticsInstance, flushTo, name}) {
		super({analyticsInstance, name});
		this.flushTo = flushTo;
	}

	onFlush() {
		const items = this.getItems();
		const storedContexts = getContexts();
		const eventsByContextHash = this._groupEventsByContextHash(items);
		const userId = this.analyticsInstance._getUserId();
		const promisesArr = [];

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
					)
				);
			}
		});

		return promisesArr;
	}

	onFlushSuccess(results) {
		const items = this.getItems();
		const filteredResults = results.filter(
			(message) => message && message.value && message.value.events
		);

		const updatedItems = removeDups(filteredResults, items);
		setItem(this.name, updatedItems);

		if (filteredResults.length === results.length) {
			this.analyticsInstance.resetContext();
		}
	}

	/**
	 * Add all of the context and identifier information to an event batch.
	 *
	 * @param {AnalyticsEvent}
	 * @returns {AnalyticsMessage}
	 */
	_createMessage({context, events, userId}) {
		const {channelId} = context;

		delete context.channelId;

		const {
			dataSourceId,
			identity: {emailAddressHashed},
		} = this.analyticsInstance.config;

		return {
			channelId,
			context,
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
	 * @example {
	 * 				1A2B3: [event, event],
	 * 				4A5B6: [event, event]
	 * 			}
	 * @param {Array.<AnalyticsEvent>}
	 * @returns {Object}
	 */
	_groupEventsByContextHash(events) {
		return events.reduce((contextEventMap, event) => {
			if (contextEventMap[event.contextHash]) {
				contextEventMap[event.contextHash].push(event);
			}
			else {
				contextEventMap[event.contextHash] = [event];
			}

			return contextEventMap;
		}, {});
	}
}

export {BaseCreateMessageQueue};
export default BaseCreateMessageQueue;
