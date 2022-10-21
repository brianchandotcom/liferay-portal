/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import {
	API,
	Card,
	Input,
	InputLocalized,
	SidePanelForm,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {useEffect} from 'react';

import {useListTypeForm} from './ListTypeFormBase';
import ListTypeTable from './ListTypeTable';
import {fixLocaleKeys} from './utils';

export default function EditListTypeDefinition({
	listTypeDefinitionId,
	readOnly,
}: IProps) {
	const onSubmit = async (values: PickList) => {
		try {
			await API.updatePickList({
				externalReferenceCode: Liferay.FeatureFlags['LPS-164278']
					? values.externalReferenceCode
					: '',
				id: parseInt(listTypeDefinitionId, 10),
				name_i18n: values.name_i18n,
			});
			saveAndReload();

			openToast({
				message: Liferay.Language.get(
					'the-picklist-was-updated-successfully'
				),
			});
		}
		catch ({message}) {
			openToast({message: message as string, type: 'danger'});
		}
	};

	const {errors, handleSubmit, setValues, values} = useListTypeForm({
		initialValues: {},
		onSubmit,
	});

	useEffect(() => {
		API.getPickList(parseInt(listTypeDefinitionId, 10)).then((response) => {
			response.name_i18n = fixLocaleKeys(response.name_i18n);
			response.listTypeEntries = response.listTypeEntries.map((item) => ({
				...item,
				name_i18n: fixLocaleKeys(item.name_i18n),
			}));
			setValues(response);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('picklist')}
		>
			{Object.keys(values).length !== 0 && (
				<>
					<Card title={Liferay.Language.get('basic-info')}>
						<InputLocalized
							disabled={readOnly}
							error={errors.name_i18n}
							label={Liferay.Language.get('name')}
							onChange={(name_i18n) => setValues({name_i18n})}
							required
							translations={
								values.name_i18n as LocalizedValue<string>
							}
						/>

						{Liferay.FeatureFlags['LPS-164278'] && (
							<Input
								autoComplete="off"
								error={errors.externalReferenceCode}
								feedbackMessage={Liferay.Language.get(
									'internal-key-to-reference-the-object-definition'
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
						)}
					</Card>

					<Card title={Liferay.Language.get('items')}>
						<div className="container-fluid container-fluid-max-xl">
							<ClayAlert displayType="info" title="Info">
								{Liferay.Language.get(
									'updating-or-deleting-a-picklist-item-will-automatically-update-every-entry-that-is-using-the-specific-item-value'
								)}
							</ClayAlert>
						</div>

						{values.id && <ListTypeTable pickListId={values.id} />}
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
