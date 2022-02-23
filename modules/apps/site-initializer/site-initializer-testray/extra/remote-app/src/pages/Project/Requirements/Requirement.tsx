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
import ClayIcon from '@clayui/icon';
import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {LoadingWrapper} from '../../../components/Loading';
import QATable from '../../../components/Table/QATable';
import {getTestrayCases, getTestrayRequirement} from '../../../graphql/queries';

const Requirement = () => {
	const {requirementId} = useParams();

	const {data, loading} = useQuery(getTestrayRequirement, {
		variables: {
			testrayRequirementId: requirementId,
		},
	});

	const testrayRequirement = data?.c?.testrayRequirement || {};

	return (
		<LoadingWrapper isLoading={loading}>
			<Container title="Details">
				<QATable
					items={[
						{
							title: 'key',
							value: testrayRequirement.key,
						},
						{
							title: 'link',
							value: (
								<a
									href={testrayRequirement.linkURL}
									rel="noopener noreferrer"
									target="_blank"
								>
									{testrayRequirement.linkTitle}

									<ClayIcon
										className="ml-2"
										symbol="shortcut"
									/>
								</a>
							),
						},
						{title: 'team', value: testrayRequirement.team},
						{
							title: 'component',
							value: testrayRequirement.component,
						},
						{
							title: 'jira components',
							value: testrayRequirement.components,
						},
						{
							title: 'summary',
							value: testrayRequirement.summary,
						},
						{
							title: 'description',
							value: testrayRequirement.description,
						},
					]}
				/>
			</Container>

			<Container className="mt-3" title="Cases">
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
		</LoadingWrapper>
	);
};

export default Requirement;
