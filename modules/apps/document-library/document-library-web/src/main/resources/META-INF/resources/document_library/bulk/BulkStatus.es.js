import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './BulkStatus.soy';

/**
 * Handles the actions of the configuration entry's progressbar.
 *
 * @abstract
 * @extends {PortletBase}
 */

class BulkStatus extends Component {
	/**
	 * @inheritDoc
	 */
	created() {
		this.show();
	}

	show() {
		this.visible = true;
	}

	hide() {
		this.visible = false;
	}
}

/**
 * BulkStatus State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
BulkStatus.STATE = {
	visible: Config.bool().value(false),
};

// Register component
Soy.register(BulkStatus, templates);

export default BulkStatus;