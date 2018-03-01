import dom from 'metal-dom';
import Soy from 'metal-soy';
import TooltipBase from './TooltipBase.es';
import templates from './Tooltip.soy';

/**
 * Tooltip component.
 */
class Tooltip extends TooltipBase {
	/**
	 * Hides the alert completely (with display "none"). This is called after the
	 * hiding animation is done.
	 * @protected
	 */
	hideCompletely_() {
		if (!this.isDisposed() && this.element && !this.visible) {
			this.element.style.display = 'none';
		}
	}

	/**
	 * State synchronization logic for `visible`. Updates the element's opacity,
	 * since bootstrap uses opacity instead of display for tooltip visibility.
	 * @param {boolean} visible
	 */
	syncVisible(visible) {
		if (!visible) {
			dom.once(this.element, 'animationend', this.hideCompletely_.bind(this));
			dom.once(this.element, 'transitionend', this.hideCompletely_.bind(this));
		} else {
			this.element.style.display = '';
		}

		this.element.style.opacity = visible ? 1 : '';
		super.syncVisible(visible);
	}
}
Soy.register(Tooltip, templates);

/**
 * @inheritDoc
 * @see `Align` class.
 * @static
 */
Tooltip.Align = TooltipBase.Align;

export default Tooltip;
export { Tooltip, TooltipBase };