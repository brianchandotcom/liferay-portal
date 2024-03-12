/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError, SidebarCategory} from '@liferay/object-js-components-web';
interface ObjectActionContainerProps {
	allowScriptContentBeExecutedOrIncluded: boolean;
	editingObjectAction?: boolean;
	isApproved?: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectActionTriggers: ObjectActionTriggerExecutorItem[];
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
export declare function ObjectActionContainer({
	allowScriptContentBeExecutedOrIncluded,
	editingObjectAction,
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
}: ObjectActionContainerProps): JSX.Element;
export {};
