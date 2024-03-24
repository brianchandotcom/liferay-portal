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
	recipientTypeData: RoleRecipientType | ScriptRecipientType;
	template: string;
	templateLanguage: string;
}

interface RoleRecipientType {
	roleName: string;
}

interface ScriptRecipientType {
	script: string;
	scriptLanguage: string;
}

interface WorkflowDefinition {
	active: true;
	content: string;
	id?: number;
	name: string;
	title_i18n: DataObject;
	version: string;
}