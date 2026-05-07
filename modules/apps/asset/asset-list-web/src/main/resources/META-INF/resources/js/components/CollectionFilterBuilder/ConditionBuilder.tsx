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
import ValueInput from './ValueInput';
import {
	getCollectionOperators,
	getCollectionQuantifierOptions,
} from './operators';

import type {
	FilterCondition,
	FilterProperty,
	FilterPropertyGroup,
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

interface ConditionBuilderProps {
	categorySelectorURL?: string;
	conditions: FilterCondition[];
	groupIds?: string[];
	namespace: string;
	onChange: (conditions: FilterCondition[]) => void;
	properties: Array<FilterProperty | FilterPropertyGroup>;
	tagSelectorURL?: string;
	vocabularyIds?: string[];
}

type ConditionRowProps = Omit<
	ConditionBuilderProps,
	'conditions' | 'onChange'
> & {
	condition: FilterCondition;
	index: number;
	onChange: (condition: FilterCondition) => void;
};

function isPropertyGroup(
	input: FilterProperty | FilterPropertyGroup
): input is FilterPropertyGroup {
	return 'items' in input;
}

function ConditionRow({
	categorySelectorURL,
	condition,
	groupIds,
	index,
	namespace,
	onChange,
	properties,
	tagSelectorURL,
	vocabularyIds,
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

	const operators = selectedProperty
		? getCollectionOperators(selectedProperty)
		: [];

	const quantifierOptions = selectedProperty
		? getCollectionQuantifierOptions(selectedProperty)
		: null;

	const handleValueChange = useCallback(
		(value: string | Array<string | object>) => {
			onChange({...condition, value});
		},
		[condition, onChange]
	);

	const propertyKey = (
		p: Pick<FilterProperty, 'classNameId' | 'classTypeId' | 'name'>
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
							? getCollectionOperators(newProperty)
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
				{selectedProperty && condition.operatorName ? (
					<ValueInput
						categorySelectorURL={categorySelectorURL}
						groupIds={groupIds}
						index={index}
						namespace={namespace}
						onChange={handleValueChange}
						operator={condition.operatorName}
						property={selectedProperty}
						tagSelectorURL={tagSelectorURL}
						value={condition.value}
						vocabularyIds={vocabularyIds}
					/>
				) : null}
			</div>
		</>
	);
}

export function ConditionBuilder({
	categorySelectorURL,
	conditions,
	groupIds,
	namespace,
	onChange,
	properties,
	tagSelectorURL,
	vocabularyIds,
}: ConditionBuilderProps) {
	return (
		<div className="condition-builder">
			<RowBuilder<FilterCondition>
				canDelete={(
					condition: FilterCondition,
					_index: number,
					items: FilterCondition[]
				) => items.length > 1 || !!condition.propertyName}
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
				renderItem={({
					index,
					item,
					onChange: onItemChange,
				}: {
					index: number;
					item: FilterCondition;
					onChange: (condition: FilterCondition) => void;
				}) => (
					<ConditionRow
						categorySelectorURL={categorySelectorURL}
						condition={item}
						groupIds={groupIds}
						index={index}
						namespace={namespace}
						onChange={onItemChange}
						properties={properties}
						tagSelectorURL={tagSelectorURL}
						vocabularyIds={vocabularyIds}
					/>
				)}
				setItems={onChange}
			/>
		</div>
	);
}
