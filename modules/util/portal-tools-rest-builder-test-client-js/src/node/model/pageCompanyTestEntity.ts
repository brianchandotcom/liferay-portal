/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { CompanyTestEntity } from './companyTestEntity';
import { Facet } from './facet';

export class PageCompanyTestEntity {
	'actions'?: { [key: string]: { [key: string]: string; }; };
	'facets'?: Array<Facet>;
	'items'?: Array<CompanyTestEntity>;
	'lastPage'?: number;
	'page'?: number;
	'pageSize'?: number;
	'totalCount'?: number;

	static discriminator: string | undefined = undefined;

	static attributeTypeMap: Array<{name: string, baseName: string, type: string}> = [
		{
			"name": "actions",
			"baseName": "actions",
			"type": "{ [key: string]: { [key: string]: string; }; }"
		},
		{
			"name": "facets",
			"baseName": "facets",
			"type": "Array<Facet>"
		},
		{
			"name": "items",
			"baseName": "items",
			"type": "Array<CompanyTestEntity>"
		},
		{
			"name": "lastPage",
			"baseName": "lastPage",
			"type": "number"
		},
		{
			"name": "page",
			"baseName": "page",
			"type": "number"
		},
		{
			"name": "pageSize",
			"baseName": "pageSize",
			"type": "number"
		},
		{
			"name": "totalCount",
			"baseName": "totalCount",
			"type": "number"
		}
	];

	static getAttributeTypeMap() {
		return PageCompanyTestEntity.attributeTypeMap;
	}
}
