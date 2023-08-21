/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
import {Fragment, FragmentFilter} from '../../constants/Fragment';
interface PropsResultsBar {
	className: string;
	filters: FragmentFilter;
	fragments: Fragment[];
	onSetFilters: Dispatch<SetStateAction<FragmentFilter>>;
}
export default function ResultsBar({
	className,
	filters,
	fragments,
	onSetFilters,
}: PropsResultsBar): JSX.Element;
export {};
