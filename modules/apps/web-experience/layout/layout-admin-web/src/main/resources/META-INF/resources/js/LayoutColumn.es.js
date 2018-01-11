import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutColumn.soy';

/**
 * LayoutColumn
 */
class LayoutColumn extends Component {

	/**
	 * Handle permission item click in order to open the target href
	 * in a dialog.
	 * @param {Event} event
	 * @private
	 */
	_handlePermissionLinkClick(event) {
		Liferay.Util.openInDialog(event, {
			dialog: {
				destroyOnHide: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			uri: event.delegateTarget.href
		});
	}

	/**
	 * Handle delete item click in order to show a previous confirmation
	 * alert.
	 * @param {Event} event
	 * @private
	 */
	_handleDeleteItemClick(event) {
		if (!confirm(Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this'
		))) {
			event.preventDefault();
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
LayoutColumn.STATE = {
	/**
	 * List of layouts in the current column
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!Array}
	 */
	layoutColumn: Config.arrayOf(
		Config.shapeOf({
			actionURLs: Config.object().required(),
			active: Config.bool().required(),
			hasChild: Config.bool().required(),
			plid: Config.string().required(),
			url: Config.string().required(),
			title: Config.string().required(),
		})
	).required(),

	/**
	 * URL for using icons
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!string}
	 */
	pathThemeImages: Config.string().required(),

	/**
	 * Namespace of portlet to prefix parameters names
	 * @default undefined
	 * @instance
	 * @memberof LayoutColumn
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),
};

Soy.register(LayoutColumn, templates);

export {LayoutColumn};
export default LayoutColumn;
