/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncerContext} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import {
	ObjectField,
	ObjectFieldAttributes,
	ObjectFields,
} from '../../../app/contexts/ObjectDataContext';
import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import FieldFragmentTypeSelector from './FieldFragmentTypeSelector';
import FormFragmentTypeSelector from './FormFragmentTypeSelector';
import RuleSelect from './RuleSelect';
import UserTypeSelector from './UserTypeSelector';

interface ConditionProps {
	condition: ConditionType;
	inputFragmentItems: {label: string; value: string}[];
	mappingFieldItems: {
		attributes?: ObjectFieldAttributes;
		label: string;
		type: string;
		value: string;
	}[];
	onConditionChange: (condition: ConditionType) => void;
}

export const TYPE_VALUES = {
	field: 'field',
	formFragment: 'form',
	user: 'user',
} as const;

export const CONDITION_TYPE_ITEMS = [
	{
		label: Liferay.Language.get('user'),
		value: TYPE_VALUES.user,
	},
	{
		label: Liferay.Language.get('form-fragment'),
		value: TYPE_VALUES.formFragment,
	},
] as const;

const SUPPORTED_FIELD_TYPES = new Set<string>([
	'boolean',
	'date',
	'date-time',
	'file',
	'long-text',
	'multiselect',
	'number',
	'select',
	'text',
	'url',
]);

export default function Condition({
	condition,
	inputFragmentItems,
	mappingFieldItems,
	onConditionChange,
}: ConditionProps) {
	const {sendMessage} = useContext(ScreenReaderAnnouncerContext);

	const onErrorChange = (error: RuleError | null) => {
		if (condition.error?.element.id !== error?.element.id) {
			onConditionChange({...condition, error});
		}
	};

	const conditionTypeItems =
		config.layoutType === LAYOUT_TYPES.display &&
		config.selectedMappingTypes?.formEnabled
			? [
					...CONDITION_TYPE_ITEMS,
					{
						label: sub(
							Liferay.Language.get('x-field'),
							config.selectedMappingTypes?.type.label
						),
						value: TYPE_VALUES.field,
					},
				]
			: CONDITION_TYPE_ITEMS;

	return (
		<>
			<RuleSelect
				aria-label={Liferay.Language.get(
					'select-item-for-the-condition'
				)}
				items={conditionTypeItems}
				onErrorChange={onErrorChange}
				onSelectionChange={(type) =>
					onConditionChange({...condition, type})
				}
				selectedKey={condition.type}
			/>

			{condition.type === TYPE_VALUES.field ? (
				<FieldFragmentTypeSelector
					condition={condition}
					items={mappingFieldItems}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}

			{condition.type === TYPE_VALUES.formFragment ? (
				<FormFragmentTypeSelector
					condition={condition}
					inputFragmentItems={inputFragmentItems}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}

			{condition.type === TYPE_VALUES.user ? (
				<UserTypeSelector
					condition={condition}
					onConditionChange={onConditionChange}
					onErrorChange={onErrorChange}
					sendMessage={sendMessage}
				/>
			) : null}
		</>
	);
}

export function filterAndConvertMappingFields(
	mappingFields: ObjectFields | null
): {
	attributes?: ObjectFieldAttributes;
	label: string;
	type: string;
	value: string;
}[] {
	if (!mappingFields || !config.selectedMappingTypes?.type) {
		return [];
	}

	return mappingFields
		.flatMap((field) => ('fields' in field ? field.fields : [field]))
		.filter((field) => {
			const objectField = field as ObjectField;

			return SUPPORTED_FIELD_TYPES.has(objectField.type);
		})
		.map((field) => {
			const objectField = field as ObjectField;

			return {
				attributes: objectField.attributes,
				label: objectField.label,
				type: objectField.type,
				value: objectField.key,
			};
		});
}
