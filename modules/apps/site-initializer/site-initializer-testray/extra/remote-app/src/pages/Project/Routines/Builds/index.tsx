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

import Chart from '../../../../components/Charts';
import Container from '../../../../components/Layout/Container';
import ProgressBar from '../../../../components/ProgressBar';
import Table from '../../../../components/Table';

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

			<Table
				className="mt-4"
				columns={[
					{key: 'create_date', value: 'Create Date'},
					{key: 'git_hash', value: 'Git Hash'},
					{key: 'product_version', value: 'Product Version'},
					{key: 'name', value: 'Build'},
					{key: 'failed', value: 'Failed'},
					{key: 'blocked', value: 'Blocked'},
					{key: 'test_fix', value: 'Test Fix'},
					{
						key: 'metrics',
						render: (_a: any, b: any) => (
							<ProgressBar
								items={{
									blocked: b.blocked,
									failed: b.failed,
									incomplete: b.incomplete,
									passed: b.passed,
									test_fix: b.test_fix,
								}}
							/>
						),
						value: 'metrics',
					},
				]}
				items={testrayData}
			/>
		</Container>
	);
};

export default Routine;
