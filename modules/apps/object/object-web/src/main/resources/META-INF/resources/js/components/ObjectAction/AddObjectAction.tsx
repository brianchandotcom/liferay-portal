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

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import ObjectAction from './index';

export default function AddObjectAction({
	apiURL,
	objectActionCodeEditorElements,
	objectActionExecutors = [],
	objectActionTriggers = [],
	objectDefinitionsRelationshipsURL,
	validateExpressionURL,
}: IProps) {
	return (
		<ObjectAction
			objectAction={{active: true}}
			objectActionCodeEditorElements={objectActionCodeEditorElements}
			objectActionExecutors={objectActionExecutors}
			objectActionTriggers={objectActionTriggers}
			objectDefinitionsRelationshipsURL={
				objectDefinitionsRelationshipsURL
			}
			requestParams={{
				method: 'POST',
				url: apiURL,
			}}
			successMessage={Liferay.Language.get(
				'the-object-action-was-created-successfully'
			)}
			title={Liferay.Language.get('new-action')}
			validateExpressionURL={validateExpressionURL}
		/>
	);
}

interface IProps {
	apiURL: string;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionsRelationshipsURL: string;
	validateExpressionURL: string;
}
