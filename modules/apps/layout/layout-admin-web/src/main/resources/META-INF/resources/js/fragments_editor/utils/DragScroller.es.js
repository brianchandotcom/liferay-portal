import State, {Config} from 'metal-state';
import throttle from 'metal-throttle';

/**
 * Distance the window moves on scroll.
 * @review
 */

const SCROLL_DISPLACEMENT = 100;

/**
 * Window height
 * @review
 */

const WINDOW_HEIGHT = window.innerHeight;

/**
 * Margin on window top and bottom in which scroll starts.
 * @review
 */

const SCROLL_MARGIN = WINDOW_HEIGHT * 0.2;

/**
 * DragScroller
 */

class DragScroller extends State {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(config, ...args) {
		super(config, ...args);
		this.scrollOnDrag = throttle(this.scrollOnDrag.bind(this), 250);
	}

	/**
     * Scrolls up or down when an item is being dragged.
     * @privat
     * @review
     */

	scrollOnDrag(placeholderItemRegion, documentHeight) {
		if (placeholderItemRegion) {
			let displacement;

			if (
				this._currentYPosition < placeholderItemRegion.top &&
                placeholderItemRegion.top > (WINDOW_HEIGHT - SCROLL_MARGIN) &&
                (placeholderItemRegion.bottom + window.scrollY) < (documentHeight + placeholderItemRegion.height)
			) {
				displacement = window.scrollY + SCROLL_DISPLACEMENT;
			}
			else if (
				this._currentYPosition > placeholderItemRegion.top &&
                placeholderItemRegion.top < (this.upOffset + SCROLL_MARGIN)
			) {
				displacement = window.scrollY - SCROLL_DISPLACEMENT;
			}

			window.scrollTo(
				{
					behavior: 'smooth',
					top: displacement
				}
			);

			this._currentYPosition = placeholderItemRegion.top;
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

DragScroller.STATE = {

	/**
	 * @instance
	 * @memberOf dragScroller
	 * @private
	 * @review
	 * @type {number}
	 */

	upOffset: Config.number(),

	/**
	 * @default undefined
	 * @instance
	 * @memberOf dragScroller
	 * @private
	 * @review
	 * @type {number}
	 */

	_currentYPosition: Config.number().internal().value(undefined)
};

export {DragScroller};