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

import React from 'react';
import {FormError} from '../hooks/useForm';
import {CustomItem} from './Form/CustomSelect/CustomSelect';
export default function ObjectActionFormBase({
	children,
	errors,
	handleChange,
	objectAction,
	objectActionExecutors,
	objectActionTriggers,
	setValues,
}: IProps): JSX.Element;
export declare function useObjectActionForm({
	initialValues,
	onSubmit,
}: IUseObjectActionForm): {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	handleSubmit: React.FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
};
export interface IObjectActionFormBaseProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	objectAction: Partial<ObjectAction>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	setValues: (values: Partial<ObjectAction>) => void;
}
interface IProps extends IObjectActionFormBaseProps {
	children?: React.ReactNode;
}
interface IUseObjectActionForm {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}
export {};
