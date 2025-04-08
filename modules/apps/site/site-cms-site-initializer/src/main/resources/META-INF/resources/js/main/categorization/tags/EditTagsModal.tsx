/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {openConfirmModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {executeAsyncItemAction} from '../../FDSPropsTransformer/utils/executeAsyncItemAction';
import {FieldText} from '../../components/forms';
import {required, validate} from '../../components/forms/validations';
import CategorizationSpaces from '../components/CategorizationSpaces';

export default function EditTagsModalContent({
	assetLibraries,
	closeModal,
	editTagURL,
	loadData,
	tagId,
	tagName,
}: {
	assetLibraries: any;
	closeModal: () => void;
	editTagURL: string;
	loadData: () => {};
	tagId: number;
	tagName: string;
}) {
	const [selectedSpaces, setSelectedSpaces] = useState<string[]>([]);
	const [spaceChange, setSpaceChange] = useState(false);

	const assetLibraryIds = selectedSpaces.map((number) => ({
		id: number,
	}));

	const updateTag = (values: any) => {
		const body = {
			assetLibraries: assetLibraryIds,
			name: values.tagName,
		};

		executeAsyncItemAction({
			method: 'PUT',
			refreshData: loadData,
			requestBody: JSON.stringify(body),
			successMessage: sub(
				Liferay.Language.get('x-was-updated-successfully'),
				`<strong>${Liferay.Util.escapeHTML(tagName)}</strong>`
			),
			url: editTagURL,
		});

		closeModal();
	};

	const {errors, handleChange, handleSubmit, touched, values} = useFormik({
		initialValues: {
			tagId,
			tagName,
		},
		onSubmit: (values) => {
			if (spaceChange) {
				openConfirmModal({
					message: Liferay.Language.get(
						'removing-a-space-will-make-the-tag-unavailable'
					),
					onConfirm: (isConfirm: boolean) => {
						if (isConfirm) {
							updateTag(values);
						}
					},
					status: 'info',
					title: Liferay.Language.get('confirm-space-change'),
				});
			}
			else {
				updateTag(values);
			}
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

				<CategorizationSpaces
					assetLibraries={assetLibraries}
					checkboxText="tag"
					setSelectedSpaces={setSelectedSpaces}
					setSpaceChange={setSpaceChange}
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

						<ClayButton displayType="primary" type="submit">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}
