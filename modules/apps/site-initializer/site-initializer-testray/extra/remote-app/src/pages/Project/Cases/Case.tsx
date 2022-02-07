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
import {useContext, useEffect} from 'react';
import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {LoadingWrapper} from '../../../components/Loading';
import QATable from '../../../components/Table/QATable';
import {HeaderContext, HeaderTypes} from '../../../context/HeaderContext';
import {
	getTestrayCase,
	getTestrayCases,
} from '../../../graphql/queries/testrayCase';

// import useHeader from '../../../hooks/useHeader';

import {Liferay} from '../../../services/liferay/liferay';

const Requirement = () => {
	const {testrayCaseId} = useParams();

	const [, dispatch] = useContext(HeaderContext);

	const {data, loading} = useQuery(getTestrayCase, {
		variables: {
			testrayCaseId,
		},
	});

	const testrayCase = data?.c?.testrayCase || {};

	// useHeader({
	// 	pageHeading: testrayCase?.name
	// 		? [
	// 				{
	// 					category: 'PROJECT',
	// 					title: testrayCase.name,
	// 				},
	// 		  ]
	// 		: undefined,
	// });

	// useEffect(() => {
	// 	if (testrayCase?.name) {
	// 		dispatch({
	// 			payload: [
	// 				{
	// 					category: 'PROJECT',
	// 					title: `${heading[0].title} / ${testrayCase.name} `,
	// 				},
	// 			],
	// 			type: HeaderTypes.SET_HEADING,
	// 		});
	// 	}
	// }, [testrayCase, dispatch]);

	useEffect(() => {
		setTimeout(() => {
			dispatch({
				payload: [],
				type: HeaderTypes.SET_TABS,
			});
		}, 0);
	}, []);

	return (
		<LoadingWrapper isLoading={loading}>
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
					variables={{
						scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
					}}
				/>
			</Container>
		</LoadingWrapper>
	);
};

export default Requirement;
