/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {
	CONTROL_KEY_CODE,
	ESCAPE_KEY_CODE,
	META_KEY_CODE,
} from '../../config/constants/keyboardCodes';

export function useMultiSelectReady() {
	const [multiSelectReady, setMultiSelectReady] = useState(false);

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPD-18221']) {
			return;
		}

		const isMultiSelectReady = (event) => {
			return (
				event.key === CONTROL_KEY_CODE || event.key === META_KEY_CODE
			);
		};

		const onKeydown = (event) => {
			if (isMultiSelectReady(event)) {
				setMultiSelectReady(true);
			}

			if (event.key === ESCAPE_KEY_CODE) {
				setMultiSelectReady(false);
			}
		};

		const onKeyup = (event) => {
			if (isMultiSelectReady(event)) {
				setMultiSelectReady(false);
			}
		};

		window.addEventListener('keydown', onKeydown, true);
		window.addEventListener('keyup', onKeyup, true);

		return () => {
			window.removeEventListener('keydown', onKeydown, true);
			window.removeEventListener('keyup', onKeyup, true);
		};
	}, [setMultiSelectReady]);

	return multiSelectReady;
}
