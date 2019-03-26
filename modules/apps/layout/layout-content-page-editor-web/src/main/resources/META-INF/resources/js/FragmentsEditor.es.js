import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/mapping_type/SelectMappingDialog.es';
import './components/mapping_type/SelectMappingTypeDialog.es';
import './components/fragment_entry_link/FragmentEntryLinkList.es';
import './components/sidebar/FragmentsEditorSidebar.es';
import './components/toolbar/FragmentsEditorToolbar.es';
import {INITIAL_STATE} from './store/state.es';
import {Store} from './store/store.es';
import templates from './FragmentsEditor.soy';

/**
 * FragmentsEditor
 * @review
 */
class FragmentsEditor extends Component {
}

/**
 * State definition.
 * @review
 * @static
 * @type {object}
 */
FragmentsEditor.STATE = Object.assign(
	{
		/**
		 * Store instance
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {Store}
		 */
		store: Config.instanceOf(Store),
	},
	INITIAL_STATE
);

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;