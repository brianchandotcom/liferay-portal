/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Input} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {ObjectRelationshipDeletionTypeSelect} from './ObjectRelationshipDeletionTypeSelect';
import {ObjectRelationshipFormBase} from './ObjectRelationshipFormBase';
import {SelectObjectRelationship} from './SelectObjectRelationship';

import type {FormError} from '@liferay/object-js-components-web';
import type {ChangeEventHandler, ElementType} from 'react';

interface EditObjectRelationshipContentProps {
	baseResourceURL: string;
	containerWrapper: ElementType;
	errors: FormError<ObjectRelationship>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionExternalReferenceCode: string;
	objectRelationshipDeletionTypes: LabelValueObject[];
	onSubmit?: (editedObjectRelationship?: Partial<ObjectRelationship>) => void;
	parameterRequired: boolean;
	readOnly?: boolean;
	restContextPath: string;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

export function EditObjectRelationshipContent({
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
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
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
				>
					<>
						<ObjectRelationshipDeletionTypeSelect
							objectRelationshipDeletionTypes={
								objectRelationshipDeletionTypes
							}
							onSubmit={onSubmit}
							readOnly={readOnly}
							setValues={setValues}
							values={values}
						/>

						{parameterRequired && values.type === 'oneToMany' && (
							<ContainerWrapper title={Liferay.Language.get('parameters')}>
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
									onChange={(parameterObjectFieldName) => {
										setValues({parameterObjectFieldName});

										if (onSubmit) {
											onSubmit({
												...values,
												parameterObjectFieldName,
											});
										}
									}}
									value={values.parameterObjectFieldName}
								/>
							</ContainerWrapper>
						)}
					</>
				</ObjectRelationshipFormBase>
			</ContainerWrapper>
		</>
	);
}
