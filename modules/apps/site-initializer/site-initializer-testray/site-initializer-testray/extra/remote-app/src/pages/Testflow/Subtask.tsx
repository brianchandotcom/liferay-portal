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

import {useEffect} from 'react';

import Avatar from '../../components/Avatar';
import Code from '../../components/Code';
import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView';
import StatusBadge from '../../components/StatusBadge';
import {StatusBadgeType} from '../../components/StatusBadge/StatusBadge';
import QATable from '../../components/Table/QATable';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import {
	PickList,
	caseResultsResource,
	testrayCaseResultImpl,
} from '../../services/rest';
import {subtask} from '../../util/mock';

const Subtasks = () => {
	const {setHeading} = useHeader();

	useEffect(() => {
		setTimeout(() => {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					title: i18n.translate('subtask'),
				},
			]);
		});
	}, [setHeading]);

	return (
		<>
			<Container className="pb-6" title="Subtasks">
				<div className="d-flex flex-wrap">
					<div className="col-4 col-lg-4 col-md-12">
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge type="blocked">
											Blocked
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('assignee'),
									value: (
										<Avatar
											displayName
											name={subtask[0].assignee[0].name}
											url={subtask[0].assignee[0].url}
										/>
									),
								},
								{
									title: i18n.translate('updated'),
									value: '6 Hours ago',
								},
								{
									title: i18n.translate('issue'),
									value: '-',
								},
								{
									title: i18n.translate('comment'),
									value: 'None',
								},
							]}
						/>
					</div>

					<div className="col-8 col-lg-8 col-md-12 pb-5">
						<QATable
							items={[
								{
									title: i18n.translate('score'),
									value: '1052',
								},
								{
									title: i18n.translate('error'),
									value: (
										<Code>
											{`java.lang.Exception: Cookie
											expiration date is not 6 months
											ahead. The expected expiration date
											is:'2022-10-08T09' while the actual
											cookie has 'ERROR: Cookie not found,
											or script not executed as
											expected.'.`}
										</Code>
									),
								},
								{
									title: i18n.translate('merged-with'),
									value: 'ST-5, ST-6',
								},
							]}
						/>
					</div>
				</div>
			</Container>

			<Container className="mt-5" title={i18n.translate('tests')}>
				<ListView
					managementToolbarProps={{
						visible: false,
					}}
					resource={caseResultsResource}
					tableProps={{
						columns: [
							{
								key: 'case',
								render: (testrayCase) => {
									return testrayCase?.caseNumber;
								},
								value: i18n.translate('run'),
							},
							{
								key: 'case',
								render: (testrayCase) => {
									return testrayCase?.priority;
								},
								value: i18n.translate('priority'),
							},
							{
								clickable: true,
								key: 'component',
								render: (component) => {
									return component?.team?.name;
								},
								value: i18n.translate('team'),
							},
							{
								clickable: true,
								key: 'component',
								render: (component) => {
									return component?.name;
								},
								value: i18n.translate('component'),
							},
							{
								clickable: true,
								key: 'case',
								render: (testrayCase) => testrayCase?.name,
								size: 'md',
								value: i18n.translate('case'),
							},
							{key: 'issues', value: i18n.translate('issues')},

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
						],
						navigateTo: ({build, id}) =>
							`/project/routines/${build?.routine?.id}/build/${build?.id}/case-result/${id}`,
					}}
					transformData={(response) =>
						testrayCaseResultImpl.transformDataFromList(response)
					}
				/>
			</Container>
		</>
	);
};

export default Subtasks;
