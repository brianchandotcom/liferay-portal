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

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView/ListView';
import ProgressBar from '../../../../../components/ProgressBar';
import {getCaseTypes} from '../../../../../graphql/queries';
import i18n from '../../../../../i18n';
import {filters} from '../../../../../schema/filter';

const CaseTypes = () => (
	<Container className="mt-4">
		<ListView
			initialContext={{
				columns: {
					blocked: false,
					in_progress: false,
					test_fix: false,
					untested: false,
				},
			}}
			managementToolbarProps={{
				filterFields: filters.build.caseTypes,
				title: i18n.translate('case-types'),
			}}
			query={getCaseTypes}
			tableProps={{
				columns: [
					{
						key: 'name',
						size: 'md',
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'total',
						render: () => 0,
						value: i18n.translate('total'),
					},
					{
						clickable: true,
						key: 'failed',
						render: () => 0,
						value: i18n.translate('failed'),
					},
					{
						clickable: true,
						key: 'blocked',
						render: () => 0,
						value: i18n.translate('blocked'),
					},
					{
						clickable: true,
						key: 'untested',
						render: () => 0,
						value: i18n.translate('untested'),
					},
					{
						clickable: true,
						key: 'in_progress',
						render: () => 0,
						value: i18n.translate('in-progress'),
					},
					{
						clickable: true,
						key: 'passed',
						render: () => 0,
						value: i18n.translate('passed'),
					},
					{
						clickable: true,
						key: 'test_fix',
						render: () => 0,
						value: i18n.translate('test-fix'),
					},
					{
						clickable: true,
						key: 'metrics',
						render: () => (
							<ProgressBar
								items={{
									blocked: 0,
									failed: 2,
									incomplete: 0,
									passed: 30,
									test_fix: 0,
								}}
							/>
						),
						size: 'sm',
						value: i18n.translate('metrics'),
					},
				],
			}}
			transformData={(data) => data?.c?.caseTypes}
		/>
	</Container>
);

export default CaseTypes;
