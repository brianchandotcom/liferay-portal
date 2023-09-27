/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function getAPIApplicationsFDSFilters(): (
	| {
			autocompleteEnabled: boolean;
			id: string;
			items: {
				label: string;
				value: string;
			}[];
			label: string;
			multiple: boolean;
			type: string;
	  }
	| {
			id: string;
			label: string;
			type: string;
			autocompleteEnabled?: undefined;
			items?: undefined;
			multiple?: undefined;
	  }
)[];
