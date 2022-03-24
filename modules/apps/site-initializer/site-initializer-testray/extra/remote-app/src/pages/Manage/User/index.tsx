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

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {getLiferayUserAccounts} from '../../../graphql/queries/liferayUserAccount';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import UserFormModal from './UserFormModal';
import useUserActions from './useUserActions';

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
		<>
			<Container title={i18n.translate('users')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{
						addButton: formModal.modal.open,
					}}
					query={getLiferayUserAccounts}
					tableProps={{
						actions,
						columns: [
							{
								key: 'givenName',
								render: (givenName, {familyName}) => {
									return `${givenName} ${familyName}`;
								},
								sorteable: true,
								value: i18n.translate('name'),
							},
							{
								key: 'alternateName',
								sorteable: true,
								value: i18n.translate('screen-name'),
							},
							{
								key: 'emailAddress',
								sorteable: true,
								value: i18n.translate('email-address'),
							},
						],
					}}
					transformData={(data) => data?.userAccounts}
				/>
			</Container>

			<UserFormModal modal={formModal.modal} />
		</>
	);
};
export default Users;
