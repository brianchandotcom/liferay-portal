/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete} from 'commerce-frontend-js';

export default function ({
	addressSubtypeConfiguration = {
		billing: '',
		billingAndShipping: '',
		shipping: '',
	},
	initialAddressType,
	initialLabel,
	initialValue,
	namespace,
}) {
	const addressTypeSelect = document.getElementById(
		`${namespace}addressListTypeId`
	);

	const initAutocomplete = (
		autoload,
		externalReferenceCode,
		initialLabel,
		initialValue,
		readOnly
	) => {
		Autocomplete('autocomplete', 'autocomplete-root', {
			apiUrl: `/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}/list-type-entries`,
			autoload,
			initialLabel,
			initialValue,
			inputId: `${namespace}subtype`,
			inputName: `${namespace}subtype`,
			inputPlaceholder: 'Subtype',
			itemsKey: 'key',
			itemsLabel: 'name',
			readOnly,
			required: false,
		});
	};

	const getExternalReferenceCode = (type) => {
		if (type === 'billing') {
			return addressSubtypeConfiguration.billing;
		}
		else if (type === 'shipping') {
			return addressSubtypeConfiguration.shipping;
		}

		return addressSubtypeConfiguration.billingAndShipping;
	};

	let addressType = initialAddressType || 'billing-and-shipping';
	let externalReferenceCode = getExternalReferenceCode(addressType);

	addressTypeSelect.addEventListener('change', (event) => {
		addressType = Array.from(event.target.children).filter(
			(item) => item.value === event.target.value
		)[0].dataset.listtypekey;

		externalReferenceCode = getExternalReferenceCode(addressType);

		if (externalReferenceCode) {
			initAutocomplete(true, externalReferenceCode, '', '', false);
		}
		else {
			initAutocomplete(false, '', '', '', true);
		}
	});

	if (externalReferenceCode) {
		initAutocomplete(
			true,
			externalReferenceCode,
			initialLabel,
			initialValue,
			false
		);
	}
	else {
		initAutocomplete(false, '', '', '', true);
	}
}
