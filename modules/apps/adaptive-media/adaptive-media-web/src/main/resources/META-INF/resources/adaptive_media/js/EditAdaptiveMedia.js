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

import ClayButton from '@clayui/button';
import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useFormik} from 'formik';
import {normalizeFriendlyURL} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useRef, useState} from 'react';

import {Checkbox, HelpMessage, Input, RequiredMark} from './form/Components';
import {alphanumeric, required, validate} from './form/validations';

const STR_BLANK = ' ';

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
	const [addHighResolution, setAddHighResolution] = useState(false);
	const formRef = useRef(null);

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

		const curMaxWidth = properties['max-width'];
		const curMaxHeight = properties['max-height'];

		if (curMaxWidth != 0) {
			maxWidth = curMaxWidth;
		}
		if (curMaxHeight != 0) {
			maxHeight = curMaxHeight;
		}
	}

	const formik = useFormik({
		initialValues: {
			[descriptionId]: amImageConfigurationEntry
				? amImageConfigurationEntry.description
				: '',
			[highResolutionId]: addHighResolution,
			[maxHeightId]: maxHeight,
			[maxWidthId]: maxWidth,
			[nameId]: amImageConfigurationEntry
				? amImageConfigurationEntry.name
				: '',
			[newUuidId]: configurationEntryUuid,
		},
		onSubmit: () => {
			submitForm(formRef.current);
		},
		validate: (values) => {
			const err = validate(
				{
					[nameId]: [required],
					[newUuidId]: [alphanumeric],
				},
				values
			);

			if (!values[maxWidthId] && !values[maxHeightId]) {
				err[maxWidthId] = Liferay.Language.get(
					'at-least-one-value-is-required'
				);

				err[maxHeightId] = STR_BLANK;
			}

			return err;
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
			setFieldValue(newUuidId, normalizeFriendlyURL(nameValue), false);
		}

		setFieldValue(nameId, nameValue);
	};

	const setFieldDimention = (fieldKey, value) => {
		if (/^\d*$/.test(value)) {
			setFieldValue(fieldKey, value, false);
		}
	};

	const handleChangeMaxWidth = (event) => {
		const value = event.target.value;

		setFieldDimention(maxWidthId, value);
	};

	const handleChangeHeight = (event) => {
		const value = event.target.value;

		setFieldDimention(maxHeightId, value);
	};

	return (
		<ClayForm
			action={actionUrl}
			method="post"
			onSubmit={formik.handleSubmit}
			ref={formRef}
		>
			<div className="sheet sheet-lg">
				{!configurationEntryEditable && (
					<div className="alert alert-info">
						{Liferay.Language.get(
							'the-images-for-this-resolution-are-already-adapted'
						)}
					</div>
				)}

				<input
					name={`${namespace}redirect`}
					type="hidden"
					value={redirect}
				/>

				<input
					name={`${namespace}uuid`}
					type="hidden"
					value={configurationEntryUuid}
				/>

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
								min="1"
								name={maxWidthId}
								onBlur={handleBlur}
								onChange={handleChangeMaxWidth}
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
									errors[maxHeightId]
								}
								label={Liferay.Language.get('max-height-px')}
								min="1"
								name={maxHeightId}
								onBlur={handleBlur}
								onChange={handleChangeHeight}
								type="number"
								value={values[maxHeightId]}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>

					{!amImageConfigurationEntry && (
						<Checkbox
							checked={addHighResolution}
							label={Liferay.Language.get(
								'add-a-resolution-for-high-density-displays'
							)}
							name={highResolutionId}
							onChange={() =>
								setAddHighResolution(!addHighResolution)
							}
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
							!automaticId &&
							touched[newUuidId] &&
							errors[newUuidId]
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
