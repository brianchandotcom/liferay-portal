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

import Container from '../../../../../components/Layout/Container';
import ListViewRest from '../../../../../components/ListView';
import {useHeader} from '../../../../../hooks';
import i18n from '../../../../../i18n';
import {filters} from '../../../../../schema/filter';
import dayjs from '../../../../../util/date';
import {searchUtil} from '../../../../../util/search';
import useBuildTemplateActions from './useBuildTemplateActions';

const BuildTemplates = () => {
	const navigate = useNavigate();
	const {projectId, routineId} = useParams();
	const {actions} = useBuildTemplateActions();

	useHeader({
		timeout: 110,
		useHeaderActions: {actions: []},
		useTabs: [],
	});

	return (
		<Container>
			<ListViewRest
				managementToolbarProps={{
					addButton: () => navigate('../create'),
					filterFields: filters.template as any,
					title: i18n.translate('templates'),
				}}
				resource="/builds"
				tableProps={{
					actions,
					columns: [
						{
							key: 'active',
							render: (active) =>
								active
									? i18n.translate('active')
									: i18n.translate('deactive'),

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
					navigateTo: ({id}) => id?.toString(),
				}}
				variables={{
					filter: `${searchUtil.eq(
						'projectId',
						projectId as string
					)} and ${searchUtil.eq(
						'routineId',
						routineId as string
					)} and ${searchUtil.eq(
						'template',
						true
					)} and ${searchUtil.eq('active', true)}`,
				}}
			/>
		</Container>
	);
};

export default BuildTemplates;
