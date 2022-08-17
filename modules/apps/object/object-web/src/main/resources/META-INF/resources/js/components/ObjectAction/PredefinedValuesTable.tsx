/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';

// @ts-ignore

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	Card,
	ExpressionBuilder,
	onActionDropdownItemClick,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo} from 'react';

import './PredefinedValuesTable.scss';

function getDataSetProps(items: Item[]) {
	return {
		creationMenu: {
			primaryItems: [
				{
					href: 'handleAddFields',
					id: 'handleAddFields',
					label: Liferay.Language.get('add-fields'),
					target: 'event',
				},
			],
		},
		id: 'PredefinedValuesTable',
		items,
		itemsActions: [
			{
				href: 'deletePredefinedValueField',
				icon: 'trash',
				id: 'deletePredefinedValueField',
				label: Liferay.Language.get('delete'),
				target: 'event',
			},
		],
		namespace: '',

		onActionDropdownItemClick,

		selectedItemsKey: 'id',
		showManagementBar: true,
		showPagination: false,
		showSearch: false,
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							fieldName: 'name',
							label: Liferay.Language.get('field'),
						},
						{
							fieldName: 'inputAsValue',
							label: Liferay.Language.get('input-method'),
						},
						{
							fieldName: 'newValue',
							label: Liferay.Language.get('new-value'),
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};
}

export default function PredefinedValuesTable({
	currentObjectDefinitionFields,
	errors,
	objectFieldsMap,
	setValues,
	validateExpressionURL,
	values,
}: IProps) {
	const {predefinedValues = []} = values.parameters as ObjectActionParameters;

	const props = useMemo(() => {
		const updatePredefinedValues = (name: string, value: string) => {
			const updatedPredefinedValues = predefinedValues.map((field) => {
				return field.name === name ? {...field, value} : field;
			});

			return updatedPredefinedValues;
		};

		const predefinedErrors = new Map<string, string>();

		if (errors) {
			Object.entries(errors).forEach(([key, value]) => {
				predefinedErrors.set(key, value);
			});
		}

		const items = predefinedValues.map(({inputAsValue, name, value}) => {
			return {
				inputAsValue: (
					<div className="lfr-object-web__predefined-values-table-input-method">
						<ClayCheckbox
							checked={inputAsValue}
							disabled={false}
							label={Liferay.Language.get('input-as-a-value')}
							onChange={({target: {checked}}) => {
								const newPredefinedValues = predefinedValues.map(
									(field) => {
										return field.name === name
											? {
													...field,
													inputAsValue: checked,
											  }
											: field;
									}
								);
								setValues({
									parameters: {
										...values.parameters,
										predefinedValues: newPredefinedValues,
									},
								});
							}}
						/>

						<ClayTooltipProvider>
							<div
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'by-checking-this-option,-expressions-will-not-be-used-for-filling-the-predefined-value-field'
								)}
							>
								<ClayIcon
									className="lfr-object-web__predefined-values-table-tooltip-icon"
									symbol="question-circle-full"
								/>
							</div>
						</ClayTooltipProvider>
					</div>
				),

				name: (
					<div className="lfr-object-web__predefined-values-table-field">
						{name}

						{objectFieldsMap.get(name)?.required === true && (
							<span className="lfr-object-web__predefined-values-table-reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						)}
					</div>
				),

				newValue: (
					<div className="lfr-object-web__predefined-values-table-new-value">
						<ExpressionBuilder
							buttonDisabled={inputAsValue}
							error={predefinedErrors.get(name)}
							hideFeedback
							onChange={({target: {value}}) => {
								setValues({
									parameters: {
										...values.parameters,
										predefinedValues: updatePredefinedValues(
											name,
											value
										),
									},
								});
							}}
							onOpenModal={() => {
								const parentWindow = Liferay.Util.getOpener();

								parentWindow.Liferay.fire(
									'openExpressionBuilderModal',
									{
										onSave: (value: string) => {
											setValues({
												parameters: {
													...values.parameters,
													predefinedValues: updatePredefinedValues(
														name,
														value
													),
												},
											});
										},
										required: objectFieldsMap.get(name)
											?.required,
										source: value,
										validateExpressionURL,
									}
								);
							}}
							placeholder={
								inputAsValue
									? Liferay.Language.get('input-a-value')
									: Liferay.Language.get(
											'input-a-value-or-create-an-expression'
									  )
							}
							value={value}
						/>
					</div>
				),
			};
		});

		return getDataSetProps(items);
	}, [
		errors,
		objectFieldsMap,
		predefinedValues,
		setValues,
		validateExpressionURL,
		values.parameters,
	]);

	useEffect(() => {
		const getSelectedFields = () => {
			const objectFields: ObjectField[] = [];

			predefinedValues?.forEach(({name}) => {
				if (objectFieldsMap.has(name)) {
					const field = objectFieldsMap.get(name);
					objectFields.push(field as ObjectField);
				}
			});

			return objectFields;
		};

		const deletePredefinedValueField = ({itemData}: {itemData: Item}) => {
			const [name] = itemData.name.props.children;

			if (objectFieldsMap.get(name)?.required) {
				openToast({
					message: Liferay.Language.get(
						'required-fields-cannot-be-deleted'
					),
					type: 'danger',
				});

				return;
			}

			const newPredefinedValues = predefinedValues?.filter(
				(field) => field.name !== name
			);

			setValues({
				parameters: {
					...values.parameters,
					predefinedValues: newPredefinedValues,
				},
			});
		};

		const handleAddFields = () => {
			const parentWindow = Liferay.Util.getOpener();

			parentWindow.Liferay.fire('openModalAddColumns', {
				disableRequired: true,
				getName: ({name}: ObjectField) => name,
				header: Liferay.Language.get('add-fields'),
				items: currentObjectDefinitionFields,
				onSave: (items: ObjectField[]) => {
					const predefinedValuesMap = new Map<
						string,
						PredefinedValue
					>();

					predefinedValues.forEach((field) => {
						predefinedValuesMap.set(field.name, field);
					});

					const newPredefinedValues = items.map(({name}) => {
						const value = predefinedValuesMap.get(name);

						return value
							? value
							: {
									inputAsValue: false,
									name,
									value: '',
							  };
					});
					setValues({
						parameters: {
							...values.parameters,
							predefinedValues: newPredefinedValues,
						},
					});
				},
				selected: getSelectedFields(),
				title: Liferay.Language.get('select-the-fields'),
			});
		};

		Liferay.on('deletePredefinedValueField', deletePredefinedValueField);
		Liferay.on('handleAddFields', handleAddFields);

		return () => {
			Liferay.detach('deletePredefinedValueField');
			Liferay.detach('handleAddFields');
		};
	}, [
		currentObjectDefinitionFields,
		objectFieldsMap,
		predefinedValues,
		setValues,
		values.parameters,
	]);

	return (
		<>
			<Card
				className="lfr-object-web__predefined-values-card"
				title={Liferay.Language.get('predefined-values')}
				viewMode="no-margin"
			>
				<div className="lfr-object-web__predefined-values-table">
					<FrontendDataSet {...props} />
				</div>
			</Card>
		</>
	);
}

interface IProps {
	currentObjectDefinitionFields: ObjectField[];
	errors: {[key: string]: string};
	objectFieldsMap: Map<string, ObjectField>;
	predefinedValues?: PredefinedValue[];
	setValues: (params: Partial<ObjectAction>) => void;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

interface Item {
	inputAsValue: JSX.Element;
	name: JSX.Element;
	newValue: JSX.Element;
}
