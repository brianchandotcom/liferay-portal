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

import {Container} from '@clayui/layout';

import ListView from '../../components/ListView/ListView';
import {getLiferayUsers} from '../../graphql/queries/liferayUser';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';

const UserList: React.FC = () => {
	useHeader({
		useDropdown: [],
		useHeading: [
			{
				title: i18n.translate('manage-users'),
			},
		],
	});

	return (
		<Container fluid title={i18n.translate('cases')}>
			<ListView
				query={getLiferayUsers}
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
			></ListView>
		</Container>
	);
};
export default UserList;
