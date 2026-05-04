/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import React, {useCallback, useMemo} from 'react';

import './ConditionBuilder.scss';

import type {
	ConditionBuilderProps,
	ConditionType,
	FilterCondition,
	GenericOperator,
	GenericProperty,
	PropertyGroup,
	ValueInputRenderer,
} from './types';

export function getRandomID() {
	return crypto.randomUUID();
}

export const TriggerLabel = React.forwardRef<HTMLButtonElement, any>(
	({children, className: _className, onClick, ...otherProps}, ref) => (
		<ClayButton
			className="form-control form-control-select form-control-sm"
			displayType="secondary"
			onClick={onClick}
			ref={ref}
			size="sm"
			{...otherProps}
		>
			{children}
		</ClayButton>
	)
);

type ConditionRowProps = {
	condition: FilterCondition;
	getOperators: (property: GenericProperty) => GenericOperator[];
	onChange: (condition: FilterCondition) => void;
	onDelete: () => void;
	properties: Array<GenericProperty | PropertyGroup>;
	renderValueInput: ValueInputRenderer;
	showDeleteButton: boolean;
};

function isPropertyGroup(
	input: GenericProperty | PropertyGroup
): input is PropertyGroup {
	return 'items' in input;
}

function ConditionRow({
	condition,
	getOperators,
	onChange,
	onDelete,
	properties,
	renderValueInput,
	showDeleteButton,
}: ConditionRowProps) {
	const flatProperties = useMemo(
		() =>
			properties.flatMap((item) =>
				isPropertyGroup(item) ? item.items : [item]
			),
		[properties]
	);

	const selectedProperty = flatProperties.find(
		(p) =>
			p.name === condition.propertyName &&
			p.classNameId === condition.classNameId &&
			p.classTypeId === condition.classTypeId
	);

	const operators = selectedProperty ? getOperators(selectedProperty) : [];

	const handleValueChange = useCallback(
		(value: string | Array<string | object>) => {
			onChange({...condition, value});
		},
		[condition, onChange]
	);

	const propertyKey = (
		p: Pick<GenericProperty, 'classNameId' | 'classTypeId' | 'name'>
	) => `${p.classNameId ?? ''}|${p.classTypeId ?? ''}|${p.name}`;

	return (
		<div
			className="align-items-center condition-builder__row d-flex justify-content-between mb-3 p-2 rounded"
			role="menuitem"
		>
			<div className="c-gap-2 d-flex flex-grow-1 flex-wrap">
				<div className="condition-builder__select form-group mb-0 w-100">
					<Picker
						aria-label={Liferay.Language.get('field')}
						as={TriggerLabel}
						items={properties}
						onSelectionChange={(key) => {
							const newProperty = flatProperties.find(
								(p) => propertyKey(p) === key
							);

							const operators = newProperty
								? getOperators(newProperty)
								: null;

							onChange({
								classNameId: newProperty?.classNameId,
								classTypeId: newProperty?.classTypeId,
								id: condition.id,
								operatorName:
									operators?.length === 0 ? 'eq' : undefined,
								propertyName: newProperty?.name,
								value: undefined,
							});
						}}
						placeholder={Liferay.Language.get('select')}
						selectedKey={
							selectedProperty
								? propertyKey(selectedProperty)
								: ''
						}
					>
						{(item) =>
							isPropertyGroup(item) ? (
								<DropDown.Group
									header={item.label}
									items={item.items}
								>
									{(prop) => (
										<Option key={propertyKey(prop)}>
											{prop.label}
										</Option>
									)}
								</DropDown.Group>
							) : (
								<Option key={propertyKey(item)}>
									{item.label}
								</Option>
							)
						}
					</Picker>
				</div>

				{!!operators.length && (
					<div className="condition-builder__select form-group mb-0 w-100">
						<Picker
							aria-label={Liferay.Language.get('operator')}
							as={TriggerLabel}
							disabled={!selectedProperty}
							items={operators.map((op) => ({
								label: op.label,
								value: op.value,
							}))}
							onSelectionChange={(key) =>
								onChange({
									...condition,
									operatorName: (key as string) || undefined,
									value: undefined,
								})
							}
							placeholder={Liferay.Language.get('select')}
							selectedKey={condition.operatorName ?? ''}
						>
							{(item) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</Picker>
					</div>
				)}

				<div className="c-gap-2 condition-builder__value-input d-flex flex-grow-1">
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

			{showDeleteButton && (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('delete-condition')}
					borderless
					className="align-self-baseline c-ml-auto condition-builder__delete"
					displayType="secondary"
					onClick={onDelete}
					size="sm"
					symbol="times-circle"
				/>
			)}
		</div>
	);
}

export function ConditionBuilder({
	conditionType,
	conditions,
	getOperators,
	onChange,
	properties,
	renderValueInput,
	showConjunctionPicker = true,
}: ConditionBuilderProps) {
	const handleAddCondition = () => {
		onChange([...conditions, {id: getRandomID()}], conditionType);
	};

	const handleDeleteCondition = (index: number) => {
		if (conditions.length === 1) {
			onChange([{id: getRandomID()}], conditionType);
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

	return (
		<div className="condition-builder">
			{showConjunctionPicker && (
				<div className="align-items-center c-gapx-2 c-mb-3 condition-builder__conjunction d-flex">
					<span>{Liferay.Language.get('if')}</span>

					<div className="condition-builder__select">
						<Picker
							aria-label={Liferay.Language.get('conjunction')}
							as={TriggerLabel}
							items={[
								{
									label: Liferay.Language.get('all'),
									value: 'all',
								},
								{
									label: Liferay.Language.get('any'),
									value: 'any',
								},
							]}
							onSelectionChange={(key) =>
								onChange(conditions, key as ConditionType)
							}
							selectedKey={conditionType}
						>
							{(item) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</Picker>
					</div>

					<span>
						{Liferay.Language.get(
							'of-the-following-conditions-are-met'
						)}
					</span>
				</div>
			)}

			<div
				className="c-gapx-3 condition-builder__conditions d-flex flex-column"
				role="list"
			>
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
				{Liferay.Language.get('add-filter')}
			</ClayButton>
		</div>
	);
}
