/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useCallback, useRef} from 'react';

const EmptyModule = {default: () => null};

export default function useLoader() {
	const modulesRef = useRef(new Map());
	const isMounted = useIsMounted();

	return useCallback(
		(module) => {
			if (!modulesRef.current.has(module)) {
				modulesRef.current.set(
					module,
					new Promise((resolve) => {
						Liferay.Loader.require(
							[module],
							(Module) => {
								if (isMounted()) {
									resolve(Module ? Module : EmptyModule);
								}
							},
							(error) => {
								if (process.env.NODE_ENV === 'development') {
									console.error(error);
								}

								if (isMounted()) {
									resolve(EmptyModule);
								}
							}
						);
					})
				);
			}

			return modulesRef.current.get(module);
		},
		[isMounted]
	);
}
