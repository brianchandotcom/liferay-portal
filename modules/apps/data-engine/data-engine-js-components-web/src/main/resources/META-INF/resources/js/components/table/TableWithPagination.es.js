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

import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React, {useContext} from 'react';

import {withLoading} from '../loading/Loading.es';
import SearchContext from '../management-toolbar/SearchContext.es';
import {withEmpty} from './EmptyState.es';
import Table from './Table.es';

const TableWithPagination = ({
	actions,
	columns,
	deltaValues = [4, 8, 20, 40, 60],
	editMode,
	items,
	noActionsMessage,
	totalCount,
}) => {
	const [{page, pageSize}, dispatch] = useContext(SearchContext);

	const deltas = deltaValues.map((label) => ({label}));

	return (
		<ClayLayout.ContainerFluid>
			<Table
				actions={actions}
				columns={columns}
				editMode={editMode}
				items={items}
				noActionsMessage={noActionsMessage}
			/>

			{totalCount > deltaValues[0] && (
				<div className="taglib-search-iterator-page-iterator-bottom">
					<ClayPaginationBarWithBasicItems
						activeDelta={Number(pageSize)}
						activePage={Number(page)}
						deltas={deltas}
						ellipsisBuffer={3}
						onDeltaChange={(pageSize) =>
							dispatch({pageSize, type: 'CHANGE_PAGE_SIZE'})
						}
						onPageChange={(page) =>
							dispatch({page, type: 'CHANGE_PAGE'})
						}
						totalItems={totalCount}
					/>
				</div>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default withLoading(withEmpty(TableWithPagination));
