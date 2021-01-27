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
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useEffect, useRef, useState} from 'react';

import useClickOutside from '../../hooks/useClickOutside.es';
import {getLocalizedUserPreferenceValue, sub} from '../../utils/lang.es';
import {
	getFormattedState,
	isLabelAtFormViewLevel,
	setPropertyAtFormViewLevel,
	setPropertyAtObjectViewLevel,
} from './shared/index.es';

const ALL_FORMS = 'all-forms';
const ONLY_THIS_FORM = 'only-this-form';

const propertyName = 'label';

const LABEL_LEVEL = {
	[ALL_FORMS]: {
		label: Liferay.Language.get('label-for-all-forms-using-this-field'),
	},
	[ONLY_THIS_FORM]: {
		label: Liferay.Language.get('label-for-only-this-form'),
	},
};

/**
 * Define an initial value to viewSelected state
 * @param {object} state
 */

function initialViewSelectedValue(state) {
	if (isLabelAtFormViewLevel(state)) {
		return ONLY_THIS_FORM;
	}

	return ALL_FORMS;
}

/**
 * Get the Label from Field inside the dataLayoutField and dataDefinitionField
 * @param {object} formattedState
 * @param {object} componentState
 */

function getLabelFromContext(
	{
		dataDefinitionFields,
		dataLayoutFields,
		defaultLanguageId,
		editingLanguageId,
	},
	{fieldName, levelSelected, value}
) {
	const findByName = ({name}) => fieldName === name;

	const dataLayoutField = dataLayoutFields[fieldName] || {};
	const dataDefinitionField = dataDefinitionFields.find(findByName) || {};

	const formViewLabel = dataLayoutField.label;
	const dataDefinitionFieldLabel = dataDefinitionField.label;

	const currentValue =
		levelSelected === ALL_FORMS
			? value
			: getLocalizedUserPreferenceValue(
					formViewLabel,
					editingLanguageId,
					defaultLanguageId
			  );

	return {
		currentValue,
		dataDefinitionFieldLabel,
		formViewLabel,
	};
}

export default ({
	AppContext,
	dataLayoutBuilder,
	field: {label, localizedValue, name, placeholder, tooltip, value},
}) => {
	const [state, dispatch] = useContext(AppContext);
	const formattedState = getFormattedState(state);
	const [showPopover, setShowPopover] = useState(false);
	const [levelSelected, setLevelSelected] = useState(
		initialViewSelectedValue(formattedState)
	);
	const [popoverRef, triggerRef] = useClickOutside(
		[useRef(null), useRef(null)],
		setShowPopover
	);

	useEffect(() => {
		setLevelSelected(initialViewSelectedValue(formattedState));
	}, [formattedState]);

	const {defaultLanguageId, editingLanguageId, fieldName} = formattedState;

	const {
		currentValue,
		dataDefinitionFieldLabel,
		formViewLabel,
	} = getLabelFromContext(formattedState, {fieldName, levelSelected, value});

	/**
	 * Set require callback function
	 * @param {function} fn
	 */

	const setLabelCallbackFn = (fn) =>
		fn({...formattedState, propertyName}, dispatch);

	const onChangeValue = ({target: {value}}) => {
		const newLabel = {[editingLanguageId]: value};

		dataLayoutBuilder.dispatch('fieldEdited', {
			fieldName,
			propertyName,
			propertyValue: value,
		});

		if (levelSelected === ALL_FORMS) {
			setLabelCallbackFn((...params) =>
				setPropertyAtObjectViewLevel({
					...localizedValue,
					...newLabel,
				})(...params)
			);
		}
		else {
			setLabelCallbackFn((...params) =>
				setPropertyAtFormViewLevel({
					...formViewLabel,
					...newLabel,
				})(...params)
			);
		}
	};

	const onChangeLabelOptions = (level) => {
		if (level === ONLY_THIS_FORM) {
			setLabelCallbackFn((...params) =>
				setPropertyAtFormViewLevel(dataDefinitionFieldLabel)(...params)
			);
		}
		else {
			setLabelCallbackFn((...params) => {
				setPropertyAtFormViewLevel({})(...params);

				setPropertyAtObjectViewLevel({
					...dataDefinitionFieldLabel,
					[editingLanguageId]: currentValue,
				})(...params);
			});
		}

		setLevelSelected(level);
	};

	return (
		<div className="d-flex form-renderer-label-field justify-content-between">
			<ClayForm.Group className="form-renderer-label-field__input">
				<label className="ddm-label">
					{label}

					<span className="ddm-tooltip">
						<ClayIcon
							symbol="question-circle-full"
							title={tooltip}
						/>
					</span>
				</label>
				<ClayInput
					autoFocus
					className="ddm-field-text"
					name={name}
					onChange={onChangeValue}
					placeholder={placeholder}
					type="text"
					value={currentValue}
				/>

				{levelSelected === ONLY_THIS_FORM && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{sub(Liferay.Language.get('object-field-label-x'), [
								[
									getLocalizedUserPreferenceValue(
										dataDefinitionFieldLabel,
										editingLanguageId,
										defaultLanguageId
									),
								],
							])}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			<ClayTooltipProvider>
				<ClayButtonWithIcon
					alignPosition="bottom-right"
					borderless
					className="form-renderer-label-field__button"
					displayType="secondary"
					onClick={() => setShowPopover(!showPopover)}
					ref={triggerRef}
					small
					symbol="ellipsis-v"
					title={Liferay.Language.get('label-options')}
				/>
			</ClayTooltipProvider>

			<ClayPopover
				alignPosition="bottom-right"
				className="form-renderer-label-field__popover"
				header={Liferay.Language.get('label-options')}
				onShowChange={setShowPopover}
				ref={popoverRef}
				show={showPopover}
			>
				<div className="mt-2">
					<ClayRadioGroup
						onSelectedValueChange={onChangeLabelOptions}
						selectedValue={levelSelected}
					>
						{Object.keys(LABEL_LEVEL).map((key) => (
							<ClayRadio
								key={key}
								value={key}
								{...LABEL_LEVEL[key]}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayPopover>
		</div>
	);
};
