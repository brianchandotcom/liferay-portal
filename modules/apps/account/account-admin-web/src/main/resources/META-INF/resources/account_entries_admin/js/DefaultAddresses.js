/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createPortletURL,
	delegate,
	openSelectionModal as openSelectionModalUtil,
} from 'frontend-js-web';

export default function ({
	baseSelectDefaultAddressURL,
	baseUpdateAccountEntryDefaultAddressesURL,
	defaultAddressesContainerId,
}) {
	const defaultAddressesContainer = document.getElementById(
		defaultAddressesContainerId
	);

	const getTitle = (type) => {
		if (type === 'billing') {
			return Liferay.Language.get('set-default-billing-address');
		}

		if (type === 'shipping') {
			return Liferay.Language.get('set-default-shipping-address');
		}

		return '';
	};

	const openSelectionModal = (title, type) => {
		openSelectionModalUtil({
			buttonAddLabel: Liferay.Language.get('save'),
			containerProps: {
				className: '',
			},
			id: '<portlet:namespace />selectDefaultAddress',
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				const updateAccountEntryDefaultAddressesURL = createPortletURL(
					baseUpdateAccountEntryDefaultAddressesURL,
					{addressId: selectedItem.entityid, type}
				);

				submitForm(
					document.hrefFm,
					updateAccountEntryDefaultAddressesURL.toString()
				);
			},
			selectEventName: '<portlet:namespace />selectDefaultAddress',
			title,
			url: createPortletURL(baseSelectDefaultAddressURL, {type}),
		});
	};

	const onClick = (event) => {
		event.preventDefault();

		const target = event.target.closest('a.btn');

		const {type} = target.dataset;

		openSelectionModal(getTitle(type), type);
	};

	const clickDelegate = delegate(
		defaultAddressesContainer,
		'click',
		'.modify-link',
		onClick
	);

	return {
		dispose() {
			clickDelegate.dispose();
		},
	};
}
