import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarBackgroundColorPanelDelegateTemplate.soy';
import templates from './FloatingToolbarBackgroundColorPanel.soy';

/**
 * FloatingToolbarBackgroundColorPanel
 */
class FloatingToolbarBackgroundColorPanel extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarBackgroundColorPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarBackgroundColorPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null)
};

Soy.register(FloatingToolbarBackgroundColorPanel, templates);

export {FloatingToolbarBackgroundColorPanel};
export default FloatingToolbarBackgroundColorPanel;