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

'use strict';

import {debounce, fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './UserInvitationModal.soy';

import 'clay-modal';

import '../user_utils/UserListItem.es';

import '../user_utils/UserInputItem.es';

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class UserInvitationModal extends Component {
	created() {
		this._debouncedFetchUser = debounce(this._fetchUsers.bind(this), 300);
	}

	attached() {
		this._fetchUsers();
	}

	syncAddedUsers() {
		const contentWrapper = this.element.querySelector(
			'.autocomplete-input__content'
		);
		this.element.querySelector('.autocomplete-input__box').focus();
		if (contentWrapper.scrollTo) {
			contentWrapper.scrollTo(0, contentWrapper.offsetHeight);
		}
	}

	_handleCloseModal(event) {
		event.preventDefault();
		this._modalVisible = false;
	}

	syncQuery() {
		this._loading = true;

		return this._debouncedFetchUser();
	}

	_handleFormSubmit(event) {
		event.preventDefault();
		if (this.query.match(EMAIL_REGEX)) {
			this.addedUsers = [
				...this.addedUsers,
				{
					email: this.query,
				},
			];
			this.query = '';
		}

		return this.query;
	}

	_handleInputBox(event) {
		if (event.keyCode === 8 && !this.query.length) {
			this.addedUsers = this.addedUsers.slice(0, -1);
		}
		else {
			this.query = event.target.value;
		}

		return event;
	}

	_handleInputName(event) {
		this.accountName = event.target.value;

		return event;
	}

	_toggleInvitation(userToBeToggled) {
		if (!userToBeToggled.id) {
			this.query = '';
		}

		const userAlreadyAdded = this.addedUsers.reduce(
			(alreadyAdded, user) =>
				alreadyAdded || user.email === userToBeToggled.email,
			false
		);

		this.addedUsers = userAlreadyAdded
			? this.addedUsers.filter(
					(user) => user.email !== userToBeToggled.email
			  )
			: [...this.addedUsers, userToBeToggled];

		return this.addedUsers;
	}

	_fetchUsers() {
		return fetch(
			this.usersAPI +
				'?groupId=' +
				themeDisplay.getScopeGroupId() +
				'&q=' +
				this.query
		)
			.then((response) => response.json())
			.then((response) => {
				this._loading = false;
				this.users = response.users;

				return this.users;
			});
	}

	_sendInvitations() {
		if (this.addedUsers.length) {
			this.emit('inviteUserToAccount', this.addedUsers);
		}

		return this.addedUsers;
	}

	toggle() {
		this._modalVisible = !this._modalVisible;

		return this._modalVisible;
	}

	open() {
		this._modalVisible = true;

		return this._modalVisible;
	}

	close() {
		this._modalVisible = false;

		return this._modalVisible;
	}
}

Soy.register(UserInvitationModal, template);

const USER_SCHEMA = Config.shapeOf({
	email: Config.string().required(),
	name: Config.string().required(),
	thumbnail: Config.string().required(),
	userId: Config.oneOfType([Config.string(), Config.number()]).required(),
});

UserInvitationModal.STATE = {
	_loading: Config.bool().internal().value(false),
	_modalVisible: Config.bool().internal().value(false),
	addedUsers: Config.array(USER_SCHEMA).value([]),
	query: Config.string().value(''),
	spritemap: Config.string(),
	users: Config.array(USER_SCHEMA).value([]),
	usersAPI: Config.string().value(''),
};

export {UserInvitationModal};
export default UserInvitationModal;
