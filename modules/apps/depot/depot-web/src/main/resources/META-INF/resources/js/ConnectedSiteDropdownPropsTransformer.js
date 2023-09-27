/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, openConfirmModal} from 'frontend-js-web';

const ACTIONS = {
	disconnect({url: disconnectSiteActionURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'removing-this-site-connection-will-not-allow-the-site-to-consume-data-from-this-asset-library-directly'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, disconnectSiteActionURL);
				}
			},
		});
	},
	shareWebContentStructures({
		shared,
		url: updateDDMStructuresAvailableActionURL,
	}) {
		const message = shared
			? Liferay.Language.get(
					'after-disabling-structure-and-document-type-sharing,-any-site-content-that-uses-the-structures-or-document-types-will-be-invalid.-do-you-want-to-disable-structure-and-document-type-sharing'
			  )
			: Liferay.Language.get(
					'you-will-not-be-able-to-disconnect-this-site-when-structure-and-document-type-sharing-is-enabled.-in-order-to-disconnect-this-site-from-this-asset-library,-you-must-disable-structure-and-document-type-sharing-first'
			  );

		openConfirmModal({
			message,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					navigate(updateDDMStructuresAvailableActionURL);
				}
			},
		});
	},
};

export default function propsTransformer({items, ...otherProps}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data);
					}
				},
			};
		}),
	};
}
