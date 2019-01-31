import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './FragmentEntryLinkContent.es';
import templates from './FragmentEntryLink.soy';
import {CLEAR_ACTIVE_ITEM, REMOVE_FRAGMENT_ENTRY_LINK, UPDATE_ACTIVE_ITEM, UPDATE_HOVERED_ITEM} from '../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {getItemMoveDirection} from '../../utils/FragmentsEditorGetUtils.es';
import {removeItem, shouldClearFocus} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';

/**
 * FragmentEntryLink
 * @review
 */
class FragmentEntryLink extends Component {

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		if (
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) &&
			(this.activeItemId === this.fragmentEntryLinkId) &&
			this.element
		) {
			this.element.focus();
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

	/**
	 * Callback executed when a fragment lose the focus
	 * @private
	 */
	_handleFragmentFocusOut() {
		requestAnimationFrame(
			() => {
				if (shouldClearFocus(this.element)) {
					this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
				}
			}
		);
	}

	/**
	 * Callback executed when a fragment is clicked
	 * @param {Object} event
	 * @private
	 */
	_handleFragmentClick(event) {
		event.stopPropagation();

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: this.fragmentEntryLinkId,
				activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			}
		);
	}

	/**
	 * Callback executed when a fragment starts being hovered.
	 * @param {Object} event
	 * @private
	 */
	_handleFragmentHoverStart(event) {
		event.stopPropagation();

		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: this.fragmentEntryLinkId,
					hoveredItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				}
			);
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
	 * @param {Object} event
	 * @private
	 */
	_handleFragmentRemoveButtonClick(event) {
		event.stopPropagation();

		removeItem(
			this.store,
			REMOVE_FRAGMENT_ENTRY_LINK,
			{
				fragmentEntryLinkId: this.fragmentEntryLinkId
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
	fragmentEntryLinkId: Config.string()
		.required(),

	/**
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	name: Config.string()
		.value(''),

	/**
	 * Shows FragmentEntryLink control toolbar
	 * @default true
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!bool}
	 */
	showControlBar: Config.bool()
		.value(true),

	/**
	 * CSS class to modify style
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	styleModifier: Config.string()
};

const ConnectedFragmentEntryLink = getConnectedComponent(
	FragmentEntryLink,
	[
		'activeItemId',
		'activeItemType',
		'defaultLanguageId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLink, templates);

export {ConnectedFragmentEntryLink, FragmentEntryLink};

export default ConnectedFragmentEntryLink;