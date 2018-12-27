import Component from 'metal-component';
import Soy from 'metal-soy';

import './SidebarAvailableSections.es';
import templates from './SidebarSectionsPanel.soy';

/**
 * SidebarSectionsPanel
 */
class SidebarSectionsPanel extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarSectionsPanel.STATE = {};

Soy.register(SidebarSectionsPanel, templates);

export {SidebarSectionsPanel};
export default SidebarSectionsPanel;