/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectInvalids from '../selectors/selectInvalids';
import selectSelection from '../selectors/selectSelection';
import selectStructureFields from '../selectors/selectStructureFields';
import focusInvalidInput from './focusInvalidInput';

export function useValidate() {
	const dispatch = useStateDispatch();
	const fields = useSelector(selectStructureFields);
	const invalids = useSelector(selectInvalids);
	const selection = useSelector(selectSelection);

	return useCallback(() => {

		// Check at least one field is added

		if (!fields.length) {
			dispatch({
				error: Liferay.Language.get(
					'at-least-one-field-must-be-added-to-save-or-publish-the-structure'
				),
				type: 'set-error',
			});

			return false;
		}

		// Check whether some item is invalid and select it

		if (invalids.size) {
			const [uuid] = [...invalids];

			const isSelected =
				selection.length === 1 && selection.includes(uuid);

			if (isSelected) {
				focusInvalidInput();
			}
			else {
				dispatch({
					selection: [uuid],
					type: 'set-selection',
				});
			}

			return false;
		}

		// It's valid

		return true;
	}, [dispatch, fields, invalids, selection]);
}
