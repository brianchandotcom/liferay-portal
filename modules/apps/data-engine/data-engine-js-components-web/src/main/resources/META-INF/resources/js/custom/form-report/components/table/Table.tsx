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
import React from 'react';

import {
	getPercentage,
	removeEmptyValues,
	roundPercentage,

	// @ts-ignore

} from '../../utils/data';

interface IProps {
	data: Item[];
	totalEntries: boolean;
}

interface Item {
	count: number;
	label: string;
}

export default function Table({data, totalEntries}: IProps) {
	data = removeEmptyValues(data);

	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell>
						{Liferay.Language.get('options')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>%</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{Liferay.Language.get('votes')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{Array.isArray(data) &&
					data.map(({count, label}, index) => {
						return (
							<ClayTable.Row key={index}>
								<ClayTable.Cell>{label}</ClayTable.Cell>

								<ClayTable.Cell>
									{roundPercentage(
										getPercentage(count, totalEntries)
									)}
								</ClayTable.Cell>

								<ClayTable.Cell>{count}</ClayTable.Cell>
							</ClayTable.Row>
						);
					})}
			</ClayTable.Body>
		</ClayTable>
	);
}
