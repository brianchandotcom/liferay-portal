/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function addOpenSelectionModalEvent({namespace, url}) {
	Liferay.on(namespace + 'addCommerceChannelCountry', () => {
		openSelectionModal({
			namespace,
			url,
		});
	});

	Liferay.on('destroyPortlet', () => {
		Liferay.detach(namespace + 'addCommerceChannelCountry');
	});
}

function openSelectionModal({namespace, url}) {
	const openerWindow = Liferay.Util.getOpener();

	const addCommerceChannelCountryFm = document.getElementById(
		namespace + 'addCommerceChannelCountryFm'
	);
	const countryIds = document.getElementById(namespace + 'countryIds');

	openerWindow.Liferay.Util.openSelectionModal({
		multiple: true,
		onSelect: (selectedItems) => {
			selectedItems = selectedItems.value || [];

			if (!selectedItems || !selectedItems.length) {
				return;
			}

			if (!Array.isArray(selectedItems)) {
				selectedItems = [selectedItems];
			}

			const countries = [];

			for (let selectedItem of selectedItems) {
				selectedItem = JSON.parse(selectedItem);

				countries.push(selectedItem.classPK);
			}

			if (countries.length && countryIds) {
				countryIds.value = countries.join(',');
			}

			if (addCommerceChannelCountryFm) {
				submitForm(addCommerceChannelCountryFm);
			}
		},
		selectEventName: 'countrySelectItem',
		title: Liferay.Language.get('add-country'),
		url,
	});
}

export default function (context) {
	addOpenSelectionModalEvent(context);
}
