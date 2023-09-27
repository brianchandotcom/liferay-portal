/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import fetcher from '../../../services/fetcher';
import {Actions} from '../../../types';
import {getUniqueList} from '../../../util';

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
				`/user-accounts?field=id&filter=${SearchBuilder.in(
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
