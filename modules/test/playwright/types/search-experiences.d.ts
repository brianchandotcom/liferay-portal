/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type SXPBlueprint = {
	configuration: Object;
	createDate: string;
	description: string;
	description_i18n: Object;
	externalReferenceCode: string;
	id: number;
	modifiedDate: string;
	schemaVersion: string;
	title: string;
	title_i18n: Object;
	userName: string;
	version: string;
};

type SXPElement = {
	createDate: string;
	description: string;
	description_i18n: Object;
	elementDefinition: Object;
	externalReferenceCode: string;
	id: number;
	modifiedDate: string;
	readonly: boolean;
	schemaVersion: string;
	title: string;
	title_i18n: Object;
	type: number;
	userName: string;
	version: string;
};
