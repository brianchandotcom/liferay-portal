/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {FormikHelpers, useFormik} from 'formik';
import {FieldBase} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import SpaceSelector from '../../common/components/SpaceSelector';
import {FieldText} from '../../common/components/forms';
import {required, validate} from '../../common/components/forms/validations';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {Space} from '../../common/types/Space';
import {AssetData} from '../props_transformer/actions/createAssetAction';
import {FolderData} from '../props_transformer/actions/createFolderAction';

type Props = {
	action: AssetData['action'] | FolderData['action'];
	assetLibraries: AssetLibrary[];
	closeModal: () => void;
	onSubmit?: (
		values: {
			groupId: number;
			name: string;
		},
		formikHelpers: FormikHelpers<{
			groupId: number;
			name: string;
		}>
	) => Promise<any> | void;
	redirect?: string;
	title: string;
};

export default function CreationModalContent({
	action,
	assetLibraries,
	closeModal,
	onSubmit,
	redirect,
	title,
}: Props) {
	const [space, setSpace] = useState<Space>();

	const groupIdInputId = `${uuidv4()}groupId`;

	const {
		errors,
		handleChange,
		handleSubmit,
		isSubmitting,
		setFieldValue,
		touched,
		values,
	} = useFormik({
		initialValues: {
			groupId:
				assetLibraries.length === 1 ? assetLibraries[0].groupId : 0,
			name: '',
		},
		onSubmit: async (values, formikHelpers) => {
			if (redirect) {
				const {groupId, name} = values;

				const url = new URL(redirect);

				url.searchParams.set('name', name);
				url.searchParams.set('groupId', String(groupId));

				navigate(url.pathname + url.search);

				return;
			}

			if (onSubmit) {
				await onSubmit(values, formikHelpers);
			}
		},
		validate: (values) =>
			validate(
				{
					groupId: [required],
					name: action === 'createFolder' ? [required] : [],
				},
				values
			),
	});

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{title}
			</ClayModal.Header>

			<ClayModal.Body>
				{action === 'createFolder' ? (
					<FieldText
						errorMessage={touched.name ? errors.name : undefined}
						label={Liferay.Language.get('name')}
						name="name"
						onChange={handleChange}
						required
						value={values.name}
					/>
				) : null}

				{assetLibraries.length > 1 && (
					<FieldBase
						errorMessage={
							touched.groupId ? errors.groupId : undefined
						}
						helpMessage={sub(
							Liferay.Language.get('choose-the-space-for-the-x'),
							title
						)}
						id={groupIdInputId}
						label={Liferay.Language.get('space')}
						required
					>
						<SpaceSelector
							id={groupIdInputId}
							onSpaceChange={(space) => {
								setFieldValue(
									'groupId',
									space ? space.siteId : null
								);
								setSpace(space);
							}}
							space={space}
						/>
					</FieldBase>
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

						<ClayButton
							disabled={isSubmitting}
							displayType="primary"
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
