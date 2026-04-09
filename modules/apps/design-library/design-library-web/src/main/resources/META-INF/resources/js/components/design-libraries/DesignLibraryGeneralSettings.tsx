/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {useFormik} from 'formik';
import {openToast, useId} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import DesignLibraryService from '../../services/DesignLibraryService';
import {DesignLibrary} from '../../types';
import {FieldText} from '../forms';
import {Errors, maxLength, required, validate} from '../forms/validations';
import focusInvalidElement from '../utils/focusInvalidElement';

export default function DesignLibraryGeneralSettings({
	backURL,
	designLibrary,
	groupId,
	setDesignLibrary,
}: {
	backURL?: string;
	designLibrary: DesignLibrary;
	groupId: string;
	setDesignLibrary?: React.Dispatch<React.SetStateAction<any>>;
}) {
	const [initialERC, setInitialERC] = useState(
		designLibrary.externalReferenceCode
	);

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
			description: designLibrary.description,
			erc: initialERC,
			name: designLibrary.name,
		},
		onSubmit: async (values) => {
			const {description, erc, name} = values;
			try {
				const data = await DesignLibraryService.update(initialERC, {
					description,
					externalReferenceCode: erc,
					name,
				});

				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-saved-successfully'),
						name
					),
					type: 'success',
				});

				const updatedDesignLibrary = data as DesignLibrary;

				if (setDesignLibrary) {
					setDesignLibrary(updatedDesignLibrary);
					setInitialERC(updatedDesignLibrary.externalReferenceCode);
				}
			}
			catch (error: any) {
				const errorMessage =
					error?.title ??
					error?.message ??
					Liferay.Language.get(
						'an-unexpected-error-occurred-while-saving-the-design-library'
					);

				openToast({
					message: errorMessage,
					type: 'danger',
				});
			}
		},
		validate: (values): Errors =>
			validate(
				{
					erc: [required],
					name: [required, maxLength(75)],
				},
				values,
				errors
			),
	});

	const onSave = () => {
		if (Object.keys(errors).length) {
			focusInvalidElement();

			return;
		}

		submitForm();
	};

	const onCancel = () => {
		if (backURL) {
			navigate(backURL);
		}
		else {
			window.history.back();
		}
	};

	return (
		<form
			className="container-fluid container-fluid-max-md p-0 p-md-4"
			onSubmit={handleSubmit}
		>
			<ClayPanel
				aria-label={Liferay.Language.get('general')}
				className="mb-4"
				collapsable
				defaultExpanded={true}
				displayTitle={
					<ClayPanel.Title>
						<h2 className="mb-0 py-2 text-6 text-dark">
							{Liferay.Language.get('general')}
						</h2>
					</ClayPanel.Title>
				}
				displayType="secondary"
				role="group"
				showCollapseIcon
			>
				<div className="pt-4 px-4">
					<FieldText
						errorMessage={touched.name ? errors?.name : undefined}
						label={Liferay.Language.get('name')}
						name="name"
						onBlur={handleBlur}
						onChange={handleChange}
						placeholder={Liferay.Language.get(
							'enter-a-design-library-name'
						)}
						required
						value={values.name}
					/>

					<ClayForm.Group>
						<label htmlFor={`${id}description`}>
							{Liferay.Language.get('description')}
						</label>

						<ClayInput
							component="textarea"
							id={`${id}description`}
							onChange={(event) =>
								setFieldValue('description', event.target.value)
							}
							value={values.description}
						/>
					</ClayForm.Group>

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
							'unique-key-for-referencing-the-design-library-definition'
						)}
						label={Liferay.Language.get('erc')}
						name="erc"
						onBlur={handleBlur}
						onChange={handleChange}
						required
						value={values.erc}
					/>
				</div>
			</ClayPanel>

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
