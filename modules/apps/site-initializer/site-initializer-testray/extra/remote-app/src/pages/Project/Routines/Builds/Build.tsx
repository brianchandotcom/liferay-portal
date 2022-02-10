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

import Container from '../../../../components/Layout/Container';
import ListView from '../../../../components/ListView/ListView';
import {getTestrayCases} from '../../../../graphql/queries';
import {Liferay} from '../../../../services/liferay/liferay';

const Build = () => {
	return (
		<Container className="mt-4" title="Tests">
			<ListView
				query={getTestrayCases}
				tableProps={{
					columns: [
						{
							key: 'priority',
							value: 'Priority',
						},
						{
							key: 'component',
							value: 'Component',
						},
						{
							key: 'name',
							value: 'Case',
						},
						{
							key: 'run',
							value: 'Run',
						},
						{
							key: 'assignee',
							value: 'Assignee',
						},
						{
							key: 'status',
							value: 'Status',
						},
						{
							key: 'issues',
							value: 'Issues',
						},
						{
							key: 'error',
							value: 'Errors',
						},
					],
				}}
				transformData={(data) => data?.c?.testrayCases}
				variables={{scopeKey: Liferay.ThemeDisplay.getScopeGroupId()}}
			/>
		</Container>
	);
};

export default Build;
