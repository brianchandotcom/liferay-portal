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

import {Link} from 'react-router-dom';

import Chart from '../../../components/Charts';
import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import ProgressBar from '../../../components/ProgressBar';
import {getTestrayBuilds} from '../../../graphql/queries';
import {Liferay} from '../../../services/liferay/liferay';

const getRandom = (max = 50) => Math.ceil(Math.random() * max);

const testrayData = [...new Array(20)].map((value, index) => ({
	blocked: getRandom(),
	create_date: new Date(2022, 1, index + 10).toLocaleString(),
	failed: getRandom(20),
	git_hash: '51bde0fe',
	incomplete: getRandom(10),
	name: `acceptance-pullrequest(7.0.x) - ${index}`,
	passed: getRandom(200),
	product_version: '7.0.x',
	test_fix: getRandom(10),
}));

const Routine = () => {
	return (
		<Container title="Build History">
			<Chart data={testrayData} />

			<ListView
				query={getTestrayBuilds}
				tableProps={{
					columns: [
						{
							key: 'name',
							render: (
								name: string,
								{testrayBuildId}: {testrayBuildId: number}
							) => (
								<Link to={`build/${testrayBuildId}`}>
									{name}
								</Link>
							),
							value: 'Build',
						},
						{key: 'dateCreated', value: 'Create Date'},
						{key: 'gitHash', value: 'Git Hash'},
						{key: 'product_version', value: 'Product Version'},
						{key: 'failed', value: 'Failed'},
						{key: 'blocked', value: 'Blocked'},
						{key: 'test_fix', value: 'Test Fix'},
						{
							key: 'metrics',
							render: () => (
								<ProgressBar
									items={{
										blocked: 0,
										failed: 2,
										incomplete: 0,
										passed: 30,
										test_fix: 0,
									}}
								/>
							),
							value: 'Metrics',
						},
					],
				}}
				transformData={(data) => data?.c?.testrayBuilds}
				variables={{scopeKey: Liferay.ThemeDisplay.getScopeGroupId()}}
			/>
		</Container>
	);
};

export default Routine;
