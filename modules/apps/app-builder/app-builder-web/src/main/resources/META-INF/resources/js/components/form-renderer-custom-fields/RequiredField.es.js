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

import {ClayToggle} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import FieldBase from './shared/FieldBase.es';
import {STRUCTURE_LEVEL, VIEW_LEVEL} from './shared/constants.es';
import {
	containsFieldInsideFormBuilder,
	getDataDefinitionField,
	getFormattedState,
	setPropertyAtFormViewLevel,
	setPropertyAtObjectViewLevel,
} from './shared/utils.es';

const PROPERTY_NAME = 'required';

const LEVEL = {
	[STRUCTURE_LEVEL]: {
		fn: (...params) => {
			setPropertyAtObjectViewLevel(PROPERTY_NAME, true)(...params);
		},
		label: Liferay.Language.get('for-all-forms-using-this-field'),
	},
	[VIEW_LEVEL]: {
		fn: (...params) => {
			setPropertyAtFormViewLevel(PROPERTY_NAME, true)(...params);

			setPropertyAtObjectViewLevel(PROPERTY_NAME, false)(...params);
		},
		label: Liferay.Language.get('only-for-this-form'),
	},
};

/**
 * Define an initial value to toggled state
 * @param {object} state
 */
function initialToggledValue(state) {
	if (
		isRequiredAtObjectViewLevel(state) ||
		isRequiredAtFormViewLevel(state)
	) {
		return true;
	}

	return false;
}

/**
 * Define an initial value to selectedValue state
 * @param {object} state
 */
function initialLevelSelected(state) {
	if (isRequiredAtObjectViewLevel(state)) {
		return STRUCTURE_LEVEL;
	}

	return VIEW_LEVEL;
}

/**
 * Check if the field has a required parameter
 * @param {object} field
 */
function isRequiredField(field) {
	return !!field?.required;
}

/**
 * Check if it is required at form view level
 * @param {object} state
 */
function isRequiredAtFormViewLevel({dataLayoutFields, fieldName}) {
	return isRequiredField(dataLayoutFields[fieldName]);
}

/**
 * Check if it is required at object view level
 * @param {object} state
 */
function isRequiredAtObjectViewLevel(state) {
	return isRequiredField(getDataDefinitionField(state));
}

export default function RequiredField({AppContext, dataLayoutBuilder}) {
	const [state, dispatch] = useContext(AppContext);
	const [showPopover, setShowPopover] = useState(false);
	const formattedState = getFormattedState(state);
	const [selectedValue, setSelectedValue] = useState(
		initialLevelSelected(formattedState)
	);
	const [toggled, setToggle] = useState(initialToggledValue(formattedState));

	useEffect(() => {
		setToggle(initialToggledValue(formattedState));
		setSelectedValue(initialLevelSelected(formattedState));

		if (!initialToggledValue(formattedState)) {
			setShowPopover(false);
		}
	}, [formattedState]);

	const callbackFn = (fn) => fn(formattedState, dispatch);

	const onSelectedValueChange = (level) => {
		setSelectedValue(level);

		callbackFn(LEVEL[level].fn);
	};

	const onToggle = (toggle) => {
		setToggle(toggle);

		if (containsFieldInsideFormBuilder(dataLayoutBuilder, formattedState)) {
			dataLayoutBuilder.dispatch('fieldEdited', {
				propertyName: PROPERTY_NAME,
				propertyValue: toggle,
			});
		}

		if (toggle) {
			setSelectedValue(VIEW_LEVEL);

			callbackFn(LEVEL[VIEW_LEVEL].fn);
		}
		else {
			callbackFn((...params) => {
				setPropertyAtFormViewLevel(PROPERTY_NAME, false)(...params);

				setPropertyAtObjectViewLevel(PROPERTY_NAME, false)(...params);
			});
		}
	};

	return (
		<FieldBase
			className="form-renderer-required-field"
			disableDropdownButton={!toggled}
			onSelectedValueChange={onSelectedValueChange}
			options={LEVEL}
			selectedValue={selectedValue}
			showPopover={showPopover}
			title={Liferay.Language.get('required-options')}
		>
			<ClayToggle
				label={Liferay.Language.get('required-field')}
				onToggle={onToggle}
				toggled={toggled}
			/>
		</FieldBase>
	);
}
