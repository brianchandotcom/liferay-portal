/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
							hrefConstructor={
								hrefConstructor ? hrefConstructor : false
							}
							onDeltaChange={changeDelta}
							onPageChange={(page) => {
								if (changePage) {
									changePage(page);
								}
								scrollToTop(top);
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
