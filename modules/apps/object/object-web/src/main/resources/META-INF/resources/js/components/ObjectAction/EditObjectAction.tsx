/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectActionContainer} from './ObjectActionContainer';

interface EditObjectActionProps {
	allowScriptContentToBeExecutedOrIncluded: boolean;
	isApproved: boolean;
	objectAction: ObjectAction;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectActionTriggers: ObjectActionTriggerExecutorItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	scriptManagementConfigurationPortletURL: string;
	systemObject: boolean;
	validateExpressionURL: string;
}

export default function EditObjectAction({
	allowScriptContentToBeExecutedOrIncluded,
	isApproved,
	objectAction: {id, ...values},
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	scriptManagementConfigurationPortletURL,
	systemObject,
	validateExpressionURL,
}: EditObjectActionProps) {
	return (
		<ObjectActionContainer
			allowScriptContentToBeExecutedOrIncluded={
				allowScriptContentToBeExecutedOrIncluded
			}
			editingObjectAction
			isApproved={isApproved}
			objectAction={values}
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
			readOnly={readOnly || values.system}
			requestParams={{
				method: 'PUT',
				url: `/o/object-admin/v1.0/object-actions/${id}`,
			}}
			scriptManagementConfigurationPortletURL={
				scriptManagementConfigurationPortletURL
			}
			successMessage={Liferay.Language.get(
				'the-object-action-was-updated-successfully'
			)}
			systemObject={systemObject}
			title={Liferay.Language.get('action')}
			validateExpressionURL={validateExpressionURL}
		/>
	);
}
