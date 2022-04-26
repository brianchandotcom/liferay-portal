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

import {ChangeEventHandler} from 'react';
import {ObjectValidationErrors} from '../ObjectValidationFormBase';
import '../Editor/Editor.scss';
declare function BasicInfo({
	componentLabel,
	defaultLocale,
	disabled,
	errors,
	handleChange,
	locales,
	setValues,
	values,
}: IBasicInfo): JSX.Element;
declare function Conditions({
	defaultLocale,
	disabled,
	errors,
	locales,
	objectValidationRuleElements,
	setValues,
	values,
}: IConditions): JSX.Element;
interface IBasicInfo {
	componentLabel: string;
	defaultLocale: {
		label: string;
		symbol: string;
	};
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	locales: Array<any>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}
interface IConditions {
	defaultLocale: {
		label: string;
		symbol: string;
	};
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	locales: Array<any>;
	objectValidationRuleElements: ObjectValidationRuleElement[];
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}
export {BasicInfo, Conditions};
