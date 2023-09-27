/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';

export default function useSetRef(ref) {
	const [element, setElement] = useState(null);

	const setRef = useCallback(
		(node) => {
			setElement(node);

			if (ref) {
				ref.current = node;
			}
		},
		[ref]
	);

	return [setRef, element];
}
