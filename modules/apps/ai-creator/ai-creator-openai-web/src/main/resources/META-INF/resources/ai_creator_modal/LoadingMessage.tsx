/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export function LoadingMessage() {
	return (
		<div className="c-p-6 text-center" role="alert">
			<ClayLoadingIndicator
				className="c-mb-5"
				displayType="primary"
				shape="squares"
				size="lg"
			/>

			<p className="c-mb-0">{Liferay.Language.get('creating-content')}</p>

			<p>{Liferay.Language.get('this-process-may-take-a-while')}</p>
		</div>
	);
}
