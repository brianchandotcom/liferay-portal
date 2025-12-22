/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {FieldText} from '../../../common/components/forms';
import {
	invalidCharacters,
	maxLength,
	required,
	validate,
} from '../../../common/components/forms/validations';
import ApiHelper from '../../../common/services/ApiHelper';
import {
	displayErrorToast,
	displayNameInUseErrorToast,
} from '../../../common/utils/toastUtil';
import CategorizationSpaces from '../components/CategorizationSpaces';

const FDS_EVENT_UPDATE_DISPLAY = 'fds-update-display';

export default function CreateTagsModalContent({
	closeModal,
	cmsGroupId,
	dataSetId,
	invalidTagCharacters,
}: {
	closeModal: () => void;
	cmsGroupId: number;
	dataSetId: string;
	invalidTagCharacters: string;
}) {
	const [nameInputError, setNameInputError] = useState<string>('');
	const [selectedSpaces, setSelectedSpaces] = useState<string[]>([]);
	const [spaceInputError, setSpaceInputError] = useState('');
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
			assetLibraries: [],
			tagName: '',
		},
		onSubmit: (values) => {
			const url = `/o/headless-admin-taxonomy/v1.0/sites/${cmsGroupId}/keywords`;
			const body = {
				assetLibraries: selectedSpaces.map((string) => ({
					scopeKey: string,
				})),
				name: values.tagName,
			};

			ApiHelper.post(url, body).then(({error, status}) => {
				if (error) {
					if (status === 'CONFLICT') {
						setNameInputError(
							Liferay.Language.get(
								'please-enter-a-unique-name.-this-one-is-already-in-use'
							)
						);

						displayNameInUseErrorToast();
					}
					else if (
						error === 'Keyword name cannot be an empty string'
					) {
						setNameInputError(
							Liferay.Language.get('this-field-is-required')
						);
					}
					else {
						displayErrorToast();

						resetForm();
						setNameInputError('');

						if (close) {
							closeModal();
						}
					}

					throw new Error(
						`POST request failed to create a new tag with name ${body.name} in the following asset libraries: ${JSON.stringify(body.assetLibraries)}`
					);
				}
				else {
					openToast({
						message: sub(
							Liferay.Language.get('x-was-created-successfully'),
							`<strong>${Liferay.Util.escapeHTML(values.tagName)}</strong>`
						),
						type: 'success',
					});

					Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {id: dataSetId});

					resetForm();
					setNameInputError('');

					if (close) {
						closeModal();
					}
				}
			});
		},
		validate: (values) => {
			const errors = validate(
				{
					assetLibraries: [required],
					tagName: [
						required,
						invalidCharacters(invalidTagCharacters.split('')),
						maxLength(75),
					],
				},
				values
			);
			if (spaceInputError) {
				errors.assetLibraries = spaceInputError;
			}

			return errors;
		},
	});

	const shouldDisableSaveBtn =
		isSubmitting || !values.tagName || !!spaceInputError;

	const errorMessage = sub(
		Liferay.Language.get('the-x-field-is-required'),
		Liferay.Language.get('name')
	);

	const handleNameInputErrorMessage = () => {
		if (nameInputError) {
			return nameInputError;
		}

		if (
			values.tagName.length !== 0 ||
			!touched.tagName ||
			!values.tagName.trim().length
		) {
			return errors.tagName;
		}

		return errorMessage;
	};

	return (
		<ClayForm onSubmit={handleSubmit}>
			<div className="categorization-modal">
				<ClayModal.Header
					closeButtonAriaLabel={Liferay.Language.get('close')}
				>
					{Liferay.Language.get('new-tag')}
				</ClayModal.Header>

				<ClayModal.Body>
					<FieldText
						errorMessage={handleNameInputErrorMessage()}
						label={Liferay.Language.get('name')}
						name="tagName"
						onBlur={handleBlur}
						onChange={(event) => {
							setNameInputError('');
							handleChange(event);
						}}
						required
						value={values.tagName}
					/>

					<CategorizationSpaces
						checkboxText="tag"
						setSelectedSpaces={setSelectedSpaces}
						setSpaceInputError={setSpaceInputError}
						spaceInputError={spaceInputError}
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
								displayType="secondary"
								type="submit"
							>
								{Liferay.Language.get('save-and-add-another')}
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
