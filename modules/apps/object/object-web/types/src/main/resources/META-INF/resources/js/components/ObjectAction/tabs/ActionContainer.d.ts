/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ActionError} from '..';
import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';
import './ActionBuilder.scss';
import {WarningStates} from './ActionBuilder';
interface ActionContainerProps {
	currentObjectDefinitionFields: ObjectField[];
	errors: ActionError;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	objectFieldsMap: Map<string, ObjectField>;
	setCurrentObjectDefinitionFields: (values: ObjectField[]) => void;
	setErrorAlert: (value: boolean) => void;
	setValues: (values: Partial<ObjectAction>) => void;
	setWarningAlerts: (value: React.SetStateAction<WarningStates>) => void;
	systemObject: boolean;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}
export declare function ActionContainer({
	currentObjectDefinitionFields,
	errors,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	objectFieldsMap,
	setCurrentObjectDefinitionFields,
	setErrorAlert,
	setValues,
	setWarningAlerts,
	systemObject,
	validateExpressionURL,
	values,
}: ActionContainerProps): JSX.Element;
export {};
