/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
interface ObjectRelationshipFormBaseProps {
	baseResourceURL: string;
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	objectDefinitionExternalReferenceCode: string;
	readonly?: boolean;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}
interface UseObjectRelationshipFormProps {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}
export declare enum ObjectRelationshipType {
	MANY_TO_MANY = 'manyToMany',
	ONE_TO_MANY = 'oneToMany',
	ONE_TO_ONE = 'oneToOne',
}
export declare function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: UseObjectRelationshipFormProps): {
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	handleSubmit: React.FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
};
export declare function ObjectRelationshipFormBase({
	baseResourceURL,
	errors,
	handleChange,
	objectDefinitionExternalReferenceCode,
	readonly,
	setValues,
	values,
}: ObjectRelationshipFormBaseProps): JSX.Element;
export {};
