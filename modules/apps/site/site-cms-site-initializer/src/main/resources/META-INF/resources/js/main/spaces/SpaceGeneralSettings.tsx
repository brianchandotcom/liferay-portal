/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {useFormik} from 'formik';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import {Space} from '../../types/Space';
import {LogoColor} from '../components/SpaceSticker';
import {FieldText} from '../components/forms';
import {
	invalidCharacters,
	maxLength,
	nonNumeric,
	notNull,
	required,
	validate,
} from '../components/forms/validations';
import SpaceBaseFields from './SpaceBaseFields';

export default function SpaceGeneralSettings({
	groupId,
	space,
}: {
	groupId: string;
	space: Space;
}) {
	const id = useId();

	const {
		errors,
		handleBlur,
		handleChange,
		handleSubmit,
		setFieldValue,
		touched,
		values,
	} = useFormik({
		initialValues: {
			description: space.description,
			erc: space.externalReferenceCode,
			logoColor: space.settings?.logoColor as LogoColor,
			name: space.name,
		},
		onSubmit: () => {},
		validate: (values) =>
			validate(
				{
					erc: [required],
					name: [
						required,
						nonNumeric,
						notNull,
						invalidCharacters(['*']),
						maxLength(150),
					],
				},
				values
			),
	});

	return (
		<form
			className="container-fluid container-fluid-max-md p-0 p-md-4"
			onSubmit={handleSubmit}
		>
			<ClayPanel title={Liferay.Language.get('general')}>
				<SpaceBaseFields
					errors={errors}
					onBlurName={handleBlur}
					onChangeDescription={(value) =>
						setFieldValue('description', value)
					}
					onChangeLogoColor={(value) =>
						setFieldValue('logoColor', value)
					}
					onChangeName={handleChange}
					touched={touched}
					values={values}
				>
					<>
						<ClayForm.Group>
							<label htmlFor={`${id}groupId`}>
								{Liferay.Language.get('group-id')}
							</label>

							<ClayInput
								id={`${id}groupId`}
								readOnly
								value={groupId}
							/>
						</ClayForm.Group>

						<FieldText
							errorMessage={touched.erc ? errors?.erc : undefined}
							helpIcon={Liferay.Language.get(
								'unique-key-for-referencing-the-space-definition'
							)}
							label={Liferay.Language.get('erc')}
							name="erc"
							onBlur={handleBlur}
							onChange={handleChange}
							required
							value={values.erc}
						/>
					</>
				</SpaceBaseFields>
			</ClayPanel>
		</form>
	);
}
