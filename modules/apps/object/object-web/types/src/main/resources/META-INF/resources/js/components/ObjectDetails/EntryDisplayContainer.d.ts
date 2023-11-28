/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
interface EntryDisplayContainerProps {
	className?: string;
	errors: FormError<ObjectDefinition>;
	isLinkedObjectDefinition?: boolean;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectFields: ObjectField[];
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}
export declare function EntryDisplayContainer({
	className,
	errors,
	isLinkedObjectDefinition,
	nonRelationshipObjectFieldsInfo,
	objectFields,
	onSubmit,
	setValues,
	values,
}: EntryDisplayContainerProps): JSX.Element;
export {};
