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
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import useForm, {invalidateRequired} from '../hooks/useForm';
import {
	availableLocales,
	defaultLanguageId,
	defaultLocale,
} from '../utils/locale';
import {objectRelationshipTypes} from '../utils/objectRelationshipTypes';
import {firstLetterUppercase} from '../utils/string';
import Card from './Card/Card';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import InputLocalized from './Form/InputLocalized/InputLocalized';
import {SidePanelForm, closeSidePanel, openToast} from './SidePanelContent';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

export default function EditObjectRelationship({
	deletionTypes,
	hasUpdateObjectDefinitionPermission,
	isReverse,
	objectRelationship: initialValues,
}: IProps) {
	const [selectedLocale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	const selectedType = objectRelationshipTypes.find(
		(relationshipType) => relationshipType.value === initialValues.type
	);

	const onSubmit = async (objectRelationship: TObjectRelationship) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-relationships/${objectRelationship.objectRelationshipId}`,
			{
				body: JSON.stringify({
					deletionType: objectRelationship.deletionType,
					label: objectRelationship.label,
				}),
				headers: HEADERS,
				method: 'PUT',
			}
		);

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			closeSidePanel();

			openToast({
				message: Liferay.Language.get(
					'the-object-relationship-was-updated-successfully'
				),
			});
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = (await response.json()) as any;

			openToast({
				message: title,
				type: 'danger',
			});
		}
	};

	const validate = (value: TObjectRelationship) => {
		const errors: {deletionType?: string; label?: string} = {};

		if (invalidateRequired(value.label[defaultLanguageId])) {
			errors.label = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	const readOnly = !hasUpdateObjectDefinitionPermission || isReverse;

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('relationship')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				{isReverse && (
					<ClayAlert
						displayType="warning"
						title={`${Liferay.Language.get('warning')}:`}
					>
						{Liferay.Language.get(
							'reverse-object-relationships-cannot-be-updated'
						)}
					</ClayAlert>
				)}

				<InputLocalized
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					locales={availableLocales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(label) => setValues({label})}
					required
					selectedLocale={selectedLocale}
					translations={values.label}
				/>

				<Input
					disabled
					label={Liferay.Language.get('name')}
					required
					value={initialValues.name}
				/>

				<CustomSelect
					disabled
					label={Liferay.Language.get('type')}
					options={objectRelationshipTypes}
					required
					value={selectedType?.label}
				/>

				<CustomSelect
					disabled
					label={Liferay.Language.get('object')}
					options={[]}
					required
					value={initialValues.objectDefinitionName2}
				/>

				<CustomSelect
					disabled={readOnly}
					label={Liferay.Language.get('deletion-type')}
					onChange={(deletionType) =>
						setValues({deletionType: deletionType.value})
					}
					options={deletionTypes}
					required
					value={firstLetterUppercase(values.deletionType)}
				/>
			</Card>
		</SidePanelForm>
	);
}

interface IProps {
	deletionTypes: TDeletionType[];
	hasUpdateObjectDefinitionPermission: boolean;
	isReverse: boolean;
	objectRelationship: TObjectRelationship;
}

type TDeletionType = {
	label: string;
	value: string;
};

type TName = {
	[key: string]: string;
};

type TObjectRelationship = {
	deletionType: string;
	label: TName;
	name: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	objectDefinitionName2: string;
	objectRelationshipId: number;
	type: string;
};
