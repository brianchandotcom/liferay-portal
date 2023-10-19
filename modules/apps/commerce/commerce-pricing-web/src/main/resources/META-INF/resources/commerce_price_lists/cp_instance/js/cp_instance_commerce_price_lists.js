/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function addOpenSelectionModalEvent({
	eventNames,
	keys,
	namespace,
	titles,
	url,
	urls,
}) {
	if (Liferay.FeatureFlags['COMMERCE-11287']) {
		Liferay.on(namespace + 'addCommercePriceEntry', () => {
			openSelectionModal({
				namespace,
				url,
			});
		});

		Liferay.on('destroyPortlet', () => {
			Liferay.detach(namespace + 'addCommercePriceEntry');
		});
	}
	else {
		for (let i = 0; i < keys.length; i++) {
			Liferay.on(namespace + eventNames[i], () => {
				openSelectionModal({
					key: keys[i],
					namespace,
					title: titles[i],
					url: urls[i],
				});
			});
		}
		Liferay.on('destroyPortlet', () => {
			for (let i = 0; i < keys.length; i++) {
				Liferay.detach(namespace + eventNames[i]);
			}
		});
	}
}

function openSelectionModal({key, namespace, title, url}) {
	const openerWindow = Liferay.Util.getOpener();

	if (Liferay.FeatureFlags['COMMERCE-11287']) {
		return openerWindow.Liferay.Util.openModal({
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					formId: `${namespace}addPriceEntryForm`,
					label: Liferay.Language.get('add'),
					type: 'submit',
				},
			],
			containerProps: {
				className: 'add-price-entry-modal modal-height-lg',
			},
			id: `${namespace}addPriceEntryDialog`,
			iframeBodyCssClass: '',
			size: 'lg',
			title: Liferay.Language.get('add-price'),
			url,
		});
	}
	else {
		const addCommercePriceEntryFm = document.getElementById(
			namespace + 'addCommercePriceEntryFm'
		);
		const commercePriceListIds = document.getElementById(
			namespace + 'commercePriceListIds'
		);
		const unitOfMeasureKeyInput = document.getElementById(
			namespace + 'unitOfMeasureKey'
		);

		openerWindow.Liferay.Util.openSelectionModal({
			multiple: true,
			onSelect: (selectedItems) => {
				if (!selectedItems || !selectedItems.length) {
					return;
				}
				if (commercePriceListIds) {
					commercePriceListIds.value = selectedItems;
				}
				if (key && unitOfMeasureKeyInput) {
					unitOfMeasureKeyInput.value = key;
				}
				if (addCommercePriceEntryFm) {
					submitForm(addCommercePriceEntryFm);
				}
			},
			selectEventName: 'priceListsSelectItem',
			title,
			url,
		});
	}
}

export default function (context) {
	addOpenSelectionModalEvent(context);
}
