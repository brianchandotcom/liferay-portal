/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {executeAsyncItemAction} from '../../FDSPropsTransformer/utils/executeAsyncItemAction';
import {FieldText} from '../../components/forms';
import {required, validate} from '../../components/forms/validations';
import CategorizationSpaces from '../components/CategorizationSpaces';

const FDS_EVENT_UPDATE_DISPLAY = 'fds-update-display';

export default function CreateTagsModalContent({
	closeModal,
	dataSetId,
}: {
	closeModal: () => void;
	dataSetId: string;
}) {
	const [selectedSpaces, setSelectedSpaces] = useState<string[]>([]);

	const assetLibraries = selectedSpaces.map((number) => ({
		id: number,
	}));

	const {errors, handleChange, handleSubmit, resetForm, touched, values} =
		useFormik({
			initialValues: {
				assetLibraries: [],
				tagName: '',
			},
			onSubmit: (values) => {
				const url = '/o/headless-admin-taxonomy/v1.0/keywords';

				const body = {
					assetLibraries,
					name: values.tagName,
				};

				executeAsyncItemAction({
					method: 'POST',
					requestBody: JSON.stringify(body),
					successMessage: sub(
						Liferay.Language.get('x-was-created-successfully'),
						`<strong>${Liferay.Util.escapeHTML(values.tagName)}</strong>`
					),
					url,
				}).then(() =>
					Liferay.fire(FDS_EVENT_UPDATE_DISPLAY, {id: dataSetId})
				);

				resetForm();
			},
			validate: (values) => {
				validate(
					{
						assetLibraries: [required],
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

				<CategorizationSpaces
					checkboxText="tag"
					setSelectedSpaces={setSelectedSpaces}
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

						<ClayButton displayType="secondary" type="submit">
							{Liferay.Language.get('save-and-add-another')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={closeModal}
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
