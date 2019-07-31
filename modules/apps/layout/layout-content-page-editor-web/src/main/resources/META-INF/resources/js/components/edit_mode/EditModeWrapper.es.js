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
import {Config} from 'metal-state';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {
	UPDATE_ACTIVE_ITEM,
	UPDATE_SELECTED_SIDEBAR_PANEL_ID
} from '../../actions/actions.es';

const WRAPPER_CLASSES = {
	default: 'fragment-entry-link-list-wrapper',
	padded: 'fragment-entry-link-list-wrapper--padded'
};

/**
 * EditModeWrapper
 * @review
 */
class EditModeWrapper extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		requestAnimationFrame(() => {
			this._handleMessageIdURLParameter();

			this._handleActiveItemURLParameter();
			this._handleSidebarPanelIdURLParameter();

			this._syncURL();
		});
	}

	/**
	 * @param {string} selectedSidebarPanelId
	 * @review
	 */
	syncSelectedSidebarPanelId(selectedSidebarPanelId) {
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASSES.default);

			if (selectedSidebarPanelId) {
				wrapper.classList.add(WRAPPER_CLASSES.padded);
			} else {
				wrapper.classList.remove(WRAPPER_CLASSES.padded);
			}
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleActiveItemURLParameter() {
		const activeItemId = this._url.searchParams.get('activeItemId');
		const activeItemType = this._url.searchParams.get('activeItemType');

		if (activeItemId && activeItemType) {
			this.store.dispatch({
				activeItemId,
				activeItemType,
				type: UPDATE_ACTIVE_ITEM
			});
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleMessageIdURLParameter() {
		const messageId = this._url.searchParams.get('messageId');

		if (this.fragmentEntryLinks && messageId) {
			const matchComment = comment => comment.commentId === messageId;

			const fragmentEntryLink = Object.values(
				this.fragmentEntryLinks
			).find(fragmentEntryLink =>
				fragmentEntryLink.comments.find(
					comment =>
						matchComment(comment) ||
						comment.children.find(matchComment)
				)
			);

			if (fragmentEntryLink) {
				this._url.searchParams.delete('messageId');
				this._url.searchParams.set('sidebarPanelId', 'comments');

				this._url.searchParams.set(
					'activeItemId',
					fragmentEntryLink.fragmentEntryLinkId
				);

				this._url.searchParams.set(
					'activeItemType',
					FRAGMENTS_EDITOR_ITEM_TYPES.fragment
				);
			}
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleSidebarPanelIdURLParameter() {
		const sidebarPanelId = this._url.searchParams.get('sidebarPanelId');

		if (sidebarPanelId) {
			this.store.dispatch({
				sidebarPanelId,
				type: UPDATE_SELECTED_SIDEBAR_PANEL_ID
			});
		}
	}

	/**
	 * Syncs internal URL to window state
	 * @private
	 * @review
	 */
	_syncURL() {
		history.replaceState(null, document.head.title, this._url.href);
	}
}

EditModeWrapper.STATE = {
	/**
	 * Internal URL object
	 * @default new URL
	 * @memberof EditModeWrapper
	 * @private
	 * @review
	 * @type {URL}
	 */
	_url: Config.instanceOf(URL)
		.internal()
		.value(new URL(location.href))
};

const ConnectedEditModeWrapper = getConnectedComponent(EditModeWrapper, [
	'fragmentEntryLinks',
	'selectedSidebarPanelId'
]);

export {ConnectedEditModeWrapper, EditModeWrapper};
export default ConnectedEditModeWrapper;
