/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import 'codemirror/mode/groovy/groovy';
import {
	CustomItem,
	FormError,
	SidebarCategory,
} from '@liferay/object-js-components-web';
interface ActionProps {
	isApproved?: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	requestParams: {
		method: 'POST' | 'PUT';
		url: string;
	};
	successMessage: string;
	systemObject: boolean;
	title: string;
	validateExpressionURL: string;
}
export declare type ActionError = FormError<
	ObjectAction & ObjectActionParameters
> & {
	predefinedValues?: {
		[key: string]: string;
	};
};
export default function Action({
	isApproved,
	objectAction: initialValues,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	requestParams: {method, url},
	successMessage,
	systemObject,
	validateExpressionURL,
}: ActionProps): JSX.Element;
export {};
