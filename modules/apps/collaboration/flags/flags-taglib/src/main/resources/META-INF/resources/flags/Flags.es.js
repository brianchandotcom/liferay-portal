import { core } from 'metal';
import Component from 'metal-component';

import Soy from 'metal-soy';

import templates from './Flags.soy';

/**
 * FlagsPortlet. It opens a dialog where the user can
 * flag the page.
 *
 * @abstract
 * @extends {Component}
 */
class FlagsPortlet extends Component {
	/**
	 * Opens a dialog where the user can flag the page.
	 */
	openReportDialog() {
		AUI().use('aui-io-plugin-deprecated', (A) => {
			let dialogTitle = Liferay.Language.get('report-inappropriate-content');

			if (this.signedUser) {
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
FlagsPortlet.STATE = {
	/**
	 * Portlet's data.
	 * @instance
	 * @memberof FlagsPortlet
	 * @type {!Object}
	 */
	data: {
		validator: core.isObject
	},

	/**
	 * Whether the user is signed in
	 * and is able to flag the page.
	 * @instance
	 * @memberof FlagsPortlet
	 * @type {!Boolean}
	 */
	signedUser: {
		validator: core.isBoolean
	},

	/**
	 * Uri of the page that will be opened
	 * in the dialog.
	 * @instance
	 * @memberof FlagsPortlet
	 * @type {String}
	 */
	uri: {
		validator: core.isString
	}
};

// Register component
Soy.register(FlagsPortlet, templates);

export default FlagsPortlet;