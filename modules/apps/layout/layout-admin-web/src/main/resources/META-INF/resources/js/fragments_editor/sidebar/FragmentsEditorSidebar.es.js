import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import Soy from 'metal-soy';

import './SidebarAddedFragments.es';
import './SidebarAvailableFragments.es';
import './SidebarMapping.es';
import templates from './FragmentsEditorSidebar.soy';

/**
 * FragmentsEditorSidebar
 * @review
 */

class FragmentsEditorSidebar extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebar.STATE = {};

Soy.register(FragmentsEditorSidebar, templates);

export {FragmentsEditorSidebar};
export default FragmentsEditorSidebar;