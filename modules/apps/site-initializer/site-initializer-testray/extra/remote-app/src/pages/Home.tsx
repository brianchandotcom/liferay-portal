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

import ClayTable from '@clayui/table';

const projects = [
	'Analytics Cloud',
	'App Builder',
	'Customer Support',
	'DXP Cloud Client',
	'Forms',
	'GS-LatAm: Gr1d - Innovation Cloud',
	'GS-LatAm: Project Petrobrás',
	'GS-USA: Project FHLB-NY',
	'Help Center',
	'HR',
	'HR Review',
	'LESA',
	'Liferay Analytics Cloud',
	'Liferay Portal 6.1',
	'Liferay Portal 6.2',
	'Liferay Portal 7.0',
	'Liferay Portal 7.1',
	'Liferay Portal 7.2',
	'Liferay Portal 7.3',
	'Liferay Portal 7.4',
];

const Home = () => (
	<div className="bg-white border-1 p-4">
		<h3>Projects</h3>

		<ClayTable className="mt-4">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell>
						Project
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>Description</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{projects.map((project) => (
					<ClayTable.Row key={project}>
						<ClayTable.Cell headingTitle>{project}</ClayTable.Cell>

						<ClayTable.Cell>{project}</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	</div>
);

export default Home;
