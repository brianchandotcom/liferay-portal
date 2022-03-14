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
import i18n from '../../../i18n';

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
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/suites`,
					title: testrayProject.name,
				},
				{
					category: i18n.translate('case').toUpperCase(),
					title: testraySuite.name,
				},
			]);
		}
	}, [testraySuite, testrayProject, setHeading]);

	return (
		<LoadingWrapper isLoading={loading}>
			<Container title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('description'),
							value: testraySuite?.description,
						},
						{
							title: i18n.translate('create-date'),
							value: testraySuite?.dateCreated,
						},
						{
							title: i18n.translate('date-last-modified'),
							value: testraySuite?.dateModified,
						},
						{
							title: i18n.translate('created-by'),
							value: 'John Doe',
						},
					]}
				/>
			</Container>

			<Container
				className="mt-4"
				title={i18n.translate('case-parameters')}
			>
				<QATable
					items={[
						{
							title: i18n.translate('case-types'),
							value: 'Manual Test',
						},
						{
							title: i18n.translate('components'),
							value: 'Deployment',
						},
						{
							title: i18n.translate('subcomponents'),
							value: '',
						},
						{title: i18n.translate('priority'), value: '5'},
						{title: i18n.translate('requirements'), value: ''},
					]}
				/>
			</Container>

			<Container className="mt-4">
				<ListView
					query={getTestrayCases}
					tableProps={{
						columns: [
							{
								key: 'priority',
								value: i18n.translate('priority'),
							},
							{
								key: 'component',
								value: i18n.translate('component'),
							},
							{
								clickable: true,
								key: 'name',
								value: i18n.translate('case-name'),
							},
						],
						navigateTo: ({id}) => id?.toString(),
					}}
					transformData={(data) => data?.c?.testrayCases}
				/>
			</Container>
		</LoadingWrapper>
	);
};

export default Suite;
