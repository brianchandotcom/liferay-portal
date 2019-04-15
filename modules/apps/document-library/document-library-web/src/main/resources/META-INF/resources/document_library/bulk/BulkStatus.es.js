import Component from 'metal-component';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
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
	 * @inheritDoc
	 */
	attached() {
		Liferay.component(this.portletNamespace + 'BulkStatus', this);

		if (this.bulkInProgress) {
			this.startWatch();
		}
	}

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
		fetch(
			this.pathModule + this.bulkStatusUrl,
			{credentials: 'include'}
		)
			.then(response => response.json())
			.then(
				response => {
					if (!response.actionInProgress) {
						this._onBulkFinish(false);
					}
				}
			)
			.catch(
				e => {
					this._onBulkFinish(true);
				}
			);
	}

	/**
	 * Stops sending ajax request and hides the component.
	 *
	 * @protected
	 */
	_onBulkFinish(error) {
		this._clearInterval();
		this._clearTimeout();
		this.bulkInProgress = false;

		this._showNotification(error);
	}

	/**
	 * Shows a toast notification.
	 *
	 * @param {boolean} error Flag indicating if is an error or not
	 * @protected
	 * @review
	 */
	_showNotification(error) {
		let message;

		if (error) {
			message = Liferay.Language.get('an-unexpected-error-occurred');
		}
		else {
			message = Liferay.Language.get('changes-saved');
		}

		const openToastParams = {
			message
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		openToast(openToastParams);
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

		if (!this.bulkInProgress) {
			this.visibleTimeOut = setTimeout(
				() => {
					this.bulkInProgress = true;
				},
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
	 * Wether to show the component or not
	 * @type {Boolean}
	 */
	bulkInProgress: Config.bool().value(false),

	/**
	 * Uri to send the bulk status fetch request.
	 * @instance
	 * @memberof BulkStatus
	 * @type {String}
	 */
	bulkStatusUrl: Config.string().value('/bulk-rest/v1.0/status'),

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
	 * PathModule
	 *
	 * @instance
	 * @memberof EditTags
	 * @review
	 * @type {String}
	 */
	pathModule: Config.string().required(),

	/**
	 * Portlet's namespace
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @review
	 * @type {string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * The time (in milliseconds) we have to wait to
	 * show the component.
	 *
	 * @instance
	 * @memberof BulkStatus
	 * @type {Number}
	 */
	waitingTime: Config.number().value(1000)
};

// Register component

Soy.register(BulkStatus, templates);

export default BulkStatus;