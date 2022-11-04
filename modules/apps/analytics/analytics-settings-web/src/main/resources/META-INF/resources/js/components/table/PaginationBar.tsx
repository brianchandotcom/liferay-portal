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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import {sub} from 'frontend-js-web';
import React from 'react';

import {DELTAS, TPagination} from '../../utils/pagination';
import {Events, useData, useDispatch} from './Context';

interface IPaginationBarProps {
	disabled: boolean;
}

const PaginationBar: React.FC<IPaginationBarProps> = ({disabled}) => {
	const {
		pagination: {page, pageSize, totalCount},
	} = useData();
	const dispatch = useDispatch();

	if (!totalCount || disabled) {
		return null;
	}

	return (
		<ClayPaginationBar>
			<ClayPaginationBar.DropDown
				items={DELTAS.map((pageSize: TPagination['pageSize']) => ({
					label: String(pageSize),
					onClick: () => {
						dispatch({
							payload: {
								page: 1,
								pageSize,
							},
							type: Events.ChangePagination,
						});
					},
				}))}
				trigger={
					<ClayButton displayType="unstyled">
						<strong>
							{sub(Liferay.Language.get('x-entries'), pageSize)}
						</strong>

						<ClayIcon symbol="caret-double-l" />
					</ClayButton>
				}
			/>

			<ClayPaginationBar.Results>
				{sub(Liferay.Language.get('showing-x-to-x-of-x-entries'), [
					(page - 1) * pageSize + 1,
					page * pageSize < totalCount ? page * pageSize : totalCount,
					totalCount,
				])}
			</ClayPaginationBar.Results>

			<ClayPaginationWithBasicItems
				active={page}
				defaultActive={1}
				onActiveChange={(page: number) => {
					dispatch({
						payload: {page},
						type: Events.ChangePagination,
					});
				}}
				totalPages={Math.ceil(totalCount / pageSize)}
			/>
		</ClayPaginationBar>
	);
};

export default PaginationBar;
