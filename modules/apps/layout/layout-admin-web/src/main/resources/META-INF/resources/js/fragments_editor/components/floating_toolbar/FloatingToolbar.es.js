import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './background_color/FloatingToolbarBackgroundColorPanel.es';
import './background_image/FloatingToolbarBackgroundImagePanel.es';
import './spacing/FloatingToolbarSpacingPanel.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {shouldUpdateOnChangeProperties} from '../../utils/FragmentsEditorComponentUtils.es';
import templates from './FloatingToolbar.soy';

/**
 * List of available panels
 * @review
 * @type {object[]}
 */
const FLOATING_TOOLBAR_PANELS = [
	{
		icon: 'format',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color')
	},
	{
		icon: 'table',
		panelId: 'spacing',
		title: Liferay.Language.get('spacing')
	},
	{
		icon: 'picture',
		panelId: 'background_image',
		title: Liferay.Language.get('background-image')
	}
];

/**
 * FloatingToolbar
 */
class FloatingToolbar extends Component {

	/**
	 * @inheritdoc
	 * @param {object} changes
	 */
	shouldUpdate(changes) {
		return shouldUpdateOnChangeProperties(
			changes,
			[
				'_selectedPanelId',
				'spritemap'
			]
		);
	}

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
FloatingToolbar.STATE = {

	/**
	 * List of available panels.
	 * @default FLOATING_TOOLBAR_PANELS
	 * @instance
	 * @memberOf FloatingToolbar
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_panels: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string().required(),
					panelId: Config.string().required(),
					title: Config.string().required()
				}
			)
		)
		.internal()
		.value(FLOATING_TOOLBAR_PANELS),

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
		.oneOf(FLOATING_TOOLBAR_PANELS.map(panel => panel.panelId))
		.internal()
		.value(null)
};

const ConnectedFloatingToolbar = getConnectedComponent(
	FloatingToolbar,
	['spritemap']
);

Soy.register(ConnectedFloatingToolbar, templates);

export {ConnectedFloatingToolbar, FloatingToolbar};
export default ConnectedFloatingToolbar;