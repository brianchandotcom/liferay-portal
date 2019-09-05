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

import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/fragment_entry_link/FragmentEntryLinkList.es';
import './components/sidebar/FragmentsEditorSidebar.es';
import './components/toolbar/FragmentsEditorToolbar.es';
import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_HOVERED_ITEM,
	UPDATE_HOVERED_ITEM
} from './actions/actions.es';
import {INITIAL_STATE} from './store/state.es';
import {
	startListeningWidgetConfigurationChange,
	stopListeningWidgetConfigurationChange
} from './utils/FragmentsEditorDialogUtils';
import {Store} from './store/store.es';
import templates from './FragmentsEditor.soy';
import {updateActiveItemAction} from './actions/updateActiveItem.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from './utils/constants';

/**
 * DOM selector where the fragmentEntryLinks are rendered
 */
const WRAPPER_SELECTOR = '.fragment-entry-link-list-wrapper';

/**
 * DOM selector where the sidebar is rendered
 */
const SIDEBAR_SELECTOR = '.fragments-editor-sidebar';

/**
 * FragmentsEditor
 * @review
 */
class FragmentsEditor extends Component {
	/**
	 * Check whether the background editable should be marked as active or not, as if we allow
	 * the background image editable to be marked always it will be almost imposible to click on
	 * the fragment without relying on the page structure.
	 *
	 * For the background image editable to be marked as active, one of the following conditions should be met:
	 *  - The fragment containing the background image editable is active
	 *  - The active element is a background image whose parent is the same that the background image
	 * we want to be active (a fragment can have several background image editable)
	 *
	 * Otherwise the fragment containing the background editable image will be marked as active.
	 *
	 * @param {HTMLElement} target
	 * @param {string} newActiveItemId
	 * @param {string} newActiveItemType
	 * @param {string} oldActiveItemId
	 * @param {string} oldActiveItemType
	 */
	static getBackgroundEditableTarget(
		target,
		newActiveItemId,
		newActiveItemType,
		oldActiveItemId,
		oldActiveItemType
	) {
		const defaultActiveItem = {
			activeItemId: newActiveItemId,
			activeItemType: newActiveItemType
		};

		if (
			!newActiveItemId ||
			!newActiveItemType ||
			newActiveItemType !==
				FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable ||
			(newActiveItemId === oldActiveItemId &&
				newActiveItemType === oldActiveItemType)
		) {
			return defaultActiveItem;
		}

		const parentFragment = dom.closest(
			target,
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}"]`
		);

		if (
			!parentFragment ||
			(parentFragment.dataset.fragmentsEditorItemId === oldActiveItemId &&
				parentFragment.dataset.fragmentsEditorItemType ===
					oldActiveItemType)
		) {
			return defaultActiveItem;
		}

		const oldParentFragment = dom.closest(
			document.querySelector(
				`[data-fragments-editor-item-id="${oldActiveItemId}"][data-fragments-editor-item-type="${oldActiveItemType}"]`
			),
			`[data-fragments-editor-item-type="${FRAGMENTS_EDITOR_ITEM_TYPES.fragment}"]`
		);

		if (
			oldParentFragment &&
			parentFragment.dataset.fragmentsEditorItemId ===
				oldParentFragment.dataset.fragmentsEditorItemId
		) {
			return defaultActiveItem;
		}

		return {
			activeItemId: parentFragment.dataset.fragmentsEditorItemId,
			activeItemType: parentFragment.dataset.fragmentsEditorItemType
		};
	}

	/**
	 * @param {KeyboardEvent|MouseEvent} event
	 * @return {{fragmentsEditorItemId: string|null, fragmentsEditorItemType: string|null}}
	 * @private
	 * @review
	 */
	static _getItemTarget(event) {
		let {fragmentsEditorItemId = null, fragmentsEditorItemType = null} =
			event.target.dataset || {};

		if (!fragmentsEditorItemId || !fragmentsEditorItemType) {
			const parent = dom.closest(
				event.target,
				'[data-fragments-editor-item-id]'
			);

			if (parent) {
				fragmentsEditorItemId = parent.dataset.fragmentsEditorItemId;
				fragmentsEditorItemType =
					parent.dataset.fragmentsEditorItemType;
			}
		}

		return {
			fragmentsEditorItemId,
			fragmentsEditorItemType
		};
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);
		this._handleDocumentKeyDown = this._handleDocumentKeyDown.bind(this);
		this._handleDocumentKeyUp = this._handleDocumentKeyUp.bind(this);
		this._handleDocumentMouseOver = this._handleDocumentMouseOver.bind(
			this
		);

		document.addEventListener('click', this._handleDocumentClick, true);
		document.addEventListener('keydown', this._handleDocumentKeyDown);
		document.addEventListener('keyup', this._handleDocumentKeyUp);
		document.addEventListener('mouseover', this._handleDocumentMouseOver);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		document.removeEventListener('click', this._handleDocumentClick, true);
		document.removeEventListener('keyup', this._handleDocumentKeyUp);
		document.removeEventListener(
			'mouseover',
			this._handleDocumentMouseOver
		);

		stopListeningWidgetConfigurationChange();
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
		const {
			fragmentsEditorItemId,
			fragmentsEditorItemType
		} = FragmentsEditor._getItemTarget(event);

		if (fragmentsEditorItemId && fragmentsEditorItemType && this.store) {
			this.store.dispatch({
				hoveredItemId: fragmentsEditorItemId,
				hoveredItemType: fragmentsEditorItemType,
				type: UPDATE_HOVERED_ITEM
			});
		} else if (this.store) {
			this.store.dispatch({
				type: CLEAR_HOVERED_ITEM
			});
		}
	}

	/**
	 * @param {KeyboardEvent|MouseEvent} event
	 * @private
	 * @review
	 */
	_updateActiveItem(event) {
		const {
			fragmentsEditorItemId,
			fragmentsEditorItemType
		} = FragmentsEditor._getItemTarget(
			event,
			this.activeItemId,
			this.activeItemType
		);

		if (fragmentsEditorItemId && fragmentsEditorItemType) {
			const {
				activeItemId,
				activeItemType
			} = FragmentsEditor.getBackgroundEditableTarget(
				event.target,
				fragmentsEditorItemId,
				fragmentsEditorItemType,
				this.activeItemId,
				this.activeItemType
			);

			this.store.dispatch(
				updateActiveItemAction(activeItemId, activeItemType, {
					appendItem: this._shiftPressed
				})
			);
		} else if (
			(dom.closest(event.target, WRAPPER_SELECTOR) ||
				event.target === document.querySelector(WRAPPER_SELECTOR)) &&
			!dom.closest(event.target, SIDEBAR_SELECTOR)
		) {
			this.store.dispatch({
				type: CLEAR_ACTIVE_ITEM
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
FragmentsEditor.STATE = Object.assign(
	{
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
		store: Config.instanceOf(Store)
	},
	INITIAL_STATE
);

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;
