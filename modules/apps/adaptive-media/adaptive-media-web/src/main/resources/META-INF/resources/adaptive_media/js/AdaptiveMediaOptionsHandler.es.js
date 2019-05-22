import core from 'metal';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * Enables/disables the actions of the configuration entry's while
 * it is being adapted.
 *
 * @abstract
 * @extends {PortletBase}
 */

class AdaptiveMediaOptionsHandler extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.bindEventsProgressBarComponent_();

		this.disableIcon = this.one('#icon-disable-' + this.uuid, 'body');
		this.adaptRemainingIcon = this.one(
			'#icon-adapt-remaining' + this.uuid,
			'body'
		);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Bind events to the bar component associated to
	 * the configuration entry.
	 *
	 * @protected
	 */
	bindEventsProgressBarComponent_() {
		if (!this.progressBarComponent_) {
			Liferay.componentReady(this.ns('AdaptRemaining' + this.uuid)).then(
				progressBarComponent => {
					this.progressBarComponent_ = progressBarComponent;

					this.eventHandler_.add(
						progressBarComponent.on('start', () => this.onStart_())
					);

					this.eventHandler_.add(
						progressBarComponent.on('finish', () =>
							this.onFinish_()
						)
					);
				}
			);
		}
	}

	/**
	 * Disables the "Disable" and "Adapt Remaining" icons.
	 *
	 * @protected
	 */
	onStart_() {
		this.disableIcon_(this.adaptRemainingIcon);
		this.disableIcon_(this.disableIcon);
	}

	/**
	 * Enable the "Disable" icon.
	 *
	 * @protected
	 */
	onFinish_() {
		this.enableIcon_(this.disableIcon);
	}

	/**
	 * Disables a configuration entry icon.
	 *
	 * @protected
	 */
	disableIcon_(element) {
		if (!element) {
			return;
		}

		dom.addClasses(element.parentElement, 'disabled');

		element.setAttribute('data-href', element.getAttribute('href'));
		element.setAttribute('data-onclick', element.getAttribute('onclick'));
		element.removeAttribute('href');
		element.removeAttribute('onclick');
	}

	/**
	 * Enables a configuration entry icon.
	 *
	 * @protected
	 */
	enableIcon_(element) {
		if (!element) {
			return;
		}

		dom.removeClasses(element.parentElement, 'disabled');

		element.setAttribute('href', element.getAttribute('data-href'));
		element.setAttribute('onclick', element.getAttribute('data-onclick'));

		element.removeAttribute('data-href');
		element.removeAttribute('data-onclick');
	}
}

/**
 * AdaptiveMediaOptionsHandler State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
AdaptiveMediaOptionsHandler.STATE = {
	/**
	 * Configuration entry's uuid.
	 *
	 * @instance
	 * @memberof AdaptiveMediaOptionsHandler
	 * @type {String}
	 */
	uuid: {
		validator: core.isString
	}
};

export default AdaptiveMediaOptionsHandler;
