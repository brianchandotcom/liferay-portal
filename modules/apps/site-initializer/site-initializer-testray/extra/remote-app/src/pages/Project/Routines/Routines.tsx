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

import {Link, Outlet} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ProgressBar from '../../../components/ProgressBar';
import Table from '../../../components/Table';

const Routines = () => {
	return (
		<Container title="Routines">
			<Table
				className="mt-2"
				columns={[
					{
						key: 'routine',
						render: (routine: string) => (
							<Link to="build">{routine}</Link>
						),
						value: 'ROUTINE',
					},
					{key: 'execution_date', value: 'EXECUTION DATE'},
					{key: 'failed', value: 'FAILED'},
					{key: 'blocked', value: 'BLOCKED'},
					{key: 'test_fix', value: 'TEST FIX'},
					{
						key: 'metrics',
						render: () => (
							<ProgressBar
								blocked={10}
								failed={0}
								incomplete={0}
								passed={90}
								test_fix={0}
							/>
						),
						value: 'METRICS',
					},
				]}
				items={[
					{
						blocked: 0,
						execution_date: '10 hours ago',
						failed: 107,
						metrics: '123',
						routine: '[master] ci:test:app-builder',
						test_fix: 0,
					},
				]}
			/>

			<Outlet />
		</Container>
	);
};

export default Routines;
