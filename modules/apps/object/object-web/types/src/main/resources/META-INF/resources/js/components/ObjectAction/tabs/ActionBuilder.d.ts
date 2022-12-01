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

/// <reference types="react" />

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import './ActionBuilder.scss';
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
