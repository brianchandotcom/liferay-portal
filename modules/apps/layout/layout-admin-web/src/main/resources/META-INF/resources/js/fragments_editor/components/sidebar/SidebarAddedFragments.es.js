import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarCard.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {Store} from '../../store/store.es';
import templates from './SidebarAddedFragments.soy';

/**
 * SidebarAddedFragments
 */

class SidebarAddedFragments extends Component {

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * It emits a 'fragmentRemoveButtonClick' event with the fragment index.
	 * @param {{itemId: !string}} event
	 * @private
	 * @review
	 */

	_handleFragmentRemoveButtonClick(event) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				REMOVE_FRAGMENT_ENTRY_LINK,
				{
					fragmentEntryLinkId: event.itemId
				}
			)
			.dispatchAction(
				UPDATE_LAST_SAVE_DATE,
				{
					lastSaveDate: new Date()
				}
			)
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: false
				}
			);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarAddedFragments.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAddedFragments
	 * @review
	 * @type {Store}
	 */

	store: Config.instanceOf(Store)
};

Soy.register(SidebarAddedFragments, templates);

export {SidebarAddedFragments};
export default SidebarAddedFragments;