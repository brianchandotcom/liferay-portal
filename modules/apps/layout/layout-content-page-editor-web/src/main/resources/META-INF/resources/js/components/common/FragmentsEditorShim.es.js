import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './FragmentsEditorShim.soy';

/**
 * FragmentsEditorShim
 */
class FragmentsEditorShim extends Component {}

Soy.register(FragmentsEditorShim, templates);

export {FragmentsEditorShim};
export default FragmentsEditorShim;
