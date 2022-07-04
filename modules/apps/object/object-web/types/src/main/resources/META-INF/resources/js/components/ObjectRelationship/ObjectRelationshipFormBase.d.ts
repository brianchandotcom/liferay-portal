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

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
export declare enum ObjectRelationshipType {
	MANY_TO_MANY = 'manyToMany',
	ONE_TO_MANY = 'oneToMany',
	ONE_TO_ONE = 'oneToOne',
}
export declare function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: IUseObjectRelationshipForm): {
	errors: FormError<ObjectRelationship>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	handleSubmit: React.FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
};
export declare function ObjectRelationshipFormBase({
	errors,
	ffOneToOneRelationshipConfigurationEnabled,
	handleChange,
	readonly,
	setValues,
	values,
}: IPros): JSX.Element;
interface IUseObjectRelationshipForm {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}
interface IPros {
	errors: FormError<ObjectRelationship>;
	ffOneToOneRelationshipConfigurationEnabled?: boolean;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	readonly?: boolean;
	setValues: (values: Partial<ObjectRelationship>) => void;
	values: Partial<ObjectRelationship>;
}
export {};
