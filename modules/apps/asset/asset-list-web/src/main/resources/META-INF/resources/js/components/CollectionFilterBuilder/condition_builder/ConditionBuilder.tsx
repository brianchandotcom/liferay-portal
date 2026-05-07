/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {RowBuilder} from '@liferay/layout-js-components-web';
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
	getQuantifierOptions?: (
		property: GenericProperty
	) => GenericOperator[] | null;
	index: number;
	onChange: (condition: FilterCondition) => void;
	properties: Array<GenericProperty | PropertyGroup>;
	renderValueInput: ValueInputRenderer;
};

function isPropertyGroup(
	input: GenericProperty | PropertyGroup
): input is PropertyGroup {
	return 'items' in input;
}

function ConditionRow({
	condition,
	getOperators,
	getQuantifierOptions,
	index,
	onChange,
	properties,
	renderValueInput,
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

	const quantifierOptions =
		selectedProperty && getQuantifierOptions
			? getQuantifierOptions(selectedProperty)
			: null;

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
		<>
			<div className="condition-builder__select form-group mb-0">
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
							quantifier: undefined,
							value: undefined,
						});
					}}
					placeholder={Liferay.Language.get('select')}
					selectedKey={
						selectedProperty ? propertyKey(selectedProperty) : ''
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
				<div className="condition-builder__select form-group mb-0">
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

			{!!quantifierOptions?.length && (
				<div className="condition-builder__select form-group mb-0">
					<Picker
						aria-label={Liferay.Language.get('quantifier')}
						as={TriggerLabel}
						disabled={!selectedProperty}
						items={quantifierOptions.map((op) => ({
							label: op.label,
							value: op.value,
						}))}
						onSelectionChange={(key) =>
							onChange({
								...condition,
								quantifier: (key as string) || undefined,
							})
						}
						placeholder={Liferay.Language.get('select')}
						selectedKey={condition.quantifier ?? ''}
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
							index,
							selectedProperty,
							condition.operatorName,
							condition.value,
							handleValueChange
						)
					: null}
			</div>
		</>
	);
}

export function ConditionBuilder({
	conditionType,
	conditions,
	getOperators,
	getQuantifierOptions,
	onChange,
	properties,
	renderValueInput,
	showConjunctionPicker = true,
}: ConditionBuilderProps) {
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

			<RowBuilder<FilterCondition>
				canDelete={(condition, _index, items) =>
					items.length > 1 || !!condition.propertyName
				}
				createItem={() => ({id: getRandomID()})}
				itemClassName="condition-builder__row"
				items={conditions}
				labels={{
					add: Liferay.Language.get('add-filter'),
					addedAnnouncement: Liferay.Language.get('condition-added'),
					delete: Liferay.Language.get('delete-condition'),
					deletedAnnouncement:
						Liferay.Language.get('condition-deleted'),
					list: Liferay.Language.get('conditions'),
				}}
				renderItem={({index, item, onChange: onItemChange}) => (
					<ConditionRow
						condition={item}
						getOperators={getOperators}
						getQuantifierOptions={getQuantifierOptions}
						index={index}
						onChange={onItemChange}
						properties={properties}
						renderValueInput={renderValueInput}
					/>
				)}
				setItems={(nextConditions) =>
					onChange(nextConditions, conditionType)
				}
			/>
		</div>
	);
}
