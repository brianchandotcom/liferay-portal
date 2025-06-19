/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {useFormik} from 'formik';
import {openToast, useId} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import React from 'react';

import SpaceService from '../../services/SpaceService';
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
		submitForm,
		touched,
		values,
	} = useFormik({
		initialValues: {
			description: space.description,
			erc: space.externalReferenceCode,
			logoColor: space.settings?.logoColor as LogoColor,
			name: space.name,
		},
		onSubmit: async (values) => {
			const {description, erc, logoColor = 'outline-0', name} = values;

			const {data, error} = await SpaceService.updateSpace({
				description,
				erc,
				name,
				settings: {logoColor},
			});

			if (error) {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred-while-saving-the-space'
					),
					type: 'danger',
				});
			}
			else if (data) {
				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-saved-successfully'),
						name
					),
					type: 'success',
				});
			}
		},
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

	const onSave = () => {
		submitForm();
	};

	const onCancel = () => {
		const url = new URL(window.location.href);
		const redirect = url.searchParams.get('redirect');

		if (redirect) {
			navigate(redirect);
		}
	};

	return (
		<form
			className="container-fluid container-fluid-max-md p-0 p-md-4"
			onSubmit={handleSubmit}
		>
			<Panel title={Liferay.Language.get('general')}>
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
			</Panel>

			<ClayButton.Group className="mt-2" spaced>
				<ClayButton onClick={onSave}>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</form>
	);
}

function Panel({children, title}: {children: React.ReactNode; title: string}) {
	return (
		<ClayPanel
			aria-label={title}
			className="mb-4"
			collapsable
			defaultExpanded={true}
			displayTitle={
				<ClayPanel.Title>
					<h2 className="mb-0 py-2 text-6 text-dark">{title}</h2>
				</ClayPanel.Title>
			}
			displayType="secondary"
			role="group"
			showCollapseIcon
		>
			<div className="pt-4 px-4">{children}</div>
		</ClayPanel>
	);
}
