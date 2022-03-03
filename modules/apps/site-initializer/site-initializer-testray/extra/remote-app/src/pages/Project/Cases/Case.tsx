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
import QATable from '../../../components/Table/QATable';
import {getTestrayCases} from '../../../graphql/queries/testrayCase';
import i18n from '../../../i18n';

const Case = () => {
	const {testrayCase}: any = useOutletContext();

	return (
		<>
			<Container title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('type'),
							value:
								testrayCase.type || 'Automated Functional Test',
						},
						{
							title: i18n.translate('priority'),
							value: testrayCase.priority,
						},
						{
							title: i18n.translate('main-component'),
							value: testrayCase.component || 'A/B Test',
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
							value: 'dez 13, 2021 12:00 PM',
						},
						{
							title: i18n.translate('date-modified'),
							value: 'dez 13, 2021 12:00 PM',
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
					query={getTestrayCases}
					tableProps={{
						columns: [
							{
								key: 'priority',
								value: i18n.translate('priority'),
							},
							{key: 'name', value: i18n.translate('case-name')},
							{
								key: 'component',
								value: i18n.translate('component'),
							},
						],
					}}
					transformData={(data) => data?.c?.testrayCases}
				/>
			</Container>
		</>
	);
};

export default Case;
