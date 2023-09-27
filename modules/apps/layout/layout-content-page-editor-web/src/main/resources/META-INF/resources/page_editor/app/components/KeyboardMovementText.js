/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';

import {
	useMovementText,
	useSetMovementText,
} from '../contexts/KeyboardMovementContext';

export default function KeyboardMovementText() {
	const text = useMovementText();
	const setText = useSetMovementText();

	useEffect(() => {
		const timeout = setTimeout(() => setText(''), 1000);

		return () => {
			clearTimeout(timeout);
		};
	}, [text, setText]);

	return (
		<span aria-live="assertive" className="sr-only">
			{text}
		</span>
	);
}
