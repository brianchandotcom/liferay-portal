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

import {useOutletContext, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import QATable from '../../../components/Table/QATable';
import i18n from '../../../i18n';
import {
	PickList,
	TestrayCase,
	testrayCaseResultImpl,
} from '../../../services/rest';
import dayjs from '../../../util/date';
import {searchUtil} from '../../../util/search';
import useCaseActions from './useCaseActions';

const Case = () => {
	const {caseId, projectId} = useParams();
	const {testrayCase}: {testrayCase: TestrayCase} = useOutletContext();
	const {actions} = useCaseActions();

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
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
							title: i18n.translate('estimated-duration'),
							value: testrayCase.estimatedDuration,
						},
						{
							title: i18n.translate('steps'),
							value: testrayCase.steps,
						},
						{
							title: i18n.translate('date-created'),
							value: dayjs(testrayCase.dateCreated).format('lll'),
						},
						{
							title: i18n.translate('date-modified'),
							value: dayjs(testrayCase.dateModified).format(
								'lll'
							),
						},
						{
							title: i18n.translate('all-issues-found'),
							value: '-',
						},
					]}
				/>
			</Container>

			<Container className="mt-3">
				<ListView
					initialContext={{
						columns: {
							caseType: false,
							dateCreated: false,
							dateModified: false,
							issues: false,
							team: false,
						},
					}}
					managementToolbarProps={{
						title: i18n.translate('test-history'),
						visible: true,
					}}
					resource={testrayCaseResultImpl.resource}
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'dateCreated',
								render: (date) => dayjs(date).format('lll'),
								size: 'sm',
								value: i18n.translate('create-date'),
							},
							{
								clickable: true,
								key: 'build',
								render: (build) => {
									return build?.gitHash;
								},
								value: i18n.translate('git-hash'),
							},
							{
								clickable: true,
								key: 'product-version',
								render: (_, {build}) =>
									build?.productVersion?.name,
								value: i18n.translate('product-version'),
							},
							{
								clickable: true,
								key: 'run',
								render: (run) => run?.externalReferencePK,
								size: 'lg',
								value: i18n.translate('environment'),
							},
							{
								clickable: true,
								key: 'routine',
								render: (_, {build}) => build?.routine?.name,
								value: i18n.translate('routine'),
							},
							{
								key: 'dueStatus',
								render: (dueStatus: PickList) => {
									return (
										<StatusBadge
											type={
												dueStatus.key as StatusBadgeType
											}
										>
											{dueStatus.name}
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
						navigateTo: ({build, id}) =>
							`/project/${projectId}/routines/${build?.routine?.id}/build/${build?.id}/case-result/${id}`,
					}}
					transformData={(response) =>
						testrayCaseResultImpl.transformDataFromList(response)
					}
					variables={{
						filter: searchUtil.eq('caseId', caseId as string),
					}}
				/>
			</Container>
		</>
	);
};

export default Case;
