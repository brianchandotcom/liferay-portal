/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ActionError} from './index';
interface UseObjectActionFormProps {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}
export declare function useObjectActionForm({
	initialValues,
	onSubmit,
}: UseObjectActionFormProps): {
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	handleValidate: () => import('@liferay/object-js-components-web').FormError<
		ObjectAction & ObjectActionParameters
	>;
	setValues: (values: Partial<ObjectAction>) => void;
	validateSubmit: () => void;
	errors: ActionError;
	values: Partial<ObjectAction>;
};
export {};
