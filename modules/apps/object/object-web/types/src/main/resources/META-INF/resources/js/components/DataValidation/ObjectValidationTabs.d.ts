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

import 'codemirror/mode/groovy/groovy';
import {SidebarCategory} from '@liferay/object-js-components-web';
import {ChangeEventHandler} from 'react';
import {ObjectValidationErrors} from '../ObjectValidationFormBase';
export declare function BasicInfo({
	componentLabel,
	disabled,
	errors,
	setValues,
	values,
}: IBasicInfo): JSX.Element;
export declare function Conditions({
	disabled,
	errors,
	objectValidationRuleElements,
	setValues,
	values,
}: IConditions): JSX.Element;
interface ITabs {
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}
interface IBasicInfo extends ITabs {
	componentLabel: string;
}
interface IConditions extends ITabs {
	objectValidationRuleElements: SidebarCategory[];
}
export {};
