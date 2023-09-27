/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactElement} from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {SWRConfig} from 'swr';

import baseFetcher from '../services/fetcher';

type PageWrapperProps = {
	children: ReactElement;
	clearCache?: boolean;
	customRoutes?: ReactElement;
	fetcher?: () => any;
};

const PageWrapper: React.FC<PageWrapperProps> = ({
	children,
	clearCache,
	customRoutes,
	fetcher = baseFetcher,
}) => {
	return (
		<SWRConfig
			value={{
				fetcher,
				provider: clearCache ? () => new Map() : undefined,
			}}
		>
			<BrowserRouter>
				<Routes>
					<Route element={children} path="/" />

					{customRoutes}
				</Routes>
			</BrowserRouter>
		</SWRConfig>
	);
};

export default PageWrapper;
