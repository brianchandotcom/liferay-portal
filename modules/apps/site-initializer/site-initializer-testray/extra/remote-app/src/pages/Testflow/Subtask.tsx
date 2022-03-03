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

import {Avatar} from '../../components/Avatar';
import Container from '../../components/Layout/Container';
import StatusBadge from '../../components/StatusBadge';
import Table from '../../components/Table';
import QATable from '../../components/Table/QATable';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import {Tests, subtask} from '../../util/mock';

const Subtasks = () => {
	const {setHeading} = useHeader();

	useEffect(() => {
		setHeading([
			{
				category: i18n.translate('project').toUpperCase(),
				title: i18n.translate('subtask'),
			},
		]);
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

					<div className="col-4 col-lg-4 col-md-12 pb-5">
						<QATable
							items={[
								{
									title: i18n.translate('score'),
									value: '1052',
								},
								{
									title: i18n.translate('assignee'),
									value: 'Failed prior to running test',
								},
							]}
						/>
					</div>
				</div>
			</Container>

			<Container className="mt-5" title="Tests">
				<Table
					columns={[
						{
							clickable: true,
							key: 'run',
							value: i18n.translate('run'),
						},
						{
							clickable: true,
							key: 'priority',
							value: i18n.translate('priority'),
						},
						{
							clickable: true,
							key: 'team',
							value: i18n.translate('team'),
						},
						{
							clickable: true,
							key: 'component',
							value: i18n.translate('component'),
						},
						{
							clickable: true,
							key: 'case',
							size: 'xl',
							value: i18n.translate('case'),
						},
						{
							clickable: true,
							key: 'issues',
							value: i18n.translate('issues'),
						},
						{
							clickable: true,
							key: 'status',
							render: () => (
								<StatusBadge type="blocked">
									Blocked
								</StatusBadge>
							),

							value: i18n.translate('status'),
						},
					]}
					items={Tests}
					navigateTo={() => '/testflow/details'}
				/>
			</Container>
		</>
	);
};

export default Subtasks;
