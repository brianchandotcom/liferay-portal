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
	Card,
	FormCustomSelect,
	Input,
	InputLocalized,
	SidePanelForm,
	closeSidePanel,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {updateRelationship} from '../../utils/api';
import {
	availableLocales,
	defaultLanguageId,
	defaultLocale,
} from '../../utils/locale';
import {firstLetterUppercase} from '../../utils/string';
import {
	ObjectRelationshipFormBase,
	ObjectRelationshipType,
	useObjectRelationshipForm,
} from './ObjectRelationshipFormBase';
import SelectRelationship from './SelectRelationship';

export default function EditRelationship({
	deletionTypes,
	hasUpdateObjectDefinitionPermission,
	objectRelationship: initialValues,
	parameterEndpoint,
	parameterRequired,
}: IProps) {
	const [selectedLocale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	const onSubmit = async (objectRelationship: ObjectRelationship) => {
		try {
			await updateRelationship(objectRelationship);
			closeSidePanel();

			openToast({
				message: Liferay.Language.get(
					'the-object-relationship-was-updated-successfully'
				),
			});
		}
		catch ({message}) {
			openToast({message: message as string, type: 'danger'});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectRelationshipForm({
		initialValues,
		onSubmit,
		parameterRequired,
	});

	const readOnly = !hasUpdateObjectDefinitionPermission || values.reverse;

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('relationship')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				{values.reverse && (
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
					defaultLanguageId={defaultLanguageId}
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					locales={availableLocales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(label) => setValues({label})}
					required
					selectedLocale={selectedLocale}
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectRelationshipFormBase
					errors={errors}
					handleChange={handleChange}
					readonly
					setValues={setValues}
					values={values}
				/>

				<FormCustomSelect
					disabled={readOnly}
					label={Liferay.Language.get('deletion-type')}
					onChange={(deletionType) =>
						setValues({deletionType: deletionType.value})
					}
					options={deletionTypes}
					required
					value={firstLetterUppercase(values.deletionType as string)}
				/>
			</Card>

			{Liferay.FeatureFlags['LPS-155537'] &&
				parameterRequired &&
				values.type === ObjectRelationshipType.ONE_TO_MANY && (
					<Card title={Liferay.Language.get('parameters')}>
						<Input
							label={Liferay.Language.get('api-endpoint')}
							readOnly
							value={parameterEndpoint}
						/>

						<SelectRelationship
							error={errors.parameterObjectFieldId}
							objectDefinitionId={values.objectDefinitionId2}
							onChange={(parameterObjectFieldId) =>
								setValues({parameterObjectFieldId})
							}
							value={values.parameterObjectFieldId}
						/>
					</Card>
				)}
		</SidePanelForm>
	);
}

interface IProps {
	deletionTypes: TDeletionType[];
	hasUpdateObjectDefinitionPermission: boolean;
	objectRelationship: ObjectRelationship;
	parameterEndpoint: string;
	parameterRequired: boolean;
}

type TDeletionType = {
	label: string;
	value: string;
};
