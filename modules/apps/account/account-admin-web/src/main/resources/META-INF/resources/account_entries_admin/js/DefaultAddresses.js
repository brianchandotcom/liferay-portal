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

import {delegate} from 'frontend-js-web';

export default function ({
	baseSelectDefaultAddressURL,
	baseUpdateAccountEntryDefaultAddressesURL,
	defaultAddressesContainerId,
}) {
	const defaultAddressesContainer = document.getElementById(
		defaultAddressesContainerId
	);

	const getTitle = (type) => {
		if (type == 'billing') {
			return Liferay.Language.get('set-default-billing-address');
		}

		if (type == 'shipping') {
			return Liferay.Language.get('set-default-shipping-address');
		}

		return '';
	};

	const openSelectionModal = (title, type) => {
		Liferay.Util.openSelectionModal({
			buttonAddLabel: Liferay.Language.get('save'),
			id: '<portlet:namespace />selectDefaultAddress',
			multiple: true,
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				const updateAccountEntryDefaultAddressesURL = Liferay.Util.PortletURL.createPortletURL(
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
			url: Liferay.Util.PortletURL.createPortletURL(
				baseSelectDefaultAddressURL,
				{type}
			),
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
