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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useEffect, useState} from 'react';

import useDebounce from '../../hooks/useDebounce.es';
import {sub} from '../../utils/lang.es';
import FieldBase from './shared/FieldBase.es';
import {STRUCTURE_LEVEL, VIEW_LEVEL} from './shared/constants.es';
import {
	containsFieldInsideFormBuilder,
	setPropertyAtStructureLevel,
	setPropertyAtViewLevel,
} from './shared/utils.es';

const PROPERTY_NAME = 'label';

const LEVEL = {
	[STRUCTURE_LEVEL]: {
		label: Liferay.Language.get('label-for-all-forms-using-this-field'),
	},
	[VIEW_LEVEL]: {
		label: Liferay.Language.get('label-for-only-this-form'),
	},
};

/**
 * Get initial selected value
 * @param {object} state
 */
function getInitialSelectedValue({dataDefinitionField: {customProperties}}) {
	if (customProperties.labelAtStructureLevel) {
		return STRUCTURE_LEVEL;
	}

	return VIEW_LEVEL;
}

/**
 * Get localized value
 * @param {object} value
 * @param {object} state
 */
function getLocalizedValue(value, {defaultLanguageId, editingLanguageId}) {
	return {
		...value,
		[editingLanguageId]:
			value[editingLanguageId] || value[defaultLanguageId],
	};
}

/**
 * Get initial value
 * @param {string} selectedValue
 * @param {object} state
 */
function getInitialValue(selectedValue, state) {
	const {dataDefinitionField, dataLayoutField} = state;

	if (selectedValue === VIEW_LEVEL) {
		return getLocalizedValue(dataLayoutField.label, state);
	}

	return getLocalizedValue(dataDefinitionField.label, state);
}

/**
 * Update variable labelAtStructureLevel
 * @param {object} state
 * @param {function} dispatch
 * @param {string} selectedValue
 */
function updateLabelAtStructureLevel(
	{dataDefinitionFields, fieldName},
	dispatch,
	selectedValue
) {
	dispatch({
		payload: {
			dataDefinitionFields: dataDefinitionFields.map((field) => {
				if (field.name === fieldName) {
					return {
						...field,
						customProperties: {
							...field.customProperties,
							labelAtStructureLevel:
								selectedValue === STRUCTURE_LEVEL,
						},
					};
				}

				return field;
			}),
		},
		type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
	});
}

export default function LabelField({
	dataLayoutBuilder,
	dispatch,
	field,
	state,
}) {
	const [selectedValue, setSelectedValue] = useState(
		getInitialSelectedValue(state)
	);

	const [value, setValue] = useState(getInitialValue(selectedValue, state));

	const {
		dataDefinitionField,
		dataLayoutField,
		defaultLanguageId,
		editingLanguageId,
	} = state;

	const debounce = useDebounce(value);

	useEffect(() => {
		if (containsFieldInsideFormBuilder(dataLayoutBuilder, state)) {
			dataLayoutBuilder.dispatch('fieldEdited', {
				propertyName: PROPERTY_NAME,
				propertyValue:
					value[editingLanguageId] || value[defaultLanguageId],
			});
		}

		const callbackFn = (fn) => fn(state, dispatch);

		if (selectedValue === VIEW_LEVEL) {
			callbackFn(
				setPropertyAtViewLevel(PROPERTY_NAME, {
					...dataLayoutField.label,
					...value,
				})
			);
		}
		else {
			callbackFn(
				setPropertyAtStructureLevel(PROPERTY_NAME, {
					...dataDefinitionField.label,
					...value,
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [debounce]);

	useEffect(() => {
		setSelectedValue(getInitialSelectedValue(state));
		setValue(getInitialValue(selectedValue, state));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [field.name]);

	return (
		<FieldBase
			className="form-renderer-label-field"
			onSelectedValueChange={(selectedValue) => {
				if (selectedValue === VIEW_LEVEL) {
					setValue(getLocalizedValue(dataLayoutField.label, state));
				}
				else {
					setValue(
						getLocalizedValue(dataDefinitionField.label, state)
					);
				}

				updateLabelAtStructureLevel(state, dispatch, selectedValue);

				setSelectedValue(selectedValue);
			}}
			options={LEVEL}
			selectedValue={selectedValue}
			title={Liferay.Language.get('label-options')}
		>
			<label className="ddm-label">
				{field.label}

				<span className="ddm-tooltip">
					<ClayIcon
						symbol="question-circle-full"
						title={field.tooltip}
					/>
				</span>
			</label>

			<ClayInput
				autoFocus
				className="ddm-field-text"
				name={field.name}
				onChange={({target: {value: currentValue}}) =>
					setValue({
						...value,
						[editingLanguageId]: currentValue,
					})
				}
				placeholder={field.placeholder}
				type="text"
				value={value[editingLanguageId] || value[defaultLanguageId]}
			/>

			{selectedValue === VIEW_LEVEL && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{sub(Liferay.Language.get('object-field-label-x'), [
							getLocalizedValue(dataDefinitionField.label, state)[
								editingLanguageId
							],
						])}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</FieldBase>
	);
}
