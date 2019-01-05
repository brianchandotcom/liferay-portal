import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentEntryLinkContent.es';
import Store from '../../store/store.es';
import {
	CLEAR_ACTIVE_ITEM,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_ACTIVE_ITEM,
	UPDATE_HOVERED_ITEM,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {DROP_TARGET_ITEM_TYPES} from '../../reducers/placeholders.es';
import {getItemMoveDirection} from '../../utils/FragmentsEditorGetUtils.es';
import templates from './FragmentEntryLink.soy';

/**
 * FragmentEntryLink
 * @review
 */
class FragmentEntryLink extends Component {

	/**
	 * Callback executed when a fragment lose the focus
	 * @private
	 */
	_handleFragmentBlur() {
		this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
	}

	/**
	 * Callback executed when a fragment is clicked
	 * @param {object} event
	 * @private
	 */
	_handleFragmentClick(event) {
		event.stopPropagation();

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: this.fragmentEntryLinkId,
				activeItemType: DROP_TARGET_ITEM_TYPES.fragment
			}
		);
	}

	/**
	 * Callback executed when a fragment starts being hovered.
	 * @param {object} event
	 * @private
	 */
	_handleFragmentHoverStart(event) {
		event.stopPropagation();

		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: this.fragmentEntryLinkId,
					hoveredItemType: DROP_TARGET_ITEM_TYPES.fragment
				}
			);
		}
	}

	/**
	 * Destroy existing FragmentEditableField instances.
	 */
	_destroyEditables() {
		if (this._editables) {
			this._editables.forEach(
				editable => editable.dispose()
			);

			this._editables = [];
		}
	}

	/**
	 * Handle fragment keyup event so it can emit when it
	 * should be moved or selected.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentKeyUp(event) {
		event.stopPropagation();

		const direction = getItemMoveDirection(event.which);

		this.emit(
			'moveFragment',
			{
				direction,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * @param {object} event
	 * @private
	 */
	_handleFragmentRemoveButtonClick(event) {
		event.stopPropagation();

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
					fragmentEntryLinkId: this.fragmentEntryLinkId
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
FragmentEntryLink.STATE = {

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	name: Config.string().value(''),

	/**
	 * Shows FragmentEntryLink control toolbar
	 * @default true
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!bool}
	 */
	showControlBar: Config.bool().value(true),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * CSS class to modify style
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	styleModifier: Config.string(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	spritemap: Config.string().required()
};

Soy.register(FragmentEntryLink, templates);

export {FragmentEntryLink};

export default FragmentEntryLink;