/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import CopyFragmentModal from './CopyFragmentModal';

const ACTIONS = {
	copyContributedEntryToFragmentCollection(
		itemData,
		portletNamespace,
		fragmentCollections
	) {
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
