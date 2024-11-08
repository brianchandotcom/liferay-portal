/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError, Input} from '@liferay/object-js-components-web';
import React, {ElementType} from 'react';

import {SelectObjectRelationship} from './SelectObjectRelationship';

interface ObjectRelationshipParameterRequired {
	autoSave?: boolean;
	containerWrapper: ElementType;
	errors: FormError<ObjectRelationship>;
	onSubmit: (values?: Partial<ObjectRelationship>) => Promise<void>;
	parameterRequired: boolean;
	restContextPath: string;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}

export function ObjectRelationshipParameterRequired({
	autoSave,
	containerWrapper: ContainerWrapper,
	errors,
	onSubmit,
	parameterRequired,
	restContextPath,
	setValues,
	values,
}: ObjectRelationshipParameterRequired) {
	return (
		<>
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

							if (autoSave) {
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
	);
}
