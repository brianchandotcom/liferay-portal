/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import {useCallback} from 'react';

import focusInvalidElement from '../../common/utils/focusInvalidElement';
import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectState from '../selectors/selectState';
import selectStructureChildren from '../selectors/selectStructureChildren';
import {RepeatableGroup, Structure, StructureChild} from '../types/Structure';
import {Field, MultiselectField, SingleSelectField} from './field';

export type ValidationProperty =
	| 'erc'
	| 'global'
	| 'name'
	| 'label'
	| 'max-length'
	| 'picklist'
	| 'spaces';

export type ValidationError = 'empty' | 'unexpected';

export type ErrorMap = Map<ValidationProperty, ValidationError>;

export function validateField({
	currentErrors,
	data,
}: {
	currentErrors?: ErrorMap;
	data: {
		erc?: Field['erc'];
		label?: Field['label'];
		name?: Field['name'];
		picklistId?:
			| SingleSelectField['picklistId']
			| MultiselectField['picklistId'];
		settings?: Field['settings'];
	};
}): ErrorMap {
	const {erc, label, name, picklistId, settings} = data;

	const errors = new Map(currentErrors);

	if (!isNullOrUndefined(erc)) {
		erc ? errors.delete('erc') : errors.set('erc', 'empty');
	}

	if (!isNullOrUndefined(name)) {
		name ? errors.delete('name') : errors.set('name', 'empty');
	}

	if (!isNullOrUndefined(label)) {
		Object.values(label ?? {}).every(Boolean)
			? errors.delete('label')
			: errors.set('label', 'empty');
	}

	if (!isNullOrUndefined(picklistId)) {
		picklistId
			? errors.delete('picklist')
			: errors.set('picklist', 'empty');
	}

	if (
		settings &&
		'maxLength' in settings &&
		!isNullOrUndefined(settings.maxLength)
	) {
		settings.maxLength
			? errors.delete('max-length')
			: errors.set('max-length', 'empty');
	}

	return errors;
}

export function validateRepeatableGroup({
	currentErrors,
	data,
}: {
	currentErrors?: ErrorMap;
	data: Partial<RepeatableGroup>;
}): ErrorMap {
	const {label} = data;

	const errors = new Map(currentErrors);

	if (!isNullOrUndefined(label)) {
		Object.values(label ?? {}).every(Boolean)
			? errors.delete('label')
			: errors.set('label', 'empty');
	}

	return errors;
}

export function validateStructure({
	currentErrors,
	data,
}: {
	currentErrors?: ErrorMap;
	data: Partial<Structure>;
}): ErrorMap {
	const {erc, label, name, spaces} = data;

	const errors = new Map(currentErrors);

	if (!isNullOrUndefined(erc)) {
		erc ? errors.delete('erc') : errors.set('erc', 'empty');
	}

	if (!isNullOrUndefined(name)) {
		name ? errors.delete('name') : errors.set('name', 'empty');
	}

	if (!isNullOrUndefined(label)) {
		const values = Object.values(label ?? {});

		if (!!values.length && values.every(Boolean)) {
			errors.delete('label');
		}
		else {
			errors.set('label', 'empty');
		}
	}

	if (!isNullOrUndefined(spaces)) {
		spaces.length ? errors.delete('spaces') : errors.set('spaces', 'empty');
	}

	return errors;
}

export function getErrorMessage(
	property: ValidationProperty,
	error: ValidationError
) {
	if (property === 'global') {
		if (error === 'unexpected') {
			return Liferay.Language.get(
				'an-unexpected-error-occurred-while-saving-or-publishing-the-content-structure'
			);
		}
	}

	if (property === 'spaces' && error === 'empty') {
		return Liferay.Language.get('spaces-must-be-selected');
	}

	if (error === 'empty') {
		return Liferay.Language.get('this-field-is-required');
	}

	return Liferay.Language.get('an-unexpected-error-occurred');
}

export function useValidate() {
	const dispatch = useStateDispatch();
	const children = useSelector(selectStructureChildren);
	const state = useSelector(selectState);

	const {structure} = state;

	const validateChild = useCallback(
		(child: StructureChild, invalids: State['invalids']) => {
			let errors: ErrorMap = new Map();

			if (child.type === 'repeatable-group') {
				errors = validateRepeatableGroup({data: child});

				if (errors.size) {
					invalids.set(child.uuid, errors);
				}

				for (const grandChild of child.children.values()) {
					if (grandChild.type === 'referenced-structure') {
						continue;
					}

					validateChild(grandChild, invalids);
				}
			}
			else {
				errors = validateField({data: child as Field});

				if (errors.size) {
					invalids.set(child.uuid, errors);
				}
			}
		},
		[]
	);

	return useCallback(() => {

		// Validate structure

		let errors: ErrorMap = new Map();

		const invalids = new Map(state.invalids);

		errors = validateStructure({data: structure});

		if (errors.size) {
			invalids.set(structure.uuid, errors);
		}

		// Validate children

		for (const child of children.values()) {
			validateChild(child, invalids);
		}

		// If there's some invalid, dispatch validate action

		if (invalids.size) {
			dispatch({
				invalids,
				type: 'validate',
			});

			focusInvalidElement();

			return false;
		}

		// It's valid

		return true;
	}, [children, dispatch, state.invalids, structure, validateChild]);
}
