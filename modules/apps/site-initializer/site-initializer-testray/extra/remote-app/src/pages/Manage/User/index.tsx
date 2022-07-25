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
import ListView, {
	ListViewProps,
} from '../../../components/ListView/ListViewRest';
import {TableProps} from '../../../components/Table';

// import {getLiferayUserAccounts} from '../../../graphql/queries/liferayUserAccount';

import {FormModal} from '../../../hooks/useFormModal';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {ActionList} from '../../../types';
import UserFormModal from './UserFormModal';
import useUserActions from './useUserActions';

type UserListViewProps = {
	actions?: ActionList;
	formModal?: FormModal;
	variables?: any;
} & {listViewProps?: Partial<ListViewProps>; tableProps?: Partial<TableProps>};

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
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Users: React.FC = () => {
	const {actions, formModal} = useUserActions();

	useHeader({
		useDropdown: [],
		useHeading: [
			{
				title: i18n.translate('manage-users'),
			},
		],
	});

	return (
		<Container>
			<UserListView actions={actions} formModal={formModal} />

			<UserFormModal modal={formModal.modal} />
		</Container>
	);
};

export {UserListView};

export default Users;
