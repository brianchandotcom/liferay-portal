import Component from 'metal-component';
import Soy from 'metal-soy';

import '../../mapping_type/SelectMappingTypeForm.es';
import templates from './SidebarMappingPanel.soy';

/**
 * SidebarMappingPanel
 */
class SidebarMappingPanel extends Component {}

Soy.register(SidebarMappingPanel, templates);

export {SidebarMappingPanel};
export default SidebarMappingPanel;
