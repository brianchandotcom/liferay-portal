/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AssetType} from './AssetType';

interface Actions {
	delete?: HTTPMethod;
	get?: HTTPMethod;
	replace?: HTTPMethod;
	update?: HTTPMethod;
}

type HTTPMethod = {
	href: string;
	method: string;
};

export interface IVocabulary {
	actions?: Actions;
	assetTypes?: AssetType[];
	description?: string;
	description_i18n?: {
		[key: string]: string;
	};
	id?: number;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
	numberOfCategories?: number;
	siteId?: number;
	visibilityType?: number;
}
