import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './BulkStatus.soy';

/**
 * Shows the bulk actions status
 *
 * @abstract
 * @extends {Component}
 */

class BulkStatus extends Component {
	/**
	 * Clears the interval to stop sending ajax requests.
	 *
	 * @protected
	 */
	_clearInterval() {
		if (this.intervalId_) {
			clearInterval(this.intervalId_);
		}
	}

	/**
	 * Clears the timeout that shows the component.
	 *
	 * @protected
	 */
	_clearTimeout() {
		if (this.visibleTimeOut) {
			clearTimeout(this.visibleTimeOut);
		}
	}

	/**
	 * Sends a request to get the status
	 * of bulk actions.
	 *
	 * @protected
	 */
	_getBulkStatus() {
		fetch(this.bulkStatusUrl)
			.then(
				response => {
					return response.json();
				}
			)
			.then(
				response => {
					if (!response.busy) {
						this._onBulkFinish();
					}
				}
			)
			.catch(
				e => {
					this._onBulkFinish();
				}
			)
	}

	/**
	 * Stops sending ajax request and hides the component.
	 *
	 * @protected
	 */
	_onBulkFinish() {
		this._clearInterval();
		this._clearTimeout();
		this.hide();
	}

	/**
	 * Shows the component.
	 */
	show() {
		this.visible = true;
	}

	/**
	 * Shows the component.
	 */
	hide() {
		this.visible = false;
	}

	/**
	 * Watch the status of bulk actions.
	 * It shows the component if it takes
	 * longer than 'waitingTime'.
	 */
	startWatch() {
		this._clearInterval();

		this._getBulkStatus();

		this.intervalId_ = setInterval(
			this._getBulkStatus.bind(this),
			this.intervalSpeed
		);

		if(!this.visible) {
			this.visibleTimeOut = setTimeout(
				() => this.show(),
				this.waitingTime
			);
		}
	}
}

/**
 * BulkStatus State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
BulkStatus.STATE = {

	/**
	 * Uri to send the bulk status fetch request.
	 * @instance
	 * @memberof BulkStatus
	 * @type {String}
	 */
	bulkStatusUrl: Config.string().value('/o/bulk/status'),

	/**
	 * The interval (in milliseconds) on how often
	 * we will check if there are bulk actions in progress.
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @type {Number}
	 */
	intervalSpeed: Config.number().value(1000),

	/**
	 * The time (in milliseconds) we have to wait to
	 * show the component.
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @type {Number}
	 */
	waitingTime: Config.number().value(1000),

	/**
	 * Wether to show the component or not
	 * @type {[type]}
	 */
	visible: Config.bool().value(false),
};

// Register component
Soy.register(BulkStatus, templates);

export default BulkStatus;