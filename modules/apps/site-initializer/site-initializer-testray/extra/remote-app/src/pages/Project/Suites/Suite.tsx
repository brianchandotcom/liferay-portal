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
import {useEffect} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {LoadingWrapper} from '../../../components/Loading';
import QATable from '../../../components/Table/QATable';
import {
	CType,
	TestraySuite,
	getTestrayCases,
	getTestraySuite,
} from '../../../graphql/queries';
import useHeader from '../../../hooks/useHeader';

const Suite = () => {
	const {testraySuiteId} = useParams();
	const {testrayProject}: any = useOutletContext();

	const {data, loading} = useQuery<CType<'testraySuite', TestraySuite>>(
		getTestraySuite,
		{
			variables: {
				testraySuiteId,
			},
		}
	);

	const testraySuite = data?.c.testraySuite;

	const {setHeading} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (testraySuite && testrayProject) {
			setHeading([
				{
					category: 'PROJECT',
					path: `/project/${testrayProject.testrayProjectId}/suites`,
					title: testrayProject.name,
				},
				{category: 'CASE', title: testraySuite.name},
			]);
		}
	}, [testraySuite, testrayProject, setHeading]);

	return (
		<LoadingWrapper isLoading={loading}>
			<Container title="Details">
				<QATable
					items={[
						{
							title: 'Description',
							value: testraySuite?.description,
						},
						{
							title: 'Create Date',
							value: testraySuite?.dateCreated,
						},
						{
							title: 'Date Last Modified',
							value: testraySuite?.dateModified,
						},
						{title: 'Created By', value: 'John Doe'},
					]}
				/>
			</Container>

			<Container className="mt-4" title="Case Parameters">
				<QATable
					items={[
						{title: 'Case Types', value: 'Manual Test'},
						{title: 'Components', value: 'Deployment'},
						{
							title: 'SubComponents',
							value: '',
						},
						{title: 'Priority', value: '5'},
						{title: 'Requirements', value: ''},
					]}
				/>
			</Container>

			<Container className="mt-4">
				<ListView
					query={getTestrayCases}
					tableProps={{
						columns: [
							{key: 'priority', value: 'Priority'},
							{key: 'component', value: 'Component'},
							{
								clickable: true,
								key: 'name',
								value: 'Case Name',
							},
						],
						navigateTo: ({testrayCaseId}) =>
							testrayCaseId?.toString(),
					}}
					transformData={(data) => data?.c?.testrayCases}
					variables={{}}
				/>
			</Container>
		</LoadingWrapper>
	);
};

export default Suite;
