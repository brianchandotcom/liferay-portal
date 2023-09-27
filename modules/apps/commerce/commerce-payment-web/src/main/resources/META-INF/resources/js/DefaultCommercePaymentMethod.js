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
	baseSelectDefaultCommercePaymentMethodURL,
	baseUpdateAccountEntryDefaultCommercePaymentMethodURL,
	defaultCommercePaymentMethodContainerId,
}) {
	const defaultCommercePaymentMethodsContainer = document.getElementById(
		defaultCommercePaymentMethodContainerId
	);

	const getTitle = () => {
		return Liferay.Language.get('set-default-commerce-payment-method');
	};

	const openSelectionModal = (title) => {
		openSelectionModalUtil({
			buttonAddLabel: Liferay.Language.get('save'),
			id: '<portlet:namespace />selectDefaultCommercePaymentMethod',
			multiple: true,
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				const updateAccountEntryDefaultCommercePaymentMethodURL = createPortletURL(
					baseUpdateAccountEntryDefaultCommercePaymentMethodURL,
					{commercePaymentMethodKey: selectedItem.entityid}
				);

				submitForm(
					document.hrefFm,
					updateAccountEntryDefaultCommercePaymentMethodURL.toString()
				);
			},
			selectEventName:
				'<portlet:namespace />selectDefaultCommercePaymentMethod',
			title,
			url: createPortletURL(baseSelectDefaultCommercePaymentMethodURL),
		});
	};

	const onClick = (event) => {
		event.preventDefault();

		openSelectionModal(getTitle());
	};

	const clickDelegate = delegate(
		defaultCommercePaymentMethodsContainer,
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
