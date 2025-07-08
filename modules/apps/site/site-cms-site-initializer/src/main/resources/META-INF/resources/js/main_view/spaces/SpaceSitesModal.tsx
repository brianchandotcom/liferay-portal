/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import React from 'react';

export default function SpaceSitesModal() {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('all-sites')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>{Liferay.Language.get('connected-sites')}</p>
			</ClayModal.Body>
		</>
	);
}
