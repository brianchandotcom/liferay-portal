import Component from 'metal-component';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import './SegmentSelector.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentsEditorToolbar.soy';
import {
	TOGGLE_SIDEBAR,
	UPDATE_TRANSLATION_STATUS
} from '../../actions/actions.es';

/**
 * FragmentsEditorToolbar
 * @review
 */
class FragmentsEditorToolbar extends Component {

	/**
	 * @inheritDoc
	 */
	created() {
		this.once(
			'storeChanged',
			() => {
				this.store.dispatchAction(
					UPDATE_TRANSLATION_STATUS
				);
			}
		);
	}

	/**
	 * @private
	 * @review
	 */
	_handleToggleContextualSidebarButtonClick() {
		this.store.dispatchAction(TOGGLE_SIDEBAR);
	}

}

const ConnectedFragmentsEditorToolbar = getConnectedComponent(
	FragmentsEditorToolbar,
	[
		'classPK',
		'fragmentsEditorSidebarVisible',
		'lastSaveDate',
		'portletNamespace',
		'publishLayoutPageTemplateEntryURL',
		'redirectURL',
		'savingChanges',
		'spritemap',
		'status'
	]
);

Soy.register(ConnectedFragmentsEditorToolbar, templates);

export {ConnectedFragmentsEditorToolbar, FragmentsEditorToolbar};
export default ConnectedFragmentsEditorToolbar;