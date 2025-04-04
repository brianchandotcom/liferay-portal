/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {fetch, navigate} from 'frontend-js-web';
import React from 'react';

import {FieldText} from '../../components/forms';
import {required, validate} from '../../components/forms/validations';
import CategorizationSpaces from '../components/CategorizationSpaces';

export default function CreationTagModalContent({
	assetLibraryId,
	tagsURL,
}: {
	assetLibraryId?: string;
	tagsURL: string;
}) {
	const {errors, handleChange, handleSubmit, resetForm, touched, values} =
		useFormik({
			initialValues: {
				assetLibraryIds: [],
				tagName: '',
			},
			onSubmit: (values) => {
				const url =
					'/o/headless-admin-taxonomy/v1.0/asset-libraries/' +
					assetLibraryId +
					'/keywords';

				const body = {
					name: values.tagName,
				};

				fetch(url, {
					body: JSON.stringify(body),
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'application/json',
					},
					method: 'POST',
				})
					.then((response) => {
						if (response.ok) {
							openToast({
								message: Liferay.Language.get(
									'your-request-completed-successfully'
								),
								title: Liferay.Language.get('success'),
								type: 'success',
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
				resetForm();
			},
			validate: (values) => {
				validate(
					{
						assetLibraryIds: [required],
						tagName: [required],
					},
					values
				);
			},
		});

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{Liferay.Language.get('new-tag')}
			</ClayModal.Header>

			<ClayModal.Body>
				<FieldText
					errorMessage={touched.tagName ? errors.tagName : undefined}
					label={Liferay.Language.get('name')}
					name="tagName"
					onChange={handleChange}
					required
					value={values.tagName}
				/>

				<CategorizationSpaces checkboxText="tag" />
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => navigate(tagsURL)}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="secondary" type="submit">
							{Liferay.Language.get('save-and-add-another')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => navigate(tagsURL)}
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}
