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

import {useParams} from 'react-router-dom';

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView/ListView';
import {APIResponse, CTypePagination} from '../../../../../graphql/queries';
import {TestrayRun, getRuns} from '../../../../../graphql/queries/testrayRun';
import i18n from '../../../../../i18n';
import {filters} from '../../../../../schema/filter';
import {searchUtil} from '../../../../../util/search';

const transformData = (
	data: CTypePagination<'runs', TestrayRun>
): APIResponse<TestrayRun> => {
	const runsPagination = data?.c?.runs || {};

	return {
		...runsPagination,
		items: runsPagination.items?.map((run) => {
			const environmentValues = run.name.split('|');

			const [
				applicationServer,
				browser,
				database,
				javaJDK,
				operatingSystem,
			] = environmentValues;

			return {
				...run,
				applicationServer,
				browser,
				database,
				javaJDK,
				operatingSystem,
			};
		}),
	};
};

const Runs = () => {
	const {buildId} = useParams();

	return (
		<Container className="mt-4">
			<ListView
				managementToolbarProps={{
					filterFields: filters.build.runs,
					title: i18n.translate('runs'),
				}}
				query={getRuns}
				tableProps={{
					columns: [
						{
							key: 'number',
							render: (_, {rowIndex}) => rowIndex + 1,
							value: i18n.translate('run'),
						},
						{
							key: 'applicationServer',
							value: i18n.translate('application-server'),
						},
						{
							key: 'browser',
							value: i18n.translate('browser'),
						},
						{
							key: 'database',
							value: i18n.translate('database'),
						},
						{
							key: 'javaJDK',
							value: 'javaJDK',
						},
						{
							key: 'operatingSystem',
							value: i18n.translate('operating-system'),
						},
					],
				}}
				transformData={transformData}
				variables={{
					filter: searchUtil.eq('buildId', buildId as string),
				}}
			/>
		</Container>
	);
};

export default Runs;
