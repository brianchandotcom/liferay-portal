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

import {useNavigate} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import {ListViewContextProviderProps} from '../../../context/ListViewContext';
import {FormModal} from '../../../hooks/useFormModal';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {Action} from '../../../types';
import useUserActions from './useUserActions';

type UserListViewProps = {
	actions?: Action[];
	formModal?: FormModal;
	variables?: any;
} & {
	listViewProps?: Partial<
		ListViewProps & {initialContext?: Partial<ListViewContextProviderProps>}
	>;
	tableProps?: Partial<TableProps>;
};

const UserListView: React.FC<UserListViewProps> = ({
	actions,
	formModal,
	listViewProps,
	tableProps,
	variables,
}) => {
	const navigate = useNavigate();

	return (
		<ListView
			forceRefetch={formModal?.forceRefetch}
			managementToolbarProps={{
				addButton: () => navigate('create'),
				title: i18n.translate('users'),
			}}
			resource="/user-accounts"
			tableProps={{
				actions,
				columns: [
					{
						clickable: true,
						key: 'givenName',
						render: (givenName, {familyName}) =>
							`${givenName} ${familyName}`,
						sorteable: true,
						value: i18n.translate('name'),
					},
					{
						clickable: true,
						key: 'alternateName',
						sorteable: true,
						value: i18n.translate('screen-name'),
					},
					{
						clickable: true,
						key: 'emailAddress',
						sorteable: true,
						value: i18n.translate('email-address'),
					},
				],
				...tableProps,
			}}
			transformData={(response) => ({
				...response,
				actions: {
					...response.actions,
					create: response.actions['post-user-account'],
				},
			})}
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Users = () => {
	const {actions, formModal} = useUserActions();
	const navigate = useNavigate();

	useHeader({
		useDropdown: [],
		useHeading: [
			{
				title: i18n.translate('manage-users'),
			},
		],
		useIcon: 'cog',
	});

	return (
		<Container>
			<UserListView
				actions={actions}
				formModal={formModal}
				tableProps={{
					onClickRow: (user) => navigate(`${user.id}/update`),
				}}
			/>
		</Container>
	);
};

export {UserListView};

export default Users;
