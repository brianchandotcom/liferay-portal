import Component from 'metal-component';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import templates from './FragmentsEditorRender.soy';

/**
 * FragmentsEditorRender
 * @review
 */

class FragmentsEditorRender extends Component {

	/**
	 * Gives focus to the specified fragmentEntryLinkId
	 * @param {string} fragmentEntryLinkId
	 * @review
	 */

	focusFragmentEntryLink(fragmentEntryLinkId) {
		requestAnimationFrame(
			() => {
				const index = this.fragmentEntryLinks.findIndex(
					_fragmentEntryLink => {
						return _fragmentEntryLink.fragmentEntryLinkId === fragmentEntryLinkId;
					}
				);

				const fragmentEntryLinkElement = this.refs.fragmentEntryLinks.querySelectorAll(
					'.fragment-entry-link-wrapper'
				)[index];

				fragmentEntryLinkElement.focus();
				fragmentEntryLinkElement.scrollIntoView();
			}
		);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleEditableChanged(event) {
		this.emit('editableChanged', event);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleFragmentMove(event) {
		this.emit('move', event);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleFragmentRemove(event) {
		this.emit('remove', event);
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */

	_handleMappeableFieldClicked(event) {
		this.emit('mappeableFieldClicked', event);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorRender.STATE = {};

Soy.register(FragmentsEditorRender, templates);

export {FragmentsEditorRender};
export default FragmentsEditorRender;