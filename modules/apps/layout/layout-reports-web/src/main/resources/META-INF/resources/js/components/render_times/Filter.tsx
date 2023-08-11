/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {SearchForm} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

export default function Filter({
	isAscendingSort,
	onSearchValue,
	onSort,
}: {
	isAscendingSort: boolean;
	onSearchValue: Function;
	onSort: Function;
}) {
	const sortLabel = sub(
		Liferay.Language.get('x-sort-fragments-by-render-time'),
		isAscendingSort
			? Liferay.Language.get('descending')
			: Liferay.Language.get('ascending')
	);

	return (
		<div className="d-flex pt-1">
			<SearchForm
				className="flex-grow-1"
				label={Liferay.Language.get('search-fragments')}
				onChange={onSearchValue}
			/>

			<ClayButtonWithIcon
				aria-label={sortLabel}
				borderless
				className="ml-2 mt-0"
				displayType="secondary"
				onClick={() => onSort(!isAscendingSort)}
				size="sm"
				symbol={isAscendingSort ? 'order-list-up' : 'order-list-down'}
				title={sortLabel}
			/>
		</div>
	);
}
