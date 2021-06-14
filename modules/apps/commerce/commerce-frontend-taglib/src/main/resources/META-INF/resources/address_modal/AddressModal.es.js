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

import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AddressModal.soy';

import 'clay-modal';

import '../input_utils/CommerceInputText';

class AddressModal extends Component {
	attached() {
		return this._fetchCountries();
	}

	sync_formData() {
		return this._validateForms();
	}

	_handleFirstDotClick(event) {
		event.preventDefault();
		this._stage = 1;

		return this._stage;
	}

	_handleSecondDotClick(event) {
		return this._handleNextButton(event);
	}

	_handleNextButton(event) {
		event.preventDefault();
		this._firstFormValid = this.refs.modal.refs.firstForm.checkValidity();
		if (this._firstFormValid) {
			this._stage = 2;
		}

		return event;
	}

	_handleCloseModal(event) {
		event.preventDefault();
		this._modalVisible = false;

		return event;
	}

	_handleSelectBox(event) {
		const value = event.target.value;
		if (event.target.name === 'commerceCountry') {
			this._formData = {
				...this._formData,
				country: value,
			};

			const country = this._countries.filter(
				(country) => country.id == value
			);

			if (country.length === 1) {
				this._isBillingAllowed = country[0].billingAllowed;
				this._isShippingAllowed = country[0].shippingAllowed;

				this._fetchRegions();
			}
			else {
				this._regions = [];
			}
		}
		else if (event.target.name === 'addressType') {
			this._formData = {
				...this._formData,
				addressType: value,
			};
		}
		else {
			this._formData = {
				...this._formData,
				region: value,
			};
		}

		return value;
	}

	_handleInputBox(event) {
		this._formData = {
			...this._formData,
			[event.target.name]: event.target.value,
		};

		return event.target.value;
	}

	_validateForms() {
		const firstFormValid = !!(
			this._formData.address &&
			this._formData.address.length &&
			this._formData.city &&
			this._formData.city.length &&
			this._formData.zipCode &&
			this._formData.zipCode.length &&
			this._formData.country &&
			this._formData.country.length &&
			this._formData.region &&
			this._formData.region.length
		);
		this._firstFormValid = firstFormValid;

		const secondFormValid = !!(
			this._formData.referent && this._formData.referent.length
		);
		this._secondFormValid = secondFormValid;

		return this._firstFormValid && this._secondFormValid;
	}

	fetchExistingAddress(id) {
		fetch('/o/commerce-ui/address/' + id)
			.then((response) => response.json())
			.then((jsonResponse) => {
				const data = JSON.parse(jsonResponse);

				this._formData = {
					...this._formData,
					address: data.street1,
					addressType: data.type,
					city: data.city,
					country: data.countryId,
					id,
					referent: data.name,
					region: data.regionId,
					telephone: data.phoneNumber,
					zipCode: data.zip,
				};

				this._fetchRegions();
			});
	}

	_fetchCountries() {
		return fetch(this.countriesAPI)
			.then((response) => response.json())
			.then((countries) => {
				this._countries = countries;

				return this._countries;
			});
	}

	_fetchRegions() {
		return fetch(this.regionsAPI + this._formData.country)
			.then((response) => response.json())
			.then((regions) => {
				this._regions = regions;

				return this._regions;
			});
	}

	_handleFormSubmit(event) {
		event.preventDefault();
		if (this._firstFormValid && this._secondFormValid) {
			this._addAddress(event);
		}

		return event;
	}

	_addAddress(_e) {
		return this.emit('addressModalSave', this._formData);
	}

	resetForm() {
		this._formData = {
			address: null,
			addressType: 2,
			city: null,
			country: null,
			id: null,
			referent: null,
			region: null,
			telephone: null,
			zipCode: null,
		};

		this._stage = 1;
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

Soy.register(AddressModal, template);

AddressModal.STATE = {
	_countries: Config.array(
		Config.shapeOf({
			billingAllowed: Config.bool().required(),
			id: Config.number().required(),
			name: Config.string().required(),
			shippingAllowed: Config.bool().required(),
		})
	).value([]),
	_firstFormValid: Config.bool().value(false),
	_formData: Config.shapeOf({
		address: Config.string(),
		addressType: Config.oneOfType([Config.string(), Config.number()]),
		city: Config.string(),
		country: Config.oneOfType([Config.string(), Config.number()]),
		id: Config.oneOfType([Config.string(), Config.number()]),
		referent: Config.string(),
		region: Config.oneOfType([Config.string(), Config.number()]),
		telephone: Config.string(),
		zipCode: Config.string(),
	}).value({
		address: null,
		addressType: 2,
		city: null,
		country: null,
		id: null,
		referent: null,
		region: null,
		telephone: null,
		zipCode: null,
	}),
	_isBillingAllowed: Config.bool().value(true),
	_isShippingAllowed: Config.bool().value(true),
	_modalVisible: Config.bool().internal().value(false),
	_regions: Config.array(
		Config.shapeOf({
			id: Config.number().required(),
			name: Config.string().required(),
		})
	).value([]),
	_secondFormValid: Config.bool().value(false),
	_stage: Config.number(Config.oneOf([1, 2])).value(1),
	countriesAPI: Config.string().required(),
	regionsAPI: Config.string().required(),
	spritemap: Config.string(),
};

export {AddressModal};
export default AddressModal;
