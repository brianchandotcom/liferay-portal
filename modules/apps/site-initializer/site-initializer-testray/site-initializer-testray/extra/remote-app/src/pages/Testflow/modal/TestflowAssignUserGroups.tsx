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

import {useEffect, useState} from 'react';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import i18n from '../../../i18n';
import fetcher from '../../../services/fetcher';
import {Actions} from '../../../types';
import {getUniqueList} from '../../../util';
import {searchUtil} from '../../../util/search';

type UserGroupsListViewProps = {
	actions?: Actions;
	projectId?: number | string;
	variables?: any;
} & {listViewProps?: Partial<ListViewProps>; tableProps?: Partial<TableProps>};

const UserGroupsListView: React.FC<UserGroupsListViewProps> = ({
	listViewProps,
	tableProps,
	variables,
}) => {
	return (
		<ListView
			resource="/user-groups"
			tableProps={{
				columns: [
					{
						key: 'name',
						size: 'xl',
						value: i18n.translate('name'),
					},
				],
				rowSelectable: true,
				...tableProps,
			}}
			transformData={(data: any) => data}
			variables={variables}
			{...listViewProps}
		/>
	);
};

type UserGroupProps = {
	setState: any;
	state: any;
};

const UserGroups: React.FC<UserGroupProps> = ({setState}) => {
	const [users, setUsers] = useState<any>([]);

	useEffect(() => {
		if (users?.length) {
			fetcher(
				`/user-accounts?field=id&filter=${searchUtil.in(
					'userGroupIds',
					users
				)}`
			).then((response) => {
				const userId = response?.items?.map(({id}: any) => id);

				setState((state: any) => getUniqueList([...state, ...userId]));
			});
		}
	}, [setState, users]);

	return (
		<Container>
			<UserGroupsListView
				listViewProps={{
					onContextChange: ({selectedRows}) => {
						setUsers(selectedRows);
					},
				}}
			/>
		</Container>
	);
};

export {UserGroupsListView, UserGroups};

export default UserGroups;
