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

import {core} from 'metal';
import State from 'metal-state';

/**
 * Adds compatibility for YUI events, re-emitting events according to YUI naming
 * and adding the capability of adding targets to bubble events to them.
 */
class CompatibilityEventProxy extends State {
	/**
	 * @inheritDoc
	 */
	constructor(config, element) {
		super(config, element);

		this.eventTargets_ = [];
		this.host = config.host;
		this.namespace = config.namespace;

		this.startCompatibility_();
	}

	/**
	 * Registers another event target as a bubble target.
	 *
	 * @param  {!Object} target The YUI component that receives the emitted
	 *         events.
	 * @private
	 */
	addTarget(target) {
		this.eventTargets_.push(target);
	}

	/**
	 * Checks if the event is an attribute modification event and adapts the
	 * event name accordingly.
	 *
	 * @param  {!String} eventName The event name.
	 * @private
	 * @return {String} The adapted event name.
	 */
	checkAttributeEvent_(eventName) {
		return eventName.replace(
			this.adaptedEvents.match,
			this.adaptedEvents.replace
		);
	}

	/**
	 * Emits the event adapted to YUI.
	 *
	 * @param  {!String} eventName The event name.
	 * @param  {!Event} event The event.
	 * @private
	 */
	emitCompatibleEvents_(eventName, event) {
		this.eventTargets_.forEach(target => {
			if (target.fire) {
				const prefixedEventName = this.namespace
					? this.namespace + ':' + eventName
					: eventName;
				const yuiEvent = target._yuievt.events[prefixedEventName];

				if (core.isObject(event)) {
					try {
						event.target = this.host;
					}
					catch (e) {
						// Do nothing
					}
				}

				let emitFacadeReference;

				if (!this.emitFacade && yuiEvent) {
					emitFacadeReference = yuiEvent.emitFacade;
					yuiEvent.emitFacade = false;
				}

				target.fire(prefixedEventName, event);

				if (!this.emitFacade && yuiEvent) {
					yuiEvent.emitFacade = emitFacadeReference;
				}
			}
		});
	}

	/**
	 * Emits YUI-based events to maintain backwards compatibility.
	 *
	 * @private
	 */
	startCompatibility_() {
		this.host.on('*', (event, eventFacade) => {
			if (!eventFacade) {
				eventFacade = event;
			}

			const compatibleEvent = this.checkAttributeEvent_(eventFacade.type);

			if (compatibleEvent !== eventFacade.type) {
				eventFacade.type = compatibleEvent;
				this.host.emit(compatibleEvent, event, eventFacade);
			}
			else if (this.eventTargets_.length > 0) {
				this.emitCompatibleEvents_(compatibleEvent, event);
			}
		});
	}
}

/**
 * State definition.
 *
 * @ignore
 * @static
 * @type {!Object}
 */
CompatibilityEventProxy.STATE = {
	/**
	 * Replaces event names with adapted YUI names.
	 *
	 * @instance
	 * @memberof CompatibilityEventProxy
	 * @type {Object}
	 */
	adaptedEvents: {
		value: {
			match: /(.*)(Changed)$/,
			replace: '$1Change'
		}
	},

	/**
	 * Whether the event facade should be emitted to the target.
	 *
	 * @default false
	 * @instance
	 * @memberof CompatibilityEventProxy
	 * @type {String}
	 */
	emitFacade: {
		value: false
	}
};

export default CompatibilityEventProxy;
