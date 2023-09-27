/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const LoadingState = ({
	className = 'border-1 pb-6 pt-6 sheet',
	message,
	messageClassName = '',
}) => (
	<div className={className}>
		<span aria-hidden="true" className="loading-animation" />

		{message && (
			<span className={`text-center text-truncate ${messageClassName}`}>
				{message}
			</span>
		)}
	</div>
);

export default LoadingState;
