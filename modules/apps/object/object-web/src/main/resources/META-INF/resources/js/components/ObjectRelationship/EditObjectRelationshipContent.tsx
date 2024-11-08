/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {ObjectRelationshipDeletionTypeSelect} from './ObjectRelationshipDeletionTypeSelect';
import {Alert, ObjectRelationshipFormBase} from './ObjectRelationshipFormBase';
import {ObjectRelationshipParameterRequired} from './ObjectRelationshipParameterRequired';

import type {FormError} from '@liferay/object-js-components-web';
import type {ChangeEventHandler, ElementType} from 'react';

interface EditObjectRelationshipContentProps {
	alert?: Alert;
	autoSave?: boolean;
	baseResourceURL: string;
	containerWrapper: ElementType;
	errors: FormError<ObjectRelationship>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionExternalReferenceCode: string;
	objectRelationshipDeletionTypes: LabelValueObject[];
	onSubmit: (values?: Partial<ObjectRelationship>) => Promise<void>;
	parameterRequired: boolean;
	readOnly?: boolean;
	restContextPath: string;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

export function EditObjectRelationshipContent({
	alert,
	autoSave,
	baseResourceURL,
	containerWrapper: ContainerWrapper,
	errors,
	handleChange,
	objectDefinitionExternalReferenceCode,
	objectRelationshipDeletionTypes,
	onSubmit,
	parameterRequired,
	readOnly,
	restContextPath,
	setValues,
	values,
}: EditObjectRelationshipContentProps) {
	return (
		<>
			<ContainerWrapper title={Liferay.Language.get('basic-info')}>
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
					onBlur={async (event) => {
						event.stopPropagation();

						if (autoSave) {
							await onSubmit();
						}
					}}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectRelationshipFormBase
					alert={alert}
					baseResourceURL={baseResourceURL}
					errors={errors}
					handleChange={handleChange}
					objectDefinitionExternalReferenceCode1={
						objectDefinitionExternalReferenceCode
					}
					onSubmit={onSubmit}
					readonly
					setValues={setValues}
					values={values}
				>
					<>
						<ObjectRelationshipDeletionTypeSelect
							autoSave={autoSave}
							objectRelationshipDeletionTypes={
								objectRelationshipDeletionTypes
							}
							onSubmit={onSubmit}
							readOnly={readOnly}
							setValues={setValues}
							values={values}
						/>

						<ObjectRelationshipParameterRequired
							autoSave={autoSave}
							containerWrapper={ContainerWrapper}
							errors={errors}
							onSubmit={onSubmit}
							parameterRequired={parameterRequired}
							restContextPath={restContextPath}
							setValues={setValues}
							values={values}
						/>
					</>
				</ObjectRelationshipFormBase>
			</ContainerWrapper>
		</>
	);
}
