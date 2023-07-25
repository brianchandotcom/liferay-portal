/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
interface ExternalDataSourceContainerProps {
	errors: FormError<ObjectDefinition>;
	setValues: (values: Partial<ObjectDefinition>) => void;
	storageTypes: LabelValueObject[];
	values: Partial<ObjectDefinition>;
}
export declare function ExternalDataSourceContainer({
	errors,
	setValues,
	storageTypes,
	values,
}: ExternalDataSourceContainerProps): JSX.Element;
export {};
