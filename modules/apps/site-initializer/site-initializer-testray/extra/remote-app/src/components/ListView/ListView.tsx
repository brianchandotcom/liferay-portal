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

import {TypedDocumentNode, useQuery} from '@apollo/client';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

import {PAGINATION} from '../../util/constants';
import EmptyState from '../EmptyState';
import Table, {TableProps} from '../Table';

type LiferayQueryResponse<T = any> = {
	items: T[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

type ListViewProps<T = any> = {
	query: TypedDocumentNode;
	tableProps: Omit<TableProps, 'items'>;
	transformData: (data: T) => LiferayQueryResponse<T>;
	variables?: any;
};

const ListView: React.FC<ListViewProps> = ({
	query,
	tableProps,
	transformData,
	variables,
}) => {
	const {data, error, loading, refetch} = useQuery(query, {
		variables,
	});

	const {items = [], page, pageSize, totalCount} = transformData(data) || {};

	const deltas = PAGINATION.delta.map((label) => ({label}));

	const onRefetch = (newVariables: any) => {
		refetch({...variables, ...newVariables});
	};

	const Pagination = () => (
		<ClayPaginationBarWithBasicItems
			activeDelta={pageSize}
			activePage={page}
			deltas={deltas}
			ellipsisBuffer={PAGINATION.ellipsisBuffer}
			onDeltaChange={(delta) => onRefetch({pageSize: delta})}
			onPageChange={(page) => onRefetch({page})}
			totalItems={totalCount}
		/>
	);

	if (error) {
		return <span>{error.message}</span>;
	}

	if (loading) {
		return <span>Loading...</span>;
	}

	if (!items.length) {
		return <EmptyState />;
	}

	return (
		<>
			<Pagination />
			<Table {...tableProps} items={items} />
			<Pagination />
		</>
	);
};

export default ListView;
