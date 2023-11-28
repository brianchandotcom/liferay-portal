/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
interface ObjectRelationshipFormBaseProps {
	baseResourceURL: string;
	className?: string;
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	hasDefinedObjectDefinitionTarget?: boolean;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2?: string;
	readonly?: boolean;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}
interface UseObjectRelationshipFormProps {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}
export declare type ObjectRelationshipType =
	| 'manyToMany'
	| 'oneToMany'
	| 'oneToOne';
declare type ObjectRelationshipTypeInfo = {
	description: string;
	label: string;
	objectInputLabel1: string;
	objectInputLabel2: string;
	value: ObjectRelationshipType;
};
export declare const OBJECT_RELATIONSHIP_TYPES: ObjectRelationshipTypeInfo[];
export declare function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: UseObjectRelationshipFormProps): {
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	handleSubmit: React.FormEventHandler<HTMLFormElement>;
	handleValidate: (
		editedValues?: Partial<ObjectRelationship> | undefined
	) => FormError<ObjectRelationship>;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
};
export declare function ObjectRelationshipFormBase({
	baseResourceURL,
	className,
	errors,
	handleChange,
	hasDefinedObjectDefinitionTarget,
	objectDefinitionExternalReferenceCode1,
	objectDefinitionExternalReferenceCode2,
	readonly,
	setValues,
	values,
}: ObjectRelationshipFormBaseProps): JSX.Element;
export {};
