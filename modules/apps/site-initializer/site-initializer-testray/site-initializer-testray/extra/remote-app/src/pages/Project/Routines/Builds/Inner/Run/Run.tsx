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

import Container from '../../../../../../components/Layout/Container';
import ListView from '../../../../../../components/ListView';
import SearchBuilder from '../../../../../../core/SearchBuilder';
import i18n from '../../../../../../i18n';
import {testrayRunImpl} from '../../../../../../services/rest';
import RunFormModal from './RunFormModal';
import useRunActions from './useRunActions';

const Runs = () => {
	const {actions, formModal} = useRunActions();
	const {buildId} = useParams();

	return (
		<Container className="mt-4">
			<ListView
				forceRefetch={formModal.forceRefetch}
				initialContext={{
					columns: {
						inprogress: false,
						passed: false,
						total: false,
						untested: false,
					},
					columnsFixed: ['number'],
				}}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					filterSchema: 'buildRuns',
					title: i18n.translate('runs'),
				}}
				resource="/runs"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'number',
							render: (number) =>
								number?.toString().padStart(2, '0'),
							value: i18n.translate('run'),
						},
						{
							clickable: true,
							key: 'applicationServer',
							value: i18n.translate('application-server'),
						},
						{
							clickable: true,
							key: 'browser',
							value: i18n.translate('browser'),
						},
						{
							clickable: true,
							key: 'database',
							value: i18n.translate('database'),
						},
						{
							clickable: true,
							key: 'javaJDK',
							value: 'javaJDK',
						},
						{
							clickable: true,
							key: 'operatingSystem',
							value: i18n.translate('operating-system'),
						},
					],
					navigateTo: (run) => {
						return `..?runId=${run.id}`;
					},
				}}
				transformData={(response) =>
					testrayRunImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq('buildId', buildId as string),
				}}
			/>

			<RunFormModal modal={formModal.modal} />
		</Container>
	);
};

export default Runs;
