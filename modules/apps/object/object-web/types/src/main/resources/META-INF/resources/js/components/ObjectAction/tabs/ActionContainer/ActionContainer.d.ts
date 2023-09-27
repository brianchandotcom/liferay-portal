/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';
import {ActionError} from '../..';
import {WarningStates} from '../ActionBuilder';
interface ActionContainerProps {
	currentObjectDefinitionFields: ObjectField[];
	errors: ActionError;
	newObjectActionExecutors: CustomItem<string>[];
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	objectFieldsMap: Map<string, ObjectField>;
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	setWarningAlerts: (value: React.SetStateAction<WarningStates>) => void;
	systemObject: boolean;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}
export declare function ActionContainer({
	currentObjectDefinitionFields,
	errors,
	newObjectActionExecutors,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	objectFieldsMap,
	setCurrentObjectDefinitionFields,
	setValues,
	setWarningAlerts,
	systemObject,
	validateExpressionURL,
	values,
}: ActionContainerProps): JSX.Element;
export {};
