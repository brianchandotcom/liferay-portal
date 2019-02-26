import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import templates from './FloatingToolbarMappingPanel.soy';

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarMappingPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required()
};

Soy.register(FloatingToolbarMappingPanel, templates);

export {FloatingToolbarMappingPanel};
export default FloatingToolbarMappingPanel;