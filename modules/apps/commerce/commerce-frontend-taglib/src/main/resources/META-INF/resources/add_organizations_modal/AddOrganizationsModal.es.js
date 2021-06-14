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

import template from './AddOrganizationsModal.soy';

import 'clay-modal';

import './OrganizationInputItem.es';

import './OrganizationListItem.es';

class AddOrganizationModal extends Component {
	created() {
		this._debouncedFetchOrganizations = debounce(
			this._fetchOrganizations.bind(this),
			300
		);
	}

	attached() {
		return this._fetchOrganizations();
	}

	syncAddedOrganizations() {
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

		return this._debouncedFetchOrganizations();
	}

	_handleFormSubmit(event) {
		event.preventDefault();

		if (this.organizations.length) {
			this._toggleItem(this.organizations[0]);
			this.query = '';
		}

		return event;
	}

	_handleInputBox(event) {
		if (event.keyCode === 8 && !this.query.length) {
			this.selectedOrganizations = this.selectedOrganizations.slice(
				0,
				-1
			);

			return false;
		}
		this.query = event.target.value;

		return this.query;
	}

	_toggleItem(organizationToBeToggled) {
		if (!organizationToBeToggled.id) {
			this.query = '';
		}

		const organizationAlreadyAdded = this.selectedOrganizations.reduce(
			(alreadyAdded, organization) =>
				alreadyAdded || organization.id === organizationToBeToggled.id,
			false
		);

		this.selectedOrganizations = organizationAlreadyAdded
			? this.selectedOrganizations.filter(
					(organization) =>
						organization.id !== organizationToBeToggled.id
			  )
			: [...this.selectedOrganizations, organizationToBeToggled];

		return this.selectedOrganizations;
	}

	_fetchOrganizations() {
		return fetch(
			this.organizationsAPI +
				'?groupId=' +
				themeDisplay.getScopeGroupId() +
				'&q=' +
				this.query
		)
			.then((response) => response.json())
			.then((response) => {
				this._loading = false;
				this.organizations = this.addColorToOrganizations(
					response.organizations
				);

				return this.organizations;
			});
	}

	addColorToOrganizations(organizations) {
		return organizations.map((organization) => ({
			colorId: Math.floor(Math.random() * 6) + 1,
			...organization,
		}));
	}

	_addOrganizations() {
		if (!this.selectedOrganizations.length) {
			return false;
		}

		return this.emit('addOrganization', this.selectedOrganizations);
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

Soy.register(AddOrganizationModal, template);

const ORGANIZATION_SCHEMA = Config.shapeOf({
	colorId: Config.number(),
	id: Config.oneOfType([Config.number(), Config.string()]).required(),
	name: Config.string().required(),
});

AddOrganizationModal.STATE = {
	_loading: Config.bool().internal().value(false),
	_modalVisible: Config.bool().internal().value(false),
	organizations: Config.array(ORGANIZATION_SCHEMA).value([]),
	organizationsAPI: Config.string().value(''),
	query: Config.string().value(''),
	selectedOrganizations: Config.array(ORGANIZATION_SCHEMA).value([]),
	spritemap: Config.string(),
};

export {AddOrganizationModal};
export default AddOrganizationModal;
