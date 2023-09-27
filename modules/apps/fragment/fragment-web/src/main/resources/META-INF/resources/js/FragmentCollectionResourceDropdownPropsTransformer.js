/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteFragmentCollectionResourceModal from './openDeleteFragmentCollectionResourceModal';

export default function propsTransformer({actions, ...props}) {
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
				if (
					actionItem.data?.action ===
					'deleteFragmentCollectionResource'
				) {
					event.preventDefault();

					openDeleteFragmentCollectionResourceModal({
						onDelete: () => {
							submitForm(
								document.hrefFm,
								actionItem.data
									?.deleteFragmentCollectionResourceURL
							);
						},
					});
				}
			},
		};
	};

	return {
		...props,
		actions: (actions || []).map(transformAction),
	};
}
