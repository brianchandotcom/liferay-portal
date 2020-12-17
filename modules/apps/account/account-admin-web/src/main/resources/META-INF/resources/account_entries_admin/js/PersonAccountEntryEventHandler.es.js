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

import {PortletBase, delegate, openSelectionModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class PersonAccountEntryEventHandler extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		this.selectUserButton.addEventListener(
			'click',
			this._handleSelectUserButtonClicked
		);

		this._removeUserButtonHandle = delegate(
			this.container,
			'click',
			this.removeUserLinkSelector,
			this._handleRemoveUserButtonClicked.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 */
	created() {
		this._handleSelectUserButtonClicked = this._handleSelectUserButtonClicked.bind(
			this
		);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();

		this.selectUserButton.removeEventListener(
			'click',
			this._handleSelectUserButtonClicked
		);

		this._removeUserButtonHandle.dispose();
	}

	_handleOnSelect(selectedItemData) {
		this._setSearchContainerUser(selectedItemData);
	}

	_handleRemoveUserButtonClicked() {
		this.searchContainer.deleteRow(1, this.searchContainer.getData());

		this.userIdInput.value = null;
	}

	_handleSelectUserButtonClicked() {
		this._selectAccountUser();
	}

	_selectAccountUser() {
		openSelectionModal({
			id: this.ns(this.selectUserEventName),
			onSelect: this._handleOnSelect.bind(this),
			selectEventName: this.ns(this.selectUserEventName),
			selectedData: [this.userIdInput.value],
			title: Liferay.Language.get('assign-user'),
			url: this.selectUserURL,
		});
	}

	_setSearchContainerUser({
		emailaddress: emailAddress,
		entityid: userId,
		entityname: userName,
		jobtitle: jobTitle,
	}) {
		this.userIdInput.value = userId;

		this.searchContainer.deleteRow(1, this.searchContainer.getData());
		this.searchContainer.addRow(
			[userName, emailAddress, jobTitle, this.removeUserIconMarkup],
			userId
		);
		this.searchContainer.updateDataStore([userId]);
	}

	_setSearchContainer(searchContainerId) {
		return Liferay.SearchContainer.get(this.ns(searchContainerId));
	}

	_setElement(selector) {
		return this.one(selector);
	}
}

PersonAccountEntryEventHandler.STATE = {
	container: Config.string().setter('_setElement'),
	removeUserIconMarkup: Config.string(),
	removeUserLinkSelector: Config.string(),
	searchContainer: Config.string().setter('_setSearchContainer'),
	selectUserButton: Config.string().setter('_setElement'),
	selectUserEventName: Config.string(),
	selectUserURL: Config.string(),
	userIdInput: Config.string().setter('_setElement'),
};

export default PersonAccountEntryEventHandler;
