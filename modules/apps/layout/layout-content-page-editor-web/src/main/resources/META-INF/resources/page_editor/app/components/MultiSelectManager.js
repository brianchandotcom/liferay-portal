/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import {
	CONTROL_KEY_CODE,
	ENTER_KEY_CODE,
	META_KEY_CODE,
} from '../config/constants/keyboardCodes';
import {
	useActivateMultiSelect,
	useMultiSelectIsActivated,
} from '../contexts/ControlsContext';

const ctrlOrMeta = (event) =>
	(event.ctrlKey && !event.metaKey) || (!event.ctrlKey && event.metaKey);

export default function MultiSelectManager() {
	const multiSelectIsActivated = useMultiSelectIsActivated();
	const activateMultiSelect = useActivateMultiSelect();

	useEffect(() => {
		const multiSelection = (event) => {
			if (ctrlOrMeta(event) && !multiSelectIsActivated) {
				activateMultiSelect(true);
			}
		};

		const onClick = (event) => {
			multiSelection(event);
		};

		const onKeydown = (event) => {
			if (event.key === ENTER_KEY_CODE) {
				multiSelection(event);
			}
		};

		const onKeyup = (event) => {
			if (
				multiSelectIsActivated &&
				(event.key === CONTROL_KEY_CODE || event.key === META_KEY_CODE)
			) {
				activateMultiSelect(false);
			}
		};

		window.addEventListener('click', onClick, true);
		window.addEventListener('keydown', onKeydown, true);
		window.addEventListener('keyup', onKeyup, true);

		return () => {
			window.removeEventListener('click', onClick, true);
			window.removeEventListener('keydown', onKeydown, true);
			window.removeEventListener('keyup', onKeyup, true);
		};
	}, [multiSelectIsActivated, activateMultiSelect]);

	return null;
}
