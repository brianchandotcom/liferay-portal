/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {fetch, navigate, sub} from 'frontend-js-web';
import React from 'react';

import {FieldText} from '../../components/forms';
import {required, validate} from '../../components/forms/validations';
import CategorizationSpaces from '../components/CategorizationSpaces';

export default function CreationTagModalContent({
	tagId,
	tagName,
	tagsURL,
}: {
	tagId: number;
	tagName: string;
	tagsURL: string;
}) {
	const {errors, handleChange, handleSubmit, touched, values} = useFormik({
		initialValues: {
			tagId,
			tagName,
		},
		onSubmit: (values) => {
			const url = '/o/headless-admin-taxonomy/v1.0/keywords/' + tagId;

			const body = {
				name: values.tagName,
			};

			fetch(url, {
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PUT',
			})
				.then((response) => {
					if (response.ok) {
						openToast({
							autoClose: true,
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
		},
		validate: (values) => {
			validate(
				{
					tagId: [required],
					tagName: [required],
				},
				values
			);
		},
	});

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{sub(Liferay.Language.get('edit-x'), '"' + tagName + '"')}
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

				<CategorizationSpaces />
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
