/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

export default function ErrorFeedback() {
	return (
		<div className="error-container">
			<ClayAlert
				displayType="danger"
				title={`${Liferay.Language.get('workflow-instance-not-found')}`}
				variant="feedback"
			/>
		</div>
	);
}
