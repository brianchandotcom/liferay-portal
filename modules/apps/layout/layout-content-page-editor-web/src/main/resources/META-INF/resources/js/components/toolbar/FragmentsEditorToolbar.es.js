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
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import 'clay-label';

import './TranslationStatus.es';

import './SegmentsExperienceSelector.es';

import './ExperimentsLabel.es';
import {TOGGLE_SIDEBAR} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {PAGE_TYPES} from '../../utils/constants';
import templates from './FragmentsEditorToolbar.soy';

/**
 * FragmentsEditorToolbar
 * @review
 */
class FragmentsEditorToolbar extends Component {
	/**
	 * @inheritdoc
	 * @param {object} state
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (state.lastSaveDate) {
			const lastSaveDate = Liferay.Language.get('draft-saved-at-x');

			nextState = setIn(
				nextState,
				['lastSaveDate'],
				lastSaveDate.replace('{0}', state.lastSaveDate)
			);
		}

		return nextState;
	}

	syncLastSaveDate() {
		this._publishButtonEnabled =
			this._online &&
			Boolean(this.lastSaveDate) &&
			(this.pageType !== PAGE_TYPES.master ||
				this.layoutData.hasDropZone);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleWindowOffline = this._handleWindowOffline.bind(this);
		this._updateOnlineStatus = this._updateOnlineStatus.bind(this);

		window.addEventListener('offline', this._handleWindowOffline);
		window.addEventListener('online', this._updateOnlineStatus);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		window.removeEventListener('offline', this._handleWindowOffline);
		window.removeEventListener('online', this._updateOnlineStatus);
	}

	/**
	 * Handles discard draft form submit action.
	 * @private
	 * @review
	 */
	_handleDiscardDraft(event) {
		if (
			!confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			event.preventDefault();
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleToggleContextualSidebarButtonClick() {
		this.store.dispatch({
			type: TOGGLE_SIDEBAR
		});
	}

	/**
	 * Starts checking if there is connection with Liferay Server
	 * @private
	 * @review
	 */
	_handleWindowOffline() {
		this._online = false;

		this._updateOnlineStatus();
	}

	/**
	 * Pings Liferay Server and set's online status.
	 * Instead of relying on window 'online' event, we use it to check our
	 * connection with Liferay server.
	 * @private
	 * @review
	 */
	_updateOnlineStatus() {
		const queryPing = () =>
			setTimeout(() => {
				this._updateOnlineStatus();
			}, 1000);

		if (!this._online) {
			Liferay.Util.fetch('/image/user_portrait')
				.then(response => {
					if (response.status < 400) {
						this._online = true;
					} else {
						queryPing();
					}
				})
				.catch(queryPing);
		}
	}
}

FragmentsEditorToolbar.STATE = {
	/**
	 * If fragments editor is online
	 * @default true
	 * @instance
	 * @memberof FragmentsEditorToolbar
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_online: Config.bool()
		.internal()
		.value(true),

	/**
	 * If the publish button should be enabled
	 * @default true
	 * @instance
	 * @memberof FragmentsEditorToolbar
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_publishButtonEnabled: Config.bool()
		.internal()
		.value(true)
};

const ConnectedFragmentsEditorToolbar = getConnectedComponent(
	FragmentsEditorToolbar,
	[
		'classPK',
		'discardDraftRedirectURL',
		'discardDraftURL',
		'hasUpdatePermissions',
		'lastSaveDate',
		'layoutData',
		'portletNamespace',
		'pageType',
		'publishURL',
		'redirectURL',
		'savingChanges',
		'selectedSidebarPanelId',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentsEditorToolbar, templates);

export {ConnectedFragmentsEditorToolbar, FragmentsEditorToolbar};
export default ConnectedFragmentsEditorToolbar;
