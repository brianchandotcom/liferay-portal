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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect, ClayToggle} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import FieldList from './FieldList';

const ORDERS = {
	ASC: {
		label: Liferay.Language.get('ascending'),
		value: 'ascending',
	},
	DESC: {
		label: Liferay.Language.get('descending'),
		value: 'descending',
	},
};

const VIEWS = {
	CLASSIC: {
		label: Liferay.Language.get('classic'),
		value: 'classic',
	},
	NEW: {
		label: Liferay.Language.get('new'),
		value: 'new',
	},
};

/**
 * Adds the possible order symbol (+/-) at the end of the indexed field name.
 * @param {Array} fields Field array of objects with fieldName, label, and order.
 * @return {Array} Field array of objects with fieldName (has the order symbol)
 * and label.
 */
const addOrdersToFieldNames = (fields) =>
	fields.map(({field: fieldName, label, order}) => ({
		field: `${fieldName}${order === ORDERS.DESC.value ? '-' : '+'}`,
		label,
	}));

/**
 * Uses the indexed field name to determine if order is ascending (+) or
 * descending (-).
 * @param {string} fieldName The indexed field name with possible order symbol.
 * @return {string} Order value (ascending or descending).
 */
const getOrderFromFieldName = (fieldName) => {
	return fieldName.slice(-1) === '-' ? ORDERS.DESC.value : ORDERS.ASC.value;
};

/**
 * Gets the relevance field object.
 * @param {Array} fieldsJSONArray
 * @returns {object}
 */
const getRelevanceFieldObject = (fieldsJSONArray) => {
	let relevanceField = null;

	fieldsJSONArray.some((item) => {
		if (item.field === '') {
			relevanceField = item;

			return true;
		}

		return false;
	});

	return relevanceField;
};

/**
 * Cleans up the fields array by removing those that have empty indexed
 * field names.
 * @param {Array} fields The list of fields.
 * @return {Array} The cleaned up list of fields.
 */
const removeEmptyFields = (fields) =>
	fields.filter(({field: fieldName}) => fieldName);

/**
 * Removes the possible order symbol (+/-) at the end of the indexed field
 * name.
 * @param {string} fieldName The indexed field name with possible order symbol.
 * @return {string} The indexed field name without order symbol.
 */
const removeOrderFromFieldName = (fieldName) => {
	return fieldName.slice(-1) === '+' || fieldName.slice(-1) === '-'
		? fieldName.slice(0, -1)
		: fieldName;
};

/**
 * Transforms the preference key fields format into a format easier to work with
 * in the component. Adds an `id` and `order` property.
 * @param {Array} fieldsJSONArray
 * @returns {Array}
 */
const transformFieldsJSONArrayToFieldsArray = (fieldsJSONArray) => {
	const fieldsJSONArrayWithRemovedRelevance = fieldsJSONArray.filter(
		({field}) => field !== ''
	);

	return fieldsJSONArrayWithRemovedRelevance.map(
		({field: fieldName, label}, index) => ({
			field: removeOrderFromFieldName(fieldName),
			id: index, // For FieldList item `key` when reordering.
			label,
			order: getOrderFromFieldName(fieldName),
		})
	);
};

function Inputs({onChange, value}) {
	const _handleChangeValue = (property) => (event) => {
		onChange({[property]: event.target.value});
	};

	return (
		<>
			<ClayInput.GroupItem>
				<label htmlFor="indexedFieldName">
					{Liferay.Language.get('indexed-field-name')}
				</label>

				<ClayInput
					id="indexedFieldName"
					onChange={_handleChangeValue('field')}
					type="text"
					value={value.field}
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem>
				<label htmlFor="displayLabel">
					{Liferay.Language.get('display-label')}
				</label>

				<ClayInput
					id="displayLabel"
					onChange={_handleChangeValue('label')}
					type="text"
					value={value.label}
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem>
				<label htmlFor="order">{Liferay.Language.get('order')}</label>

				<ClaySelect
					aria-label={Liferay.Language.get('select-order')}
					id="order"
					onChange={_handleChangeValue('order')}
					value={value.order}
				>
					{Object.keys(ORDERS).map((key) => (
						<ClaySelect.Option
							key={ORDERS[key].value}
							label={ORDERS[key].label}
							value={ORDERS[key].value}
						/>
					))}
				</ClaySelect>
			</ClayInput.GroupItem>
		</>
	);
}

