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
import {getTestrayCases} from '../../../graphql/queries/testrayCase';

const Cases = () => (
	<Container title="Cases">
		<ListView
			query={getTestrayCases}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'name',
						value: 'Case Name',
					},
					{key: 'priority', value: 'Priority'},
					{key: 'type', value: 'Case Type'},
					{key: 'team', value: 'Team'},
					{key: 'component', value: 'Component'},
					{key: 'issues', value: 'Issues'},

					{
						key: 'createdDate',
						render: () => 'dez 13, 2021 12:00 PM',
						value: 'Create Date',
					},
					{
						key: 'modifiedDate',
						render: () => 'dez 13, 2021 12:00 PM',
						value: 'Modified Date',
					},
				],
				navigateTo: ({testrayCaseId}) => testrayCaseId?.toString(),
			}}
			transformData={(data) => data?.c?.testrayCases}
			variables={{}}
		/>
	</Container>
);

export default Cases;
