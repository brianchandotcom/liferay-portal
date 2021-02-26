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
import React, {useEffect, useState} from 'react';

import FieldBase from './shared/FieldBase.es';
import {STRUCTURE_LEVEL, VIEW_LEVEL} from './shared/constants.es';
import {
	containsFieldInsideFormBuilder,
	setPropertyAtStructureLevel,
	setPropertyAtViewLevel,
} from './shared/utils.es';

const PROPERTY_NAME = 'required';

const LEVEL = {
	[STRUCTURE_LEVEL]: {
		fn: (...params) => {
			setPropertyAtStructureLevel(PROPERTY_NAME, true)(...params);
		},
		label: Liferay.Language.get('for-all-forms-using-this-field'),
	},
	[VIEW_LEVEL]: {
		fn: (...params) => {
			setPropertyAtViewLevel(PROPERTY_NAME, true)(...params);

			setPropertyAtStructureLevel(PROPERTY_NAME, false)(...params);
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
function isRequiredAtFormViewLevel({dataLayoutField}) {
	return isRequiredField(dataLayoutField);
}

/**
 * Check if it is required at object view level
 * @param {object} state
 */
function isRequiredAtObjectViewLevel({dataDefinitionField}) {
	return isRequiredField(dataDefinitionField);
}

export default function RequiredField({dataLayoutBuilder, dispatch, state}) {
	const [showPopover, setShowPopover] = useState(false);
	const [selectedValue, setSelectedValue] = useState(
		initialLevelSelected(state)
	);
	const [toggled, setToggle] = useState(initialToggledValue(state));

	useEffect(() => {
		setToggle(initialToggledValue(state));
		setSelectedValue(initialLevelSelected(state));

		if (!initialToggledValue(state)) {
			setShowPopover(false);
		}
	}, [state]);

	const callbackFn = (fn) => fn(state, dispatch);

	const onSelectedValueChange = (level) => {
		setSelectedValue(level);

		callbackFn(LEVEL[level].fn);
	};

	const onToggle = (toggle) => {
		setToggle(toggle);

		if (containsFieldInsideFormBuilder(dataLayoutBuilder, state)) {
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
				'fieldEdited',
				{
					propertyName: PROPERTY_NAME,
					propertyValue: toggle,
				}
			);
		}

		if (toggle) {
			setSelectedValue(VIEW_LEVEL);

			callbackFn(LEVEL[VIEW_LEVEL].fn);
		}
		else {
			callbackFn((...params) => {
				setPropertyAtViewLevel(PROPERTY_NAME, false)(...params);

				setPropertyAtStructureLevel(PROPERTY_NAME, false)(...params);
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
