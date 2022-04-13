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

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import React, {useState} from 'react';

import FieldList, {ORDERS} from './FieldList';

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
	const hasRelevance = fieldsJSONArray[0].field === '';

	const [relevanceLabel, setRelevanceLabel] = useState(
		hasRelevance ? fieldsJSONArray[0].label : 'relevance'
	);
	const [relevanceOn, setRelevanceOn] = useState(hasRelevance);

	const [fields, setFields] = useState(
		(hasRelevance ? fieldsJSONArray.slice(1) : fieldsJSONArray).map(
			({field: fieldName, label}, index) => ({
				field: removeOrderFromFieldName(fieldName),
				id: index, // For FieldList item `key` when reordering.
				label,
				order: getOrderFromFieldName(fieldName),
			})
		)
	);

	return (
		<div className="sort-configurations-options">
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
						: addOrdersToFieldNames(removeEmptyFields(fields))
				)}
			/>

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
							onToggle={() => setRelevanceOn(!relevanceOn)}
							toggled={relevanceOn}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<FieldList fields={fields} onChangeFields={setFields} />
		</div>
	);
}

export default SortConfigurationOptions;
