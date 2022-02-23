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
import {getTestrayComponents} from '../../../../../graphql/queries/testrayComponent';

const Component = () => {
	return (
		<Container className="mt-4" title="Component">
			<ListView
				query={getTestrayComponents}
				tableProps={{
					columns: [
						{
							key: 'name',
							value: 'Team',
						},
						{
							key: 'failed',
							value: 'Failed',
						},
						{
							key: 'Total',
							value: 'Total',
						},
						{
							key: 'metrics',
							value: 'Metrics',
						},
					],
				}}
				transformData={(data) => data?.c?.testrayComponents}
				variables={{}}
			/>
		</Container>
	);
};

export default Component;
