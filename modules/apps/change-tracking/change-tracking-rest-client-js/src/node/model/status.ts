/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


export class Status {
	'code'?: number;
	'label'?: string;
	'label_i18n'?: string;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "code",
			"baseName": "code",
			"type": "number"
		},
		{
			"name": "label",
			"baseName": "label",
			"type": "string"
		},
		{
			"name": "label_i18n",
			"baseName": "label_i18n",
			"type": "string"
		}
	];

	static getAttributeTypeMap() {
		return Status.attributeTypeMap;
	}
}
