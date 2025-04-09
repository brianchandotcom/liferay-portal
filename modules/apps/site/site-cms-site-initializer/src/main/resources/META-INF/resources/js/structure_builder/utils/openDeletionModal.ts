/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from '@liferay/layout-js-components-web';

export default function openDeletionModal() {
	openConfirmModal({
		buttonLabel: Liferay.Language.get('done'),
		center: true,
		hideCancel: true,
		status: 'warning',
		text: Liferay.Language.get(
			'fields-cannot-be-deleted-because-the-structure-requires-at-least-one-field'
		),
		title: Liferay.Language.get('deletion-not-allowed'),
	});
}
