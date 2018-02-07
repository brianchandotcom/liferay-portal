import State from 'metal-state';
import {core} from 'metal';

/**
 * CompatibilityEventProxy
 *
 * This class adds compatibility for YUI events, re-emitting events
 * according to YUI naming and adding the capability of adding targets
 * to bubble events to them.
 * @review
 */

class CompatibilityEventProxy extends State {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(config, element) {
		super(config, element);

		this.eventTargets_ = [];
		this.host = config.host;
		this.namespace = config.namespace;

		this.startCompatibility_();
	}

	/**
	 * Registers another EventTarget as a bubble target.
	 * @param  {!Object} target YUI component where events will be emited to
	 * @private
	 * @review
	 */

	addTarget(target) {
		this.eventTargets_.push(target);
	}

	/**
	 * Check if the event is an attribute modification event and addapt
	 * the eventName.
	 * @param  {!String} eventName
	 * @private
	 * @return {String} Adapted event name
	 * @review
	 */

	checkAttributeEvent_(eventName) {
		return eventName.replace(
			this.adaptedEvents.match,
			this.adaptedEvents.replace
		);
	}

	/**
	 * Emit the event adapted to yui
	 * @param  {!String} eventName
	 * @param  {!Event} event
	 * @private
	 * @review
	 */

	emitCompatibleEvents_(eventName, event) {
		this.eventTargets_.forEach(
			target => {
				if (target.fire) {
					let prefixedEventName = this.namespace ?
						this.namespace + ':' + eventName :
						eventName;
					let yuiEvent = target._yuievt.events[prefixedEventName];

					if (core.isObject(event)) {
						try {
							event.target = this.host;
						}
						catch (e) {
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
			}
		);
	}

	/**
	 * Configuration to emit yui-based events to maintain
	 * backwards compatibility.
	 * @private
	 * @review
	 */

	startCompatibility_() {
		this.host.on(
			'*',
			(event, eventFacade) => {
				if (!eventFacade) {
					eventFacade = event;
				}

				let compatibleEvent = this.checkAttributeEvent_(eventFacade.type);

				if (compatibleEvent !== eventFacade.type) {
					eventFacade.type = compatibleEvent;
					this.host.emit(compatibleEvent, event, eventFacade);
				}
				else if (this.eventTargets_.length > 0) {
					this.emitCompatibleEvents_(compatibleEvent, event);
				}
			}
		);
	}
}

/**
 * State definition.
 * @ignore
 * @review
 * @static
 * @type {!Object}
 */

CompatibilityEventProxy.STATE = {

	/**
	 * Regex for replace event names to YUI adapted names.
	 * @review
	 * @type {Object}
	 */

	adaptedEvents: {
		value: {
			match: /(.*)(Changed)$/,
			replace: '$1Change'
		}
	},

	/**
	 * Indicates if event facade should be emited to the target
	 * @review
	 * @type {String}
	 */

	emitFacade: {
		value: false
	}
};

export default CompatibilityEventProxy;