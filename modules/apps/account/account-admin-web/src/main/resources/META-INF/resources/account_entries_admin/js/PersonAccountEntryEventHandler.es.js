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
	created(props) {
		this.container = this._setElement(props.container);
		this.removeUserIconMarkup = props.removeUserIconMarkup;
		this.removeUserLinkSelector = props.removeUserLinkSelector;

		/**
		 * Weird hack to get around a race condition where
		 * Liferay.SearchContainer doesn't exist yet. This will be mitigated
		 * once we migrate that component away from AUI.
		 */
		if (!Liferay.SearchContainer) {
			const interval = setInterval(() => {
				if (Liferay.SearchContainer) {
					this.searchContainer = this._setSearchContainer(
						props.searchContainer
					);

					clearInterval(interval);
				}
			}, 60);
		}

		this.selectUserButton = this._setElement(props.selectUserButton);
		this.selectUserEventName = props.selectUserEventName;
		this.selectUserURL = props.selectUserURL;
		this.userIdInput = this._setElement(props.userIdInput);

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
			containerProps: {
				className: '',
			},
			id: this.ns(this.selectUserEventName),
			iframeBodyCssClass: '',
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

export default PersonAccountEntryEventHandler;
