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
import ClayForm, {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useFormik} from 'formik';
import {
	fetch,
	normalizeFriendlyURL,
	objectToFormData,
	openToast,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import {HelpMessage, Input, RequiredMark} from './form/Components';
import {alphanumeric, required, validate} from './form/validations';

const getRandomUuid = () => Math.floor(Math.random() * 10000);
const scrollToTop = () => window.scrollTo({behavior: 'smooth', top: 0});

const VALID_INPUT_KEYS = new Set([
	'0',
	'1',
	'2',
	'3',
	'4',
	'5',
	'6',
	'7',
	'8',
	'9',
	'ArrowUp',
	'ArrowRight',
	'ArrowDown',
	'ArrowLeft',
	'Backspace',
	'Up',
	'Right',
	'Down',
	'Left',
	'Enter',
	'Tab',
]);

const EditAdaptiveMedia = ({
	actionUrl,
	amImageConfigurationEntry,
	automaticUuid,
	configurationEntryEditable,
	configurationEntryUuid,
	namespace,
	redirect,
}) => {
	const [automaticId, setAutomaticId] = useState(automaticUuid);
	const [errorMessage, setErrorMessage] = useState(null);

	const nameId = `${namespace}name`;
	const descriptionId = `${namespace}description`;
	const maxWidthId = `${namespace}maxWidth`;
	const maxHeightId = `${namespace}maxHeight`;
	const highResolutionId = `${namespace}addHighResolution`;
	const newUuidId = `${namespace}newUuid`;
	const automaticRadioId = `${namespace}automaticUuid`;

	let maxWidth = '';
	let maxHeight = '';

	if (amImageConfigurationEntry) {
		const properties = amImageConfigurationEntry.properties;

		const currentMaxWidth = properties['max-width'];
		const currentMaxHeight = properties['max-height'];

		if (currentMaxWidth != 0) {
			maxWidth = currentMaxWidth;
		}
		if (currentMaxHeight != 0) {
			maxHeight = currentMaxHeight;
		}
	}

	const formik = useFormik({
		initialValues: {
			[descriptionId]: amImageConfigurationEntry
				? amImageConfigurationEntry.description
				: '',
			[highResolutionId]: false,
			[maxHeightId]: maxHeight,
			[maxWidthId]: maxWidth,
			[nameId]: amImageConfigurationEntry
				? amImageConfigurationEntry.name
				: '',
			[newUuidId]: configurationEntryUuid,
			[`${namespace}uuid`]: configurationEntryUuid,
		},
		onSubmit: (values) => {
			fetch(actionUrl, {
				body: objectToFormData(values),
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({message, success}) => {
					if (success) {
						openToast({
							message,
							title: Liferay.Language.get('success'),
							type: 'success',
						});

						Liferay.Util.navigate(redirect);
					}
					else {
						setErrorMessage(message);
						scrollToTop();
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					});
				});
		},
		validate: (values) => {
			const errorsList = validate(
				{
					[nameId]: [required],
					[newUuidId]: [alphanumeric],
				},
				values
			);

			if (values[maxWidthId] === 0 && values[maxHeightId] === 0) {
				errorsList[maxWidthId] = Liferay.Language.get(
					'please-enter-a-max-width-or-max-height-value-larger-than-0'
				);
			}
			else if (!values[maxWidthId] && !values[maxHeightId]) {
				errorsList[maxWidthId] = Liferay.Language.get(
					'at-least-one-value-is-required'
				);
			}

			return errorsList;
		},
	});

	const {
		errors,
		handleBlur,
		handleChange,
		setFieldValue,
		touched,
		values,
	} = formik;

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
	}, [redirect]);

	const handleChangeUuid = (event) => {
		const nameValue = event.target.value;

		if (automaticId) {
			setFieldValue(
				newUuidId,
				normalizeFriendlyURL(nameValue) || getRandomUuid(),
				false
			);
		}

		setFieldValue(nameId, nameValue);
	};

	const handleKeydownNumbersOnly = (event) => {
		if (!VALID_INPUT_KEYS.has(event.key)) {
			event.preventDefault();
		}
	};

	return (
		<ClayForm onSubmit={formik.handleSubmit}>
			{errorMessage && (
				<ClayAlert displayType="danger">{errorMessage}</ClayAlert>
			)}

			{!configurationEntryEditable && (
				<ClayAlert>
					{Liferay.Language.get(
						'the-images-for-this-resolution-are-already-adapted'
					)}
				</ClayAlert>
			)}

			<Input
				error={touched[nameId] && errors[nameId]}
				label={Liferay.Language.get('name')}
				name={nameId}
				onBlur={handleBlur}
				onChange={handleChangeUuid}
				required
				value={values[nameId]}
			/>

			<Input
				label={Liferay.Language.get('description')}
				name={descriptionId}
				onBlur={handleBlur}
				onChange={handleChange}
				type="textarea"
				value={values[descriptionId]}
			/>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('size')}
					<RequiredMark />
				</h3>

				<label className="control-label form-group">
					{Liferay.Language.get(
						'please-enter-at-least-one-of-the-following-fields'
					)}

					<HelpMessage
						message={Liferay.Language.get(
							'leave-a-size-field-empty-to-get-images-scaled-proportionally'
						)}
					/>
				</label>

				<ClayLayout.Row>
					<ClayLayout.Col md="3">
						<Input
							disabled={!configurationEntryEditable}
							error={
								touched[maxWidthId] &&
								touched[maxHeightId] &&
								errors[maxWidthId]
							}
							label={Liferay.Language.get('max-width-px')}
							min="0"
							name={maxWidthId}
							onBlur={handleBlur}
							onChange={handleChange}
							onKeyDown={handleKeydownNumbersOnly}
							type="number"
							value={values[maxWidthId]}
						/>
					</ClayLayout.Col>
					<ClayLayout.Col md="3">
						<Input
							disabled={!configurationEntryEditable}
							error={
								touched[maxWidthId] &&
								touched[maxHeightId] &&
								Boolean(errors[maxWidthId])
							}
							label={Liferay.Language.get('max-height-px')}
							min="0"
							name={maxHeightId}
							onBlur={handleBlur}
							onChange={handleChange}
							onKeyDown={handleKeydownNumbersOnly}
							type="number"
							value={values[maxHeightId]}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>

				{!amImageConfigurationEntry && (
					<ClayCheckbox
						checked={values[highResolutionId]}
						id={highResolutionId}
						label={Liferay.Language.get(
							'add-a-resolution-for-high-density-displays'
						)}
						name={highResolutionId}
						onChange={(event) => {
							setFieldValue(
								highResolutionId,
								event.target.checked
							);
						}}
					/>
				)}
			</div>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('identifier')}
				</h3>

				<ClayRadioGroup
					name={automaticRadioId}
					onSelectedValueChange={setAutomaticId}
					selectedValue={automaticId}
				>
					<ClayRadio
						disabled={!configurationEntryEditable}
						label={Liferay.Language.get('automatic')}
						value={true}
					>
						<HelpMessage
							message={Liferay.Language.get(
								'the-id-is-based-on-the-name-field'
							)}
						/>
					</ClayRadio>

					<ClayRadio
						disabled={!configurationEntryEditable}
						label={Liferay.Language.get('custom')}
						value={false}
					/>
				</ClayRadioGroup>

				<Input
					disabled={automaticId || !configurationEntryEditable}
					error={
						!automaticId && touched[newUuidId] && errors[newUuidId]
					}
					label={Liferay.Language.get('id')}
					name={newUuidId}
					onBlur={handleBlur}
					onChange={handleChange}
					value={values[newUuidId]}
				/>
			</div>

			<div className="sheet-footer">
				<ClayButton.Group spaced>
					<ClayButton type="submit">
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={onCancel}
						type="cancel"
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</ClayForm>
	);
};

EditAdaptiveMedia.propTypes = {
	actionUrl: PropTypes.string.isRequired,
	amImageConfigurationEntry: PropTypes.shape({
		description: PropTypes.string,
		name: PropTypes.string,
		properties: PropTypes.shape({
			['max-height']: PropTypes.string,
			['max-width']: PropTypes.string,
		}),
	}),
	automaticUuid: PropTypes.bool,
	configurationEntryEditable: PropTypes.bool.isRequired,
	configurationEntryUuid: PropTypes.string,
	namespace: PropTypes.string.isRequired,
	redirect: PropTypes.string.isRequired,
};

export default EditAdaptiveMedia;
