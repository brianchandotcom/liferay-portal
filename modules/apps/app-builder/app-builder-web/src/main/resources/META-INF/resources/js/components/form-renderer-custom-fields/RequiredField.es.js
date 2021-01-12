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
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import ClayPopover from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useContext, useEffect, useRef, useState} from 'react';

import isClickOutside from '../../utils/clickOutside.es';

const FORM_VIEW_LEVEL = 'form-view-level';
const OBJECT_VIEW_LEVEL = 'object-view-level';

const VIEW_LEVEL = {
	[FORM_VIEW_LEVEL]: {
		fn: (...params) => {
			setRequiredAtFormViewLevel(true)(...params);

			setRequiredAtObjectViewLevel(false)(...params);
		},
		label: Liferay.Language.get('only-for-this-form'),
	},
	[OBJECT_VIEW_LEVEL]: {
		fn: (...params) => {
			setRequiredAtObjectViewLevel(true)(...params);
		},
		label: Liferay.Language.get('for-all-forms-using-this-field'),
	},
};

/**
 * Return the formatted state
 * @param {object} state
 */
function getFormattedState({
	dataDefinition: {dataDefinitionFields},
	dataLayout: {dataLayoutFields},
	focusedField: {fieldName},
}) {
	return {
		dataDefinitionFields,
		dataLayoutFields,
		fieldName,
	};
}

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
 * Define an initial value to viewSelected state
 * @param {object} state
 */
function initialViewSelectedValue(state) {
	if (isRequiredAtObjectViewLevel(state)) {
		return OBJECT_VIEW_LEVEL;
	}

	return FORM_VIEW_LEVEL;
}

/**
 * Check if the field has a required parameter
 * @param {object} field
 */
function isRequiredField(field) {
	return field?.required ?? false;
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
function isRequiredAtObjectViewLevel({dataDefinitionFields, fieldName}) {
	const field = dataDefinitionFields.find(({name}) => name === fieldName);

	return isRequiredField(field);
}

/**
 * Set required at form view level
 * @param {boolean} required
 */
function setRequiredAtFormViewLevel(required) {
	return ({dataLayoutFields, fieldName}, dispatch) => {
		dispatch({
			payload: {
				dataLayoutFields: {
					...dataLayoutFields,
					[fieldName]: {
						...dataLayoutFields[fieldName],
						required,
					},
				},
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_FIELDS,
		});
	};
}

/**
 * Set required at object view level
 * @param {boolean} required
 */
function setRequiredAtObjectViewLevel(required) {
	return ({dataDefinitionFields, fieldName}, dispatch) => {
		dispatch({
			payload: {
				dataDefinitionFields: dataDefinitionFields.map((field) => {
					if (field.name === fieldName) {
						return {
							...field,
							required,
						};
					}

					return field;
				}),
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
		});
	};
}

export default ({AppContext, dataLayoutBuilder}) => {
	const [state, dispatch] = useContext(AppContext);
	const popoverRef = useRef(null);
	const triggerRef = useRef(null);
	const [showPopover, setShowPopover] = useState(false);

	const formattedState = getFormattedState(state);

	const [viewSelected, setViewSelected] = useState(
		initialViewSelectedValue(formattedState)
	);
	const [toggled, setToggle] = useState(initialToggledValue(formattedState));

	/**
	 * Set require callback function
	 * @param {function} fn
	 */
	const setRequireCallbackFn = (fn) => fn(formattedState, dispatch);

	/**
	 * UseEffect to click outside and close the popover
	 */
	useEffect(() => {
		const handler = ({target}) => {
			const outside = isClickOutside(
				target,
				popoverRef?.current,
				triggerRef?.current
			);

			if (outside) {
				setShowPopover(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [popoverRef, triggerRef]);

	useEffect(() => {
		setToggle(initialToggledValue(formattedState));
		setViewSelected(initialViewSelectedValue(formattedState));

		if (!initialToggledValue(formattedState)) {
			setShowPopover(false);
		}
	}, [formattedState]);

	return (
		<div className="d-flex form-renderer-required-field justify-content-between">
			<ClayForm.Group className="form-renderer-required-field__toggle">
				<ClayToggle
					label={Liferay.Language.get('required-field')}
					onToggle={(toggle) => {
						setToggle(toggle);

						dataLayoutBuilder.dispatch('fieldEdited', {
							fieldName: state.focusedField.fieldName,
							propertyName: 'required',
							propertyValue: toggle,
						});

						if (toggle) {
							setViewSelected(FORM_VIEW_LEVEL);

							setRequireCallbackFn(
								VIEW_LEVEL[FORM_VIEW_LEVEL].fn
							);
						}
						else {
							setRequireCallbackFn((...params) => {
								setRequiredAtFormViewLevel(false)(...params);

								setRequiredAtObjectViewLevel(false)(...params);
							});
						}
					}}
					toggled={toggled}
				/>
			</ClayForm.Group>

			<ClayTooltipProvider>
				<ClayButtonWithIcon
					borderless
					disabled={!toggled}
					displayType="secondary"
					onClick={() => setShowPopover(!showPopover)}
					ref={triggerRef}
					small
					symbol="ellipsis-v"
					title={Liferay.Language.get('required-options')}
				/>
			</ClayTooltipProvider>

			<ClayPopover
				alignPosition="bottom-right"
				className="form-renderer-required-field__popover"
				header={Liferay.Language.get('required-options')}
				onShowChange={setShowPopover}
				ref={popoverRef}
				show={showPopover}
			>
				<div className="mt-2">
					<ClayRadioGroup
						onSelectedValueChange={(newValue) => {
							setViewSelected(newValue);

							setRequireCallbackFn(VIEW_LEVEL[newValue].fn);
						}}
						selectedValue={viewSelected}
					>
						{Object.keys(VIEW_LEVEL).map((key) => (
							<ClayRadio
								key={key}
								label={VIEW_LEVEL[key].label}
								value={key}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayPopover>
		</div>
	);
};
