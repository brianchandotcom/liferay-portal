/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Creator {
	additionalName: string;
	contentType: string;
	familyName: string;
	givenName: string;
	id: number;
	name: string;
}

interface DefinitionInfo {
	dateCreated: string;
	dateModified: string;
	totalModifications: string;
}

interface LabelValueObject<T> {
	label: string;
	value: T;
}

interface Role {
	actions: {
		'create-organization-rol-user-account-association': RoleAction;
		'create-role-user-account-association': RoleAction;
		'create-site-role-user-account-association': RoleAction;
		'delete-organization-role-user-account-association': RoleAction;
		'delete-role-user-account-association': RoleAction;
		'delete-site-role-user-account-association': RoleAction;
		'get': RoleAction;
	};
	availableLanguages: string[];
	dateCreated: string;
	dateModified: string;
	description: string;
	description_i18n: {
		[key: string]: string;
	};
	externalReferenceCode: string;
	id: number;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
	rolePermissions: RolePermission[];
	roleType: string;
}

interface RoleAction {
	href: string;
	method: string;
}

interface RolePermission {
	actionIds: string[];
	id: number;
	label: string;
	primaryKey: string;
	resourceName: string;
	roleId: number;
	scope: number;
}

interface WorkflowDefinition {
	active: boolean;
	content: string;
	creator?: Creator;
	dateCreated?: string;
	dateModified?: string;
	name: string;
	title: string;
	title_i18n: {
		[key: string]: string;
	};
	version: string;
}

interface WorkflowDefinitionVersion {
	creatorName: string;
	dateCreated: string;
	version: string;
}
