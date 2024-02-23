/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ActionError} from '../ObjectActionContainer';
interface ConditionContainerProps {
	disabled: boolean;
	errors: ActionError;
	setValues: (values: Partial<ObjectAction>) => void;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}
export declare function ConditionContainer({
	disabled,
	errors,
	setValues,
	validateExpressionURL,
	values,
}: ConditionContainerProps): JSX.Element;
export {};
