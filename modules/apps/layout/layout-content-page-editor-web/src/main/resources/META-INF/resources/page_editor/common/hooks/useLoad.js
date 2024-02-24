/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import React from 'react';

const {useCallback, useRef} = React;

/**
 * Provides a way to load a module on demand.
 *
 * The returned `load()` function expects an identifying `key` for
 * the module and an entry point (ie. path to the module), and returns a
 * promise that resolves to the loaded module's default export.
 */
export default function useLoad() {
	const modulesRef = useRef(new Map());

	const isMounted = useIsMounted();

	return useCallback(
		(key, pluginClass) => {
			if (!modulesRef.current.get(key)) {
				modulesRef.current.set(key, pluginClass);
			}

			return Promise.resolve(modulesRef.current.get(key));
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[isMounted]
	);
}
