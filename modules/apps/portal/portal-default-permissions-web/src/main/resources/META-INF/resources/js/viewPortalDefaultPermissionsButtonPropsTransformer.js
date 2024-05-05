/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export default function propsTransformer({additionalProps, ...props}) {
	return {
		...props,
		onClick() {
			openModal({
				size: 'full-screen',
				title: Liferay.Language.get('edit-default-permissions'),
				url: additionalProps.url,
			});
		},
	};
}
