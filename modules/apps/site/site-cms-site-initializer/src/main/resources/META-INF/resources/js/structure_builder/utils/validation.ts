/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import {useCallback} from 'react';

import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureFields from '../selectors/selectStructureFields';
import {Field} from './field';

export type ValidationError = 'no-erc' | 'no-label' | 'no-name';

export function validateField({
	currentErrors,
	data,
}: {
	currentErrors?: Set<ValidationError>;
	data: {
		erc?: Field['erc'];
		label?: Field['label'];
		name?: Field['name'];
		picklistId?:
			| SingleSelectField['picklistId']
			| MultiselectField['picklistId'];
	};
}): Set<ValidationError> {
	const {erc, label, name} = data;

	const errors = new Set(currentErrors);

	if (!isNullOrUndefined(erc)) {
		erc ? errors.delete('no-erc') : errors.add('no-erc');
	}

	if (!isNullOrUndefined(name)) {
		name ? errors.delete('no-name') : errors.add('no-name');
	}

	if (!isNullOrUndefined(label)) {
		Object.values(label ?? {}).every(Boolean)
			? errors.delete('no-label')
			: errors.add('no-label');
	}

	return errors;
}

export function validateStructure({
	currentErrors,
	data,
}: {
	currentErrors?: Set<ValidationError>;
	data: Partial<State>;
}): Set<ValidationError> {
	const {erc, label, name} = data;

	const errors = new Set(currentErrors);

	if (!isNullOrUndefined(erc)) {
		erc ? errors.delete('no-erc') : errors.add('no-erc');
	}

	if (!isNullOrUndefined(name)) {
		name ? errors.delete('no-name') : errors.add('no-name');
	}

	if (!isNullOrUndefined(label)) {
		Object.values(label ?? {}).every(Boolean)
			? errors.delete('no-label')
			: errors.add('no-label');
	}

	return errors;
}

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
