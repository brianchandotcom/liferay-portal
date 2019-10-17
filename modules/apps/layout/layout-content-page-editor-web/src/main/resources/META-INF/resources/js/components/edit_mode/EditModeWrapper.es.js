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

import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../../actions/actions.es';
import {updateActiveItemAction} from '../../actions/updateActiveItem.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';

const WRAPPER_CLASSES = {
	default: 'fragment-entry-link-list-wrapper',
	padded: 'fragment-entry-link-list-wrapper--padded'
};

const HIGHLIGHTED_COMMENT_ID_KEY = 'FRAGMENTS_EDITOR_HIGHLIGHTED_COMMENT_ID';

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
		this._url = new URL(location.href);

		requestAnimationFrame(() => {
			this._handleMessageIdURLParameter();

			this._handleActiveItemURLParameter();
			this._handleSidebarPanelIdURLParameter();

			this._syncURL();

			this.on(
				'activeItemIdChanged',
				this._syncValueToURL('activeItemId')
			);

			this.on(
				'activeItemTypeChanged',
				this._syncValueToURL('activeItemType')
			);

			this.on(
				'selectedSidebarPanelIdChanged',
				this._syncValueToURL('sidebarPanelId')
			);
		});
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncSelectedSidebarPanelId() {
		this._toggleWrapperPadding();
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncSidebarPanels() {
		this._toggleWrapperPadding();
	}

	/**
	 * @private
	 * @review
	 */
	_handleActiveItemURLParameter() {
		const activeItemId = this._url.searchParams.get('activeItemId');
		const activeItemType = this._url.searchParams.get('activeItemType');

		if (activeItemId && activeItemType) {
			this.store.dispatch(
				updateActiveItemAction(activeItemId, activeItemType)
			);
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

				window.sessionStorage.setItem(
					HIGHLIGHTED_COMMENT_ID_KEY,
					messageId
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

		const sidebarPanel = this.sidebarPanels.find(
			panel => panel.sidebarPanelId === sidebarPanelId
		);

		if (sidebarPanelId !== null && sidebarPanel) {
			this.store.dispatch({
				type: UPDATE_SELECTED_SIDEBAR_PANEL_ID,
				value: sidebarPanel.sidebarPanelId
			});
		}
	}

	/**
	 * Syncs internal URL to window state
	 * @private
	 * @review
	 */
	_syncURL() {
		let skipLoadPopstate;

		if (Liferay.SPA && Liferay.SPA.app) {
			skipLoadPopstate = Liferay.SPA.app.skipLoadPopstate;
			Liferay.SPA.app.skipLoadPopstate = true;
		}

		history.replaceState(null, document.head.title, this._url.href);

		requestAnimationFrame(() => {
			if (
				Liferay.SPA &&
				Liferay.SPA.app &&
				typeof skipLoadPopstate === 'boolean'
			) {
				Liferay.SPA.app.skipLoadPopstate = skipLoadPopstate;
			}
		});
	}

	/**
	 * @param {string} key
	 * @private
	 * @review
	 */
	_syncValueToURL(key) {
		/**
		 * @param {{ newVal: any }} change
		 */
		return change => {
			if (change.newVal !== null) {
				this._url.searchParams.set(key, change.newVal);
			} else {
				this._url.searchParams.delete(key);
			}

			this._syncURL();
		};
	}

	/**
	 * @private
	 * @review
	 */
	_toggleWrapperPadding() {
		const sidebarPanel = this.sidebarPanels.find(
			panel => panel.sidebarPanelId === this.selectedSidebarPanelId
		);

		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASSES.default);

			if (sidebarPanel) {
				wrapper.classList.add(WRAPPER_CLASSES.padded);
			} else {
				wrapper.classList.remove(WRAPPER_CLASSES.padded);
			}
		}
	}
}

EditModeWrapper.STATE = {
	/**
	 * Internal URL object
	 * @default new URL
	 * @memberof EditModeWrapper
	 * @review
	 * @type {URL}
	 */
	_url: Config.instanceOf(URL).internal()
};

const ConnectedEditModeWrapper = getConnectedComponent(EditModeWrapper, [
	'activeItemId',
	'activeItemType',
	'fragmentEntryLinks',
	'selectedSidebarPanelId',
	'sidebarPanels'
]);

export {ConnectedEditModeWrapper, EditModeWrapper, HIGHLIGHTED_COMMENT_ID_KEY};
export default ConnectedEditModeWrapper;
