/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import {ActionError} from '../index';
interface ActionBuilderProps {
	errors: ActionError;
	isApproved: boolean;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	setValues: (values: Partial<ObjectAction>) => void;
	systemObject: boolean;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}
export interface WarningStates {
	mandatoryRelationships: boolean;
	requiredFields: boolean;
}
export default function ActionBuilder({
	errors,
	isApproved,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	setValues,
	systemObject,
	validateExpressionURL,
	values,
}: ActionBuilderProps): JSX.Element;
export {};
