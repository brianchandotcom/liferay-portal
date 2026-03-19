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
	closeModal,
	dataSetId,
	entryIdKey,
	redirectURL: baseRedirectURL,
}: {
	closeModal: () => void;
	dataSetId: string;
	entryIdKey: string;
	redirectURL: string;
}) {
	const [nameInputError, setNameInputError] = useState<string>('');
	const [close, setClose] = useState(false);

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
		onSubmit: (values) => {
			const url = '/o/headless-asset-library/v1.0/asset-libraries';
			const body = {
				description: values.designLibraryDescription,
				name: values.designLibraryName,
				settings: {},
				type: 'DesignLibrary',
			};

			return fetch(url, {
				body: JSON.stringify(body),
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'POST',
			})
				.then(async (response) => {
					const data = await response.json().catch(() => ({}));

					if (!response.ok) {
						throw {
							error: data.title || 'UNKNOWN_ERROR',
							status: data.status || response.status,
						};
					}

					return data;
				})
				.then((data) => {
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

					if (close) {
						closeModal();
					}

					const redirectURL = createPortletURL(baseRedirectURL, {
						[entryIdKey]: (data as {id: string}).id,
					});

					navigate(redirectURL);
				})
				.catch(({error}) => {
					if (error === 'Please enter a unique name.') {
						setNameInputError(
							Liferay.Language.get(
								'please-enter-a-unique-name.-this-one-is-already-in-use'
							)
						);

						openToast({
							message: Liferay.Language.get(
								'please-enter-a-unique-name.-this-one-is-already-in-use'
							),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});
					}
					else {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});

						if (close) {
							closeModal();
						}
					}

					throw new Error(
						`POST request failed to create a new Design Library with name ${body.name}`
					);
				});
		},
		validate: (values) => {
			const errors = validate(
				{
					designLibraryName: [required, maxLength(75)],
				},
				values
			);

			return errors;
		},
	});

	const shouldDisableSaveBtn = isSubmitting || !values.designLibraryName;

	const errorMessage = sub(
		Liferay.Language.get('the-x-field-is-required'),
		Liferay.Language.get('name')
	);

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

		return errorMessage;
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
								onClick={closeModal}
								type="button"
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={shouldDisableSaveBtn}
								displayType="primary"
								onClick={() => setClose(true)}
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
