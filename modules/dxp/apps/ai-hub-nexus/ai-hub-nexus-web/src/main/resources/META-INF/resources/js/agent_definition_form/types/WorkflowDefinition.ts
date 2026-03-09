/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type WorkflowDefinition = {
	actions: {
		delete?: {
			href: string;
			method: string;
		};
		update?: {
			href: string;
			method: string;
		};
	};
	active: boolean;
	content: string;
	creator: {
		additionalName: string;
		contentType: string;
		familyName: string;
		givenName: string;
		id: number;
		name: string;
		profileURL: string;
	};
	dateCreated: string;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	nodes: Array<{
		label: string;
		name: string;
		type: string;
	}>;
	scope: string;
	title: string;
	title_i18n: Record<string, string>;
	transitions: Array<{
		label: string;
		name: string;
		sourceNodeName: string;
		targetNodeName: string;
	}>;
	version: string;
};
