import State from 'metal-state';
import { core } from 'metal';

/**
 * CompatibilityEventProxy
 *
 * This class adds compatibility for YUI events, re-emitting events 
 * according to YUI naming and adding the capability of adding targets
 * to bubble events to them.
 *
 */
class CompatibilityEventProxy extends State {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config, opt_element) {
		super(opt_config, opt_element);

		this.eventTargets_ = [];
		this.host = opt_config.host;
		this.namespace = opt_config.namespace;

		this.startCompatibility_();
	}

	/**
	 * Registers another EventTarget as a bubble target.
	 * @param  {!Object} target YUI component where events will be emited to
	 * @private
	 */
	addTarget(target) {
		this.eventTargets_.push(target);
	}

	/**
	 * Check if the event is an attribute modification event and addapt
	 * the eventName.
	 * @param  {!String} eventName
	 * @private
	 */
	checkAttributeEvent_(eventName) {
		return eventName.replace(this.adaptedEvents.match, this.adaptedEvents.replace);
	}

	/**
	 * Emit the event adapted to yui
	 * @param  {!String} eventName
	 * @param  {!Event} event
	 * @private
	 */
	emitCompatibleEvents_(eventName, event) {
		this.eventTargets_.forEach((target) => {
			if (target.fire) {
				let prefixedEventName = this.namespace ? this.namespace + ':' + eventName : eventName;
				let yuiEvent = target._yuievt.events[prefixedEventName];

				if (core.isObject(event)) {
					try {
						event.target = this.host;
					} catch(err) {}
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
	 * Configuration to emit yui-based events to maintain
	 * backwards compatibility.
	 * @private
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
				else if (this.eventTargets_.length > 0 && event.key) {
					this.emitCompatibleEvents_(compatibleEvent, event);
				}
			}
		);
	}
}

/**
 * State definition.
 * @ignore
 * @type {!Object}
 * @static
 */
CompatibilityEventProxy.STATE = {
	/**
	 * Regex for replace event names to YUI adapted names.
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
	 * @type {String}
	 */
	emitFacade: {
		value: false
	}
};

export default CompatibilityEventProxy;