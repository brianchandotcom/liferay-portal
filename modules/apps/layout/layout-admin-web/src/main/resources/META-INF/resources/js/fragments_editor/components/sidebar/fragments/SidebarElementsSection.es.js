import Component from 'metal-component';
import Soy from 'metal-soy';

import './SidebarAvailableElements.es';
import templates from './SidebarElementsSection.soy';

/**
 * SidebarElementsSection
 */
class SidebarElementsSection extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarElementsSection.STATE = {
};

Soy.register(SidebarElementsSection, templates);

export {SidebarElementsSection};
export default SidebarElementsSection;