/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import React from 'react';

export default function DesignLibraryManageMembersModal({
	externalReferenceCode,
}: {
	externalReferenceCode: string;
}) {
	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('manage-members')}
			</ClayModal.Header>

			<ClayModal.Body>modal body: {externalReferenceCode}</ClayModal.Body>
		</>
	);
}
