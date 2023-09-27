/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	Card,
	DatePicker,
	ExpressionBuilder,
	getLocalizableLabel,
	onActionDropdownItemClick,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo} from 'react';

import './PredefinedValuesTable.scss';

export default function PredefinedValuesTable({
	creationLanguageId,
	currentObjectDefinitionFields,
	disableRequiredChecked,
	errors,
	objectFieldsMap,
	setValues,
	title,
	validateExpressionURL,
	values,
}: IProps) {
	const {predefinedValues = []} = values.parameters as ObjectActionParameters;

	const items = useMemo(() => {
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

		return predefinedValues.map(
			({businessType, inputAsValue, label, name, value}) => {
				const isDateTime = businessType === 'DateTime';
				const renderDatePicker =
					(businessType === 'Date' && inputAsValue) || isDateTime;

				return {
					inputAsValue: (
						<div className="lfr-object-web__predefined-values-table-input-method">
							<ClayCheckbox
								checked={inputAsValue}
								disabled={isDateTime}
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

					label: (
						<div className="lfr-object-web__predefined-values-table-field">
							{getLocalizableLabel(
								creationLanguageId,
								label,
								name
							)}

							{objectFieldsMap.get(name)?.required === true && (
								<span className="lfr-object-web__predefined-values-table-reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							)}
						</div>
					),

					name,

					newValue: (
						<div className="lfr-object-web__predefined-values-table-new-value">
							{renderDatePicker ? (
								<DatePicker
									error={predefinedErrors.get(name)}
									hideFeedback
									name={name}
									onChange={(value: string) => {
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
									type={businessType}
									value={value}
								/>
							) : (
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
												required: objectFieldsMap.get(
													name
												)?.required,
												source: value,
												validateExpressionURL,
											}
										);
									}}
									placeholder={
										inputAsValue
											? Liferay.Language.get(
													'input-a-value'
											  )
											: Liferay.Language.get(
													'input-a-value-or-create-an-expression'
											  )
									}
									value={value}
								/>
							)}
						</div>
					),
				};
			}
		);
	}, [
		creationLanguageId,
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
			const {name} = itemData;

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
				disableRequiredChecked,
				getLabel: ({label, name}: ObjectField) =>
					getLocalizableLabel(creationLanguageId, label, name),
				getName: ({name}: ObjectField) => name,
				header: Liferay.Language.get('add-fields'),
				items: currentObjectDefinitionFields.filter(
					({localized}) => !localized
				),
				onSave: (items: ObjectField[]) => {
					const predefinedValuesMap = new Map<
						string,
						PredefinedValue
					>();

					predefinedValues.forEach((field) => {
						predefinedValuesMap.set(field.name, field);
					});

					const newPredefinedValues = items.map(
						({businessType, label, name}) => {
							const value = predefinedValuesMap.get(name);
							const inputAsValue =
								businessType === 'DateTime' ? true : false;

							return value
								? value
								: {
										businessType,
										inputAsValue,
										label,
										name,
										value: '',
								  };
						}
					);
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
		creationLanguageId,
		currentObjectDefinitionFields,
		disableRequiredChecked,
		objectFieldsMap,
		predefinedValues,
		setValues,
		values.parameters,
	]);

	return (
		<>
			<Card
				className="lfr-object-web__predefined-values-card"
				title={title ?? Liferay.Language.get('predefined-values')}
				viewMode="no-margin"
			>
				<div className="lfr-object-web__predefined-values-table">
					<FrontendDataSet
						creationMenu={{
							primaryItems: [
								{
									href: 'handleAddFields',
									id: 'handleAddFields',
									label: Liferay.Language.get('add-fields'),
									target: 'event',
								},
							],
						}}
						id="PredefinedValuesTable"
						items={items}
						itemsActions={[
							{
								href: 'deletePredefinedValueField',
								icon: 'trash',
								id: 'deletePredefinedValueField',
								label: Liferay.Language.get('delete'),
								target: 'event',
							},
						]}
						onActionDropdownItemClick={onActionDropdownItemClick}
						selectedItemsKey="name"
						showManagementBar={true}
						showPagination={false}
						showSearch={false}
						views={[
							{
								contentRenderer: 'table',
								label: 'Table',
								name: 'table',
								schema: {
									fields: [
										{
											fieldName: 'label',
											label: Liferay.Language.get(
												'field'
											),
										},
										{
											fieldName: 'inputAsValue',
											label: Liferay.Language.get(
												'input-method'
											),
										},
										{
											fieldName: 'newValue',
											label: Liferay.Language.get(
												'new-value'
											),
										},
									],
								},
								thumbnail: 'table',
							},
						]}
					/>
				</div>
			</Card>
		</>
	);
}

interface IProps {
	creationLanguageId: Liferay.Language.Locale;
	currentObjectDefinitionFields: ObjectField[];
	disableRequiredChecked?: boolean;
	errors: {[key: string]: string};
	objectFieldsMap: Map<string, ObjectField>;
	predefinedValues?: PredefinedValue[];
	setValues: (params: Partial<ObjectAction>) => void;
	title?: string;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}

interface Item {
	inputAsValue: JSX.Element;
	label: JSX.Element;
	name: string;
	newValue: JSX.Element;
}
