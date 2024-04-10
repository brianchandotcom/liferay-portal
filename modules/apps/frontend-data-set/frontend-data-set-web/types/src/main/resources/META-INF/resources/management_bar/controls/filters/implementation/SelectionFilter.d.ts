/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FilterImplementation, FilterImplementationArgs} from '../Filter';
import {EEntityFieldType} from '../utils/types';
export interface SelectionFilterImplementationArgs
	extends FilterImplementationArgs<SelectedData> {
	apiURL: string;
	autocompleteEnabled: boolean;
	entityFieldType: EEntityFieldType;
	inputPlaceholder: string;
	itemKey: string;
	itemLabel: string;
	items: TItem[];
	multiple: boolean;
}
interface SelectedData {
	exclude: boolean;
	selectedItems: TItem[];
}
interface TItem {
	label: string;
	value: string;
}
declare const filterImplementation: FilterImplementation<SelectionFilterImplementationArgs>;
export default filterImplementation;
