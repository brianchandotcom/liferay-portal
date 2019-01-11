import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './SidebarWidgetsPanel.soy';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';

/**
 * SidebarWidgetsPanel
 */
class SidebarWidgetsPanel extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarWidgetsPanel.STATE = {};

const ConnectedSidebarWidgetsPanel = getConnectedComponent(
	SidebarWidgetsPanel,
	[
		'widgets',
		'spritemap'
	]
);

Soy.register(ConnectedSidebarWidgetsPanel, templates);

export {ConnectedSidebarWidgetsPanel, SidebarWidgetsPanel};
export default ConnectedSidebarWidgetsPanel;