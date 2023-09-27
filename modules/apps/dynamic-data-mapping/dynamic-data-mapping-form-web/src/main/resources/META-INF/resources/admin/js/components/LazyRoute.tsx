/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {lazy, useMemo} from 'react';
import {Route} from 'react-router-dom';

export default function LazyRoute({
	importPath,
	...otherProps
}: {
	exact?: boolean;
	importPath: string;
	path: string;
}) {
	const Component = useMemo(
		() =>
			lazy(
				() =>
					new Promise((resolve, reject) => {

						// @ts-ignore

						Liferay.Loader.require(
							[importPath],

							// @ts-ignore

							(Component) => resolve(Component),

							// @ts-ignore

							(error) => reject(error as Error)
						);
					})
			),
		[importPath]
	);

	return (
		<Route
			{...otherProps}
			render={(props: any) => <Component {...props} />}
		/>
	);
}
