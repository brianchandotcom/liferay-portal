/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Card, Input, SingleSelect} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {ObjectRelationshipFormBase} from './ObjectRelationshipFormBase';
import {SelectObjectRelationship} from './SelectObjectRelationship';

import type {FormError} from '@liferay/object-js-components-web';
import type {ChangeEventHandler} from 'react';

interface EditObjectRelationshipContentProps {
	baseResourceURL: string;
	errors: FormError<ObjectRelationship>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionExternalReferenceCode: string;
	objectRelationshipDeletionTypes: LabelValueObject[];
	parameterRequired: boolean;
	readOnly?: boolean;
	restContextPath: string;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

export function EditObjectRelationshipContent({
	baseResourceURL,
	errors,
	handleChange,
	objectDefinitionExternalReferenceCode,
	objectRelationshipDeletionTypes,
	parameterRequired,
	readOnly,
	restContextPath,
	setValues,
	values,
}: EditObjectRelationshipContentProps) {
	return (
		<>
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
					disabled={readOnly}
					error={errors.label}
					id="lfr-objects__object-relationship-form-base-label"
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectRelationshipFormBase
					baseResourceURL={baseResourceURL}
					errors={errors}
					handleChange={handleChange}
					objectDefinitionExternalReferenceCode1={
						objectDefinitionExternalReferenceCode
					}
					readonly
					setValues={setValues}
					values={values}
				/>

				<SingleSelect
					disabled={
						readOnly ||
						(Liferay.FeatureFlags['LPS-187142'] && values.edge)
					}
					id="lfr-objects__object-relationship-deletion-type"
					items={objectRelationshipDeletionTypes}
					label={Liferay.Language.get('deletion-type')}
					onSelectionChange={(value) =>
						setValues({deletionType: value as string})
					}
					required
					selectedKey={values.deletionType}
				/>
			</Card>

			{parameterRequired && values.type === 'oneToMany' && (
				<Card title={Liferay.Language.get('parameters')}>
					<Input
						id="lfr-objects__object-relationship-api-endpoint"
						label={Liferay.Language.get('api-endpoint')}
						readOnly
						value={restContextPath}
					/>

					<SelectObjectRelationship
						error={errors.parameterObjectFieldName}
						objectDefinitionExternalReferenceCode1={
							values.objectDefinitionExternalReferenceCode2 as string
						}
						onChange={(parameterObjectFieldName) =>
							setValues({parameterObjectFieldName})
						}
						value={values.parameterObjectFieldName}
					/>
				</Card>
			)}
		</>
	);
}
