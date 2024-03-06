/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSpritemap} from '@liferay/frontend-icons-web';
import {openConfirmModal, openModal} from 'frontend-js-web';

import {openShareFormModal} from './components/share-form/openShareFormModal.es';

const ACTIONS = {
	delete({deleteFormInstanceURL}) {
		openConfirmModal({
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
