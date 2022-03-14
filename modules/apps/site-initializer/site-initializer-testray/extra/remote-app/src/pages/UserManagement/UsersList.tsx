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

import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView/ListView';
import {getLiferayUserAccounts} from '../../graphql/queries/liferayUser';
import useFormModal from '../../hooks/useFormModal';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import UserModal from './UserModal';

const UserList: React.FC = () => {
	useHeader({
		useDropdown: [],
		useHeading: [
			{
				title: i18n.translate('manage-users'),
			},
		],
	});

	const {forceRefetch, modal} = useFormModal({isVisible: false});

	return (
		<>
			<Container title={i18n.translate('users')}>
				<ListView
					forceRefetch={forceRefetch}
					managementToolbarProps={{
						addButton: modal.open,
						visible: true,
					}}
					query={getLiferayUserAccounts}
					tableProps={{
						columns: [
							{
								clickable: true,
								key: 'givenName',
								render: (value, {familyName}) => {
									return `${value} ${familyName}`;
								},
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
						navigateTo: () => '/manage/adduser',
					}}
					transformData={(data) => data?.userAccounts}
				/>
			</Container>
			<UserModal modal={modal} />
		</>
	);
};
export default UserList;
