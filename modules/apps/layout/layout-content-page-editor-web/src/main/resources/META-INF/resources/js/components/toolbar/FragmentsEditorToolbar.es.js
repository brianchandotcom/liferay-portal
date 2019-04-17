import Component from 'metal-component';
import Soy from 'metal-soy';

import './TranslationStatus.es';
import './SegmentsExperienceSelector.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentsEditorToolbar.soy';
import {
	TOGGLE_SIDEBAR,
	UPDATE_LAST_SAVE_DATE,
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

		const instance = this;

		Liferay.after(
			'popupReady',
			event => {
				const popupDocument = event.win.document;

				const form = popupDocument.querySelector('.portlet-configuration-setup > form');

				if (form) {
					form.addEventListener(
						'submit',
						() => instance.store.dispatchAction(
							UPDATE_LAST_SAVE_DATE,
							{
								lastSaveDate: new Date()
							}
						)
					);
				}
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