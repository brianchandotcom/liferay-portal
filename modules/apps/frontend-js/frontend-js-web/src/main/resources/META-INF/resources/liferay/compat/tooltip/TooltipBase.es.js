import core from 'metal';
import dom from 'metal-dom';
import { Align } from 'metal-position';
import Component from 'metal-component';
import { EventHandler } from 'metal-events';

/**
 * The base class to be shared between components that have tooltip behavior.
 * This helps decouple this behavior logic from the UI, which may be different
 * between components. The Tooltip component itself extends from this, as does
 * the crystal Popover component, which can be accessed at metal/crystal-popover.
 */
class TooltipBase extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.align();
		this.syncTriggerEvents(this.triggerEvents);
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this.currentAlignElement = this.alignElement;
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * @inheritDoc
	 */
	disposeInternal() {
		super.disposeInternal();
		clearTimeout(this.delay_);
	}

	/**
	 * Aligns the tooltip with the best region around alignElement. The best
	 * region is defined by clockwise rotation starting from the specified
	 * `position`. The element is always aligned in the middle of alignElement
	 * axis.
	 * @param {Element=} opt_alignElement Optional element to align with.
	 */
	align(opt_alignElement) {
		this.syncCurrentAlignElement(opt_alignElement || this.currentAlignElement);
	}

	/**
	 * @param {!function()} fn
	 * @param {number} delay
	 * @private
	 */
	callAsync_(fn, delay) {
		clearTimeout(this.delay_);
		this.delay_ = setTimeout(fn.bind(this), delay);
	}

	/**
	 * Handles hide event triggered by `events`.
	 * @param {!Event} event
	 * @protected
	 */
	handleHide(event) {
		const delegateTarget = event.delegateTarget;
		const interactingWithDifferentTarget = delegateTarget && (delegateTarget !== this.currentAlignElement);
		this.callAsync_(function() {
			if (this.locked_) {
				return;
			}
			if (interactingWithDifferentTarget) {
				this.currentAlignElement = delegateTarget;
			} else {
				this.visible = false;
				this.syncVisible(false);
			}
		}, this.delay[1]);
	}

	/**
	 * Handles show event triggered by `events`.
	 * @param {!Event} event
	 * @protected
	 */
	handleShow(event) {
		const delegateTarget = event.delegateTarget;
		super.syncVisible(true);
		this.callAsync_(function() {
			this.currentAlignElement = delegateTarget;
			this.visible = true;
		}, this.delay[0]);
	}

	/**
	 * Handles toggle event triggered by `events`.
	 * @param {!Event} event
	 * @protected
	 */
	handleToggle(event) {
		if (this.visible) {
			this.handleHide(event);
		} else {
			this.handleShow(event);
		}
	}

	/**
	 * Locks tooltip visibility.
	 * @param {!Event} event
	 */
	lock() {
		this.locked_ = true;
	}

	/**
	 * Unlocks tooltip visibility.
	 * @param {!Event} event
	 */
	unlock(event) {
		this.locked_ = false;
		this.handleHide(event);
	}

	/**
	 * Synchronizes the value of the `currentAlignElement` internal state
	 * with the `alignElement`.
	 * @param {Element} alignElement
	 */
	syncAlignElement(alignElement) {
		this.currentAlignElement = alignElement;
	}

	/**
	 * State synchronization logic for `currentAlignElement`.
	 * @param {Element} alignElement
	 * @param {Element} prevAlignElement
	 */
	syncCurrentAlignElement(alignElement, prevAlignElement) {
		if (prevAlignElement) {
			alignElement.removeAttribute('aria-describedby');
		}
		if (alignElement) {
			const dataTitle = alignElement.getAttribute('data-title');
			if (dataTitle) {
				this.title = dataTitle;
			}
			if (this.inDocument) {
				this.alignedPosition = TooltipBase.Align.align(this.element, alignElement, this.position);
			}
		}
	}

	/**
	 * State synchronization logic for `position`.
	 */
	syncPosition() {
		this.syncCurrentAlignElement(this.currentAlignElement);
	}

	/**
	 * State synchronization logic for `selector`.
	 */
	syncSelector() {
		this.syncTriggerEvents(this.triggerEvents);
	}

	/**
	 * State synchronization logic for `triggerEvents`.
	 * @param {!Array<string>} triggerEvents
	 */
	syncTriggerEvents(triggerEvents) {
		if (!this.inDocument) {
			return;
		}
		this.eventHandler_.removeAllListeners();
		const selector = this.selector;
		if (!selector) {
			return;
		}

		this.eventHandler_.add(
			this.on('mouseenter', this.lock),
			this.on('mouseleave', this.unlock));

		if (triggerEvents[0] === triggerEvents[1]) {
			this.eventHandler_.add(
				dom.delegate(document, triggerEvents[0], selector, this.handleToggle.bind(this)));
		} else {
			this.eventHandler_.add(
				dom.delegate(document, triggerEvents[0], selector, this.handleShow.bind(this)),
				dom.delegate(document, triggerEvents[1], selector, this.handleHide.bind(this)));
		}
	}

	/**
	 * State synchronization logic for `visible`. Realigns the tooltip.
	 */
	syncVisible() {
		this.align();
	}
}

/**
 * @inheritDoc
 * @see `Align` class.
 * @static
 */
TooltipBase.Align = Align;

/**
 * TooltipBase state definition.
 * @type {!Object}
 * @static
 */
TooltipBase.STATE = {
	/**
	 * The current position of the tooltip after being aligned via `Align.align`.
	 * @type {number}
	 */
	alignedPosition: {
		validator: TooltipBase.Align.isValidPosition
	},

	/**
	 * Element to align tooltip with.
	 * @type {Element}
	 */
	alignElement: {
		setter: dom.toElement
	},

	/**
	 * The current element aligned tooltip with.
	 * @type {Element}
	 */
	currentAlignElement: {
		internal: true,
		setter: dom.toElement
	},

	/**
	 * Delay showing and hiding the tooltip (ms).
	 * @type {!Array<number>}
	 * @default [ 500, 250 ]
	 */
	delay: {
		validator: Array.isArray,
		value: [500, 250]
	},

	/**
	 * Trigger events used to bind handlers to show and hide tooltip.
	 * @type {!Array<string>}
	 * @default ['mouseenter', 'mouseleave']
	 */
	triggerEvents: {
		validator: Array.isArray,
		value: ['mouseenter', 'mouseleave']
	},

	/**
	 * If a selector is provided, tooltip objects will be delegated to the
	 * specified targets by setting the `alignElement`.
	 * @type {?string}
	 */
	selector: {
		validator: core.isString
	},

	/**
	 * The position to try alignment. If not possible the best position will be
	 * found.
	 * @type {number}
	 * @default Align.Bottom
	 */
	position: {
		validator: TooltipBase.Align.isValidPosition,
		value: TooltipBase.Align.Bottom
	},

	/**
	 * Content to be placed inside tooltip.
	 * @type {string}
	 */
	title: {
	}
};

/**
 * CSS classes used for each align position.
 * @type {!Array}
 * @static
 */
TooltipBase.PositionClasses = ['top', 'right', 'bottom', 'left'];

export { TooltipBase };
export default TooltipBase;