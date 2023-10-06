/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ObjectValidationErrors} from './useObjectValidationForm';
interface ErrorMessageProps {
	children: React.ReactNode;
	disabled: boolean;
	errors: ObjectValidationErrors;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}
export declare function ErrorMessage({
	children,
	disabled,
	errors,
	setValues,
	values,
}: ErrorMessageProps): JSX.Element;
export {};
