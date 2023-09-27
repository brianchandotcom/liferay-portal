/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export function withLoading(Component) {
	return ({loading, ...restProps}) => {
		if (loading) {
			return (
				<div className="align-items-center d-flex w-100">
					<ClayLoadingIndicator />
				</div>
			);
		}

		return <Component {...restProps} />;
	};
}

export const Loading = withLoading(({children}) => <>{children}</>);
