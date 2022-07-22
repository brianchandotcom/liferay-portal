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

import {getSpritemap} from '@liferay/frontend-icons-web';
import {openModal} from 'frontend-js-web';

import {openShareFormModal} from './components/share-form/openShareFormModal.es';
import openConfirm from './openConfirm';

const ACTIONS = {
	delete({deleteFormInstanceURL}) {
		openConfirm({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirm) =>
				isConfirm && submitForm(document.hrefFm, deleteFormInstanceURL),
		});
	},

	exportForm({exportFormURL}) {
		Liferay.fire('openExportFormModal', {exportFormURL});
	},

	permissions({permissionsFormInstanceURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsFormInstanceURL,
		});
	},

	shareForm({
		autocompleteUserURL,
		localizedName,
		portletNamespace,
		shareFormInstanceURL,
		url,
	}) {
		openShareFormModal({
			autocompleteUserURL,
			localizedName,
			portletNamespace,
			shareFormInstanceURL,
			spritemap: getSpritemap(),
			url,
		});
	},
};

export default function propsTransformer({items: groups, ...props}) {
	return {
		...props,
		items: groups.map((group) => {
			return {
				...group,
				items: group.items?.map((item) => ({
					...item,
					onClick(event) {
						const action = item.data?.action;

						if (action) {
							event.preventDefault();

							ACTIONS[action](item.data);
						}
					},
				})),
			};
		}),
	};
}
