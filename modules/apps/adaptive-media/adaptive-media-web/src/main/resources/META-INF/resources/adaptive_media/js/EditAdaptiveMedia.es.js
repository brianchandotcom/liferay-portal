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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useFormik} from 'formik';
import React, {useState} from 'react';

import {HelpMessage, RequiredMark} from './utils/formComponents.es';
import Input from './utils/input.es';

const EditAdaptiveMedia = ({namespace}) => {
	const [automaticUuid, setAutomaticUuid] = useState(true);
	const [addHighResolution, setAddHighResolution] = useState(false);

	const formik = useFormik({
		initialValues: {
			name: '',
			description: '',
		},
		validate: (values) => {
			const errors = {};
			console.log(values);

			if (!values.name) {
				errors.name = Liferay.Language.get('this-field-is-required');
			}

			//number

			return errors;
		},
		onSubmit: (values) => {
			console.log(values);
		},
	});

	const {errors, handleChange, touched, values} = formik;

	return (
		<ClayForm onSubmit={formik.handleSubmit}>
			<Input
				error={touched.name && errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={handleChange}
				required
				value={values.name}
			/>

			<Input
				label={Liferay.Language.get('description')}
				name="description"
				onChange={handleChange}
				type="textarea"
				value={values.description}
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
							label={Liferay.Language.get('max-width-px')}
							name="maxWidth"
							onChange={handleChange}
							type="number"
							value={values.maxWidth}
						/>
					</ClayLayout.Col>
					<ClayLayout.Col md="3">
						<Input
							label={Liferay.Language.get('max-height-px')}
							name="maxHeight"
							onChange={handleChange}
							type="number"
							value={values.maxHeight}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<ClayCheckbox
					checked={addHighResolution}
					label={Liferay.Language.get(
						'add-a-resolution-for-high-density-displays'
					)}
					onChange={() => setAddHighResolution(!addHighResolution)}
				/>
			</div>

			<div className="sheet-section">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('identifier')}
				</h3>

				<ClayRadioGroup
					name="automaticUuid"
					onSelectedValueChange={setAutomaticUuid}
					selectedValue={automaticUuid}
				>
					<ClayRadio
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
						label={Liferay.Language.get('custom')}
						value={false}
					/>
				</ClayRadioGroup>

				<Input
					disabled={automaticUuid}
					label={Liferay.Language.get('id')}
					name="newUuid"
					onChange={handleChange}
					value={values.id}
				/>
			</div>

			<ClayButton.Group spaced>
				<ClayButton name="submitBtn" type="submit">
					Save
				</ClayButton>
			</ClayButton.Group>
		</ClayForm>
	);
};

export default EditAdaptiveMedia;
