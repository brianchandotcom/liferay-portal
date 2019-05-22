import Component from 'metal-component';
import Soy from 'metal-soy';

import '../common/FragmentsEditorShim.es';
import templates from './ColumnOverlayGrid.soy';

/**
 * ColumnOverlayGrid
 */
class ColumnOverlayGrid extends Component {}

Soy.register(ColumnOverlayGrid, templates);

export {ColumnOverlayGrid};
export default ColumnOverlayGrid;
