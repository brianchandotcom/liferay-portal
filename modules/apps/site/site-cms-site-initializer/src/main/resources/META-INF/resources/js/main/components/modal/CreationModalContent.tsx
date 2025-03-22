/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {useFormik} from 'formik';
import {navigate, sub} from 'frontend-js-web';
import React from 'react';

import {FieldPicker, FieldText} from '../forms';
import {required, validate} from '../forms/validations';

export type AssetLibray = {
	id: string;
	name: string;
};

type Props = {
	assetLibraries: AssetLibray[];
	closeModal: () => void;
	redirect?: string;
	title: string;
};

export default function CreationModalContent({
	assetLibraries,
	closeModal,
	redirect,
	title,
}: Props) {
	const {errors, handleChange, handleSubmit, setFieldValue, touched, values} =
		useFormik({
			initialValues: {
				assetLibraryId:
					assetLibraries.length === 1 ? assetLibraries[0].id : '',
				name: '',
			},
			onSubmit: (values) => {
				if (redirect) {
					const {assetLibraryId, name} = values;

					const url = new URL(redirect);

					url.searchParams.set('name', name);
					url.searchParams.set('assetLibraryId', assetLibraryId);

					navigate(url.pathname + url.search);
				}
				else {
					alert(JSON.stringify(values, null, 4));
				}
			},
			validate: (values) =>
				validate(
					{
						assetLibraryId: [required],
						name: [required],
					},
					values
				),
		});

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<FieldText
					errorMessage={touched.name ? errors.name : undefined}
					label={Liferay.Language.get('name')}
					name="name"
					onChange={handleChange}
					required
					value={values.name}
				/>

				{assetLibraries.length > 1 && (
					<FieldPicker
						errorMessage={
							touched.assetLibraryId
								? errors.assetLibraryId
								: undefined
						}
						helpMessage={sub(
							Liferay.Language.get('choose-the-space-for-the-x'),
							title
						)}
						items={assetLibraries.map(({id, name}) => ({
							label: name,
							value: id,
						}))}
						label={Liferay.Language.get('space')}
						name="assetLibraryId"
						onSelectionChange={(value: string) => {
							setFieldValue('assetLibraryId', value);
						}}
						placeholder={Liferay.Language.get('select-a-space')}
						required
						selectedKey={values.assetLibraryId}
					/>
				)}
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
