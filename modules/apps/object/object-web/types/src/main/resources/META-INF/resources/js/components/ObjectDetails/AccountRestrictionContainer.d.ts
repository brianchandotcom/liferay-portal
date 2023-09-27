/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
interface AccountRestrictionContainerProps {
	errors: FormError<ObjectDefinition>;
	isApproved: boolean;
	isLinkedObjectDefinition?: boolean;
	isRootDescendantNode: boolean;
	objectFields: ObjectField[];
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}
export declare function AccountRestrictionContainer({
	errors,
	isApproved,
	isLinkedObjectDefinition,
	isRootDescendantNode,
	objectFields,
	setValues,
	values,
}: AccountRestrictionContainerProps): JSX.Element;
export {};
