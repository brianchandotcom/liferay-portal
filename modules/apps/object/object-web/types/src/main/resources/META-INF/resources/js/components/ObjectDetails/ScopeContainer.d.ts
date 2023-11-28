/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
import {Scope} from './EditObjectDetails';
interface ScopeContainerProps {
	className?: string;
	companies: Scope[];
	errors: FormError<ObjectDefinition>;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	isRootDescendantNode: boolean;
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	sites: Scope[];
	values: Partial<ObjectDefinition>;
}
export declare function ScopeContainer({
	className,
	companies,
	errors,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isLinkedObjectDefinition,
	isRootDescendantNode,
	onSubmit,
	setValues,
	sites,
	values,
}: ScopeContainerProps): JSX.Element;
export {};
