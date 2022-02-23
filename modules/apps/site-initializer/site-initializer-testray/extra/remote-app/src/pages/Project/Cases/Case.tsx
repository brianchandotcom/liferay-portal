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

import {useOutletContext} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import QATable from '../../../components/Table/QATable';
import {getTestrayCases} from '../../../graphql/queries/testrayCase';

const Case = () => {
	const {testrayCase}: any = useOutletContext();

	return (
		<>
			<Container title="Details">
				<QATable
					items={[
						{
							title: 'type',
							value:
								testrayCase.type || 'Automated Functional Test',
						},
						{
							title: 'priority',
							value: testrayCase.priority,
						},
						{
							title: 'main component',
							value: testrayCase.component || 'A/B Test',
						},
						{
							title: 'description',
							value: testrayCase.description,
						},
						{
							title: 'estimed duration',
							value: testrayCase.estimatedDuration,
						},
						{
							title: 'steps',
							value: testrayCase.steps,
						},
						{
							title: 'date created',
							value: 'dez 13, 2021 12:00 PM',
						},
						{
							title: 'date modified',
							value: 'dez 13, 2021 12:00 PM',
						},
						{
							title: 'all issues found',
							value: '-',
						},
					]}
				/>
			</Container>

			<Container className="mt-3" title="Test History">
				<ListView
					query={getTestrayCases}
					tableProps={{
						columns: [
							{key: 'priority', value: 'Priority'},
							{key: 'name', value: 'Case Name'},
							{key: 'component', value: 'Component'},
						],
					}}
					transformData={(data) => data?.c?.testrayCases}
					variables={{}}
				/>
			</Container>
		</>
	);
};

export default Case;
