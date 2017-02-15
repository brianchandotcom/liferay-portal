import { core } from 'metal';
import Component from 'metal-component';

import Soy from 'metal-soy';

import templates from './Flags.soy';

/**
 * Flags
 * 
 * It opens a dialog where the user can flag the page.
 *
 * @abstract
 * @extends {Component}
 */
class Flags extends Component {
	/**
	 * Opens a dialog where the user can flag the page.
	 */
	openReportDialog() {
		AUI().use('aui-io-plugin-deprecated', (A) => {
			let dialogTitle = Liferay.Language.get('report-inappropriate-content');

			if (this.flagsEnabled) {
				let popup = Liferay.Util.Window.getWindow(
					{
						dialog: {
							destroyOnHide: true,
							height: 680,
							width: 680
						},
						title: dialogTitle
					}
				);

				popup.plug(
					A.Plugin.IO, {
						data: this.data,
						uri: this.uri
					}
				);
			}
			else {
				Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: Liferay.Language.get('please-sign-in-to-flag-this-as-inappropriate'),
							destroyOnHide: true,
							height: 680,
							width: 680
						},
						title: dialogTitle
					}
				);
			}
		});
	}
};

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
Flags.STATE = {
	/**
	 * Portlet's data.
	 * @instance
	 * @memberof Flags
	 * @type {!Object}
	 */
	data: {
		validator: core.isObject
	},

	/**
	 * Whether the user is able to flag the page.
	 * @instance
	 * @memberof Flags
	 * @type {!Boolean}
	 */
	flagsEnabled: {
		validator: core.isBoolean
	},

	/**
	 * Uri of the page that will be opened
	 * in the dialog.
	 * @instance
	 * @memberof Flags
	 * @type {String}
	 */
	uri: {
		validator: core.isString
	}
};

// Register component
Soy.register(Flags, templates);

export default Flags;