function SortConfigurationOptions({
	fieldsInputName = '',
	fieldsJSONArray = [
		{field: '', label: 'relevance'},
		{field: 'modified-', label: 'modified'},
		{field: 'modified+', label: 'modified-oldest-first'},
		{field: 'created-', label: 'created'},
		{field: 'created+', label: 'created-oldest-first'},
		{field: 'userName', label: 'user'},
	],
	namespace = '',
}) {
	const relevanceField = getRelevanceFieldObject(fieldsJSONArray);

	const [classicFields, setClassicFields] = useState(fieldsJSONArray);
	const [fields, setFields] = useState(
		transformFieldsJSONArrayToFieldsArray(fieldsJSONArray)
	);
	const [relevanceLabel, setRelevanceLabel] = useState(
		relevanceField ? relevanceField.label : 'relevance'
	);
	const [relevanceOn, setRelevanceOn] = useState(!!relevanceField);
	const [showAlert, setShowAlert] = useState(true);
	const [view, setView] = useState(VIEWS.NEW);

	useEffect(() => {
		if (view.value !== VIEWS.CLASSIC.value) {
			return;
		}

		AUI().use('liferay-auto-fields', () => {
			new Liferay.AutoFields({
				contentBox: `#${namespace}fieldsId`,
				namespace: `${namespace}`,
			}).render();
		});
	}, [namespace, view]);

	const _handleChangeClassicValue = (index, property) => (event) => {
		const newClassicFields = [...classicFields];

		newClassicFields[index][property] = event.target.value;

		setClassicFields(newClassicFields);
	};

	const _handleCloseAlert = () => {
		setShowAlert(false);
	};

	const _handleSwitchView = () => {
		if (view.value === VIEWS.NEW.value) {
			setClassicFields(
				relevanceOn
					? [
							{field: '', label: relevanceLabel},
							...addOrdersToFieldNames(removeEmptyFields(fields)),
					  ]
					: addOrdersToFieldNames(removeEmptyFields(fields))
			);

			setView(VIEWS.CLASSIC);
		}
		else {
			const newFields = [];

			const autoFieldFormRows = [
				...document.getElementsByClassName('field-form-row'),
			].filter((element) => {
				return !element.hidden;
			});

			autoFieldFormRows.forEach((item) => {
				const field = item.getElementsByClassName('sort-field-input')[0]
					.value;
				const label = item.getElementsByClassName('label-input')[0]
					.value;

				newFields.push({
					field,
					label,
				});
			});

			setFields(transformFieldsJSONArrayToFieldsArray(newFields));

			const classicRelevanceField = getRelevanceFieldObject(newFields);

			setRelevanceLabel(
				classicRelevanceField
					? classicRelevanceField.label
					: 'relevance'
			);
			setRelevanceOn(!!classicRelevanceField);

			setView(VIEWS.NEW);
		}
	};

	return (
		<div className="sort-configurations-options">
			<div className="view-switcher">
				<ClayButton
					borderless
					displayType="secondary"
					onClick={_handleSwitchView}
					small
				>
					{Liferay.Util.sub(
						Liferay.Language.get('switch-to-x-view'),
						[
							view.value === VIEWS.NEW.value
								? VIEWS.CLASSIC.label
								: VIEWS.NEW.label,
						]
					)}
				</ClayButton>
			</div>

			{view.value === VIEWS.NEW.value && (
				<>
					<ClayForm.Group className="field-item relevance">
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<label htmlFor="relevance">
									{Liferay.Language.get('display-label')}
								</label>

								<ClayInput
									id="relevance"
									onChange={(event) =>
										setRelevanceLabel(event.target.value)
									}
									type="text"
									value={relevanceLabel}
								/>

								<div className="text-secondary">
									{Liferay.Language.get(
										'relevance-can-be-turned-on-or-off-but-not-removed'
									)}
								</div>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<ClayToggle
									label={
										relevanceOn
											? Liferay.Language.get('on')
											: Liferay.Language.get('off')
									}
									onToggle={() =>
										setRelevanceOn(!relevanceOn)
									}
									toggled={relevanceOn}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<FieldList
						initialValue={{
							field: '',
							label: '',
							order: ORDERS.ASC.value,
						}}
						onChange={setFields}
						renderInputs={(props) => <Inputs {...props} />}
						value={fields}
					/>

					<input
						name={`${namespace}${fieldsInputName}`}
						type="hidden"
						value={JSON.stringify(
							relevanceOn
								? [
										{field: '', label: relevanceLabel},
										...addOrdersToFieldNames(
											removeEmptyFields(fields)
										),
								  ]
								: addOrdersToFieldNames(
										removeEmptyFields(fields)
								  )
						)}
					/>
				</>
			)}

			{view.value === VIEWS.CLASSIC.value && showAlert && (
				<ClayAlert
					displayType="info"
					onClose={_handleCloseAlert}
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get(
						'the-classic-view-will-be-removed-in-a-future-version'
					)}
				</ClayAlert>
			)}

			{view.value === VIEWS.CLASSIC.value && (
				<span id={`${namespace}fieldsId`}>
					{classicFields.map(({field, label}, index) => (
						<div
							className="field-form-row lfr-form-row lfr-form-row-inline"
							key={index}
						>
							<div className="row-fields">
								<div className="form-group">
									<label htmlFor={`label_${index}`}>
										{Liferay.Language.get('label')}
									</label>

									<ClayInput
										className="label-input"
										id={`label_${index}`}
										onChange={_handleChangeClassicValue(
											index,
											'label'
										)}
										type="text"
										value={label}
									/>
								</div>

								<div className="form-group">
									<label htmlFor={`field_${index}`}>
										{Liferay.Language.get('field')}
									</label>

									<ClayInput
										className="sort-field-input"
										id={`field_${index}`}
										onChange={_handleChangeClassicValue(
											index,
											'field'
										)}
										type="text"
										value={field}
									/>
								</div>
							</div>
						</div>
					))}

					<input
						name={`${namespace}${fieldsInputName}`}
						type="hidden"
						value={JSON.stringify(classicFields)}
					/>
				</span>
			)}
		</div>
	);
}

export default SortConfigurationOptions;
