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

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		copyContributedFragmentEntryURL,
		selectFragmentCollectionURL,
	},
	portletNamespace,
	...otherProps
}) {
	const copyToSelectedContributedFragmentEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const fragmentEntryKeys = Liferay.Util.listCheckedExcept(
			form,
			`${portletNamespace}allRowIds`
		);

		openSelectionModal({
			id: `${portletNamespace}selectFragmentCollection`,
			onSelect(selectedItem) {
				if (selectedItem) {
					const fragmentCollectionIdElement = document.getElementById(
						`${portletNamespace}fragmentCollectionId`
					);

					if (fragmentCollectionIdElement) {
						fragmentCollectionIdElement.setAttribute(
							'value',
							selectedItem.id
						);
					}

					const fragmentEntryKeysElement = document.getElementById(
						`${portletNamespace}fragmentEntryKeys`
					);

					if (fragmentEntryKeysElement) {
						fragmentEntryKeysElement.setAttribute(
							'value',
							fragmentEntryKeys
						);
					}

					const form = document.getElementById(
						`${portletNamespace}fragmentEntryFm`
					);

					if (form) {
						submitForm(form, copyContributedFragmentEntryURL);
					}
				}
			},
			selectEventName: `${portletNamespace}selectFragmentCollection`,
			title: Liferay.Language.get('select-collection'),
			url: selectFragmentCollectionURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'copyToSelectedContributedFragmentEntries') {
				copyToSelectedContributedFragmentEntries();
			}
		},
	};
}
