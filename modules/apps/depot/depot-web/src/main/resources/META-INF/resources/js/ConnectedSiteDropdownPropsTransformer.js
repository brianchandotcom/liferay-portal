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
