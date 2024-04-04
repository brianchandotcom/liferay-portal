/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectActionContainer} from './ObjectActionContainer';

interface AddObjectActionProps {
	allowScriptContentToBeExecutedOrIncluded: boolean;
	apiURL: string;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectActionTriggers: ObjectActionTriggerExecutorItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	scriptManagementConfigurationPortletURL: string;
	systemObject: boolean;
	validateExpressionURL: string;
}

export default function AddObjectAction({
	allowScriptContentToBeExecutedOrIncluded,
	apiURL,
	objectActionCodeEditorElements,
	objectActionExecutors = [],
	objectActionTriggers = [],
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	scriptManagementConfigurationPortletURL,
	systemObject,
	validateExpressionURL,
}: AddObjectActionProps) {
	return (
		<ObjectActionContainer
			allowScriptContentToBeExecutedOrIncluded={
				allowScriptContentToBeExecutedOrIncluded
			}
			objectAction={{active: true, system: false}}
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
			scriptManagementConfigurationPortletURL={
				scriptManagementConfigurationPortletURL
			}
			successMessage={Liferay.Language.get(
				'the-object-action-was-created-successfully'
			)}
			systemObject={systemObject}
			title={Liferay.Language.get('new-action')}
			validateExpressionURL={validateExpressionURL}
		/>
	);
}
