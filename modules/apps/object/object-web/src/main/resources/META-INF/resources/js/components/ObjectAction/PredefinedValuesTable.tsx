/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {
	Card,
	DatePicker,
	ExpressionBuilder,
	onActionDropdownItemClick,
	openToast,
	stringUtils,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import './PredefinedValuesTable.scss';

interface PredefinedValueTableProps {
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

interface PredefinedValueTableItem {
	inputAsValue: JSX.Element;
	label: JSX.Element;
	name: string;
	newValue: JSX.Element;
}

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
}: PredefinedValueTableProps) {
	const [reloadFDS, setReloadFDS] = useState(false);
	const {predefinedValues = []} = values.parameters as ObjectActionParameters;

	const items = useMemo(() => {
		const updatePredefinedValues = (name: string, value: string) => {
			const updatedPredefinedValues = predefinedValues.map(
				(objectField) => {
					return objectField.name === name
						? {...objectField, value}
						: objectField;
				}
			);

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
								disabled={isDateTime || values.system}
								label={Liferay.Language.get('input-as-a-value')}
								onChange={({target: {checked}}) => {
									const newPredefinedValues =
										predefinedValues.map((objectField) => {
											return objectField.name === name
												? {
														...objectField,
														inputAsValue: checked,
													}
												: objectField;
										});
									setValues({
										parameters: {
											...values.parameters,
											predefinedValues:
												newPredefinedValues,
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
							{stringUtils.getLocalizableLabel(
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
									disabled={values.system}
									error={predefinedErrors.get(name)}
									hideFeedback
									name={name}
									onChange={(value: string) => {
										setValues({
											parameters: {
												...values.parameters,
												predefinedValues:
													updatePredefinedValues(
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
									buttonDisabled={
										inputAsValue || values.system
									}
									disabled={values.system}
									error={predefinedErrors.get(name)}
									hideFeedback
									onChange={({target: {value}}) => {
										setValues({
											parameters: {
												...values.parameters,
												predefinedValues:
													updatePredefinedValues(
														name,
														value
													),
											},
										});
									}}
									onOpenModal={() => {
										const parentWindow =
											Liferay.Util.getOpener();

										parentWindow.Liferay.fire(
											'openExpressionBuilderModal',
											{
												onSave: (value: string) => {
													setValues({
														parameters: {
															...values.parameters,
															predefinedValues:
																updatePredefinedValues(
																	name,
																	value
																),
														},
													});
												},
												required:
													objectFieldsMap.get(name)
														?.required,
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
		values.system,
	]);

	const handleReloadFDS = () => {
		setReloadFDS(true);

		setTimeout(() => setReloadFDS(false), 200);
	};

	useEffect(() => {
		const getSelectedObjectFields = () => {
			const objectFields: ObjectField[] = [];

			predefinedValues?.forEach(({name}) => {
				if (
					objectFieldsMap.has(name) ||
					objectFieldsMap.get(name)?.required
				) {
					const objectField = objectFieldsMap.get(name);
					objectFields.push(objectField as ObjectField);
				}
			});

			return objectFields;
		};

		const deletePredefinedValueObjectField = ({
			itemData,
		}: {
			itemData: PredefinedValueTableItem;
		}) => {
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
				(objectField) => objectField.name !== name
			);

			setValues({
				parameters: {
					...values.parameters,
					predefinedValues: newPredefinedValues,
				},
			});
		};

		const handleAddObjectFields = () => {
			const parentWindow = Liferay.Util.getOpener();

			parentWindow.Liferay.fire('openModalSelectObjectFields', {
				getLabel: ({label, name}: ObjectField) =>
					stringUtils.getLocalizableLabel(
						creationLanguageId,
						label,
						name
					),
				getName: ({name}: ObjectField) => name,
				header: Liferay.Language.get('add-fields'),
				items: currentObjectDefinitionFields
					.filter(
						(currentObjectDefinitionField) =>
							!currentObjectDefinitionField.localized
					)
					.map((currentObjectDefinitionField) => ({
						...currentObjectDefinitionField,
						disableCheckbox:
							currentObjectDefinitionField.required &&
							!disableRequiredChecked,
					})),
				onSave: (items: ObjectField[]) => {
					const predefinedValuesMap = new Map<
						string,
						PredefinedValue
					>();

					predefinedValues.forEach((objectField) => {
						predefinedValuesMap.set(objectField.name, objectField);
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
				selected: getSelectedObjectFields(),
				showModal: true,
				title: Liferay.Language.get('select-the-fields'),
			});
		};

		Liferay.on(
			'deletePredefinedValueObjectField',
			deletePredefinedValueObjectField
		);
		Liferay.on('handleAddObjectFields', handleAddObjectFields);

		return () => {
			Liferay.detach('deletePredefinedValueObjectField');
			Liferay.detach('handleAddObjectFields');
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

	useEffect(() => {
		Liferay.on('reloadFDS', handleReloadFDS);

		return () => {
			Liferay.detach('reloadFDS', handleReloadFDS);
		};
	}, []);

	return (
		<>
			<Card
				className="lfr-object-web__predefined-values-card"
				title={title ?? Liferay.Language.get('predefined-values')}
				viewMode="no-margin"
			>
				<div className="lfr-object-web__predefined-values-table">
					{reloadFDS ? (
						<ClayLoadingIndicator
							displayType="secondary"
							size="sm"
						/>
					) : (
						<FrontendDataSet
							creationMenu={{
								primaryItems: !values.system
									? [
											{
												href: 'handleAddObjectFields',
												id: 'handleAddObjectFields',
												label: Liferay.Language.get(
													'add-fields'
												),
												target: 'event',
											},
										]
									: [],
							}}
							id="PredefinedValuesTable"
							items={items}
							itemsActions={
								!values.system
									? [
											{
												href: 'deletePredefinedValueObjectField',
												icon: 'trash',
												id: 'deletePredefinedValueObjectField',
												label: Liferay.Language.get(
													'delete'
												),
												target: 'event',
											},
										]
									: []
							}
							onActionDropdownItemClick={
								onActionDropdownItemClick
							}
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
					)}
				</div>
			</Card>
		</>
	);
}
