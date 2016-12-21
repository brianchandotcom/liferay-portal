import core from 'metal/src/core';
import Component from 'metal-component/src/Component';

import Soy from 'metal-soy/src/Soy';

import templates from './Flags.soy';

class FlagsPortlet extends Component {
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

FlagsPortlet.STATE = {
	data: {
		validator: core.isObject
	},
	inTrash: {
		validator: core.isBoolean,
		value: false
	},
	label: {
		validator: core.isBoolean,
		value: true
	},
	message: {
		validator: core.isString
	},
	signedUser: {
		validator: core.isBoolean
	},
	uri: {
		validator: core.isString
	}
};

// Register component
Soy.register(FlagsPortlet, templates);

export default FlagsPortlet;