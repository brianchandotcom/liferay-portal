/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomItem, SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import ObjectAction from './index';

interface EditObjectActionProps {
	isApproved: boolean;
	objectAction: ObjectAction;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	systemObject: boolean;
	validateExpressionURL: string;
}

export default function EditObjectAction({
	isApproved,
	objectAction: {id, ...values},
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	systemObject,
	validateExpressionURL,
}: EditObjectActionProps) {
	return (
		<ObjectAction
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
			readOnly={readOnly}
			requestParams={{
				method: 'PUT',
				url: `/o/object-admin/v1.0/object-actions/${id}`,
			}}
			successMessage={Liferay.Language.get(
				'the-object-action-was-updated-successfully'
			)}
			systemObject={systemObject}
			title={Liferay.Language.get('action')}
			validateExpressionURL={validateExpressionURL}
		/>
	);
}
