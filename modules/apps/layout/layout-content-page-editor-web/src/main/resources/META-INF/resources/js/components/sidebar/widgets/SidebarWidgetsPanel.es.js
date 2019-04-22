import Component from 'metal-component';
import position from 'metal-position';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';

import {ADD_PORTLET, CLEAR_DROP_TARGET, UPDATE_DROP_TARGET, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS} from '../../../actions/actions.es';
import {FRAGMENTS_EDITOR_DRAGGING_CLASS, FRAGMENTS_EDITOR_ITEM_BORDERS, FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {initializeDragDrop} from '../../../utils/FragmentsEditorDragDrop.es';
import {setDraggingItemPosition, setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdateOnChangeProperties} from '../../../utils/FragmentsEditorComponentUtils.es';
import templates from './SidebarWidgetsPanel.soy';

/**
 * KeyBoardEvent enter key
 * @review
 * @type {!string}
 */
const ENTER_KEY = 'Enter';

/**
 * SidebarWidgetsPanel
 */
class SidebarWidgetsPanel extends Component {

	/**
	 * Returns true if the given keywords matches the given text
	 * @param {string} text
	 * @param {string} keywords
	 * @return {boolean}
	 * @review
	 */
	static _keywordsMatch(text, keywords) {
		return text.toLowerCase().indexOf(keywords.toLowerCase()) !== -1;
	}

	/**
	 * Filters widgets tree based on the keywords provided
	 * @param {Object[]} widgets
	 * @param {string} [keywords='']
	 * @private
	 * @return {Object[]}
	 * @review
	 */
	static _filterWidgets(widgets, keywords = '') {
		let filteredWidgets = widgets;

		if (keywords) {
			filteredWidgets = SidebarWidgetsPanel._filterCategories(
				widgets,
				keywords
			);
		}

		return filteredWidgets;
	}

	/**
	 * Returns a filtered list of categories
	 * @param {Object[]} categories
	 * @param {string} keywords
	 * @private
	 * @return {Object[]}
	 * @review
	 */
	static _filterCategories(categories, keywords) {
		return categories
			.map(
				widgetCategory => SidebarWidgetsPanel._filterCategory(
					widgetCategory,
					keywords
				)
			)
			.filter(
				widgetCategory => widgetCategory
			);
	}

	/**
	 * Filters a widget category based on the keywords provided
	 * @param {Object} category
	 * @param {string} keywords
	 * @private
	 * @return {Object}
	 * @review
	 */
	static _filterCategory(category, keywords) {
		let filteredCategory = setIn(
			category,
			['categories'],
			SidebarWidgetsPanel._filterCategories(
				category.categories || [],
				keywords
			)
		);

		const filteredPortlets = (category.portlets || []).filter(
			portlet => SidebarWidgetsPanel._keywordsMatch(portlet.title, keywords)
		);

		if (!SidebarWidgetsPanel._keywordsMatch(category.title, keywords)) {
			filteredCategory = setIn(filteredCategory, ['portlets'], filteredPortlets);
		}

		if (!filteredCategory.portlets.length && !filteredCategory.categories.length) {
			filteredCategory = null;
		}

		return filteredCategory;
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	attached() {
		this._initializeDragAndDrop();
	}

	/**
	 * @inheritdoc
	 * @private
	 * @review
	 */
	disposed() {
		this._dragDrop.dispose();
	}

	/**
	 * @inheritdoc
	 * @param {Object} state
	 * @return {Object}
	 * @review
	 */
	prepareStateForRender(state) {
		return setIn(
			state,
			['widgets'],
			SidebarWidgetsPanel._filterWidgets(
				state.widgets,
				state._keywords
			)
		);
	}

	/**
	 * @inheritdoc
	 * @param {Object} changes
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdateOnChangeProperties(
			changes,
			[
				'_keywords',
				'spritemap',
				'widgets'
			]
		);
	}

	/**
	 * Callback that is executed when an item is being dragged.
	 * @param {Object} eventData
	 * @param {MouseEvent} eventData.originalEvent
	 * @private
	 * @review
	 */
	_handleDrag(eventData) {
		const targetItem = eventData.target;

		const data = targetItem ? targetItem.dataset : null;
		const targetIsColumn = targetItem && ('columnId' in data);
		const targetIsFragment = targetItem && ('fragmentEntryLinkId' in data);
		const targetIsRow = targetItem && ('layoutRowId' in data);

		setDraggingItemPosition(eventData.originalEvent);

		if (targetIsColumn || targetIsFragment || targetIsRow) {
			const mouseY = eventData.originalEvent.clientY;
			const targetItemRegion = position.getRegion(targetItem);

			let nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.bottom;

			if (
				Math.abs(mouseY - targetItemRegion.top) <=
				Math.abs(mouseY - targetItemRegion.bottom)
			) {
				nearestBorder = FRAGMENTS_EDITOR_ITEM_BORDERS.top;
			}

			let dropTargetItemId = null;
			let dropTargetItemType = null;

			if (targetIsColumn) {
				dropTargetItemId = data.columnId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.column;
			}
			else if (targetIsFragment) {
				dropTargetItemId = data.fragmentEntryLinkId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.fragment;
			}
			else if (targetIsRow) {
				dropTargetItemId = data.layoutRowId;
				dropTargetItemType = FRAGMENTS_EDITOR_ITEM_TYPES.row;
			}

			this.store.dispatchAction(
				UPDATE_DROP_TARGET,
				{
					dropTargetBorder: nearestBorder,
					dropTargetItemId,
					dropTargetItemType
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
			CLEAR_DROP_TARGET
		);
	}

	/**
	 * When the search form is submitted, nothing should happen,
	 * as filtering is performed on keypress.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleSearchInputKeyUp(event) {
		if (event.key === ENTER_KEY) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}

		this._keywords = event.delegateTarget.value;
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * @param {!Object} data
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const {instanceable, portletId} = data.source.dataset;

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
					ADD_PORTLET,
					{
						instanceable,
						portletId
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
					CLEAR_DROP_TARGET
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

		this._dragDrop = initializeDragDrop(
			{
				autoScroll: true,
				draggingClass: FRAGMENTS_EDITOR_DRAGGING_CLASS,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.fragments-editor__drag-handler',
				sources: '.fragments-editor__drag-source--sidebar-widget',
				targets: '.fragments-editor__drop-target--sidebar-widget'
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
SidebarWidgetsPanel.STATE = {

	/**
	 * @default ''
	 * @instance
	 * @memberOf SidebarWidgetsPanel
	 * @private
	 * @review
	 * @type {string}
	 */
	_keywords: Config
		.string()
		.internal()
		.value('')
};

const ConnectedSidebarWidgetsPanel = getConnectedComponent(
	SidebarWidgetsPanel,
	[
		'spritemap',
		'widgets'
	]
);

Soy.register(ConnectedSidebarWidgetsPanel, templates);

export {ConnectedSidebarWidgetsPanel, SidebarWidgetsPanel};
export default ConnectedSidebarWidgetsPanel;