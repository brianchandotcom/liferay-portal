import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './SidebarWidgetsPanel.soy';

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

Soy.register(SidebarWidgetsPanel, templates);

export {SidebarWidgetsPanel};
export default SidebarWidgetsPanel;