/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import {ClayToggle} from '@clayui/form';
import React, {useState} from 'react';

import {Job, jobs as initialJobs} from './jobs';

const Recommendations: React.FC = () => {
	const [jobs, setJobs] = useState<Job[]>(initialJobs);

	return (
		<Table className="table-bordered" columnsVisibility={false}>
			<Head>
				<Cell key="model-name">
					{Liferay.Language.get('model-name')}
				</Cell>

				<Cell key="description">
					{Liferay.Language.get('description')}
				</Cell>

				<Cell key="type" width="104px">
					{Liferay.Language.get('type')}
				</Cell>

				<Cell
					children={undefined}
					key="toggle"
					textValue={Liferay.Language.get('toggle')}
					width="80px"
				/>
			</Head>

			<Body>
				{jobs.map(({active, description, id, title, type}, index) => (
					<Row key={id}>
						<Cell>
							<Text size={3} weight="bold">
								{title}
							</Text>
						</Cell>

						<Cell>
							<Text size={3}>{description}</Text>
						</Cell>

						<Cell>
							<Text size={3}>{type}</Text>
						</Cell>

						<Cell>
							<ClayToggle
								onToggle={() => {
									const updatedJobs = [...jobs];

									updatedJobs[index] = {
										...updatedJobs[index],
										active: !updatedJobs[index].active,
									};

									setJobs(updatedJobs);
								}}
								toggled={active}
							/>
						</Cell>
					</Row>
				))}
			</Body>
		</Table>
	);
};

export default Recommendations;
