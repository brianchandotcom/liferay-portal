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

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {getTestraySuites} from '../../../graphql/queries';

const Suites = () => (
	<Container title="Suites">
		<ListView
			query={getTestraySuites}
			tableProps={{
				columns: [
					{clickable: true, key: 'name', value: 'Case Name'},
					{key: 'description', value: 'Description'},
					{key: 'type', value: 'Type'},
				],
				navigateTo: ({testraySuiteId}) => testraySuiteId?.toString(),
			}}
			transformData={(data) => data?.c?.testraySuites}
			variables={{}}
		/>
	</Container>
);

export default Suites;
