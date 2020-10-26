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
import ClayForm, {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useFormik} from 'formik';
import {fetch, normalizeFriendlyURL, objectToFormData} from 'frontend-js-web';
import React, {useCallback, useState} from 'react';

import {HelpMessage, RequiredMark} from './utils/formComponents.es';
import {alphanumeric, required, validate} from './utils/formValidations.es';
import Input from './utils/input.es';

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
			[`${namespace}uuid`]: configurationEntryUuid,
		},
		onSubmit: () => {
			fetch(actionUrl, {
				body: objectToFormData(values),
				method: 'POST',
			})
				.then(() => {
					Liferay.Util.navigate(redirect);
				})
				.catch(() => {
					Liferay.Util.navigate(redirect);
				});
		},
		validate: () => {
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

	const {errors, handleChange, setFieldValue, values} = formik;

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
	}, [redirect]);

	const updateUuid = (event) => {
		const nameValue = event.target.value;

		if (automaticId && !amImageConfigurationEntry) {
			values[newUuidId] = normalizeFriendlyURL(nameValue);
		}

		setFieldValue(nameId, nameValue);
	};

	return (
		<ClayForm onSubmit={formik.handleSubmit}>
			<div className="sheet sheet-lg">
				{!configurationEntryEditable && (
					<div className="alert alert-info">
						{Liferay.Language.get(
							'the-images-for-this-resolution-are-already-adapted'
						)}
					</div>
				)}

				<Input
					error={errors[nameId]}
					label={Liferay.Language.get('name')}
					name={nameId}
					onChange={updateUuid}
					required
					value={values[nameId]}
				/>

				<Input
					label={Liferay.Language.get('description')}
					name={descriptionId}
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
								error={errors[maxWidthId]}
								label={Liferay.Language.get('max-width-px')}
								name={maxWidthId}
								onChange={handleChange}
								type="number"
								value={values[maxWidthId]}
							/>
						</ClayLayout.Col>
						<ClayLayout.Col md="3">
							<Input
								disabled={!configurationEntryEditable}
								error={errors[maxHeightId]}
								label={Liferay.Language.get('max-height-px')}
								name={maxHeightId}
								onChange={handleChange}
								type="number"
								value={values[maxHeightId]}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>

					{!amImageConfigurationEntry && (
						<ClayCheckbox
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
						error={!automaticId && errors[newUuidId]}
						label={Liferay.Language.get('id')}
						name={newUuidId}
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

export default EditAdaptiveMedia;
