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

import {render} from '@liferay/frontend-js-react-web';
import {getCheckedCheckboxes, openSelectionModal} from 'frontend-js-web';

import CopyFragmentModal from './CopyFragmentModal';

export default function propsTransformer({
	additionalProps: {
		addFragmentCollectionURL,
		copyContributedEntryURL,
		fragmentCollections,
		selectFragmentCollectionURL,
	},
	portletNamespace,
	...otherProps
}) {
	const copyContributedEntriesToFragmentCollection = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const contributedEntryKeys = getCheckedCheckboxes(
			form,
			`${portletNamespace}allRowIds`
		);

		if (Liferay.FeatureFlags['LPS-166203']) {
			render(
				CopyFragmentModal,
				{
					addFragmentCollectionURL,
					contributedEntryKeys: contributedEntryKeys.split(','),
					copyFragmentEntriesURL: copyContributedEntryURL,
					fragmentCollections,
					portletNamespace,
				},
				document.createElement('div')
			);
		}
		else {
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

						const contributedEntryKeysElement = document.getElementById(
							`${portletNamespace}contributedEntryKeys`
						);

						if (contributedEntryKeysElement) {
							contributedEntryKeysElement.setAttribute(
								'value',
								contributedEntryKeys
							);
						}

						const form = document.getElementById(
							`${portletNamespace}fragmentEntryFm`
						);

						if (form) {
							submitForm(form, copyContributedEntryURL);
						}
					}
				},
				selectEventName: `${portletNamespace}selectFragmentCollection`,
				title: Liferay.Language.get('select-fragment-set'),
				url: selectFragmentCollectionURL,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (
				item?.data?.action ===
				'copyContributedEntriesToFragmentCollection'
			) {
				copyContributedEntriesToFragmentCollection();
			}
		},
	};
}
