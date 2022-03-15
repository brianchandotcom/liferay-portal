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
import StatusBadge from '../../../components/StatusBadge';
import QATable from '../../../components/Table/QATable';
import {TestrayCase, getCaseResults} from '../../../graphql/queries';
import i18n from '../../../i18n';
import {getStatusLabel} from '../../../util/constants';

const Case = () => {
	const {testrayCase}: {testrayCase: TestrayCase} = useOutletContext();

	return (
		<>
			<Container title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('type'),
							value: testrayCase.caseType?.name,
						},
						{
							title: i18n.translate('priority'),
							value: testrayCase.priority,
						},
						{
							title: i18n.translate('main-component'),
							value: testrayCase.component?.name,
						},
						{
							title: i18n.translate('description'),
							value: testrayCase.description,
						},
						{
							title: i18n.translate('estimed-duration'),
							value: testrayCase.estimatedDuration,
						},
						{
							title: i18n.translate('steps'),
							value: testrayCase.steps,
						},
						{
							title: i18n.translate('date-created'),
							value: testrayCase.dateCreated,
						},
						{
							title: i18n.translate('date-modified'),
							value: testrayCase.dateModified,
						},
						{
							title: i18n.translate('all-issues-found'),
							value: '-',
						},
					]}
				/>
			</Container>

			<Container className="mt-3" title={i18n.translate('test-history')}>
				<ListView
					query={getCaseResults}
					tableProps={{
						columns: [
							{
								key: 'dateCreated',
								value: i18n.translate('create-date'),
							},
							{
								key: 'build',
								render: (build) => {
									return build?.gitHash;
								},
								value: i18n.translate('git-hash'),
							},
							{
								key: 'product-version',
								render: (_, {build}) => {
									return build?.productVersion?.name;
								},
								value: i18n.translate('product-version'),
							},
							{
								key: 'run',
								render: (run) => {
									return run?.externalReferencePK;
								},
								size: 'lg',
								value: i18n.translate('environment'),
							},
							{
								key: 'routine',
								render: (_, {build}) => build?.routine?.name,
								value: i18n.translate('routine'),
							},
							{
								key: 'dueStatus',
								render: (dueStatus) => {
									return (
										<StatusBadge
											type={getStatusLabel(
												dueStatus
											)?.toLowerCase()}
										>
											{getStatusLabel(dueStatus)}
										</StatusBadge>
									);
								},
								value: i18n.translate('status'),
							},
							{
								key: 'warnings',
								value: i18n.translate('warnings'),
							},
							{key: 'issues', value: i18n.translate('issues')},
							{key: 'errors', value: i18n.translate('errors')},
						],
					}}
					transformData={(data) => data?.caseResults}
				/>
			</Container>
		</>
	);
};

export default Case;
