import Component from 'metal-component';
import Soy from 'metal-soy';

import './fragments/SidebarFragmentsSection.es';
import templates from './FragmentsEditorSidebarContent.soy';

/**
 * FragmentsEditorSidebarContent
 * @review
 */

class FragmentsEditorSidebarContent extends Component {}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebarContent.STATE = {};

Soy.register(FragmentsEditorSidebarContent, templates);

export {FragmentsEditorSidebarContent};
export default FragmentsEditorSidebarContent;