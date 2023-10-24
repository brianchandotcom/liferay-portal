/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ObjectValidationErrors} from './useObjectValidationForm';
import './UniqueCompositeKey.scss';
export interface UniqueCompositeKeyProps {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	disabled: boolean;
	errors: ObjectValidationErrors;
	objectDefinitionExternalReferenceCode: string;
	setShowUniqueCompositeKeyAlert: (value: boolean) => void;
	setValues: (values: Partial<ObjectValidation>) => void;
	showUniqueCompositeKeyAlert: boolean;
	values: Partial<ObjectValidation>;
}
export declare function UniqueCompositeKey({
	baseResourceURL,
	creationLanguageId,
	customObjectFields,
	disabled,
	errors,
	objectDefinitionExternalReferenceCode,
	setShowUniqueCompositeKeyAlert,
	setValues,
	showUniqueCompositeKeyAlert,
	values,
}: UniqueCompositeKeyProps): JSX.Element;
