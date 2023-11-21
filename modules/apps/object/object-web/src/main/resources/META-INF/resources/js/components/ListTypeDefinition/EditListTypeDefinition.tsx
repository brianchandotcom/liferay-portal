/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {
	API,
	Card,
	Input,
	SidePanelForm,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useEffect} from 'react';

import {useListTypeForm} from './ListTypeFormBase';
import ListTypeTable from './ListTypeTable';
import {fixLocaleKeys} from './utils';

export default function EditListTypeDefinition({
	listTypeDefinitionId,
	readOnly,
}: IProps) {
	const onSubmit = async (values: ListTypeDefinition) => {
		try {
			await API.putListTypeDefinition({
				externalReferenceCode: values.externalReferenceCode,
				id: parseInt(listTypeDefinitionId, 10),
				listTypeEntries: values.listTypeEntries,
				name_languageId: values.name_languageId,
			});
			saveAndReload();

			openToast({
				message: Liferay.Language.get(
					'the-picklist-was-updated-successfully'
				),
			});
		}
		catch (error: unknown) {
			const {message} = error as Error;

			openToast({message, type: 'danger'});
		}
	};

	const {errors, handleSubmit, setValues, values} = useListTypeForm({
		initialValues: {},
		onSubmit,
	});

	useEffect(() => {
		API.getListTypeDefinition(parseInt(listTypeDefinitionId, 10)).then(
			(response) => {
				response.name_languageId = fixLocaleKeys(
					response.name_languageId
				);
				response.listTypeEntries = response.listTypeEntries.map(
					(item) => ({
						...item,
						name_languageId: fixLocaleKeys(item.name_languageId),
					})
				);
				setValues(response);
			}
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('picklist')}
		>
			{Object.keys(values).length !== 0 && (
				<>
					<Card title={Liferay.Language.get('basic-info')}>
						<InputLocalized
							disabled={readOnly}
							error={errors.name_languageId}
							label={Liferay.Language.get('name')}
							onChange={(name_languageId) =>
								setValues({name_languageId})
							}
							required
							translations={
								values.name_languageId as LocalizedValue<string>
							}
						/>

						<Input
							autoComplete="off"
							disabled={values?.system}
							error={errors.externalReferenceCode}
							feedbackMessage={Liferay.Language.get(
								'unique-key-for-referencing-the-picklist-definition'
							)}
							label={Liferay.Language.get(
								'external-reference-code'
							)}
							onChange={({target: {value}}) => {
								setValues({
									externalReferenceCode: value,
								});
							}}
							required
							value={values.externalReferenceCode}
						/>
					</Card>

					<Card title={Liferay.Language.get('items')}>
						<div className="container-fluid container-fluid-max-xl">
							<ClayAlert displayType="info" title="Info">
								{Liferay.Language.get(
									'updating-or-deleting-a-picklist-item-will-automatically-update-every-entry-that-is-using-the-specific-item-value'
								)}
							</ClayAlert>
						</div>

						{values.id && (
							<ListTypeTable
								pickListId={values.id}
								readOnly={readOnly}
								setValues={setValues}
								values={values}
							/>
						)}
					</Card>
				</>
			)}
		</SidePanelForm>
	);
}

interface IProps {
	listTypeDefinitionId: string;
	readOnly: boolean;
}
