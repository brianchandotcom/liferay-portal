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

import {useQuery} from '@apollo/client';
import {Link} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import Table from '../../components/Table';
import {getTestrayProjects} from '../../graphql/queries';
import {Liferay} from '../../services/liferay/liferay';

const Home = () => {
	const {data} = useQuery(getTestrayProjects, {
		variables: {scopeKey: Liferay.ThemeDisplay.getSiteGroupId()},
	});

	const testrayProjects = data?.c?.testrayProjects?.items || [];

	return (
		<Container title="Projects">
			<Table
				columns={[
					{
						key: 'name',
						render: (value: string) => (
							<Link
								to={`/project/${value
									.toLowerCase()
									.replace(' ', '_')}/routines`}
							>
								{value}
							</Link>
						),
						value: 'Project',
					},
					{key: 'description', value: 'Description'},
				]}
				items={testrayProjects}
			/>
		</Container>
	);
};

export default Home;
