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
import ListViewRest from '../../../../../components/ListView';
import {useHeader} from '../../../../../hooks';
import i18n from '../../../../../i18n';
import {filters} from '../../../../../schema/filter';
import {getTimeFromNow} from '../../../../../util/date';
import {searchUtil} from '../../../../../util/search';
import useBuildTemplateActions from './useBuildTemplateActions';

const BuildTemplates = () => {
	const {projectId} = useParams();
	const {actions} = useBuildTemplateActions();

	useHeader({
		timeout: 110,
		useTabs: [],
	});

	return (
		<Container>
			<ListViewRest
				managementToolbarProps={{
					addButton: () => alert('created'),
					filterFields: filters.template as any,
					title: i18n.translate('templates'),
				}}
				resource="/builds"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							render: () => 'Active',
							sorteable: true,
							value: i18n.translate('status'),
						},
						{
							clickable: true,
							key: 'templateName',
							render: () => 'Test Name',
							value: i18n.translate('template-name'),
						},
						{
							clickable: true,
							key: 'templateTest',
							render: () => 0,
							value: i18n.translate('template-test'),
						},
						{
							clickable: true,
							key: 'latestBuild',
							render: () => 0,
							value: i18n.translate('latest-build'),
						},
						{
							clickable: true,
							key: 'lastUsedDate',
							render: getTimeFromNow,
							value: i18n.translate('last-used-date'),
						},
					],
					navigateTo: ({id}) => id?.toString(),
				}}
				variables={{
					filter: `${searchUtil.eq(
						'projectId',
						Number(projectId)
					)} and ${searchUtil.eq('template', 'true')}`,
				}}
			/>
		</Container>
	);
};

export default BuildTemplates;
