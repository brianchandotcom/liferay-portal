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
