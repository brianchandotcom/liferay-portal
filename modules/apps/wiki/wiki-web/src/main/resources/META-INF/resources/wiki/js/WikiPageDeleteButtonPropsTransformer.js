/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

export default function propsTransformer(props) {
	return {
		...props,
		onClick() {
			const trashEnabled = props['data-trash-enabled'];

			if (trashEnabled) {
				submitForm(document.hrefFm, props['data-url']);
			}
			else {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							submitForm(document.hrefFm, props['data-url']);
						}
					},
				});
			}
		},
	};
}
