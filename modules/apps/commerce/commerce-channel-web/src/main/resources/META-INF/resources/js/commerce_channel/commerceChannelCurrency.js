/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function addOpenSelectionModalEvent({namespace, url}) {
	Liferay.on(namespace + 'addCommerceChannelCurrency', () => {
		openSelectionModal({
			namespace,
			url,
		});
	});

	Liferay.on('destroyPortlet', () => {
		Liferay.detach(namespace + 'addCommerceChannelCurrency');
	});
}

function openSelectionModal({namespace, url}) {
	const openerWindow = Liferay.Util.getOpener();

	const addCommerceChannelCurrencyFm = document.getElementById(
		namespace + 'addCommerceChannelCurrencyFm'
	);
	const currencyIds = document.getElementById(namespace + 'currencyIds');

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

			const currencies = [];

			for (let selectedItem of selectedItems) {
				selectedItem = JSON.parse(selectedItem);

				currencies.push(selectedItem.classPK);
			}

			if (currencies.length && currencyIds) {
				currencyIds.value = currencies.join(',');
			}

			if (addCommerceChannelCurrencyFm) {
				submitForm(addCommerceChannelCurrencyFm);
			}
		},
		selectEventName: 'currencySelectItem',
		title: Liferay.Language.get('add-currency'),
		url,
	});
}

export default function (context) {
	addOpenSelectionModalEvent(context);
}
