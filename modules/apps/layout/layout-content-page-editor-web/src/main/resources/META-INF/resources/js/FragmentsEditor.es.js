/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {debounce} from 'frontend-js-web';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/fragment_entry_link/FragmentEntryLinkList.es';

import './components/master_layout/MasterFragmentEntryLinkList.es';

import './components/sidebar/FragmentsEditorSidebar.es';

import './components/toolbar/FragmentsEditorToolbar.es';
import templates from './FragmentsEditor.soy';
import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_HOVERED_ITEM,
	UPDATE_HOVERED_ITEM
} from './actions/actions.es';
import {updateActiveItemAction} from './actions/updateActiveItem.es';
import {INITIAL_STATE} from './store/state.es';
import {Store} from './store/store.es';
import {
	startListeningWidgetConfigurationChange,
	stopListeningWidgetConfigurationChange
} from './utils/FragmentsEditorDialogUtils';
import {getElements} from './utils/FragmentsEditorGetUtils.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from './utils/constants';

/**
 * @type {string}
 */
const ITEM_CLASS = 'fragments-editor__item';

/**
 * @type {string}
 */
const HOVERED_ITEM_CLASS = `${ITEM_CLASS}--hovered`;

/**
 * DOM selector where the sidebar is rendered
 */
const SIDEBAR_SELECTOR = '.fragments-editor-sidebar';

/**
 * DOM selector where the fragmentEntryLinks are rendered
 */
const WRAPPER_SELECTOR = '.fragment-entry-link-list-wrapper';

/**
 * FragmentsEditor
 * @review
 */
class FragmentsEditor extends Component {
	/**
	 * @param {KeyboardEvent|MouseEvent} event
	 * @return {{targetItem: HTMLElement, targetItemId: string|null, targetItemType: string|null}}
	 * @private
	 * @review
	 */
	static _getTargetItemData(event) {
		let targetItem = event.target;
		let {targetItemId = null, targetItemType = null} =
			targetItem.dataset || {};

		if (!targetItemId || !targetItemType) {
			targetItem = dom.closest(
				event.target,
				'[data-fragments-editor-item-id]'
			);

			if (targetItem) {
				targetItemId = targetItem.dataset.fragmentsEditorItemId;
				targetItemType = targetItem.dataset.fragmentsEditorItemType;
			}
		}

		return {
			targetItem,
			targetItemId,
			targetItemType
		};
	}

	/**
	 * Adds edition listeners
	 * @review
	 */
	_addEditionListeners() {
		if (!this._activeEditionListeners) {
			this._activeEditionListeners = true;

			document.addEventListener('click', this._handleDocumentClick, true);
			document.addEventListener('keydown', this._handleDocumentKeyDown);
			document.addEventListener('keyup', this._handleDocumentKeyUp);
			document.addEventListener(
				'mouseover',
				this._handleDocumentMouseOver
			);
		}
	}

	/**
	 * Removes edition listeners
	 * @review
	 */
	_removeEditionListeners() {
		if (this._activeEditionListeners) {
			this._activeEditionListeners = false;

			document.removeEventListener(
				'click',
				this._handleDocumentClick,
				true
			);
			document.removeEventListener(
				'keydown',
				this._handleDocumentKeyDown
			);
			document.removeEventListener('keyup', this._handleDocumentKeyUp);
			document.removeEventListener(
				'mouseover',
				this._handleDocumentMouseOver
			);
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);
		this._handleDocumentKeyDown = this._handleDocumentKeyDown.bind(this);
		this._handleDocumentKeyUp = this._handleDocumentKeyUp.bind(this);
		this._handleDocumentMouseOver = debounce(
			this._handleDocumentMouseOver.bind(this),
			100
		);

		if (!this.lockedSegmentsExperience) {
			this._addEditionListeners();
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		this._removeEditionListeners();

		stopListeningWidgetConfigurationChange();
	}

	/**
	 * Listens for changes in the Experience lock status to hide edition features
	 * @inheritdoc
	 * @review
	 */
	syncLockedSegmentsExperience(value, previousValue) {
		if (value && value !== previousValue) {
			if (this.store) {
				this.store.dispatch({
					type: CLEAR_ACTIVE_ITEM
				});
			}

			this._removeEditionListeners();
		}
		else if (value !== previousValue) {
			this._addEditionListeners();
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncStore() {
		if (this.store) {
			startListeningWidgetConfigurationChange(this.store);
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentClick(event) {
		this._updateActiveItem(event);
	}

	/**
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentKeyDown(event) {
		this._shiftPressed = event.shiftKey;
	}

	/**
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentKeyUp(event) {
		this._shiftPressed = event.shiftKey;

		if (event.key !== 'Shift') {
			this._updateActiveItem(event);
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentMouseOver(event) {
		if (this.store) {
			this._updateHoveredItem(event);
		}
	}

	/**
	 * @param {KeyboardEvent|MouseEvent} event
	 * @private
	 * @review
	 */
	_updateActiveItem(event) {
		const {
			targetItemId,
			targetItemType
		} = FragmentsEditor._getTargetItemData(event);

		if (targetItemId && targetItemType) {
			this.store.dispatch(
				updateActiveItemAction(targetItemId, targetItemType, {
					appendItem: this._shiftPressed
				})
			);
		}
		else if (
			(dom.closest(event.target, WRAPPER_SELECTOR) ||
				event.target === document.querySelector(WRAPPER_SELECTOR)) &&
			!dom.closest(event.target, SIDEBAR_SELECTOR)
		) {
			this.store.dispatch({
				type: CLEAR_ACTIVE_ITEM
			});
		}
	}

	/**
	 * Update hovered item
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_updateHoveredItem(event) {
		const {
			targetItemId,
			targetItemType
		} = FragmentsEditor._getTargetItemData(event);

		let hoveredItemId = targetItemId;
		let hoveredItemType = targetItemType;

		document
			.querySelectorAll(`.${HOVERED_ITEM_CLASS}`)
			.forEach(hoveredItem => {
				hoveredItem.classList.remove(HOVERED_ITEM_CLASS);
			});

		const targetItems = getElements(targetItemId, targetItemType);

		if (targetItems.length === 0) {
			const targetItemIsEditable =
				targetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable ||
				targetItemType ===
					FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable;

			if (
				targetItemIsEditable &&
				!targetItems[0].classList.contains(
					'fragments-editor__editable--highlighted'
				)
			) {
				const fragment = getElements(
					targetItems[0].dataset.fragmentEntryLinkId,
					FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				)[0];

				hoveredItemId = fragment.dataset.fragmentsEditorItemId;
				hoveredItemType = FRAGMENTS_EDITOR_ITEM_TYPES.fragment;
			}
		}
		else if (targetItems.length > 1) {
			targetItems.forEach(targetItem => {
				targetItem.classList.add(ITEM_CLASS);
				targetItem.classList.add(HOVERED_ITEM_CLASS);
			});
		}

		if (hoveredItemId && hoveredItemType) {
			this.store.dispatch({
				hoveredItemId,
				hoveredItemType,
				type: UPDATE_HOVERED_ITEM
			});
		}
		else {
			this.store.dispatch({
				type: CLEAR_HOVERED_ITEM
			});
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {object}
 */
FragmentsEditor.STATE = {
	/**
	 * @default false
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_shiftPressed: Config.bool()
		.internal()
		.value(false),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),
	...INITIAL_STATE
};

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;
