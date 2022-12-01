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
import {openSelectionModal} from 'frontend-js-web';

import CopyFragmentModal from './CopyFragmentModal';

const ACTIONS = {
	copyContributedEntryToFragmentCollection(
		itemData,
		portletNamespace,
		fragmentCollections
	) {
		if (Liferay.FeatureFlags['LPS-166203']) {
			render(
				CopyFragmentModal,
				{
					addFragmentCollectionURL: itemData.addFragmentCollectionURL,
					contributedEntryKeys: [itemData.contributedEntryKey],
					copyFragmentEntriesURL: itemData.copyContributedEntryURL,
					fragmentCollections,
					portletNamespace,
				},
				document.createElement('div')
			);
		}
		else {
			openSelectionModal({
				id: `${portletNamespace}selectFragmentCollection`,
				onSelect: (selectedItem) => {
					if (selectedItem) {
						document.getElementById(
							`${portletNamespace}contributedEntryKeys`
						).value = itemData.contributedEntryKey;
						document.getElementById(
							`${portletNamespace}fragmentCollectionId`
						).value = selectedItem.id;

						submitForm(
							document.getElementById(
								`${portletNamespace}fragmentEntryFm`
							),
							itemData.copyContributedEntryURL
						);
					}
				},
				selectEventName: `${portletNamespace}selectFragmentCollection`,
				title: Liferay.Language.get('select-fragment-set'),
				url: itemData.selectFragmentCollectionURL,
			});
		}
	},
};

export default function propsTransformer({
	actions,
	additionalProps: {fragmentCollections},
	portletNamespace,
	...props
}) {
	const transformAction = (actionItem) => {
		if (actionItem.type === 'group') {
			return {
				...actionItem,
				items: actionItem.items?.map(transformAction),
			};
		}

		return {
			...actionItem,
			onClick(event) {
				const action = actionItem.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action](
						actionItem.data,
						portletNamespace,
						fragmentCollections
					);
				}
			},
		};
	};

	return {
		...props,
		actions: (actions || []).map(transformAction),
	};
}
