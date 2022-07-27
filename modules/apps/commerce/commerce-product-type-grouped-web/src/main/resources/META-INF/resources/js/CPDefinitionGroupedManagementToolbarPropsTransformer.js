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

import {openConfirmModal, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...props}) {
	return {
		...props,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteEntries') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-selected-entries'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (!form) {
								return;
							}

							submitForm(form);
						}
					},
				});
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item?.data;

			openSelectionModal({
				id: `${portletNamespace}addDefinitionGroupedEntry`,
				multiple: true,
				onSelect: (selectedItems) => {
					if (selectedItems && selectedItems.length) {
						const entryCPDefinitionIds = document.getElementById(
							`${portletNamespace}entryCPDefinitionIds`
						);

						if (entryCPDefinitionIds) {
							entryCPDefinitionIds.value = selectedItems.map(
								(item) => item.value
							);
						}

						const addCPDefinitionGroupedEntryFm = document.getElementById(
							`${portletNamespace}addCPDefinitionGroupedEntryFm`
						);

						if (addCPDefinitionGroupedEntryFm) {
							submitForm(addCPDefinitionGroupedEntryFm);
						}
					}
				},
				selectEventName: `${portletNamespace}selectCPDefinition`,
				title: data?.dialogTitle,
				url: data?.addDefinitionGroupedEntryItemSelectorURL,
			});
		},
	};
}
