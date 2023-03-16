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

import {useNavigate, useParams} from 'react-router-dom';
import {PickList} from '~/services/rest';
import {BuildStatuses} from '~/util/statuses';

import Container from '../../../../../components/Layout/Container';
import ListViewRest from '../../../../../components/ListView';
import {useHeader} from '../../../../../hooks';
import useSearchBuilder from '../../../../../hooks/useSearchBuilder';
import i18n from '../../../../../i18n';
import dayjs from '../../../../../util/date';
import useBuildTemplateActions from './useBuildTemplateActions';

const templateFilterInitialContext = {
	entries: [
		{
			label: i18n.translate('status'),
			name: 'dueStatus',
			value: BuildStatuses.ACTIVATED,
		},
	],
	filter: {dueStatus: BuildStatuses.ACTIVATED},
};

const BuildTemplates = () => {
	const {actions} = useBuildTemplateActions();
	const {projectId, routineId} = useParams();
	const navigate = useNavigate();
	const searchBuilder = useSearchBuilder({useURIEncode: false});

	const buildFilter = searchBuilder
		.eq('projectId', projectId as string)
		.and()
		.eq('routineId', routineId as string)
		.and()
		.eq('template', true)
		.and()
		.build();

	useHeader({
		headerActions: {actions: []},
		tabs: [],
		timeout: 110,
	});

	return (
		<Container>
			<ListViewRest
				initialContext={{
					filters: templateFilterInitialContext,
				}}
				managementToolbarProps={{
					addButton: () => navigate('../create/template/true'),
					filterSchema: 'buildTemplates',
					title: i18n.translate('templates'),
				}}
				resource="/builds"
				tableProps={{
					actions,
					columns: [
						{
							key: 'dueStatus',
							render: (dueStatus: PickList) => dueStatus?.name,
							sorteable: true,
							value: i18n.translate('status'),
						},
						{
							key: 'name',
							value: i18n.translate('template-name'),
						},
						{
							key: 'templateTestrayBuildId',
							value: i18n.translate('template-test'),
						},
						{
							key: 'templateTestrayBuildId',
							value: i18n.translate('latest-build'),
						},
						{
							key: 'modifiedDate',
							render: (modifiedDate) =>
								dayjs(modifiedDate).format('lll'),
							value: i18n.translate('last-used-date'),
						},
					],
					navigateTo: ({id}) => id,
				}}
				variables={{
					filter: buildFilter,
				}}
			/>
		</Container>
	);
};

export default BuildTemplates;
