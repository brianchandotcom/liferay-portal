/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ObjectFieldErrors} from './ObjectFieldFormBase';
import './AutoIncrementFormBase.scss';
interface AutoIncrementFormBaseProps {
	disabled: boolean;
	errors: ObjectFieldErrors;
	modelBuilder?: boolean;
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function AutoIncrementFormBase({
	disabled,
	errors,
	modelBuilder,
	onSubmit,
	setValues,
	values,
}: AutoIncrementFormBaseProps): JSX.Element;
export {};
