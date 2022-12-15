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
	SingleSelect,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React from 'react';

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
	const onSubmit = async (objectRelationship: ObjectRelationship) => {
		try {
			await API.updateRelationship(objectRelationship);
			saveAndReload();

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
			customLabel={{
				displayType: values.reverse ? 'info' : 'success',
				message: values.reverse
					? Liferay.Language.get('child')
					: Liferay.Language.get('parent'),
			}}
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
					disableFlag={readOnly}
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectRelationshipFormBase
					errors={errors}
					handleChange={handleChange}
					readonly
					setValues={setValues}
					values={values}
				/>

				<SingleSelect
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

			{parameterRequired &&
				values.type === ObjectRelationshipType.ONE_TO_MANY && (
					<Card title={Liferay.Language.get('parameters')}>
						<Input
							label={Liferay.Language.get('api-endpoint')}
							readOnly
							value={parameterEndpoint}
						/>

						<SelectRelationship
							error={errors.parameterObjectFieldName}
							objectDefinitionExternalReferenceCode={
								values.objectDefinitionExternalReferenceCode2 as string
							}
							onChange={(parameterObjectFieldName) =>
								setValues({parameterObjectFieldName})
							}
							value={values.parameterObjectFieldName}
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
