import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './SidebarLayoutsSection.soy';

/**
 * SidebarLayoutsSection
 */

class SidebarLayoutsSection extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarLayoutsSection.STATE = {};

Soy.register(SidebarLayoutsSection, templates);

export {SidebarLayoutsSection};
export default SidebarLayoutsSection;