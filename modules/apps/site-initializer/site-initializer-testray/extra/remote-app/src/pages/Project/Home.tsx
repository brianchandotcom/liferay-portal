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
import {initialState} from '../../context/HeaderContext';
import {getTestrayProjects} from '../../graphql/queries';
import useHeader from '../../hooks/useHeader';

const Home = () => {
	useHeader({useHeading: initialState.heading});

	return (
		<Container title="Projects">
			<ListView
				query={getTestrayProjects}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'name',
							value: 'Project',
						},
						{key: 'description', value: 'Description'},
					],
					navigateTo: (item) =>
						`/project/${item.testrayProjectId}/routines`,
				}}
				transformData={(data) => data?.c?.testrayProjects}
				variables={{}}
			/>
		</Container>
	);
};

export default Home;
