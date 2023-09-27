/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
import {KeyValuePair} from './EditObjectDetails';
interface ScopeContainerProps {
	companyKeyValuePairs: KeyValuePair[];
	errors: FormError<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	isRootDescendantNode: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	siteKeyValuePairs: KeyValuePair[];
	values: Partial<ObjectDefinition>;
}
export declare function ScopeContainer({
	companyKeyValuePairs,
	errors,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isLinkedObjectDefinition,
	isRootDescendantNode,
	setValues,
	siteKeyValuePairs,
	values,
}: ScopeContainerProps): JSX.Element;
export {};
