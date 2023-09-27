/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, lazy} from 'react';

import useLoader from './useLoader.es';

export default function useLazy(hideLoading) {
	const load = useLoader();

	return ({module, props}) => {
		const Component = lazy(() => load(module));

		return (
			<Suspense fallback={hideLoading ? <></> : <ClayLoadingIndicator />}>
				<Component {...props} />
			</Suspense>
		);
	};
}
