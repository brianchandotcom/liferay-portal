/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSViewType} from './FDSViews';
interface IField {
	format: string;
	label: string;
	name: string;
	type: string;
}
export declare function getFields(fdsView: FDSViewType): Promise<IField[]>;
export interface IPickList {
	externalReferenceCode: string;
	id: string;
	listTypeEntries: IListTypeEntry[];
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}
interface IListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}
export declare function getAllPicklists(
	page?: number,
	items?: IPickList[]
): Promise<IPickList[]>;
export {};
