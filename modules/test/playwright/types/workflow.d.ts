/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Notification {
	notificationDescription: string;
	notificationName: string;
	notificationTypeEmail: boolean;
	notificationTypeUser: boolean;
	recipientType: string;
	recipientTypeData: Role | RoleType | ScriptedRecipient;
	template: string;
	templateLanguage: string;
}

interface Role {
	roleName: string;
}

interface RoleType {
	autocreate: boolean;
	roleName: string;
	roleType: string;
}

interface ScriptedRecipient {
	script: string;
	scriptLanguage: string;
}

interface WorkflowDefinition {
	active: boolean;
	content: string;
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	title_i18n: DataObject;
	version: string;
}

interface WorkflowTaskDefinition {
	completed: boolean;
	description: string;
	id: number;
	label: string;
	name: string;
	objectReviewed: {
		assetTitle: string;
		assetType: string;
		id: number;
		resourceType: string;
	};
	workflowDefinitionId: number;
	workflowDefinitionName: string;
	workflowDefinitionVersion: string;
	workflowInstanceId: number;
}

interface WorkflowTaskDefinitions {
	items: WorkflowTaskDefinition[];
}
