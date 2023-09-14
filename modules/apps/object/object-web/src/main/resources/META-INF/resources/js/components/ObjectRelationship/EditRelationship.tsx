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
	SingleSelect,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {firstLetterUppercase} from '../../utils/string';
import {
	ObjectRelationshipFormBase,
	ObjectRelationshipType,
	useObjectRelationshipForm,
} from './ObjectRelationshipFormBase';
import SelectRelationship from './SelectRelationship';

interface EditRelationshipProps {
	baseResourceURL: string;
	deletionTypes: LabelValueObject[];
	hasUpdateObjectDefinitionPermission: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectRelationship: ObjectRelationship;
	parameterRequired: boolean;
	restContextPath: string;
}

export default function EditRelationship({
	baseResourceURL,
	deletionTypes,
	hasUpdateObjectDefinitionPermission,
	objectDefinitionExternalReferenceCode,
	objectRelationship: initialValues,
	parameterRequired,
	restContextPath,
}: EditRelationshipProps) {
	const onSubmit = async (objectRelationship: ObjectRelationship) => {
		try {
			await API.putObjectRelationship(objectRelationship);
			saveAndReload();

			openToast({
				message: Liferay.Language.get(
					'the-object-relationship-was-updated-successfully'
				),
			});
		}
		catch (error: unknown) {
			const {message} = error as Error;

			openToast({message, type: 'danger'});
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
					baseResourceURL={baseResourceURL}
					errors={errors}
					handleChange={handleChange}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
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
							value={restContextPath}
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
