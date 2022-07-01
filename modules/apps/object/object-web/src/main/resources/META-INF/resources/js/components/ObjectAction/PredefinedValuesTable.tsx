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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {
	Card,
	ExpressionBuilder,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useMemo} from 'react';

import './PredefinedValuesTable.scss';

export default function PredefinedValuesTable({
	currentObjectDefinitionFields,
	errors,
	objectFieldsMap,
	setValues,
	validateExpressionURL,
	values,
}: IProps) {
	const {predefinedValues = []} = values.parameters as ObjectActionParameters;
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

	const getTableRows = useMemo(() => {
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

		const rows = predefinedValues.map(({inputAsValue, name, value}) => {
			return (
				<ClayTable.Row key={name}>
					<ClayTable.Cell className="lfr-object-web__predefined-values-table-cell-field">
						<div className="lfr-object-web__predefined-values-table-field">
							{name}

							{objectFieldsMap.get(name)?.required === true && (
								<span className="lfr-object-web__predefined-values-table-reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							)}
						</div>
					</ClayTable.Cell>

					<ClayTable.Cell className="lfr-object-web__predefined-values-table-cell-input-method">
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
					</ClayTable.Cell>

					<ClayTable.Cell className="lfr-object-web__predefined-values-table-cell-new-value">
						<div className="lfr-object-web__predefined-values-table-new-value">
							<ExpressionBuilder
								buttonDisabled={inputAsValue}
								error={predefinedErrors.get(name)}
								hideFeedback
								onChange={({target: {value}}: any) => {
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
					</ClayTable.Cell>

					<ClayTable.Cell
						className="lfr-object-web__predefined-values-table-cell-delete-button"
						columnTextAlignment="end"
					>
						<div className="lfr-object-web__predefined-values-table-delete-button">
							<ClayButtonWithIcon
								className="reorder-page-button"
								displayType="secondary"
								id={name}
								onClick={() => {
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
								}}
								small
								symbol="trash"
								title={Liferay.Language.get('delete')}
							/>
						</div>
					</ClayTable.Cell>
				</ClayTable.Row>
			);
		});

		return rows;
	}, [
		errors,
		objectFieldsMap,
		predefinedValues,
		setValues,
		validateExpressionURL,
		values.parameters,
	]);

	const handleAddFields = () => {
		const parentWindow = Liferay.Util.getOpener();

		parentWindow.Liferay.fire('openModalAddColumns', {
			disableRequired: true,
			getName: ({name}: ObjectField) => name,
			header: Liferay.Language.get('add-fields'),
			items: currentObjectDefinitionFields,
			onSave: (items: ObjectField[]) => {
				const predefinedValuesMap = new Map<string, PredefinedValue>();

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

	return (
		<>
			<Card
				title={Liferay.Language.get('predefined-values')}
				viewMode="no-margin"
			>
				<div className="lfr-object-web__predefined-values-table-add-modal-button">
					<ClayButtonWithIcon
						className="add-modal-button"
						displayType="primary"
						onClick={handleAddFields}
						symbol="plus"
						title={Liferay.Language.get('add-fields')}
					/>
				</div>

				{predefinedValues.length ? (
					<ClayTable className="predefined-values-table">
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell headingCell>
									{Liferay.Language.get('field')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('input-method')}
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{Liferay.Language.get('new-value')}
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>{getTableRows}</ClayTable.Body>
					</ClayTable>
				) : (
					<ClayEmptyState
						description={Liferay.Language.get(
							'add-fields-to-be-reused-on-the-newly-created-entry'
						)}
						imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
						title={Liferay.Language.get('no-fields-added-yet')}
					/>
				)}
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
