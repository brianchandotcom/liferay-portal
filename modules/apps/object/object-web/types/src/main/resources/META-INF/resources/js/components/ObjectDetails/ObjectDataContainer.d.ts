/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormError} from '@liferay/object-js-components-web';
import {ChangeEventHandler} from 'react';
interface ObjectDataContainerProps {
	dbTableName: string | undefined;
	errors: FormError<ObjectDefinition>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}
export declare function ObjectDataContainer({
	dbTableName,
	errors,
	handleChange,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isLinkedObjectDefinition,
	onSubmit,
	setValues,
	values,
}: ObjectDataContainerProps): JSX.Element;
export {};
