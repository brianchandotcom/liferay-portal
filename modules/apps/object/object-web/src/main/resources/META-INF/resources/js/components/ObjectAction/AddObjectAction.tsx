/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import ObjectAction from './index';

interface AddObjectActionProps {
	apiURL: string;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	systemObject: boolean;
	validateExpressionURL: string;
}

export default function AddObjectAction({
	apiURL,
	objectActionCodeEditorElements,
	objectActionExecutors = [],
	objectActionTriggers = [],
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	systemObject,
	validateExpressionURL,
}: AddObjectActionProps) {
	return (
		<ObjectAction
			objectAction={{active: true}}
			objectActionCodeEditorElements={objectActionCodeEditorElements}
			objectActionExecutors={objectActionExecutors}
			objectActionTriggers={objectActionTriggers}
			objectDefinitionExternalReferenceCode={
				objectDefinitionExternalReferenceCode
			}
			objectDefinitionId={objectDefinitionId}
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
			systemObject={systemObject}
			title={Liferay.Language.get('new-action')}
			validateExpressionURL={validateExpressionURL}
		/>
	);
}
