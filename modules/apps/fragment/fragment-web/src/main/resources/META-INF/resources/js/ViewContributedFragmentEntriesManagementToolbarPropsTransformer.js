/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {getCheckedCheckboxes} from 'frontend-js-web';

import CopyFragmentModal from './CopyFragmentModal';

export default function propsTransformer({
	additionalProps: {
		addFragmentCollectionURL,
		copyContributedEntryURL,
		fragmentCollections,
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
