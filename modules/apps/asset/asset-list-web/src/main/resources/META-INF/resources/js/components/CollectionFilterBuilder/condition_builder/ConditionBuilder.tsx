/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useCallback} from 'react';

import './ConditionBuilder.scss';

import type {
	ConditionBuilderProps,
	ConditionType,
	FilterCondition,
	GenericOperator,
	GenericProperty,
	ValueInputRenderer,
} from './types';

let condCounter = 0;

export const generateConditionId = () => {
	return `condition_${++condCounter}`;
}

type ConditionRowProps = {
	condition: FilterCondition;
	getOperators: (property: GenericProperty) => GenericOperator[];
	onChange: (condition: FilterCondition) => void;
	onDelete: () => void;
	properties: GenericProperty[];
	renderValueInput: ValueInputRenderer;
	showDeleteButton: boolean;
};

function ConditionRow({
	condition,
	getOperators,
	onChange,
	onDelete,
	properties,
	renderValueInput,
	showDeleteButton,
}: ConditionRowProps) {
	const selectedProperty = properties.find(
		(p) => p.name === condition.propertyName
	);

	const operators = selectedProperty ? getOperators(selectedProperty) : [];

	const handlePropertyChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		onChange({
			id: condition.id,
			operatorName: undefined,
			propertyName: e.target.value || undefined,
			value: undefined,
		});
	};

	const handleOperatorChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		onChange({
			...condition,
			operatorName: e.target.value || undefined,
			value: undefined,
		});
	};

	const handleValueChange = useCallback(
		(value: string) => {
			onChange({...condition, value});
		},
		[condition, onChange]
	);

	return (
		<div className="condition-builder__row" role="listitem">
			{showDeleteButton && (
				<ClayButton
					aria-label={Liferay.Language.get('delete-condition')}
					displayType="secondary"
					monospaced
					onClick={onDelete}
					size="sm"
				>
					<ClayIcon symbol="times" />
				</ClayButton>
			)}

			<ClaySelectWithOption
				aria-label={Liferay.Language.get('field')}
				onChange={handlePropertyChange}
				options={[
					{
						label: `-- ${Liferay.Language.get('select-field')} --`,
						value: '',
					},
					...properties.map((p) => ({
						label: p.label,
						value: p.name,
					})),
				]}
				value={condition.propertyName ?? ''}
			/>

			<ClaySelectWithOption
				aria-label={Liferay.Language.get('operator')}
				disabled={!selectedProperty}
				onChange={handleOperatorChange}
				options={[
					{
						label: `-- ${Liferay.Language.get('select-operator')} --`,
						value: '',
					},
					...operators.map((op) => ({
						label: op.label,
						value: op.value,
					})),
				]}
				value={condition.operatorName ?? ''}
			/>

			<div className="condition-builder__value-input">
				{selectedProperty && condition.operatorName
					? renderValueInput(
							selectedProperty,
							condition.operatorName,
							condition.value,
							handleValueChange
						)
					: null}
			</div>
		</div>
	);
}

export function ConditionBuilder({
	conditions,
	conditionType,
	getOperators,
	onChange,
	properties,
	renderValueInput,
	showConjunctionPicker = true,
}: ConditionBuilderProps) {
	const handleAddCondition = () => {
		onChange([...conditions, {id: generateConditionId()}], conditionType);
	};

	const handleDeleteCondition = (index: number) => {
		if (conditions.length === 1) {
			onChange([{id: generateConditionId()}], conditionType);
		}
		else {
			onChange(
				conditions.filter((_, i) => i !== index),
				conditionType
			);
		}
	};

	const handleUpdateCondition = (index: number, updated: FilterCondition) => {
		const next = [...conditions];

		next[index] = updated;

		onChange(next, conditionType);
	};

	const handleConjunctionChange = (
		e: React.ChangeEvent<HTMLSelectElement>
	) => {
		onChange(conditions, e.target.value as ConditionType);
	};

	return (
		<div className="condition-builder">
			{showConjunctionPicker && (
				<div className="condition-builder__conjunction">
					<span>{Liferay.Language.get('if')}</span>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('conjunction')}
						onChange={handleConjunctionChange}
						options={[
							{
								label: Liferay.Language.get('all'),
								value: 'all',
							},
							{
								label: Liferay.Language.get('any'),
								value: 'any',
							},
						]}
						value={conditionType}
					/>

					<span>
						{Liferay.Language.get(
							'of-the-following-conditions-are-met'
						)}
					</span>
				</div>
			)}

			<div className="condition-builder__conditions" role="list">
				{conditions.map((condition, index) => (
					<ConditionRow
						condition={condition}
						getOperators={getOperators}
						key={condition.id}
						onChange={(updated) =>
							handleUpdateCondition(index, updated)
						}
						onDelete={() => handleDeleteCondition(index)}
						properties={properties}
						renderValueInput={renderValueInput}
						showDeleteButton={
							conditions.length > 1 || !!condition.propertyName
						}
					/>
				))}
			</div>

			<ClayButton
				className="mt-2"
				displayType="secondary"
				onClick={handleAddCondition}
				size="sm"
			>
				{Liferay.Language.get('add-condition')}
			</ClayButton>
		</div>
	);
}
