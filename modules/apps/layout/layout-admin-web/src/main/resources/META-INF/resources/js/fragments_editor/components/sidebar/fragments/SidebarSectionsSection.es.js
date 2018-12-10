import Component from 'metal-component';
import Soy from 'metal-soy';

import './SidebarAvailableSections.es';
import templates from './SidebarSectionsSection.soy';

/**
 * SidebarSectionsSection
 */
class SidebarSectionsSection extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarSectionsSection.STATE = {
};

Soy.register(SidebarSectionsSection, templates);

export {SidebarSectionsSection};
export default SidebarSectionsSection;