import Component from 'metal-component';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import './SegmentsExperienceSelector.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentsEditorToolbar.soy';
import {TOGGLE_SIDEBAR, UPDATE_TRANSLATION_STATUS} from '../../actions/actions.es';

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
	 * Handles discard draft form submit action.
	 * @private
	 * @review
	 */
	_handleDiscardDraft(event) {
		if (!confirm(Liferay.Language.get('are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'))) {
			event.preventDefault();
		}
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
		'discardDraftRedirectURL',
		'discardDraftURL',
		'lastSaveDate',
		'portletNamespace',
		'publishURL',
		'redirectURL',
		'savingChanges',
		'selectedSidebarPanelId',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentsEditorToolbar, templates);

export {ConnectedFragmentsEditorToolbar, FragmentsEditorToolbar};
export default ConnectedFragmentsEditorToolbar;