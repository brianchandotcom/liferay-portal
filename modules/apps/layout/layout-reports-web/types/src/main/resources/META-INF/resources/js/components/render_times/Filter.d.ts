/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
import {FragmentFilter} from '../../constants/Fragment';
interface Props {
	filters: FragmentFilter;
	isAscendingSort: boolean;
	onFilterValue: Dispatch<SetStateAction<FragmentFilter>>;
	onSearchValue: Dispatch<SetStateAction<string>>;
	onSort: Dispatch<SetStateAction<boolean>>;
}
export default function Filter({
	filters,
	isAscendingSort,
	onFilterValue,
	onSearchValue,
	onSort,
}: Props): JSX.Element;
export {};
