import Component from 'metal-component';
import Soy from 'metal-soy';

import './SidebarAvailableElements.es';
import templates from './SidebarElementsPanel.soy';

/**
 * SidebarElementsPanel
 */
class SidebarElementsPanel extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarElementsPanel.STATE = {};

Soy.register(SidebarElementsPanel, templates);

export {SidebarElementsPanel};
export default SidebarElementsPanel;