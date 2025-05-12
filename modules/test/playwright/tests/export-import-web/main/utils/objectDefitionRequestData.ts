/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinition} from '@liferay/object-admin-rest-client-js';

export function objectDefitionRequestData(
	objectDefinition: Partial<ObjectDefinition> = {}
): ObjectDefinition {
	return {
		active: true,
		externalReferenceCode: 'test',
		label: {
			en_US: 'Test',
		},
		name: 'Test',
		objectFields: [
			{
				DBType: 'String',
				businessType: 'Text',
				indexed: true,
				indexedAsKeyword: true,
				label: {
					en_US: 'Name',
				},
				name: 'name',
				required: true,
			},
		],
		pluralLabel: {
			en_US: 'Tests',
		},
		portlet: true,
		scope: 'company',
		status: {
			code: 0,
		},
		...objectDefinition,
	};
}
