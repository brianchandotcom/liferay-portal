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
	baseSelectDefaultCommerceTermEntryURL,
	baseUpdateAccountEntryDefaultCommerceTermEntryURL,
	defaultCommerceTermEntriesContainerId,
}) {
	const defaultCommerceTermEntriesContainer = document.getElementById(
		defaultCommerceTermEntriesContainerId
	);

	const getTitle = (type) => {
		if (type === 'payment') {
			return Liferay.Language.get(
				'set-default-payment-commerce-terms-entry'
			);
		}

		if (type === 'delivery') {
			return Liferay.Language.get(
				'set-default-delivery-commerce-terms-entry'
			);
		}

		return '';
	};

	const openSelectionModal = (title, type) => {
		Liferay.Util.openSelectionModal({
			buttonAddLabel: Liferay.Language.get('save'),
			id: '<portlet:namespace />selectDefaultCommerceTermEntry',
			multiple: true,
			onSelect: (selectedItem) => {
				if (!selectedItem) {
					return;
				}

				const updateAccountEntryDefaultCommerceTermEntryURL = Liferay.Util.PortletURL.createPortletURL(
					baseUpdateAccountEntryDefaultCommerceTermEntryURL,
					{commerceTermEntryId: selectedItem.entityid, type}
				);

				submitForm(
					document.hrefFm,
					updateAccountEntryDefaultCommerceTermEntryURL.toString()
				);
			},
			selectEventName:
				'<portlet:namespace />selectDefaultCommerceTermEntry',
			title,
			url: Liferay.Util.PortletURL.createPortletURL(
				baseSelectDefaultCommerceTermEntryURL,
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
		defaultCommerceTermEntriesContainer,
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
