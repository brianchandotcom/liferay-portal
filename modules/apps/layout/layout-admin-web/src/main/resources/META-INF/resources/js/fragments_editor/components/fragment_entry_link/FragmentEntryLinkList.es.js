import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import {
	CLEAR_DRAG_TARGET,
	MOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_DRAG_TARGET,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {
	DRAG_POSITIONS,
	DROP_TARGET_TYPES
} from '../../reducers/placeholders.es';
import {getFragmentRowIndex, setIn} from '../../utils/utils.es';
import state from '../../store/state.es';
import templates from './FragmentEntryLinkList.soy';

/**
 * FragmentEntryLinkList
 * @review
 */
class FragmentEntryLinkList extends Component {

	/**
	 * Adds drop target types to state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _addDropTypesToState(_state) {
		return setIn(_state, ['dropTargetTypes'], DROP_TARGET_TYPES);
	}

	/**
	 * Checks wether a section is empty or not, sets empty parameter
	 * and returns a new state
	 * @param {Object} _state
	 * @private
	 * @return {Object}
	 * @static
	 */
	static _setEmptySections(_state) {
		return setIn(
			_state,
			[
				'layoutData',
				'structure'
			],
			_state.layoutData.structure.map(
				section => setIn(
					section,
					['empty'],
					section.columns.every(
						column => column.fragmentEntryLinkIds.length === 0
					)
				)
			)
		);
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	attached() {
		this._initializeDragAndDrop();
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	dispose() {
		this._dragDrop.dispose();
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */
	prepareStateForRender(state) {
		let _state = FragmentEntryLinkList._addDropTypesToState(state);

		_state = FragmentEntryLinkList._setEmptySections(_state);

		return _state;
	}

	/**
	 * Gives focus to the specified fragmentEntryLinkId
	 * @param {string} fragmentEntryLinkId
	 * @review
	 */
	focusFragmentEntryLink(fragmentEntryLinkId) {
		requestAnimationFrame(
			() => {
				const fragmentEntryLinkElement = this.refs[fragmentEntryLinkId];

				if (fragmentEntryLinkElement) {
					fragmentEntryLinkElement.focus();
					fragmentEntryLinkElement.scrollIntoView();
				}
			}
		);
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {object} data
	 * @param {MouseEvent} data.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(data) {
		const targetItem = data.target;

		if (targetItem && 'fragmentEntryLinkId' in targetItem.dataset) {
			const mouseY = data.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			this._targetBorder = DRAG_POSITIONS.bottom;

			if (Math.abs(mouseY - targetItemRegion.top) <= Math.abs(mouseY - targetItemRegion.bottom)) {
				this._targetBorder = DRAG_POSITIONS.top;
			}

			this.store.dispatchAction(
				UPDATE_DRAG_TARGET,
				{
					hoveredElementBorder: this._targetBorder,
					hoveredFragmentEntryLinkId: targetItem.dataset.fragmentEntryLinkId
				}
			);
		}
	}

	/**
	 * Callback that is executed when we leave a drag target.
	 * @private
	 * @review
	 */
	_handleDragEnd() {
		this.store.dispatchAction(
			CLEAR_DRAG_TARGET
		);
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {object} data
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		const placeholderId = data.source.dataset.fragmentEntryLinkId;
		const targetId = data.target ?
			data.target.dataset.fragmentEntryLinkId :
			'';

		if (targetId && targetId !== placeholderId) {
			requestAnimationFrame(
				() => {
					this._initializeDragAndDrop();
				}
			);

			this.store
				.dispatchAction(
					UPDATE_SAVING_CHANGES_STATUS,
					{
						savingChanges: true
					}
				)
				.dispatchAction(
					MOVE_FRAGMENT_ENTRY_LINK,
					{
						originFragmentEntryLinkId: placeholderId,
						targetFragmentEntryLinkBorder: this._targetBorder,
						targetFragmentEntryLinkId: targetId
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
				)
				.dispatchAction(
					CLEAR_DRAG_TARGET
				);
		}
	}

	/**
	 * @param {object} event
	 * @private
	 * @review
	 */
	_handleFragmentMove(event) {
		const placeholderId = event.fragmentEntryLinkId;

		const rowIndex = getFragmentRowIndex(
			this.layoutData.structure,
			placeholderId
		);

		let targetId;

		const targetRow = this
			.layoutData
			.structure[rowIndex + event.direction];

		if (targetRow && targetRow.columns.length) {
			targetId = targetRow.columns[0].fragmentEntryLinkIds[0];
		}

		if (event.direction === 1) {
			this._targetBorder = DRAG_POSITIONS.bottom;
		}
		else {
			this._targetBorder = DRAG_POSITIONS.top;
		}

		if (targetId && targetId !== placeholderId) {
			this.store
				.dispatchAction(
					UPDATE_SAVING_CHANGES_STATUS,
					{
						savingChanges: true
					}
				)
				.dispatchAction(
					MOVE_FRAGMENT_ENTRY_LINK,
					{
						originFragmentEntryLinkId: placeholderId,
						targetFragmentEntryLinkBorder: this._targetBorder,
						targetFragmentEntryLinkId: targetId
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
	 * @private
	 * @review
	 */
	_initializeDragAndDrop() {
		if (this._dragDrop) {
			this._dragDrop.dispose();
		}

		this._dragDrop = new DragDrop(
			{
				autoScroll: true,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.drag-handler',
				sources: '.drag-fragment',
				targets: '.fragment-entry-link-drop-target'
			}
		);

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDrag.bind(this)
		);

		this._dragDrop.on(
			DragDrop.Events.END,
			this._handleDrop.bind(this)
		);

		this._dragDrop.on(
			DragDrop.Events.TARGET_LEAVE,
			this._handleDragEnd.bind(this)
		);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLinkList.STATE = {

	/**
	 * Data associated to the layout
	 * @default {object}
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object}
	 */
	layoutData: state.layoutData,

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null),

	/**
	 * Nearest border of the hovered fragment while dragging
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkList
	 * @review
	 * @type {!string}
	 */
	_targetBorder: Config.internal().string()
};

Soy.register(FragmentEntryLinkList, templates);

export {FragmentEntryLinkList};
export default FragmentEntryLinkList;