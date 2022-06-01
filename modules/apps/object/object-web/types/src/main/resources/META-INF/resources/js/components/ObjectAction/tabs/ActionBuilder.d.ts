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

/// <reference types="react" />

import {CustomItem, FormError} from '@liferay/object-js-components-web';
import './ActionBuilder.scss';
export default function ActionBuilder({
	errors,
	ffNotificationTemplates,
	objectActionExecutors,
	objectActionTriggers,
	setValues,
	values,
}: IProps): JSX.Element;
interface IProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	ffNotificationTemplates: boolean;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}
export {};
