import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FloatingToolbar.soy';

/**
 * FloatingToolbar
 */
class FloatingToolbar extends Component {

	/**
	 * Handle button click
	 * @param {MouseEvent} event Click event
	 */
	_handleButtonClick(event) {
		const {panelId = null} = event.delegateTarget.dataset;

		if (this._selectedPanelId === panelId) {
			this._selectedPanelId = null;
		}
		else {
			this._selectedPanelId = panelId;
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbar.STATE = {,

	/**
	 * Selected panel ID.
	 * @default null
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {string|null}
	 */
	_selectedPanelId: Config
		.string()
		.internal()
		.value(null),

	/**
	 * List of available panels.
	 * @default undefined
	 * @instance
	 * @memberOf FloatingToolbar
	 * @review
	 * @type {object[]}
	 */
	panels: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string(),
					panelId: Config.string(),
					title: Config.string()
				}
			)
		)
		.required()
};

const ConnectedFloatingToolbar = getConnectedComponent(
	FloatingToolbar,
	['spritemap']
);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;