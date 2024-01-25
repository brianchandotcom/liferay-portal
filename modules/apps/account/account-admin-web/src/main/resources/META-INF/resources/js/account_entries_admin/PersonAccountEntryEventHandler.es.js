/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		this.searchContainerId = props.searchContainer;
		this.selectUserButton = this._setElement(props.selectUserButton);
		this.selectUserEventName = props.selectUserEventName;
		this.selectUserURL = props.selectUserURL;
		this.userIdInput = this._setElement(props.userIdInput);

		this._handleOnSelect = this._handleOnSelect.bind(this);
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
		const searchContainer = this._getSearchContainer();

		searchContainer.deleteRow(1, searchContainer.getData());

		this.userIdInput.value = null;
	}

	_handleSelectUserButtonClicked() {
		this._selectAccountUser();
	}

	_getSearchContainer() {
		return Liferay.SearchContainer.get(this.ns(this.searchContainerId));
	}

	_selectAccountUser() {
		openSelectionModal({
			containerProps: {
				className: '',
			},
			id: this.ns(this.selectUserEventName),
			iframeBodyCssClass: '',
			onSelect: this._handleOnSelect,
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

		const searchContainer = this._getSearchContainer();

		searchContainer.deleteRow(1, searchContainer.getData());
		searchContainer.addRow(
			[userName, emailAddress, jobTitle, this.removeUserIconMarkup],
			userId
		);
		searchContainer.updateDataStore([userId]);
	}

	_setElement(selector) {
		return this.one(selector);
	}
}

export default PersonAccountEntryEventHandler;
