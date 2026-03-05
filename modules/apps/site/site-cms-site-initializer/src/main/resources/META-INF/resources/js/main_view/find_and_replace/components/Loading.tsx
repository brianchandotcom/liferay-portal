/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import React from 'react';

export function Loading() {
	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('find-and-replace')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayLoadingIndicator className="text-secondary" />

				<p className="text-secondary">
					{Liferay.Language.get(
						'scanning-content-may-take-some-time.-closing-the-window-will-interrupt-the-process'
					)}
				</p>
			</ClayModal.Body>
		</>
	);
}
