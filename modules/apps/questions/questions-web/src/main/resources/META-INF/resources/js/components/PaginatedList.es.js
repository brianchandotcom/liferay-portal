/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React, {useEffect, useState} from 'react';

import {withLoading} from './Loading.es';

const scrollToTop = () => window.scrollTo({behavior: 'smooth', top: 0});

const PaginatedList = ({
	activeDelta,
	activePage,
	changeDelta,
	changePage,
	children,
	data,
	emptyState,
	hidden,
	hrefConstructor,
	showEmptyState,
	totalCount,
}) => {
	const deltaValues = [4, 8, 20, 40, 60];

	const deltas = deltaValues.map((label) => ({label}));

	const [totalItems, setTotalItems] = useState(0);

	useEffect(() => {
		setTotalItems(totalCount ? totalCount : data.totalCount);
	}, [data.totalCount, totalCount]);

	useEffect(() => {
		scrollToTop(top);
	}, [data]);

	return (
		<>
			{emptyState && !data.totalCount && {...emptyState}}
			{showEmptyState && !data.totalCount && (
				<ClayEmptyState
					description={Liferay.Language.get('there-are-no-results')}
					title={Liferay.Language.get('there-are-no-results')}
				/>
			)}
			{data.totalCount > 0 && (
				<>
					{data.items && data.items.map((data) => children(data))}

					{totalItems > deltaValues[0] && (
						<ClayPaginationBarWithBasicItems
							activeDelta={activeDelta}
							activePage={activePage}
							className="c-mt-4 w-100"
							deltas={deltas}
							ellipsisBuffer={3}
							hidden={hidden}
							hrefConstructor={hrefConstructor}
							onDeltaChange={changeDelta}
							onPageChange={(page) => {
								if (changePage) {
									changePage(page);
								}
							}}
							totalItems={totalItems}
						/>
					)}
				</>
			)}
		</>
	);
};

export default withLoading(PaginatedList);
