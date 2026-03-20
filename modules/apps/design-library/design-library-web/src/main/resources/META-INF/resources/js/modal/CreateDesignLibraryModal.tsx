/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {createPortletURL, fetch, navigate, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {
	FieldText,
	maxLength,
	required,
	validate,
} from '../common/components/forms';

const FDS_EVENT_UPDATE_DISPLAY = 'fds-update-display';

export default function CreateDesignLibraryModal({
	dataSetId,
	entryIdKey,
	onClose,
	redirectURL: baseRedirectURL,
}: {
	dataSetId: string;
	entryIdKey: string;
	onClose: () => void;
	redirectURL: string;
}) {
	const [nameInputError, setNameInputError] = useState<string>('');

	const createDesignLibrary = async (name: string, description: string) => {
		const url = '/o/headless-asset-library/v1.0/asset-libraries';
		const body = {
			description,
			name,
			type: 'DesignLibrary',
		};

		const response = await fetch(url, {
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		const data = await response.json().catch(() => ({}));

		if (!response.ok) {
			throw {
				error: data.title || 'UNKNOWN_ERROR',
				status: data.status || response.status,
			};
		}

		return data as {id: string};
	};

	const {
		errors,
		handleBlur,
		handleChange,
		handleSubmit,
		isSubmitting,
		resetForm,
		touched,
		values,
	} = useFormik({
		initialValues: {
			designLibraryDescription: '',
			designLibraryName: '',
		},
		onSubmit: async (values) => {
			try {
				const data = await createDesignLibrary(
					values.designLibraryName,
					values.designLibraryDescription
				);

				openToast({
					message: sub(
						Liferay.Language.get('x-was-created-successfully'),
						`<strong>${Liferay.Util.escapeHTML(values.designLibraryName)}</strong>`
					),
					type: 'success',
				});

				Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {id: dataSetId});

				resetForm();
				setNameInputError('');

				navigate(
					createPortletURL(baseRedirectURL, {
						[entryIdKey]: (data as {id: string}).id,
					})
				);
			}
			catch (error: any) {
				const errorMessage =
					error?.error ||
					Liferay.Language.get('an-unexpected-error-occurred');

				if (error?.error) {
					setNameInputError(errorMessage);
				}

				openToast({
					message: errorMessage,
					title: Liferay.Language.get('error'),
					type: 'danger',
				});

				throw new Error(
					`POST request failed to create a new Design Library with name ${values.designLibraryName}`
				);
			}
		},
		validate: (values) => {
			return validate(
				{
					designLibraryName: [required, maxLength(75)],
				},
				values
			);
		},
	});

	const shouldDisableSaveButton = isSubmitting || !values.designLibraryName;

	const handleNameInputErrorMessage = () => {
		if (nameInputError) {
			return nameInputError;
		}

		if (
			values.designLibraryName.length !== 0 ||
			!touched.designLibraryName ||
			!values.designLibraryName.trim().length
		) {
			return errors.designLibraryName;
		}

		return sub(
			Liferay.Language.get('the-x-field-is-required'),
			Liferay.Language.get('name')
		);
	};

	return (
		<ClayForm onSubmit={handleSubmit}>
			<div className="categorization-modal">
				<ClayModal.Header
					closeButtonAriaLabel={Liferay.Language.get('close')}
				>
					{Liferay.Language.get('new-design-library')}
				</ClayModal.Header>

				<ClayModal.Body>
					<FieldText
						errorMessage={handleNameInputErrorMessage()}
						label={Liferay.Language.get('name')}
						name="designLibraryName"
						onBlur={handleBlur}
						onChange={(event) => {
							setNameInputError('');
							handleChange(event);
						}}
						required
						value={values.designLibraryName}
					/>

					<FieldText
						component="textarea"
						label={Liferay.Language.get('description')}
						name="designLibraryDescription"
						onBlur={handleBlur}
						onChange={(event) => {
							handleChange(event);
						}}
						value={values.designLibraryDescription}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
								type="button"
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={shouldDisableSaveButton}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</div>
		</ClayForm>
	);
}
