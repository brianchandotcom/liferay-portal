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
import React, {useContext, useEffect, useState} from 'react';

import useDebounce from '../../hooks/useDebounce.es';
import {sub} from '../../utils/lang.es';
import FieldBase from './shared/FieldBase.es';
import {STRUCTURE_LEVEL, VIEW_LEVEL} from './shared/constants.es';
import {
	containsFieldInsideFormBuilder,
	getDataDefinitionField,
	getDataLayoutField,
	getFormattedState,
	setPropertyAtFormViewLevel,
	setPropertyAtObjectViewLevel,
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
 * @param {Object} state
 */
function getInitialSelectedValue(state) {
	const label = getDataLayoutField(state)?.label;

	if (label && !!Object.keys(label).length) {
		return VIEW_LEVEL;
	}

	return STRUCTURE_LEVEL;
}

/**
 * Get localized value
 * @param {Object} label
 * @param {Object} state
 */
function getLocalizedValue(label, {defaultLanguageId, editingLanguageId}) {
	return label[editingLanguageId] || label[defaultLanguageId];
}

/**
 * Get initial value
 * @param {Object} state
 * @param {Object} field
 */
function getInitialValue({editingLanguageId}, {value}) {
	return () => {
		if (typeof value === 'object') {
			return value[editingLanguageId];
		}

		return value;
	};
}

export default function LabelField({AppContext, dataLayoutBuilder, field}) {
	const [state, dispatch] = useContext(AppContext);
	const formattedState = getFormattedState(state);
	const [value, setValue] = useState(getInitialValue(formattedState, field));
	const [selectedValue, setSelectedValue] = useState(
		getInitialSelectedValue(formattedState)
	);

	const dataDefinitionField = getDataDefinitionField(formattedState);
	const dataLayoutField = getDataLayoutField(formattedState);

	const callbackFn = (fn) => fn(formattedState, dispatch);

	const onSelectedValueChange = (value) => {
		setSelectedValue(value);

		if (value === VIEW_LEVEL) {
			callbackFn(
				setPropertyAtFormViewLevel(
					PROPERTY_NAME,
					dataDefinitionField.label
				)
			);
		}
		else {
			callbackFn(setPropertyAtFormViewLevel(PROPERTY_NAME, {}));

			setValue(
				getLocalizedValue(dataDefinitionField.label, formattedState)
			);
		}
	};

	const debounce = useDebounce(value);

	useEffect(() => {
		if (!dataDefinitionField) {
			return;
		}

		const label = {[formattedState.editingLanguageId]: value};

		if (containsFieldInsideFormBuilder(dataLayoutBuilder, formattedState)) {
			dataLayoutBuilder.dispatch('fieldEdited', {
				propertyName: PROPERTY_NAME,
				propertyValue: value,
			});
		}

		if (selectedValue === VIEW_LEVEL) {
			callbackFn(
				setPropertyAtFormViewLevel(PROPERTY_NAME, {
					...dataLayoutField.label,
					...label,
				})
			);
		}
		else {
			callbackFn(
				setPropertyAtObjectViewLevel(PROPERTY_NAME, {
					...dataDefinitionField.label,
					...label,
				})
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [debounce]);

	useEffect(() => {
		if (!dataDefinitionField) {
			return;
		}

		if (selectedValue === VIEW_LEVEL) {
			setValue(getLocalizedValue(dataLayoutField.label, formattedState));
		}
		else {
			setValue(
				getLocalizedValue(dataDefinitionField.label, formattedState)
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [field.name]);

	useEffect(() => {
		setSelectedValue(getInitialSelectedValue(formattedState));
	}, [formattedState]);

	return (
		<FieldBase
			className="form-renderer-label-field"
			onSelectedValueChange={onSelectedValueChange}
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
				onChange={({target: {value}}) => setValue(value)}
				placeholder={field.placeholder}
				type="text"
				value={value}
			/>

			{selectedValue === VIEW_LEVEL && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{sub(Liferay.Language.get('object-field-label-x'), [
							getLocalizedValue(
								dataDefinitionField.label,
								formattedState
							),
						])}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</FieldBase>
	);
}
