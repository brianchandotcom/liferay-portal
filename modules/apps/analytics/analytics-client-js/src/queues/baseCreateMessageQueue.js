/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

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

		this.analyticsInstance.resetContext();
		this.reset();
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
