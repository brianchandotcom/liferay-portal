import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './FragmentEntryLinkContent.es';
import templates from './FragmentEntryLink.soy';
import {MOVE_FRAGMENT_ENTRY_LINK, REMOVE_FRAGMENT_ENTRY_LINK} from '../../actions/actions.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {getFragmentColumn, getFragmentRowIndex, getItemMoveDirection, getItemPath, getTargetBorder, itemIsInPath} from '../../utils/FragmentsEditorGetUtils.es';
import {FRAGMENT_ENTRY_LINK_TYPES, FRAGMENTS_EDITOR_ITEM_TYPES, FRAGMENTS_EDITOR_ROW_TYPES} from '../../utils/constants';
import {moveItem, moveRow, removeItem, setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';

/**
 * FragmentEntryLink
 * @review
 */
class FragmentEntryLink extends Component {

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		const hoveredPath = getItemPath(
			state.hoveredItemId,
			state.hoveredItemType,
			state.layoutData.structure
		);

		const fragmentEntryLinkInHoveredPath = itemIsInPath(
			hoveredPath,
			state.fragmentEntryLinkId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		let fragmentEntryLinkType = FRAGMENT_ENTRY_LINK_TYPES.component;

		if (state.rowType === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow) {
			fragmentEntryLinkType = FRAGMENT_ENTRY_LINK_TYPES.section;
		}

		let nextState = setIn(
			state,
			['_fragmentEntryLinkType'],
			fragmentEntryLinkType
		);

		nextState = setIn(
			nextState,
			['_fragmentsEditorItemTypes'],
			FRAGMENTS_EDITOR_ITEM_TYPES
		);

		nextState = setIn(
			nextState,
			['_fragmentsEditorRowTypes'],
			FRAGMENTS_EDITOR_ROW_TYPES
		);

		return setIn(
			nextState,
			['_hovered'],
			fragmentEntryLinkInHoveredPath
		);
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
	 * Handle fragment keyup event so it can emit when it
	 * should be moved or selected.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentKeyUp(event) {
		if (!this.fragmentEditorEnabled) {
			event.stopPropagation();

			const direction = getItemMoveDirection(event.which);
			const {fragmentEntryLinkType} = event.delegateTarget.dataset;

			if (fragmentEntryLinkType === FRAGMENT_ENTRY_LINK_TYPES.section) {
				moveRow(
					direction,
					getFragmentRowIndex(
						this.layoutData.structure,
						this.fragmentEntryLinkId
					),
					this.store,
					this.layoutData.structure
				);
			}
			else {
				const column = getFragmentColumn(
					this.layoutData.structure,
					this.fragmentEntryLinkId
				);
				const fragmentIndex = column.fragmentEntryLinkIds.indexOf(
					this.fragmentEntryLinkId
				);
				const targetFragmentEntryLinkId = column.fragmentEntryLinkIds[
					fragmentIndex + direction
				];

				if (direction && targetFragmentEntryLinkId) {
					const moveItemPayload = {
						fragmentEntryLinkId: this.fragmentEntryLinkId,
						targetBorder: getTargetBorder(direction),
						targetItemId: targetFragmentEntryLinkId,
						targetItemType: FRAGMENTS_EDITOR_ITEM_TYPES.fragment
					};

					moveItem(
						this.store,
						MOVE_FRAGMENT_ENTRY_LINK,
						moveItemPayload
					);
				}
			}
		}
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
	 * Row type
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	rowType: Config.string(),

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
		'dropTargetItemId',
		'dropTargetItemType',
		'dropTargetBorder',
		'fragmentEditorEnabled',
		'hoveredItemId',
		'hoveredItemType',
		'imageSelectorURL',
		'languageId',
		'layoutData',
		'portletNamespace',
		'selectedMappingTypes',
		'selectedSidebarPanelId',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLink, templates);

export {ConnectedFragmentEntryLink, FragmentEntryLink};

export default ConnectedFragmentEntryLink;